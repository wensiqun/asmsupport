package cn.wensiqun.asmsupport.core.operator.relational;

import cn.wensiqun.asmsupport.core.AbstractExample;
import cn.wensiqun.asmsupport.core.block.method.common.KernelMethodBody;
import cn.wensiqun.asmsupport.core.builder.impl.ClassBuilderImpl;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;

public class RelationalGenerator extends AbstractExample 
{

    public static void main(String[] args)
    {
        ClassBuilderImpl creator = new ClassBuilderImpl(Opcodes.V1_5, Opcodes.ACC_PUBLIC , "generated.block.RelationalGeneratorExample", null, null);
        
        creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "test", null, null, null, null, new KernelMethodBody(){

               @Override
               public void body(LocalVariable... argus)
               {
                   return_();
               }
        });
           
       creator.createMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", 
    		   new IClass[]{creator.getClassLoader().getType(String[].class)}, new String[]{"args"}, null, null,
           new KernelMethodBody(){
               @Override
               public void body(LocalVariable... argus) {
                   call(getMethodDeclaringClass(), "test");
                   return_();
               }
       
       });

       generate(creator);
    }

}
