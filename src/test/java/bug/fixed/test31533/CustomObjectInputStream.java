package bug.fixed.test31533;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import cn.wensiqun.asmsupport.core.loader.ASMClassLoader;

public class CustomObjectInputStream extends ObjectInputStream {

	protected CustomObjectInputStream() throws IOException, SecurityException {
		super();
	}

	public CustomObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
		try{
			String name = desc.getName();
			return ASMClassLoader.getInstance().loadClass(name);
		}catch(Exception e){
			return super.resolveClass(desc);
		}
	}

	
}
