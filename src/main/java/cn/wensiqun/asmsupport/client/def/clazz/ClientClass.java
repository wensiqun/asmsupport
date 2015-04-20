package cn.wensiqun.asmsupport.client.def.clazz;

import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;

public class ClientClass implements IClass<FieldVar> {
    
    private AClass target;
    
    public ClientClass(AClass target) {
        this.target = target;
    }

    @Override
    public FieldVar field(String name) {
        return new FieldVar(target.field(name));
    }

    @Override
    public boolean isArray() {
        return target.isArray();
    }

    @Override
    public int getDimension() {
        return target.getDimension();
    }

    @Override
    public String getPackage() {
        return target.getPackage();
    }

    @Override
    public String getName() {
        return target.getName();
    }

    @Override
    public int getModifiers() {
        return target.getModifiers();
    }

    @Override
    public int getVersion() {
        return target.getVersion();
    }

    @Override
    public Class<?> getSuperClass() {
        return target.getSuperClass();
    }

    @Override
    public Class<?>[] getInterfaces() {
        return target.getInterfaces();
    }

    @Override
    public String getDescription() {
        return target.getDescription();
    }

    @Override
    public Field getField(String name) {
        return target.getField(name);
    }

    @Override
    public Type getType() {
        return target.getType();
    }

    @Override
    public boolean isPrimitive() {
        return target.isPrimitive();
    }

    @Override
    public boolean isInterface() {
        return target.isInterface();
    }

    @Override
    public boolean isAbstract() {
        return target.isAbstract();
    }

    @Override
    public int getCastOrder() {
        return target.getCastOrder();
    }

    @Override
    public Value getDefaultValue() {
        return target.getDefaultValue();
    }

    @Override
    public boolean existStaticInitBlock() {
        return target.existStaticInitBlock();
    }

    public boolean isChildOrEqual(ClientClass otherType) {
        return target.isChildOrEqual(otherType.target);
    }
    
    public AClass getTarget() {
        return target;
    }

}
