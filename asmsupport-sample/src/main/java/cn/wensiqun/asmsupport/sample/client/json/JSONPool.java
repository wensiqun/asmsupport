package cn.wensiqun.asmsupport.sample.client.json;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.block.ConstructorBody;
import cn.wensiqun.asmsupport.client.block.IF;
import cn.wensiqun.asmsupport.client.block.MethodBody;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.sample.SampleConstant;
import cn.wensiqun.asmsupport.sample.client.json.generator.IValueGeneratorChain;
import cn.wensiqun.asmsupport.sample.client.json.generator.IValueGeneratorChain.GeneratorContext;
import cn.wensiqun.asmsupport.sample.client.json.generator.impl.*;
import cn.wensiqun.asmsupport.sample.client.json.parser.*;
import cn.wensiqun.asmsupport.sample.client.json.utils.ReflectionUtils;
import cn.wensiqun.asmsupport.sample.client.json.utils.StringEncoder;
import cn.wensiqun.asmsupport.utils.Modifiers;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JSONPool {

    private ConcurrentMap<Class<?>, AbstractParser> parserMap;
    
    private AbstractParser arrayParser = new ArrayParser(this);
    
    private IValueGeneratorChain header;
    
    public JSONPool() {
        parserMap = new ConcurrentHashMap<>();
        
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
        
        parser = new MapParser(this);
        parserMap.put(Map.class, parser);

        parser = new IterableParser(this);
        parserMap.put(Iterable.class, parser);
        
        
        header = new BaseGeneratorChain();
        header.setNext(new ArrayGeneratorChain())
              .setNext(new MapGeneratorChain())
              .setNext(new IterableGeneratorChain())
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
                        parser = (AbstractParser) target.getConstructor(JSONPool.class).newInstance(this);
                        parserMap.put(type, parser);
                    } catch (Exception e) {
                        System.out.println("Error build parser class : " + type + " cause by " + e.getMessage());
                        e.printStackTrace();
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
        if(type.isArray()) {
        	return arrayParser.parse(val);
        }
        if(Iterable.class.isAssignableFrom(type)) {
        	return this.getOrRegister(Iterable.class).parse(val);
        }
        if(Map.class.isAssignableFrom(type)) {
        	return this.getOrRegister(Map.class).parse(val);
        }
        return this.getOrRegister(type).parse(val);
    }
    
    public void getJson(StringEncoder encoder, Object val) {
        if(val == null) {
            encoder.append("null");
        }
        Class<?> type = val.getClass();
        getOrRegister(type).parse(encoder, val);
    }
    
    private Class<?> buildClass(final GeneratorContext context, final Class<?> type) {
        DummyClass dummy = new DummyClass(type.getName() + "Parser")
                           .public_().extends_(AbstractParser.class).setClassOutPutPath(SampleConstant.classOutPutPath);
        
        dummy.newConstructor().public_().argTypes(JSONPool.class).body(new ConstructorBody() {

            @Override
            public void body(LocVar... args) {
                supercall(args);
                return_();
            }
            
        });
        
        dummy.newMethod("parse").public_().argTypes(StringEncoder.class, Object.class).argNames("encoder", "object").body(new MethodBody(){

            @Override
            public void body(LocVar... args) {
                
                final LocVar encoder = args[0];
                final LocVar val = var(type, checkcast(args[1], type));
                
                encoder.call("append", val('{'));
                
                Field[] fields = type.getDeclaredFields();
                for(Field f : fields) {
                    final String name = f.getName();
                    final Class<?> fieldType = f.getType();
                    
                    if(Modifiers.isStatic(f.getModifiers())) {
                       System.out.println("Warning : field '" + name + "' is static, pass it.");
                       continue;
                    }
                    
                    String getterStr = getGetter(fieldType, name);
                    
                    try {
                       type.getMethod(getterStr);
                    } catch (SecurityException e) {
                        System.out.println("Warning : error parse field '" + name + "' , pass it, cause by " + e.getMessage());
                        continue;
                    } catch (NoSuchMethodException e) {
                        System.out.println("Warning : not found getter method of field '" + name + "', pass it.");
                        continue;
                    }
                    
                    final UncertainParam getterCall = val.call(getterStr);
                    
                    if(fieldType.isPrimitive()) {
                        if(name.matches("^[A-Za-z][_A-Za-z0-9]*$")) {
                            encoder.call("append", val(name + ":"));
                        } else {
                            encoder.call("appendDirect", val('\"'));
                            encoder.call("append", val(name));
                            encoder.call("appendDirect", val('\"'));
                            encoder.call("appendDirect", val(':'));
                        }
                        encoder.call("append", getterCall);
                        encoder.call("appendDirect", val(','));
                    } else {
                        if_(new IF(getterCall.ne(null_(Object.class))) {

                            @Override
                            public void body() {
                                if(name.matches("^[A-Za-z][_A-Za-z0-9]*$")) {
                                    encoder.call("append", val(name + ":"));
                                } else {
                                    encoder.call("appendDirect", val('\"'));
                                    encoder.call("append", val(name));
                                    encoder.call("appendDirect", val('\"'));
                                    encoder.call("appendDirect", val(':'));
                                }
                                header.generate(context, this, encoder, getType(fieldType), getterCall);
                                encoder.call("appendDirect", val(','));
                            }
                            
                        });
                    }
                }
                encoder.call("trimLastComma");
                encoder.call("append", val('}'));
                return_();
            }
            
        });
        
        return dummy.build();
    }
    
    private String getGetter(Class<?> type, String name) {
        if(type == boolean.class || 
           type == Boolean.class) {
            return ReflectionUtils.getIsGetter(name);
        } else {
            return ReflectionUtils.getGetter(name);
        }
    }
}
