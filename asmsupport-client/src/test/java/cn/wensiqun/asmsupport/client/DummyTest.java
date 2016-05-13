package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.client.block.*;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DummyTest {
    
    //##################################
    // The following class we will  
    // excepted to generated.
    //##################################
    public interface ExceptedInterface {
        
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
    
    public static class ExceptedDummy extends ExceptedAbstractClass {
        
        public static String excepted() {
            
            ExceptedDummy dm = new ExceptedDummy();
            StringBuilder sb = new StringBuilder();
            
            ExceptedInterface.interface_field_list.add("hello asmsupport.");
            sb.append(ExceptedInterface.interface_field_string);
            for(String str : ExceptedInterface.interface_field_list) {
                sb.append(str);
                long len = str.length();
                sb.append(len);
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

    //##################################
    // The following method we will  
    // generate the classes of preceding.
    //##################################
    public String amsupportTest() throws Exception {
        String className = getClass().getName();
        String packageName = className.substring(0, className.lastIndexOf('.'));
        String classOutPutPath = ".//target//asmsupport-test-generated";
        //Generate ExceptedInterface
        DummyInterface excIter = new DummyInterface();
        excIter.setClassOutPutPath(classOutPutPath)
               .public_().package_(packageName).name("ExceptedInterface").setJavaVersion(Opcodes.V1_6);
        excIter.newField(String.class, "interface_field_string").val("ExceptedInterface\n");
        excIter.newField(List.class, "interface_field_list");
        
        excIter.newStaticBlock(new StaticBlockBody(){
            @Override
            public void body() {
                Var interface_field_list = val(getMethodOwner()).field("interface_field_list");
                assign(interface_field_list, this.new_(ArrayList.class));
                return_();
            }
        });
        excIter.newMethod("method1").return_(String.class).throws_(IllegalArgumentException.class);
        excIter.newMethod("method2").return_(String.class);

        final Class<?> ExceptedInterface = excIter.build();
        
        
        //Generate ExceptedAbstractClass
        DummyClass excAbsCls = new DummyClass(packageName + "." + "ExceptedAbstractClass").setClassOutPutPath(classOutPutPath)
                .abstract_().implements_(ExceptedInterface).setJavaVersion(Opcodes.V1_6);
        excAbsCls.newField(String.class, "abstract_class_field_string").protected_();
        excAbsCls.newField(List.class, "abstract_class_field_list").public_().static_();
        excAbsCls.newStaticBlock(new StaticBlockBody() {

            @Override
            public void body() {
                Var gv = val(getMethodOwner()).field("abstract_class_field_list");
                assign(gv, new_(ArrayList.class));
                call(gv, "add", val("Hello "));
                call(gv, "add", val("ASMSupport\n"));
                return_();
            }
            
        });
        
        
        excAbsCls.newConstructor().public_().body(new ConstructorBody() {

            @Override
            public void body(LocVar... args) {
                supercall();
                assign(this_("abstract_class_field_string"), val("ExceptedAbstractClass\n"));
                return_();
            }
            
        });
        
        excAbsCls.newMethod("appendString1").protected_().argTypes(StringBuilder.class).body(new MethodBody() {

            @Override
            public void body(LocVar... args) {
                call(args[0], "append", val("ExceptedAbstractClass#appendString\n"));
                return_();
            }
            
        });
        excAbsCls.newMethod("appendString2")
                 .public_()
                 .abstract_()
                 .argTypes(StringBuilder.class);
        Class<?> ExceptedAbstractClass = excAbsCls.build();
        
        //Crate enum class ExceptedEnum
        DummyEnum excEnum = new DummyEnum(packageName + "ExceptedEnum").setClassOutPutPath(classOutPutPath).setJavaVersion(Opcodes.V1_6);
        excEnum.newEnum("ENUM1").newEnum("ENUM2");
        excEnum.newField(String.class, "field");
        excEnum.newConstructor().argTypes(String.class).body(new EnumConstructorBody(){

            @Override
            public void body(LocVar... args) {
                Var field = this_().field("field");
                assign(field, args[0]);
                return_();
            }
            
        });
        
        excEnum.newStaticBlock(new EnumStaticBlockBody() {

            @Override
            public void body(LocVar... args) {
                return_();
            }

            @Override
            public void constructEnumConsts() {
                //init enum constant
                constructEnumConst("ENUM1", val("field1"));
                constructEnumConst("ENUM2", val("field2"));
            }
            
        });
        
        excEnum.newMethod("getEnumName").return_(String.class).public_().body(new MethodBody(){
            @Override
            public void body(LocVar... args) {
                return_(stradd(call("name"), val(" - "), this_().field("field")));
            }
        });
        
        final Class<?> ExceptedEnum = excEnum.build();
        
        DummyClass excDummy = new DummyClass(packageName + "." + "ExceptedDummy").extends_(ExceptedAbstractClass).setClassOutPutPath(classOutPutPath).setJavaVersion(Opcodes.V1_6);
        
        excDummy.newMethod("method1").public_().return_(String.class).throws_(IllegalArgumentException.class)
                .body(new MethodBody() {

                    @Override
                    public void body(LocVar... args) {
                        return_(val("method1\n"));
                    }
                    
                });
        
        excDummy.newMethod("method2").public_().return_(String.class)
        .body(new MethodBody() {

            @Override
            public void body(LocVar... args) {
                return_(val("method2\n"));
            }
            
        });


        excDummy.newMethod("appendString2").public_().argTypes(StringBuilder.class).body(new MethodBody() {

            @Override
            public void body(LocVar... args) {
                call(args[0], "append", val("DummyMain#appendString2\n"));
                return_();
            }
            
        });
        
        excDummy.newMethod("excepted").public_().static_().return_(String.class).body(new MethodBody() {

            @Override
            public void body(LocVar... args) {
                LocVar dm = var("dm", getMethodOwner(), new_(getMethodOwner()));
                final LocVar sb = var("sb", StringBuilder.class, new_(StringBuilder.class));
                
                IClass ExceptedInterfaceAClass = getType(ExceptedInterface);
                FieldVar interface_field_list = val(ExceptedInterfaceAClass).field("interface_field_list");
                
                call(interface_field_list, "add", val("hello asmsupport."));
                call(sb, "append", val(ExceptedInterfaceAClass).field("interface_field_string"));
                for_(new ForEach(interface_field_list, getType(String.class)) {

                    @Override
                    public void body(LocVar str) {
                        call(sb, "append", str);
                        LocVar len = var("len", long.class, call(str, "length"));
                        call(sb, "append", len);
                    }
                    
                });
                
                call(sb, "append", dm.field("abstract_class_field_string"));
                for_(new ForEach(dm.field("abstract_class_field_list")) {

                    @Override
                    public void body(LocVar str) {
                        call(sb, "append", str);
                    }
                    
                });
                
                call(sb, "append", call(dm, "method1"));
                call(sb, "append", call(dm, "method2"));
                
                call(dm, "appendString1", sb);
                call(dm, "appendString2", sb);
                
                DummyParam ExceptedEnumAClass = val(getType(ExceptedEnum));
                sb.call("append", ExceptedEnumAClass.field("ENUM1").call("getEnumName"))
                .call("append", val("\n"))
                .call("append", ExceptedEnumAClass.field("ENUM2"));
                return_(call(sb, "toString"));
            }
            
        });
        Class<?> ExceptedDummyCls = excDummy.build();
        
        Method exceptedMethod = ExceptedDummyCls.getMethod("excepted");
        
        return (String) exceptedMethod.invoke(ExceptedDummyCls);
    }
    
    @Test
    public void test() {
        try {
        	String actual = amsupportTest();
            Assert.assertEquals(ExceptedDummy.excepted(), actual);
        } catch (Exception e) {
        	e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
    
    public static void main(String... a) {
        new DummyTest().test();
        //System.out.println(ExceptedInterface.class.getName());
    }
    
}
