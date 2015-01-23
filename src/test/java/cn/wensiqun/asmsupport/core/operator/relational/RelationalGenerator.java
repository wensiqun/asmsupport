package cn.wensiqun.asmsupport.core.operator.relational;

import cn.wensiqun.asmsupport.core.block.classes.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreatorInternal;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import example.AbstractExample;

public class RelationalGenerator extends AbstractExample 
{

    public static void main(String[] args)
    {
        ClassCreatorInternal creator = new ClassCreatorInternal(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.RelationalGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "test", null, null, null, null, new StaticMethodBodyInternal(){

               @Override
               public void body(LocalVariable... argus)
               {
                   
                   
                   _return();
               }
        });
           
       creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.getProductClass(String[].class)}, new String[]{"args"}, null, null,
           new StaticMethodBodyInternal(){
               @Override
               public void body(LocalVariable... argus) {
                   _invokeStatic(getMethodOwner(), "test");
                   _return();
               }
       
       });

       generate(creator);
    }

}
