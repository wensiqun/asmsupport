package dummy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.ConstructorBody;
import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.DummyEnum;
import cn.wensiqun.asmsupport.client.DummyInterface;
import cn.wensiqun.asmsupport.client.EnumConstructorBody;
import cn.wensiqun.asmsupport.client.EnumStaticBlockBody;
import cn.wensiqun.asmsupport.client.ForEach;
import cn.wensiqun.asmsupport.client.MethodBody;
import cn.wensiqun.asmsupport.client.StaticBlockBody;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;

public class DummyTest {
    
    //##################################
    // The following class we will  
    // excepted to generated.
    //##################################
    public static interface ExceptedInterface {
        
        String interface_field_string = "ExceptedInterface\n";
        
        List<String> interface_field_list = new ArrayList<String>();
        
        String method1() throws IllegalArgumentException;
        
        String method2();
    }

    static abstract class ExceptedAbstractClass implements ExceptedInterface {
        
        protected String abstract_class_field_string = "ExceptedAbstractClass\n";
        
        public static List<String> abstract_class_field_list;
        
        static {
            
            abstract_class_field_list = new ArrayList<String>();
            abstract_class_field_list.add("Hello ");
            abstract_class_field_list.add("ASMSupport\n");
            
        }
        
        protected void appendString1(StringBuilder sb) {
            sb.append("ExceptedAbstractClass#appendString\n");
        }
        
        public abstract void appendString2(StringBuilder sb);
        
    }

    enum ExceptedEnum {
        
        ENUM1("field1"), ENUM2("field2");
        
        private String field;
        
        private ExceptedEnum(String field) {
            this.field = field;
        }
        
        public String getEnumName() {
            return name() + " - " + field;
        }
    }

    public String amsupportTest() throws Exception {
        
        //Generate ExceptedInterface
        DummyInterface excIter = new DummyInterface();
        excIter._classOutPutPath(".//target//dummy-generated");
        excIter._public()._package("dummy")._name("ExceptedInterface")
                  .newField(String.class, "interface_field_string")
                  .newField(List.class, "interface_field_list");
        excIter.newStaticBlock(new StaticBlockBody(){
            @Override
            public void body() {
                GlobalVariable interface_field_string = getMethodOwner().getGlobalVariable("interface_field_string");
                _assign(interface_field_string, Value.value("ExceptedInterface\n"));
                
                GlobalVariable interface_field_list = getMethodOwner().getGlobalVariable("interface_field_list");
                _assign(interface_field_list, this._new(ArrayList.class));
                _return();
            }
        });
        excIter.newMethod("method1")._return(String.class)._throws(IllegalArgumentException.class);
        excIter.newMethod("method2")._return(String.class);
        
        final Class<?> ExceptedInterface = excIter.build();
        
        
        //Generate ExceptedAbstractClass
        DummyClass excAbsCls = new DummyClass("dummy.ExceptedAbstractClass")._abstract()._implements(ExceptedInterface);
        excAbsCls.newField(String.class, "abstract_class_field_string")._protected();
        excAbsCls.newField(List.class, "abstract_class_field_list")._public()._static();
        excAbsCls.newStaticBlock(new StaticBlockBody() {

            @Override
            public void body() {
                GlobalVariable gv = getMethodOwner().getGlobalVariable("abstract_class_field_list");
                _assign(gv, _new(ArrayList.class));
                _invoke(gv, "add", Value.value("Hello "));
                _invoke(gv, "add", Value.value("ASMSupport\n"));
                _return();
            }
            
        });
        
        
        excAbsCls.newConstructor()._public()._body(new ConstructorBody() {

            @Override
            public void body(LocalVariable... args) {
                _supercall();
                _assign(_this("abstract_class_field_string"), Value.value("ExceptedAbstractClass\n"));
                _return();
            }
            
        });
        
        excAbsCls.newMethod("appendString1")._protected()._argumentTypes(StringBuilder.class)._body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                _invoke(args[0], "append", Value.value("ExceptedAbstractClass#appendString\n"));
                _return();
            }
            
        });
        excAbsCls.newMethod("appendString2")
                 ._public()
                 ._abstract()
                 ._argumentTypes(StringBuilder.class);
        @SuppressWarnings("unused")
        Class<?> ExceptedAbstractClass = excAbsCls.build();
        
        //Crate enum class ExceptedEnum
        DummyEnum excEnum = new DummyEnum("dummy.ExceptedEnum");
        excEnum.newEnum("ENUM1").newEnum("ENUM2");
        excEnum.newField(String.class, "field");
        excEnum.newConstructor()._argumentTypes(String.class)._body(new EnumConstructorBody(){

            @Override
            public void body(LocalVariable... args) {
                GlobalVariable field = _this().getGlobalVariable("field");
                _assign(field, args[0]);
                _return();
            }
            
        });
        
        excEnum.newStaticBlock(new EnumStaticBlockBody() {

            @Override
            public void body(LocalVariable... args) {
                _return();
            }

            @Override
            public void constructEnumConsts() {
                //init enum constant
                constructEnumConst("ENUM1", Value.value("field1"));
                constructEnumConst("ENUM2", Value.value("field2"));
            }
            
        });
        
        excEnum.newMethod("getEnumName")._public()._body(new MethodBody(){
            @Override
            public void body(LocalVariable... args) {
                _return(_append(_invoke("name"), Value.value(" - "), _this().getGlobalVariable("field")));
            }
        });
        
        final Class<?> ExceptedEnum = excEnum.build();
        
        DummyClass excDummy = new DummyClass("dummy.ExceptedDummy")._extends(ExceptedAbstractClass);
        
        excDummy.newMethod("method1")._public()._return(String.class)._throws(IllegalArgumentException.class)
                ._body(new MethodBody() {

                    @Override
                    public void body(LocalVariable... args) {
                        _return(Value.value("method1\n"));
                    }
                    
                });
        
        excDummy.newMethod("method2")._public()._return(String.class)
        ._body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                _return(Value.value("method2\n"));
            }
            
        });


        excDummy.newMethod("appendString2")._public()._argumentTypes(StringBuilder.class)._body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                _invoke(args[0], "append", Value.value("DummyMain#appendString2\n"));
                _return();
            }
            
        });
        
        excDummy.newMethod("excepted")._public()._static()._return(String.class)._body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                LocalVariable dm = _createVariable("dm", getMethodOwner(), _new(getMethodOwner()));
                final LocalVariable sb = _createVariable("sb", StringBuilder.class, _new(StringBuilder.class));
                
                AClass ExceptedInterfaceAClass = AClassFactory.getProductClass(ExceptedInterface);
                GlobalVariable interface_field_list = ExceptedInterfaceAClass.getGlobalVariable("interface_field_list");
                
                _invoke(interface_field_list, "add", Value.value("hello asmsupport."));
                _invoke(sb, "append", interface_field_list);
                _for(new ForEach(interface_field_list) {

                    @Override
                    public void body(LocalVariable str) {
                        _invoke(sb, "append", str);
                    }
                    
                });
                
                _invoke(sb, "append", dm.getGlobalVariable("abstract_class_field_string"));
                _for(new ForEach(dm.getGlobalVariable("abstract_class_field_string")) {

                    @Override
                    public void body(LocalVariable str) {
                        _invoke(sb, "append", str);
                    }
                    
                });
                
                _invoke(sb, "append", _invoke(dm, "method1"));
                _invoke(sb, "append", _invoke(dm, "method2"));
                
                _invoke(dm, "appendString1", sb);
                _invoke(dm, "appendString2", sb);
                
                AClass ExceptedEnumAClass = AClassFactory.getProductClass(ExceptedEnum);
                _invoke(
                    _invoke(
                        _invoke(sb, "append", _invoke(ExceptedEnumAClass.getGlobalVariable("ENUM1"), "getEnumName")),
                        "append", 
                        Value.value("\n")),
                    "append", ExceptedEnumAClass.getGlobalVariable("ENUM2")
                );
                
                _return();
            }
            
        });
        Class<?> ExceptedDummyCls = excDummy.build();
        
        Method exceptedMethod = ExceptedDummyCls.getMethod("excepted");
        
        return (String) exceptedMethod.invoke(ExceptedDummyCls);
        //return "";
    }
    
    public static class ExceptedDummy extends ExceptedAbstractClass {
        
        public static String excepted() {
            
            ExceptedDummy dm = new ExceptedDummy();
            StringBuilder sb = new StringBuilder("Start===");
            
            ExceptedInterface.interface_field_list.add("hello asmsupport.");
            sb.append(ExceptedInterface.interface_field_string);
            for(String str : ExceptedInterface.interface_field_list) {
                sb.append(str);
            }
            
            sb.append(dm.abstract_class_field_string);
            for(String str : ExceptedAbstractClass.abstract_class_field_list) {
                sb.append(str);
            }
            
            sb.append(dm.method1());
            sb.append(dm.method2());
            
            dm.appendString1(sb);
            dm.appendString2(sb);
         
            sb.append(ExceptedEnum.ENUM1.getEnumName()).append("\n").append(ExceptedEnum.ENUM2);
            
            return sb.toString();
        }

        @Override
        public String method1() throws IllegalArgumentException {
            return "method1\n";
        }

        @Override
        public String method2() {
            return "method2\n";
        }

        @Override
        public void appendString2(StringBuilder sb) {
            sb.append("DummyMain#appendString2\n");
        }
        
    }
    
    @Test
    public void test() {
        /*try {
            System.out.println(amsupportTest());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //System.out.println(ExceptedDummy.excepted());
    }
    
}
