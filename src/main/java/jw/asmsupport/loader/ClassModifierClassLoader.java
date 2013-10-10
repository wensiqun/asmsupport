package jw.asmsupport.loader;

import java.io.InputStream;

import jw.asmsupport.asm.proxy.ClassModifierClassAdapter;
import jw.asmsupport.creator.ClassModifier;
import jw.asmsupport.exception.ASMSupportException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ClassModifierClassLoader extends ClassLoader {
	
    private static Log log = LogFactory.getLog(ClassModifierClassLoader.class);
	
	private ClassModifier modifier;
	private String className;
	private byte[] modifiedClassBytes;
	
	public ClassModifierClassLoader(ClassModifier modifier){
		this.modifier = modifier;
		className = modifier.getCurrentClass().getName();
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if (!name.equals(className)) {
			return loader.loadClass(name);
        }
		
		// gets an input stream to read the bytecode of the class
		String resource = name.replace('.', '/') + ".class";
		InputStream is = loader.getResourceAsStream(resource);
		
		// adapts the class on the fly
		try {
			//modify class
			ClassReader cr = new ClassReader(is);
			ClassWriter cw = new ClassWriter(0);
			ClassVisitor cv = new ClassModifierClassAdapter(cw, modifier);
			modifier.setClassWriter(cw);
			cr.accept(cv, 0);
			modifiedClassBytes = cw.toByteArray();
			
		} catch (Exception e) {
			throw new ASMSupportException(e.getMessage(), e);
		}
        return loader.loadClass(name);
	}

	public byte[] getModifiedClassBytes() {
		return modifiedClassBytes;
	}
	
}
