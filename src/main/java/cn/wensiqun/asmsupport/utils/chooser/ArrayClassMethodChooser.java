package cn.wensiqun.asmsupport.utils.chooser;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.ArrayClass;
import cn.wensiqun.asmsupport.clazz.ProductClass;
import cn.wensiqun.asmsupport.definition.method.meta.AMethodMeta;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayClassMethodChooser extends AbstractMethodChooser{

    private AbstractMethodChooser chooser;
    
    public ArrayClassMethodChooser(AClass invoker, ArrayClass methodOwner, String name,
            AClass[] argumentTypes) {
        super(invoker, name, argumentTypes);
        chooser = new ProductClassMethodChooser(invoker, (ProductClass)AClass.OBJECT_ACLASS, name, argumentTypes);
    }

    @Override
    public AMethodMeta firstPhase() {
        return chooser.firstPhase();
    }

    @Override
    public AMethodMeta secondPhase() {
        return chooser.secondPhase();
    }

    @Override
    public AMethodMeta thirdPhase() {
        return chooser.thirdPhase();
    }

    @Override
    protected AMethodMeta foundMethodWithNoArguments() {
        return super.foundMethodWithNoArguments(Object.class);
    }


}
