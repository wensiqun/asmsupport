package json.generator;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.IF;
import cn.wensiqun.asmsupport.client.MethodBody;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import json.ReflectionUtils;
import json.StringEncoder;
import json.generator.IValueGeneratorChain.GeneratorContext;
import json.parser.AbstractParser;
import json.parser.BaseParser;
import json.parser.CharSequenceParser;

public class JSONPool {

    public ConcurrentMap<Class<?>, AbstractParser> parserMap;
    
    private IValueGeneratorChain header;
    
    public JSONPool() {
        parserMap = new ConcurrentHashMap<Class<?>, AbstractParser>();
        AbstractParser parser = new BaseParser(this);
        parserMap.put(boolean.class, parser);
        parserMap.put(Boolean.class, parser);
        parserMap.put(byte.class, parser);
        parserMap.put(Byte.class, parser);
        parserMap.put(short.class, parser);
        parserMap.put(Short.class, parser);
        parserMap.put(int.class, parser);
        parserMap.put(Integer.class, parser);
        parserMap.put(float.class, parser);
        parserMap.put(Float.class, parser);
        parserMap.put(long.class, parser);
        parserMap.put(Long.class, parser);
        parserMap.put(double.class, parser);
        parserMap.put(Double.class, parser);
        parser = new CharSequenceParser(this);
        parserMap.put(char.class, parser);
        parserMap.put(Character.class, parser);
        parserMap.put(String.class, parser);
        
        header = new BaseGeneratorChain();
        header.setNext(new BeanGeneratorChain())
              .setNext(new BeanGeneratorChain());
    }
    
    public AbstractParser register(Class<?> type, AbstractParser parser) {
        return parserMap.putIfAbsent(type, parser);
    }
    
    public AbstractParser getOrRegister(Class<?> type) {
        AbstractParser parser = parserMap.get(type);
        if(parser == null) {
            synchronized(parserMap) {
                parser = parserMap.get(type);
                if(parser == null) {
                    try {
                        Class<?> target = buildClass(new GeneratorContext(this, header), type);
                        parser = (AbstractParser) target.newInstance();
                        parserMap.put(type, parser);
                    } catch (Exception e) {
                        System.out.println("Error build parser class : " + type + " cause by " + e.getMessage());
                    }
                }
            }
        }
        return parser;
    }
    
    /**
     * 
     * @param val
     * @return
     */
    public String getJson(Object val) {
        if(val == null) {
            return "null";
        }
        Class<?> type = val.getClass();
        
        if(type == char.class || type == Character.class) {
            Character c = (Character) val;
            if(c == '\\') {
                return "\\\\";
            } else if (c == '"') {
                return "\\\"";
            } else {
                return c.toString();
            }
        }
        
        if(type == boolean.class || type == Boolean.class ||
           type == byte.class || type == Byte.class || 
           type == short.class || type == Short.class || 
           type == int.class || type == Integer.class || 
           type == float.class || type == Float.class || 
           type == long.class || type == Long.class ||
           type == double.class || type == Double.class) {
            return val.toString();
        } else {
            return this.getOrRegister(type).parse(val);
        }
    }
    
    /*public void populateJson(StringEncoder encoder, Object val) {
        Class<?> type = val.getClass();
        if (type == boolean.class || type == Boolean.class) {
            encoder.append((Boolean) val);
        } else if (type == byte.class || type == Byte.class) {
            encoder.append((Byte) val);
        } else if (type == short.class || type == Short.class) {
            encoder.append((Short) val);
        } else if (type == char.class || type == Character.class) {
            encoder.append((Character) val);
        } else if (type == int.class || type == Integer.class) {
            encoder.append((Integer) val);
        } else if (type == float.class || type == Float.class) {
            encoder.append((Float) val);
        } else if (type == long.class || type == Long.class) {
            encoder.append((Long) val);
        } else if (type == double.class || type == Double.class) {
            encoder.append((Double) val);
        } else {
            this.getOrRegister(type).parse(encoder, val);
        }
    }*/
    
    Class<?> buildClass(final GeneratorContext context, final Class<?> type) {
        DummyClass dummy = new DummyClass("json." + type.getName() + "_" + UUID.randomUUID().toString())
                           .public_().extends_(AbstractParser.class);
        
        dummy.newMethod("parse").public_().return_(String.class).argTypes(Object.class).argNames("object").body(new MethodBody(){

            @Override
            public void body(LocalVariable... args) {
                return_(call("parse", this_().field("encoder"), args[0]));
            }
            
        });
        

        dummy.newMethod("parse").protected_().return_(String.class).argTypes(StringEncoder.class, Object.class).argNames("encoder", "object").body(new MethodBody(){

            @Override
            public void body(LocalVariable... args) {
                final LocalVariable encoder = args[0];
                call(encoder, "append", val('{'));
                
                Field[] fields = type.getDeclaredFields();
                for(Field f : fields) {
                    final String name = f.getName();
                    final Class<?> type = f.getType();
                    
                    if(ModifierUtils.isStatic(f.getModifiers())) {
                       System.out.println("Warning : field '" + name + "' is static, pass it.");
                       continue;
                    }
                    
                    String getterStr = getGetter(type, name);
                    
                    try {
                       type.getMethod(getterStr);
                    } catch (SecurityException e) {
                        System.out.println("Warning : error parse field '" + name + "' , pass it, cause by " + e.getMessage());
                        continue;
                    } catch (NoSuchMethodException e) {
                        System.out.println("Warning : not found getter method of field '" + name + "', pass it.");
                        continue;
                    }
                    
                    final MethodInvoker getterCall = call(args[0], getterStr);
                    
                    if(type.isPrimitive()) {
                        if(name.matches("^[A-Za-z][_A-Za-z0-9]*$")) {
                            call(encoder, "append", val(name + ":"));
                        } else {
                            call(encoder, "appendDirect", val('\"'));
                            call(encoder, "append", val(name));
                            call(encoder, "appendDirect", val('\"'));
                            call(encoder, "appendDirect", val(':'));
                        }
                        call(encoder, "append", getterCall);
                        call(encoder, "appendDirect", val(','));
                    } else {
                        if_(new IF(ne(getterCall, null_(Object.class))) {

                            @Override
                            public void body() {
                                if(name.matches("^[A-Za-z][_A-Za-z0-9]*$")) {
                                    call(encoder, "append", val(name + ":"));
                                } else {
                                    call(encoder, "appendDirect", val('\"'));
                                    call(encoder, "append", val(name));
                                    call(encoder, "appendDirect", val('\"'));
                                    call(encoder, "appendDirect", val(':'));
                                }
                                header.generate(context, this, encoder, defType(type), getterCall);
                                call(encoder, "appendDirect", val(','));
                            }
                            
                        });
                    }
                }
                call(encoder, "trimLastComma");
                call(encoder, "append", val('}'));
                return_(call(encoder, "toString"));
            }
            
        });
        
        return dummy.build();
    }
    
    private String getGetter(Class<?> type, String name) {
        if(type == boolean.class || 
           type == Boolean.class) {
            return ReflectionUtils.getGetter(name);
        } else {
            return ReflectionUtils.getIsGetter(name);
        }
    }
}
