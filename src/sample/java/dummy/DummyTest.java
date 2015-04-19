package dummy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

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
import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

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
        
        //Generate ExceptedInterface
        DummyInterface excIter = new DummyInterface();
        excIter.setClassOutPutPath(".//target//dummy-generated")
               .public_().package_("dummy").name("ExceptedInterface").setJavaVersion(Opcodes.V1_6);
        excIter.newField(String.class, "interface_field_string").val("ExceptedInterface\n");
        excIter.newField(List.class, "interface_field_list");
        
        excIter.newStaticBlock(new StaticBlockBody(){
            @Override
            public void body() {
                GlobalVariable interface_field_list = getMethodOwner().field("interface_field_list");
                assign(interface_field_list, this.new_(ArrayList.class));
                return_();
            }
        });
        excIter.newMethod("method1").return_(String.class).throws_(IllegalArgumentException.class);
        excIter.newMethod("method2").return_(String.class);
        
        final Class<?> ExceptedInterface = excIter.build();
        
        
        //Generate ExceptedAbstractClass
        DummyClass excAbsCls = new DummyClass("dummy.ExceptedAbstractClass").setClassOutPutPath(".//target//dummy-generated")
                .abstract_().implements_(ExceptedInterface).setJavaVersion(Opcodes.V1_6);
        excAbsCls.newField(String.class, "abstract_class_field_string").protected_();
        excAbsCls.newField(List.class, "abstract_class_field_list").public_().static_();
        excAbsCls.newStaticBlock(new StaticBlockBody() {

            @Override
            public void body() {
                GlobalVariable gv = getMethodOwner().field("abstract_class_field_list");
                assign(gv, new_(ArrayList.class));
                call(gv, "add", Value.value("Hello "));
                call(gv, "add", Value.value("ASMSupport\n"));
                return_();
            }
            
        });
        
        
        excAbsCls.newConstructor().public_().body(new ConstructorBody() {

            @Override
            public void body(LocalVariable... args) {
                supercall();
                assign(this_("abstract_class_field_string"), Value.value("ExceptedAbstractClass\n"));
                return_();
            }
            
        });
        
        excAbsCls.newMethod("appendString1").protected_().argTypes(StringBuilder.class).body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                call(args[0], "append", Value.value("ExceptedAbstractClass#appendString\n"));
                return_();
            }
            
        });
        excAbsCls.newMethod("appendString2")
                 .public_()
                 .abstract_()
                 .argTypes(StringBuilder.class);
        Class<?> ExceptedAbstractClass = excAbsCls.build();
        
        //Crate enum class ExceptedEnum
        DummyEnum excEnum = new DummyEnum("dummy.ExceptedEnum").setClassOutPutPath(".//target//dummy-generated").setJavaVersion(Opcodes.V1_6);
        excEnum.newEnum("ENUM1").newEnum("ENUM2");
        excEnum.newField(String.class, "field");
        excEnum.newConstructor().argTypes(String.class).body(new EnumConstructorBody(){

            @Override
            public void body(LocalVariable... args) {
                GlobalVariable field = this_().field("field");
                assign(field, args[0]);
                return_();
            }
            
        });
        
        excEnum.newStaticBlock(new EnumStaticBlockBody() {

            @Override
            public void body(LocalVariable... args) {
                return_();
            }

            @Override
            public void constructEnumConsts() {
                //init enum constant
                constructEnumConst("ENUM1", Value.value("field1"));
                constructEnumConst("ENUM2", Value.value("field2"));
            }
            
        });
        
        excEnum.newMethod("getEnumName").return_(String.class).public_().body(new MethodBody(){
            
        	@Override
            public void body(LocalVariable... args) {
                return_(stradd(call("name"), Value.value(" - "), this_().field("field")));
            }
        });
        
        final Class<?> ExceptedEnum = excEnum.build();
        
        DummyClass excDummy = new DummyClass("dummy.ExceptedDummy").extends_(ExceptedAbstractClass).setClassOutPutPath(".//target//dummy-generated").setJavaVersion(Opcodes.V1_6);
        
        excDummy.newMethod("method1").public_().return_(String.class).throws_(IllegalArgumentException.class)
                .body(new MethodBody() {

                    @Override
                    public void body(LocalVariable... args) {
                        return_(Value.value("method1\n"));
                    }
                    
                });
        
        excDummy.newMethod("method2").public_().return_(String.class)
        .body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                return_(Value.value("method2\n"));
            }
            
        });


        excDummy.newMethod("appendString2").public_().argTypes(StringBuilder.class).body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                call(args[0], "append", Value.value("DummyMain#appendString2\n"));
                return_();
            }
            
        });
        
        excDummy.newMethod("excepted").public_().static_().return_(String.class).body(new MethodBody() {

            @Override
            public void body(LocalVariable... args) {
                LocalVariable dm = var("dm", getMethodOwner(), new_(getMethodOwner()));
                final LocalVariable sb = var("sb", StringBuilder.class, new_(StringBuilder.class));
                
                AClass ExceptedInterfaceAClass = AClassFactory.getType(ExceptedInterface);
                GlobalVariable interface_field_list = ExceptedInterfaceAClass.field("interface_field_list");
                
                call(interface_field_list, "add", Value.value("hello asmsupport."));
                call(sb, "append", ExceptedInterfaceAClass.field("interface_field_string"));
                for_(new ForEach(interface_field_list, String.class) {

                    @Override
                    public void body(LocalVariable str) {
                        call(sb, "append", str);
                        LocalVariable len = var("len", long.class, call(str, "length"));
                        call(sb, "append", len);
                    }
                    
                });
                
                call(sb, "append", dm.field("abstract_class_field_string"));
                for_(new ForEach(dm.field("abstract_class_field_list")) {

                    @Override
                    public void body(LocalVariable str) {
                        call(sb, "append", str);
                    }
                    
                });
                
                call(sb, "append", call(dm, "method1"));
                call(sb, "append", call(dm, "method2"));
                
                call(dm, "appendString1", sb);
                call(dm, "appendString2", sb);
                
                AClass ExceptedEnumAClass = getType(ExceptedEnum);
                call(call(call(sb, "append", call(ExceptedEnumAClass.field("ENUM1"), "getEnumName")),"append", Value.value("\n")),
                    "append", ExceptedEnumAClass.field("ENUM2")
                );
                LocalVariable testLoc = var(int.class, neg(neg(val(1))));
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
            Assert.assertEquals(ExceptedDummy.excepted(), amsupportTest());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
}
