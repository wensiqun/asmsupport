package cn.wensiqun.asmsupport.issues.fixed.earlier.test31533;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.issues.IssuesConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class TestSerializableWithASMSupport {
   
    public static void main(String[] args) throws Exception{

    	ClassCreator creator = 
				new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "bug.fixed.Test31533", null, new Class<?>[]{Serializable.class});
        
    	creator.createField("name", Opcodes.ACC_PRIVATE, AClassFactory.getType(String.class));
		
    	creator.createMethod(Opcodes.ACC_PUBLIC, "setName", 
    			new AClass[]{AClassFactory.getType(String.class)}, new String[]{"name"}, 
    			null, null, new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						assign(this_().field("name"), argus[0]);
						return_();
					}
    		
    	});
    	

    	creator.createMethod( Opcodes.ACC_PUBLIC, "getName", 
    			null, null, 
    			AClassFactory.getType(String.class), null,new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						return_(this_().field("name"));
					}
    		
    	});
    	

    	creator.createMethod(Opcodes.ACC_PUBLIC, "toString", 
    			null, null, 
    			AClassFactory.getType(String.class), null, new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						return_(stradd(Value.value("User [name="), this_().field("name")));
					}
    		
    	});
    	
		creator.setClassOutPutPath(IssuesConstant.classOutPutPath);
		Class<?> cls = creator.startup();
		
		
    	Object user = cls.newInstance();
        Method m = cls.getMethod("setName", new Class[]{String.class});
    	m.invoke(user, "asmsupport");
        
       /*****************序列化***************/
       Utils.writeObject(user);
       System.out.println("before:" + user);
       
       /*****************反序列化***************/

       InputStream is = new FileInputStream(new File(".//target//bug.fixed.Test31533_tmp"));
	   CustomObjectInputStream ois = new CustomObjectInputStream(is);
       Object user2 = ois.readObject();
       System.out.println("after:" + user2);

    }
    
    @org.junit.Test
	public void test() throws Exception{
		main(null);
	}
}
