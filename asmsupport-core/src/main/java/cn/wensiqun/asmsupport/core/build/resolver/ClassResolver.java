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
package cn.wensiqun.asmsupport.core.build.resolver;


import cn.wensiqun.asmsupport.core.block.method.clinit.KernelStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.init.KernelConstructorBody;
import cn.wensiqun.asmsupport.core.build.FieldBuilder;
import cn.wensiqun.asmsupport.core.build.MethodBuilder;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;


/**
 * 
 * 
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class ClassResolver extends ClassBuildResolver {
	
	public ClassResolver(int version, int access, String name,
						 IClass superCls, IClass[] itfs) {
        super(version, access, name, superCls, itfs, CachedThreadLocalClassLoader.getInstance());
    }
	
	
    public ClassResolver(int version, int access, String name,
						 IClass superCls, IClass[] itfs, ASMSupportClassLoader classLoader) {
        super(version, access, name, superCls, itfs, classLoader);
    }
    

    /**
     * 
     * Create a field with null value.
     * 
     * @param name            the field name
     * @param modifiers       the field modifiers
     * @param type      the field type
     * @return
     */
    public FieldBuilder createField(String name, int modifiers, IClass type) {
        return createField(name, modifiers, type, null);
    }
    
    /**
     * Create a field with special value
     *
     * @param name
     * @param modifiers
     * @param type
     * @param initVal
	 * @see #createFieldInternal(String, int, IClass, Object)
     */
    public FieldBuilder createField(String name, int modifiers, IClass type, Object initVal) {
        return createFieldInternal(name, modifiers, type, initVal);
    }

    /**
     * 
     * Create a constructor.
     * 
     * @param access
     * @param argTypes
     * @param argNames
     * @param body
     * @return
     */
	public MethodBuilder createConstructor(int access, IClass[] argTypes, String[] argNames, IClass[] exceptions, KernelConstructorBody body) {
		return createConstructorInternal(access, argTypes, argNames, exceptions, body);
	}

	/**
     * Create a method 
     * 
     * @param access          the method modifiers
     * @param name            the method name
     * @param argTypes        the method argument type list
     * @param argNames        the method arguments name list
     * @param returnClass     the method return class
     * @param exceptions      throw exceptions for this method
     * @param body            method body that is method logic implementation
     * @return
     */
	public MethodBuilder createMethod(int access, String name, IClass[] argTypes, String[] argNames,
									  IClass returnClass, IClass[] exceptions, KernelMethodBody body) {
        return createMethodInternal(name, argTypes, argNames, returnClass, exceptions, access, body);
	}

	/**
	 * Create static block.
	 * 
	 * @param block
	 * @return
	 */
	public MethodBuilder createStaticBlock(KernelStaticBlockBody block) {
		return createStaticBlockInternal(block);
	}

    @Override
    protected void createDefaultConstructor() {
        createConstructor(Opcodes.ACC_PUBLIC, null, null, null, new KernelConstructorBody() {
            @Override
            public void body(LocalVariable... argus) {
                supercall();
                return_();
            }
            
        });
    }
}
