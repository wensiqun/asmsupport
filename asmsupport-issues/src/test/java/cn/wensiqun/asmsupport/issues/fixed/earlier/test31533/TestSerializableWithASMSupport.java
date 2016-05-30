package cn.wensiqun.asmsupport.issues.fixed.earlier.test31533;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.build.resolver.ClassResolver;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.issues.IssuesConstant;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;

public class TestSerializableWithASMSupport {
   
    public static void main(String[] args) throws Exception{

    	ASMSupportClassLoader classloader = CachedThreadLocalClassLoader.getInstance();

		ClassResolver creator = new ClassResolver(Opcodes.V1_5,
				Opcodes.ACC_PUBLIC, "bug.fixed.Test31533", null,
				new IClass[] { classloader.getType(Serializable.class) },
				classloader);

		creator.createField("name", Opcodes.ACC_PRIVATE,
				classloader.getType(String.class));
		
    	creator.createMethod(Opcodes.ACC_PUBLIC, "setName", 
    			new IClass[]{creator.getClassLoader().getType(String.class)}, new String[]{"name"}, 
    			null, null, new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						assign(this_().field("name"), argus[0]);
						return_();
					}
    		
    	});
    	

    	creator.createMethod( Opcodes.ACC_PUBLIC, "getName", 
    			null, null, 
    			creator.getClassLoader().getType(String.class), null,new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						return_(this_().field("name"));
					}
    		
    	});
    	

    	creator.createMethod(Opcodes.ACC_PUBLIC, "toString", 
    			null, null, 
    			creator.getClassLoader().getType(String.class), null, new KernelMethodBody(){

					@Override
					public void body(LocalVariable... argus) {
						return_(stradd(val("User [name="), this_().field("name")));
					}
    		
    	});
    	
		creator.setClassOutputPath(IssuesConstant.classOutPutPath);
		Class<?> cls = creator.resolve();
		
		
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
