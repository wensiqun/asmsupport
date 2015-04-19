package json.generator.impl;

import java.util.Map;

import json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.client.ProgramBlock;
import cn.wensiqun.asmsupport.core.InternalParameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.standard.clazz.AClass;

public class MapGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isChildOrEqual(AClassFactory.getType(Map.class));
    }

    @Override
    protected boolean doGenerate(final GeneratorContext context, ProgramBlock<? extends ProgramBlockInternal> block,
            final LocalVariable encoder, AClass type, InternalParameterized value) {
        GlobalVariable jsonPool = block.this_().field("jsonPool");
        MethodInvoker getOrRegisterCall = block.call(jsonPool, "getOrRegister", block.val(block.getType(Map.class)));
        block.call(getOrRegisterCall, "parse", encoder, value);
        return true;
    }


}
