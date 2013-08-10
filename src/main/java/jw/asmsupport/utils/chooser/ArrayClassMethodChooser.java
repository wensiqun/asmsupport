package jw.asmsupport.utils.chooser;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.ArrayClass;
import jw.asmsupport.clazz.ProductClass;
import jw.asmsupport.entity.MethodEntity;

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
    public MethodEntity firstPhase() {
        return chooser.firstPhase();
    }

    @Override
    public MethodEntity secondPhase() {
        return chooser.secondPhase();
    }

    @Override
    public MethodEntity thirdPhase() {
        return chooser.thirdPhase();
    }

    @Override
    protected MethodEntity foundMethodWithNoArguments() {
        return super.foundMethodWithNoArguments(Object.class);
    }


}
