package jw.asmsupport.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.entity.MethodEntity;
import jw.asmsupport.loader.ASMClassLoader;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

import com.googlecode.jwcommon.StringUtils;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ClassUtils extends com.googlecode.jwcommon.ClassUtils {
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static List<MethodEntity> getAllMethod(Class<?> clazz, final String findName) throws IOException{
		final AClass owner = AClassFactory.getProductClass(clazz);
		InputStream classStream = ASMClassLoader.asmClassLoader.getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
		ClassReader cr = new ClassReader(classStream);
		final List<MethodEntity> list = new ArrayList<MethodEntity>();
		
		cr.accept(new ClassAdapter(new EmptyVisitor()){

			@Override
			public MethodVisitor visitMethod(int access, String name,
					String desc, String signature, String[] exceptions) {
				if((StringUtils.isEmpty(findName) || name.equals(findName))){
					
					if(exceptions == null){
						exceptions = new String[0];
					}
					
					try {
						
						Type[] types = Type.getArgumentTypes(desc);
						AClass[] aclass = new AClass[types.length];
						String[] args = new String[types.length];
						for(int i=0; i<types.length; i++){
							aclass[i] = AClassFactory.getProductClass(forName(types[i].getDescriptor()));
							args[i] = "arg" + i;
						}
						
						AClass returnType = AClassFactory.getProductClass(
								forName(Type.getReturnType(desc).getDescriptor()));
						

						AClass[] exceptionArray = new AClass[exceptions.length];
						for(int i=0; i<exceptions.length; i++){
							exceptionArray[i] = AClassFactory.getProductClass(forName(exceptions[i]));
						}
						
						MethodEntity me = new MethodEntity(
					    		name, owner, owner, aclass, args, returnType, exceptionArray, access);
						
						list.add(me);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				return super.visitMethod(access, name, desc, signature, exceptions);
			}
			
		}, 0);
		
		return list;
	}

}
