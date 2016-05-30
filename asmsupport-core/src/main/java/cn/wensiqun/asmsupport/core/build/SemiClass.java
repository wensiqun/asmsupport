package cn.wensiqun.asmsupport.core.build;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.clazz.MutableClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;
import cn.wensiqun.asmsupport.utils.Modifiers;

import java.util.LinkedList;

public class SemiClass extends MutableClass {

    public SemiClass(ASMSupportClassLoader classLoader, int version, int access, String name, IClass superCls,
                     IClass[] interfaces) {
        super(classLoader);
        this.version = version;
        this.name = name;
        this.mod = access;
        this.superClass = superCls;
        this.interfaces = interfaces;

        if (!Modifiers.isInterface(mod)) {
            this.mod += Opcodes.ACC_SUPER;
        }
    }

    @Override
    public String getDescription() {
        return new StringBuilder("L").append(getName().replace(".", "/"))
                .append(";").toString();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public int getDimension() {
        return -1;
    }

    @Override
    public Field getField(final String name) throws NoSuchFieldException {
        final LinkedList<Field> found = new LinkedList<Field>();
        for (Field gv : getFields()) {
            if (gv.getName().equals(name)) {
                found.add(gv);
            }
        }

        if (found.isEmpty()) {
            IClass fieldOwner = getSuperclass();
            IClass objectType = getClassHolder().getType(Object.class);
            while (fieldOwner != null && !fieldOwner.equals(objectType)) {
                Field field = fieldOwner.getField(name);
                if (field != null) {
                    found.add(field);
                    break;
                }
                fieldOwner = fieldOwner.getSuperclass();
            }
        }

        for (IClass itf : getInterfaces()) {
            try {
                Field field = itf.getField(name);
                if (field != null) {
                    found.add(field);
                }
            } catch (NoSuchFieldException e) {
            }
        }

        if (found.size() == 0) {
            throw new NoSuchFieldException("Not found field " + name);
        } else if (found.size() == 1) {
            return found.getFirst();
        }

        StringBuilder errorSuffix = new StringBuilder();
        for (Field field : found) {
            errorSuffix.append(field.getDeclaringClass()).append(',');
        }
        throw new ASMSupportException("The field '" + name + "' is ambiguous, found it in class [" + errorSuffix + "]");
    }

}