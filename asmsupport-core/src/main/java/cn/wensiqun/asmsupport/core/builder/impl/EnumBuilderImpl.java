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
package cn.wensiqun.asmsupport.core.builder.impl;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.block.method.clinit.KernelEnumStaticBlockBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.block.method.common.KernelStaticMethodBody;
import cn.wensiqun.asmsupport.core.block.method.init.KernelEnumConstructorBody;
import cn.wensiqun.asmsupport.core.builder.IFieldBuilder;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.operator.array.KernelArrayLength;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.AsmsupportClassLoader;
import cn.wensiqun.asmsupport.utils.AsmsupportConstant;
import cn.wensiqun.asmsupport.utils.collections.CollectionUtils;
import cn.wensiqun.asmsupport.utils.lang.ArrayUtils;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class EnumBuilderImpl extends AbstractClassCreator {

    private boolean existEnumConstant;

    private boolean existField;

    private boolean existNoArgumentsConstructor;

    List<String> enumConstantNameList;


    /**
     * 
     * @param version
     * @param name
     * @param itfs
     */
    public EnumBuilderImpl(int version, String name, IClass[] itfs) {
        this(version, name, itfs, CachedThreadLocalClassLoader.getInstance());
    }
    
    /**
     * 
     * @param version
     * @param name
     * @param itfs
     * @param asmsupportClassLoader
     */
    public EnumBuilderImpl(int version, String name, IClass[] itfs, AsmsupportClassLoader asmsupportClassLoader) {
        super(version, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_SUPER + Opcodes.ACC_ENUM, name, 
        		asmsupportClassLoader.getType(Enum.class),
                itfs, asmsupportClassLoader);
        enumConstantNameList = new ArrayList<String>();
    }

    /**
     * 
     * Create a field with null value.
     * 
     * @param name
     *            the field name
     * @param modifiers
     *            the field modifiers
     * @param type
     *            the field type
     * @return
     */
    public IFieldBuilder createField(String name, int modifiers, IClass type) {
        return createField(name, modifiers, type, null);
    }

    /**
     * Create a field with special value.
     * 
     * @param name
     * @param modifiers
     * @param type
     * @param value
     *            The initial value, this value is only support static field,
     *            otherwise will ignored.This parameter, which may be null if
     *            the field does not have an initial value, must be an Integer,
     *            a Float, a Long, a Double or a String (for int, float, long or
     *            String fields respectively). This parameter is only used for
     *            static fields. Its value is ignored for non static fields,
     *            which must be initialized through bytecode instructions in
     *            constructors or methods.
     * @return
     */
    public IFieldBuilder createField(String name, int modifiers, IClass type, Object value) {
        if (!existEnumConstant) {
            throw new ASMSupportException("first field must be an enum object of current enum type " + sc.getName());
        }
        FieldBuildImpl fc = new FieldBuildImpl(name, modifiers, type, value);
        fieldCreators.add(fc);
        existField = !ModifierUtils.isEnum(modifiers);
        return fc;
    }

    /**
     * 
     * @param name
     * @return
     */
    public void createEnumConstant(String name) {
        if (existField) {
            throw new ASMSupportException("declare enum constant must before other field");
        }
        existEnumConstant = true;
        sc.setEnumNum(sc.getEnumNum() + 1);
        enumConstantNameList.add(name);
        createField(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL + Opcodes.ACC_ENUM, sc);
    }

    /**
     * 
     * @param name
     * @param argClasses
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     */
    public final void createMethodForDummy(String name, IClass[] argClasses, String[] argNames, IClass returnClass,
            IClass[] exceptions, int access, KernelMethodBody mb) {
        methodCreaters.add(MethodBuilderImpl.methodCreatorForAdd(name, argClasses, argNames, returnClass, exceptions,
                access, mb));
    }

    /**
     * 
     * @param name
     * @param arguments
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     * @return
     */
    public final void createMethod(String name, IClass[] argClasses, String[] argNames, IClass returnClass,
            IClass[] exceptions, int access, KernelMethodBody mb) {
        if ((access & Opcodes.ACC_STATIC) != 0) {
            access -= Opcodes.ACC_STATIC;
        }
        methodCreaters.add(MethodBuilderImpl.methodCreatorForAdd(name, argClasses, argNames, returnClass, exceptions,
                access, mb));
    }

    /**
     * 
     * @param name
     * @param argClasses
     * @param argNames
     * @param returnClass
     * @param exceptions
     * @param access
     * @param mb
     * @return
     */
    public void createStaticMethod(String name, IClass[] argClasses, String[] argNames, IClass returnClass,
            IClass[] exceptions, int access, KernelStaticMethodBody mb) {
        if ((access & Opcodes.ACC_STATIC) == 0) {
            access += Opcodes.ACC_STATIC;
        }
        methodCreaters.add(MethodBuilderImpl.methodCreatorForAdd(name, argClasses, argNames, returnClass, exceptions,
                access, mb));
    }

    /**
     * create constructor;
     * 
     * @param arguments
     * @param argNames
     * @param mb
     * @param access
     */
    public void createConstructor(IClass[] argClasses, String[] argNames, KernelEnumConstructorBody mb) {

        if (ArrayUtils.isNotEmpty(argClasses) && ArrayUtils.isEmpty(argNames)) {
            argNames = new String[argClasses.length];
            for (int i = 0; i < argNames.length; i++) {
                argNames[i] = "arg" + i;
            }
        }

        if (argNames == null) {
            argNames = new String[0];
        }
        if (argClasses == null) {
            argClasses = new IClass[0];
        }

        if (argNames.length != argClasses.length) {
            throw new IllegalArgumentException("Different arugment class number and argument name number");
        }

        if (argNames.length == 0) {
            existNoArgumentsConstructor = true;
        }

        String[] enumArgNames = new String[argNames.length + 2];
        enumArgNames[0] = "name";
        enumArgNames[1] = "ordinal";
        System.arraycopy(argNames, 0, enumArgNames, 2, argNames.length);

        IClass[] enumArgClasses = new IClass[argClasses.length + 2];
        enumArgClasses[0] = asmsupportClassLoader.getType(String.class);
        enumArgClasses[1] = asmsupportClassLoader.getType(int.class);
        System.arraycopy(argClasses, 0, enumArgClasses, 2, argClasses.length);

        methodCreaters.add(MethodBuilderImpl.methodCreatorForAdd(AsmsupportConstant.INIT, enumArgClasses, enumArgNames, null, null,
                Opcodes.ACC_PRIVATE, mb));
        haveInitMethod = true;
    }

    /**
     * 
     * @param mb
     */
    public void createStaticBlock(final KernelEnumStaticBlockBody body) {
        checkStaticBlock();
        body.setEnumNameList(enumConstantNameList);
        if (existEnumConstant) {
            // create implicit global variable ENUM$VALUES for enum type
            createField("ENUM$VALUES", Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC
                    + Opcodes.ACC_SYNTHETIC, asmsupportClassLoader.getArrayType(sc, 1));

        }
        existedStaticBlock = true;
        methodCreaters.add(0,
                MethodBuilderImpl.methodCreatorForAdd(AsmsupportConstant.CLINIT, null, null, null, null, Opcodes.ACC_STATIC, body));
    }

    @Override
    protected void createDefaultConstructor() {
        createConstructor(null, null, new KernelEnumConstructorBody() {
            @Override
            public void body(LocalVariable... argus) {
                return_();
            }
        });
        createDefaultStaticBlock();
    }

    @Override
    protected void beforeCreate() {

        final IClass enumArrayType = asmsupportClassLoader.getArrayType(sc, 1);

        // create "publis static Enum[] values()" method
        createStaticMethod("values", null, null, enumArrayType, null, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                new KernelStaticMethodBody() {

                    @Override
                    public void body(LocalVariable... argus) {
                        IClass owner = getMethodDeclaringClass();
                        // get ENUM$VALUES
                        GlobalVariable values = val(owner).field("ENUM$VALUES");

                        // get length operator
                        KernelArrayLength al = arrayLength(values);
                        LocalVariable copy = var(enumArrayType, makeArray(enumArrayType, al));//arrayvar2dim("", enumArrayType, true, al);

                        // get lengt operator for tmpValues;
                        KernelParam copyLen = arrayLength(copy);

                        // System
                        IClass systemClass = asmsupportClassLoader.getType(System.class);

                        // zero value
                        Value zero = val(0);

                        // call arraycopy
                        call(systemClass, "arraycopy", values, zero, copy, zero, copyLen);

                        return_(copy);
                    }

                });

        // create "public static Enum valueOf(java.lang.String)" method
        this.createStaticMethod("valueOf", new IClass[] { asmsupportClassLoader.getType(String.class) }, new String[] { "name" }, sc, null,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, new KernelStaticMethodBody() {

                    @Override
                    public void body(LocalVariable... argus) {
                        LocalVariable name = argus[0];
                        IClass owner = getMethodDeclaringClass();
                        return_(checkcast(call(owner, "valueOf", val(owner), name), owner));
                    }

                });

    }

    private void createDefaultStaticBlock() {
        if (!existedStaticBlock && !CollectionUtils.isEmpty(enumConstantNameList) && existNoArgumentsConstructor) {
            createStaticBlock(new KernelEnumStaticBlockBody() {

                {
                    enumNameList = enumConstantNameList;
                }

                @Override
                public void constructEnumConsts() {
                    for (String name : enumNameList) {
                        constructEnumConst(name);
                    }
                }

                @Override
                public void body(LocalVariable... argus) {
                    return_();
                }
            });
        }
    }
}
