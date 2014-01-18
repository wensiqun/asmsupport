package cn.wensiqun.asmsupport.utils.chooser;

import java.util.List;
import java.util.Map;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.definition.method.meta.AMethodMeta;

public interface DetermineMethodSignature {
    
	/**
	 * <p>jsl3 := The Java™ Language Specification Third Edition<br>
	 * A member method is potentially applicable to a method invocation if and only if all of the following are true:
	 * <ol>
	 * <li>The name of the member is identical to the name of the method in the method invocation.</li>
	 * <li>The member is accessible (jsl3 6.6) to the class or interface in which the method invocation appears.</li>
	 * <li>The arity of the member is lesser or equal to the arity of the method invocation.</li>
	 * <li>If the member is a variable arity method with arity n, the arity of the method invocation is greater or equal to n-1.</li>
	 * <li>If the member is a fixed arity method with arity n, the arity of the method invocation is equal to n.</li>
	 * <li>If the method invocation includes explicit type parameters, and the member is a generic method, 
	 *   then the number of actual type parameters is equal to the number of formal type parameters.</li>
	 * </ol>
	 * </p>
	 * @return
	 */
	Map<AClass, List<AMethodMeta>> identifyPotentiallyApplicableMethods();
	
	/**
	 * <p>
     * Phase 1 Identify Matching Arity Methods Applicable by Subtyping <br>
     * </p>
     * <b>reference to : The Java™ Language Specification 15.12.2.2</b>
     * @return
     */
    AMethodMeta firstPhase();
    
    /**
	 * <p>
     * Phase 2: Identify Matching Arity Methods Applicable by Method Invocation Conversion
     * </p>
     * <b>reference to : The Java™ Language Specification 15.12.2.3</b>
     * @return
     */
    AMethodMeta secondPhase();
   
    /**
	 * <p>
     * Phase 3: Identify Applicable Variable Arity Methods
     * </p>
     * <b>reference to : The Java™ Language Specification 15.12.2.4</b>
     * @return
     */
    AMethodMeta thirdPhase();
    
    /**
	 * <p>
     * Choosing the Most Specific Method <br>
     * </p>
     * <b>reference to : The Java™ Language Specification 15.12.2.5</b>
     * @return
     */
    AMethodMeta choosingTheMostSpecificMethod(List<AMethodMeta> entities);
}
