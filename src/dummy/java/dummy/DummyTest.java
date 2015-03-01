package dummy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.DummyClass;
import cn.wensiqun.asmsupport.client.DummyInterface;
import cn.wensiqun.asmsupport.client.StaticBlockBody;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;

public class DummyTest {

    public String amsupportTest() {
        
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
            }
        });
        excIter.newMethod()._returnType(String.class)._name("method1")._throws(IllegalArgumentException.class);
        excIter.newMethod()._returnType(String.class)._name("method2");
        
        Class<?> exceptedInterface = excIter.build();
        
        
        DummyClass excAbsCls = new DummyClass();
        excAbsCls._package("dummy")
            ._name("ExceptedAbstractClass")._implements(exceptedInterface);
        
        return "";
    }
    
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
        amsupportTest();
        //System.out.println(ExceptedDummy.excepted());
    }
    
}
