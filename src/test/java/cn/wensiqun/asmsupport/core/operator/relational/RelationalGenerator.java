package cn.wensiqun.asmsupport.core.operator.relational;

import sample.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.StaticMethodBodyInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.creator.clazz.ClassCreator;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class RelationalGenerator extends AbstractExample 
{

    public static void main(String[] args)
    {
        ClassCreator creator = new ClassCreator(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.RelationalGeneratorExample", null, null);
        
        creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "test", null, null, null, null, new StaticMethodBodyInternal(){

               @Override
               public void body(LocalVariable... argus)
               {
                   return_();
               }
        });
           
       creator.createStaticMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", new AClass[]{AClassFactory.defType(String[].class)}, new String[]{"args"}, null, null,
           new StaticMethodBodyInternal(){
               @Override
               public void body(LocalVariable... argus) {
                   call(getMethodOwner(), "test");
                   return_();
               }
       
       });

       generate(creator);
    }

}
