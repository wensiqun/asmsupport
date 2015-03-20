/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.wensiqun.asmsupport.core.utils.lang.StringUtils;
import cn.wensiqun.asmsupport.org.apache.commons.collections.CollectionUtils;


/**
 * 
 * <h3>Release notes</h3>
 * <ul>
 * <li><b>version 0.4</b> : Thanks for "aruanruan at vip.sina.com"(oschina account "aruan") suggestion. for detail see <a href="http://code.taobao.org/p/asmsupport/issue/31553/">http://code.taobao.org/p/asmsupport/issue/31553/</a> </li>
 * </ul>
 * 
 * @author wensiqun(at)163.com, aruanruan(at)vip.sina.com
 * @version 0.4
 * 
 */
public class ASMClassLoader extends ClassLoader {
	
	private static Map<Key, byte[]> classByteMap = new ConcurrentHashMap<Key, byte[]>();
	
	private ASMClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	public static ASMClassLoader getInstance() {
		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
		if(currentClassLoader != null && currentClassLoader instanceof ASMClassLoader)
		{
			return (ASMClassLoader) currentClassLoader;
		}else{
			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
			if(systemClassLoader != null){
				return getInstance(systemClassLoader);
			}else{
				return getInstance(currentClassLoader);
			}
		}
	}

	public static ASMClassLoader getInstance(ClassLoader parent){
		return new ASMClassLoader(parent);
	}
	
	public final Class<?> defineClass(String name, byte[] b) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

        Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] {
                String.class, byte[].class, int.class, int.class });
        method.setAccessible(true);
        Object[] args = new Object[] { name, b, new Integer(0),
                new Integer(b.length) };
        
        ClassLoader loader = getParent();
        String resource = name.replace('.', '/') + ".class";
		if(loader.getResource(resource) != null)
		{
		    loader = this;
		}
		Class<?> clazz = (Class<?>) method.invoke(loader, args);
		classByteMap.put(new Key(clazz), b);
		return clazz;
	}
	

	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		Set<Key> keySet = classByteMap.keySet();
		if(CollectionUtils.isNotEmpty(keySet)){
			for(Key k : keySet){
				if(k.equals(new Key(translateClassName(name)))){
					return k.clazz;
				}
			}
		}
		return super.findClass(name);
	}
	
	
	/**
	 * format like : a/b/c/D.class
	 */
	@Override
	public InputStream getResourceAsStream(String name) {
		byte[] byteArray = classByteMap.get(new Key(name));
		InputStream stream = null;
		if(byteArray != null){
			stream = new ByteArrayInputStream(byteArray);
		}
		
		if(stream == null){
			stream = super.getResourceAsStream(name);
		}
		
		if (stream == null) {
			stream = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
		}
		return stream;
	}
	
	/**
	 * e.g : "<b>jw.asmsupport.loader.ASMClassLoader<b>" convert to "<b>jw/asmsupport/loader/ASMClassLoader.class<b>"
	 * 
	 * @param classQualifiedName
	 * @return
	 */
	private String translateClassName(String classQualifiedName){
		return classQualifiedName.replace('.', '/') + ".class";
	}

	private class Key {
		
		private Class<?> clazz;

		/**
		 * format like :  a/b/c/D.class
		 */
		private String name;

		private Key(Class<?> clazz) {
			this.clazz = clazz;
		}

		/**
		 * name format like :  a/b/c/D.class
		 * @param name
		 */
		private Key(String name) {
			this.name = name;
		}
		
		@Override
		public int hashCode() {
			if(clazz != null){
				return translateClassName(clazz.getName()).hashCode();
			}else{
				return name.hashCode();
			}
		}

		@Override
		public boolean equals(Object obj) {
			if(obj != null && obj instanceof Key){
			    Key k = (Key) obj;
			    String currentName = clazz == null ? name : translateClassName(clazz.getName());
			    String compareName = k.clazz == null ? k.name : translateClassName(k.clazz.getName());
			    if(StringUtils.equals(currentName, compareName)){
			    	return true;
			    }
			}
			return false;
		}
	}
	
	
}
