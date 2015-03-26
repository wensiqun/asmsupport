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
/**
 * 
 */
package cn.wensiqun.asmsupport.core.utils.jls15_12_2;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ProductClass;
import cn.wensiqun.asmsupport.core.definition.method.AMethod;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.exception.ASMSupportException;
import cn.wensiqun.asmsupport.core.utils.AClassUtils;
import cn.wensiqun.asmsupport.core.utils.ASConstant;
import cn.wensiqun.asmsupport.core.utils.collections.CollectionUtils;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class ProductClassMethodChooser extends AbstractMethodChooser {
    
    private ProductClass methodOwner;
    
    public ProductClassMethodChooser(AClass invoker, ProductClass methodOwner, String name, AClass[] argumentTypes) {
        super(invoker, name, argumentTypes);
        this.methodOwner = methodOwner;
    }


    @Override
    public AMethodMeta firstPhase() {
    	AMethodMeta me4ReallyClass = determineMostSpecificMethodEntity(determineMethodInJavaClass(invoker, methodOwner.getReallyClass(), name, argumentTypes, allArgTypes));
    	
    	if(!CollectionUtils.isEmpty(methodOwner.getMethods())){
    		//find in current class
            List<AMethod> methods = new ArrayList<AMethod>(methodOwner.getMethods());
            //Collections.copy(methods, methodOwner.getMethods());
            TypeTreeNode[] ttns;

            //所有找到的可能的方法
            List<MethodEntityTypeTreeNodeCombine> foundCombine = new ArrayList<MethodEntityTypeTreeNodeCombine>();
            //参数是否和传入的参数相同
            boolean sameToPass = true;
            AMethodMeta mte;
            for(int i=0, length = allArgTypes.size(); i<length; i++){
                ttns = allArgTypes.get(i);
                for(int k=0, mlen = methods.size();  k<mlen; k++){
                    mte = methods.get(k).getMethodMeta();
                    //如果名字不相同直接跳过
                    if(!name.equals(mte.getName())){
                        continue;
                    }
                    
                    AClass[] mtdArgs = mte.getArgClasses();
                    //如果参数个数不同直接跳过
                    if(mtdArgs.length != ttns.length){
                        continue;
                    }
                    //如果有一个参数的类型不同直接跳过
                    for(int j=0; j<ttns.length; j++){
                        if(!ttns[j].type.equals(mtdArgs[j])){
                            sameToPass = false;
                            continue;
                        }
                    }
                    
                    if(i == 0 && sameToPass){
                        return mte;
                    }
                    methods.remove(k);
                    foundCombine.add(new MethodEntityTypeTreeNodeCombine(mte, allArgTypes.remove(i)));
                    length--;
                    break;
                }
            }
            
            MethodEntityTypeTreeNodeCombine[] mettncs = foundCombine.toArray(new MethodEntityTypeTreeNodeCombine[foundCombine.size()]);
            
            AMethodMeta me4newCreateMethod = determineMostSpecificMethodEntity(mettncs);
            
            if(me4ReallyClass == null && me4newCreateMethod != null){
            	return me4newCreateMethod;
            }else if(me4ReallyClass != null && me4newCreateMethod == null){
            	return me4ReallyClass;
            }else{
            	return null;
            }
    	}else{
    		return me4ReallyClass;
    	}
    	
    	//return determineMostSpecificMethodEntity(determineMethodInJavaClass(invoker, methodOwner.getReallyClass(), name, argumentTypes));
    }

    @Override
    public AMethodMeta secondPhase() {
        List<AClass[]> list = new ArrayList<AClass[]>(AClassUtils.allArgumentWithBoxAndUnBoxCountExceptSelf(argumentTypes));
        AClassUtils.allArgumentWithBoxAndUnBox(argumentTypes, AClassUtils.primitiveFlag(argumentTypes), 0, new AClass[argumentTypes.length], list);
        
        int foundNumber = 0;
        AMethodMeta me = null;
        //MethodEntityTypeTreeNodeCombine[] mettnc;
        for(AClass[] argsTypes : list){
        	ProductClassMethodChooser pcmc = new ProductClassMethodChooser(invoker, methodOwner, name, argsTypes);
            me = pcmc.firstPhase();
            //mettnc = determineMethodInJavaClass(invoker, methodOwner.getReallyClass(), name, argsTypes);
            //me = determineMostSpecificMethodEntity(mettnc);
        	if(me != null){
                foundNumber++;
                if(foundNumber > 1){
                    throw new ASMSupportException(" Ambiguous ...............");
                }
            }
        }
        
        return me;
    }

    @Override
    public AMethodMeta thirdPhase() {
        List<AMethodMeta> applicable = applicableVariableVarifyMethod(invoker, methodOwner, name, argumentTypes);
        List<TypeTreeNode[]> appliNodes = applicableVariableVarifyMethodArgumentsNodes(applicable);

        if(appliNodes.size() > 0){
            int mostIndex = mostSpecificIndexForVariableVarify(appliNodes);
            if(mostIndex == -1){
                throw new ASMSupportException(" Ambiguous ...............");
            }else{
                AMethodMeta me = applicable.get(mostIndex);
                return me;
            }
        }else{
            return null;
        }
    }


    @Override
    protected AMethodMeta foundMethodWithNoArguments() {
    	for(AMethod m : methodOwner.getMethods()){
    		AMethodMeta me = m.getMethodMeta();
    		if(m.getMode() == ASConstant.METHOD_CREATE_MODE_ADD &&
    		   me.getName().equals(this.name) &&
    		   me.getArgClasses().length == 0){
    			return me;
    		}
    	}
    	
        return foundMethodWithNoArguments(methodOwner.getReallyClass());
    }

}
