package cn.wensiqun.asmsupport.utils.chooser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.clazz.ArrayClass;
import cn.wensiqun.asmsupport.clazz.ProductClass;
import cn.wensiqun.asmsupport.clazz.SemiClass;
import cn.wensiqun.asmsupport.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.utils.AClassUtils;
import cn.wensiqun.asmsupport.utils.ASConstant;
import cn.wensiqun.asmsupport.utils.lang.ClassUtils;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class AbstractMethodChooser implements IMethodChooser, DetermineMethodSignature {

    protected AClass invoker;
    protected String name;
    protected AClass[] argumentTypes;
    protected List<TypeTreeNode[]> allArgTypes;
    
    public AbstractMethodChooser(AClass invoker, String name, AClass[] argumentTypes){
        this.invoker = invoker;
        this.name = name;
        this.argumentTypes = argumentTypes;
        
        TypeTreeNode[] argTypeNodes = new TypeTreeNode[argumentTypes.length];
        for(int i = 0; i<argTypeNodes.length; i++){
            argTypeNodes[i] = translateToTypeTreeRoot(argumentTypes[i]);
        }
        //所有可能的参数
        //List<TypeTreeNode[]> allArgTypes = allPossibleArguments(argTypeNodes);
        allPossibleArguments(argTypeNodes);
    }

    /**
     * SUPER means the m1 is super of m2
     * CHILD means the m1 is child of m2
     * EQUAL means the m1 equal m2
     * NON means they are no any relation betweent m1 and m2
     * @author 温斯群
     *
     */
    private enum Relation{
        SUPER,
        CHILD,
        EQUAL,
        NON
    }
    
    /**
     * 
     * @author 温斯群
     *
     */
    public static class TypeTreeNode{
        
        AClass type;
        
        private TypeTreeNode[] superType;
        
        private boolean used;
        
        private TypeTreeNode(AClass type, TypeTreeNode[] superType){
            this.type = type;
            this.superType = superType;
        }

        @Override
        public String toString() {
            return type.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj){
                return true;
            }
            
            if(obj instanceof TypeTreeNode){
                if(((TypeTreeNode)obj).type.equals(type)){
                    return true;
                }
            }
            
            return false;
        }
        
        private Relation relationWith(TypeTreeNode node){
            if(this.equals(node)){
                return Relation.EQUAL;
            }
            
            if(isSuperOf(node)){
                return Relation.SUPER;
            }
            
            if(node.isSuperOf(this)){
                return Relation.CHILD;
            }
            
            return Relation.NON;
        }
        
        private boolean properSupertypeOf(TypeTreeNode node){
            if(this.equals(node)){
                return true;
            }else{
                return isSuperOf(node);
            }
        }
        
        private boolean isSuperOf(TypeTreeNode node){
            if(node.superType != null){
                for(TypeTreeNode sup : node.superType){
                    if(sup.equals(this)){
                        return true;
                    }else{
                        return isSuperOf(sup);
                    }
                }
            }
            return false;
        }
        
        private boolean nonSemiClass(){
            return !(type instanceof SemiClass) &&
                   !(type instanceof ArrayClass && ((ArrayClass) type).getRootComponentClass() instanceof SemiClass) &&
                   ! used;
        }
        
    }

    static class MethodEntityTypeTreeNodeCombine{
        private TypeTreeNode[] nodes;
        private AMethodMeta entity;

        MethodEntityTypeTreeNodeCombine(AMethodMeta entity, TypeTreeNode[] nodes
                ) {
            super();
            this.nodes = nodes;
            this.entity = entity;
        }
    }
    
    private MethodEntityTypeTreeNodeCombine[] translateToCombine(List<AMethodMeta> mes, List<TypeTreeNode[]> ttns){
        MethodEntityTypeTreeNodeCombine[] mettnc = new MethodEntityTypeTreeNodeCombine[ttns.size()];
        
        for(int i=0; i<mettnc.length; i++){
            mettnc[i] = new MethodEntityTypeTreeNodeCombine(mes.get(i), ttns.get(i));
        }
        return mettnc;
    }
       
    @Override
	public Map<AClass, List<AMethodMeta>> identifyPotentiallyApplicableMethods() {
		return null;
	}


	@Override
	public AMethodMeta choosingTheMostSpecificMethod(List<AMethodMeta> entities) {
		return null;
	}

	/**
     * according a class type to get argument structure
     * @param as
     * @return
     */
    private TypeTreeNode translateToTypeTreeRoot(AClass as){
        List<TypeTreeNode> cache = new ArrayList<TypeTreeNode>();
        TypeTreeNode ttn =  new TypeTreeNode(as, null);
        cache.add(ttn);
        ttn.superType = getTypeTreeNode(as, cache);
        return ttn;
    }
    
    /**
     * 
     * @param as
     * @param cache
     * @return
     */
    private TypeTreeNode[] getTypeTreeNode(AClass as, List<TypeTreeNode> cache){
        AClass[] supers = AClassUtils.getDirectSuperType(as);
        TypeTreeNode[] nodes = null;
        if(supers != null && supers.length != 0){
            nodes = new TypeTreeNode[supers.length];
            for(int i=0; i<supers.length; i++){
                for(TypeTreeNode ttn : cache){
                    if(ttn.type.equals(supers[i])){
                        nodes[i] = ttn;
                        break;
                    }
                }
                if(nodes[i] == null){
                    nodes[i] = new TypeTreeNode(supers[i], null);
                }
                cache.add(nodes[i]);
                nodes[i].superType = getTypeTreeNode(supers[i], cache);
            }
        }
        return nodes;    
    }

    private boolean canConvertedByMethodInvocationConversion(AClass from, AClass to){
        TypeTreeNode fromNode;
        TypeTreeNode toNode;
        if(from.isPrimitive() && !to.isPrimitive()){
            fromNode = translateToTypeTreeRoot(AClassUtils.getPrimitiveWrapAClass(from));
        } else if(!from.isPrimitive() && to.isPrimitive()){
            fromNode = translateToTypeTreeRoot(AClassUtils.getPrimitiveAClass(from));
        }else{
            fromNode = translateToTypeTreeRoot(from);
        }
        
        toNode = translateToTypeTreeRoot(to);
        if(toNode.properSupertypeOf(fromNode)){
            return true;
        }
        
        return false;
    }
    
    
    /**List<TypeTreeNode[]>
     * get all possible arguments
     * @param orgi
     * @return
     */
    private void allPossibleArguments(TypeTreeNode[] orgi){
        if(orgi.length == 0){
        	this.allArgTypes = new ArrayList<TypeTreeNode[]>(0);
        }
        
        List<TypeTreeNode[]> allArg = new ArrayList<TypeTreeNode[]>();
        
        for(int i=0; i<orgi.length; i++){
            orgi[i].used = false;
            allPossibleArguments(orgi[i], orgi.length, allArg.size(), i, false, allArg);
        }
        this.allArgTypes = allArg;
        //return allArg;
    }
    
    /**
     * 
     * @param curNode
     * @param length
     * @param endRow
     * @param index
     * @param hasSetFirst
     * @param list
     */
    private void allPossibleArguments(TypeTreeNode curNode, int length, int endRow, int index, boolean hasSetFirst, List<TypeTreeNode[]> list){
        
        if(endRow == 0){
            if(curNode.nonSemiClass()){
                TypeTreeNode[] ttns = new TypeTreeNode[length];
                ttns[index] = curNode;
                curNode.used = true;
                list.add(ttns);
            }
        } else {
            
            if(!hasSetFirst){
                if(curNode.nonSemiClass()){
                    int i = 0;
                    for(TypeTreeNode[] nodes : list){
                        if(i < endRow){
                            nodes[index] = curNode;
                            curNode.used = true;
                            i++;
                        } else {
                            break;
                        }
                    }
                    hasSetFirst = true;
                }
            }else{
                if(curNode.nonSemiClass()){
                    List<TypeTreeNode[]> newttns = new ArrayList<TypeTreeNode[]>();
                    int i = 0;
                    for(TypeTreeNode[] nodes : list){
                        if(i < endRow){
                            TypeTreeNode[] newttn = new TypeTreeNode[length];
                            System.arraycopy(nodes, 0, newttn, 0, length);
                            newttn[index] = curNode;
                            curNode.used = true;
                            newttns.add(newttn);
                            i++;
                        } else {
                            break;
                        }
                    }
                    list.addAll(newttns);
                }
            }
        }
        
        if(curNode.superType != null) {
            for(TypeTreeNode supNode : curNode.superType){
                allPossibleArguments(supNode, length, endRow, index, hasSetFirst, list);
            }
        }
    }
    
    
    /**
     * If more than one member method is both accessible and applicable to a method
     * invocation, it is necessary to choose one to provide the descriptor for the run-time
     * method dispatch. The Java programming language uses the rule that the most specific
     * method is chosen.
     * @param methodArguments
     * @return
     */
    private int mostSpecificIndex(MethodEntityTypeTreeNodeCombine[] mettnces){
        int mostIndex = 0;
        TypeTreeNode[] mostTypeTreeNodes;
        
        for(int i=0, length = mettnces.length - 1; i<length; ){
            try {
                mostTypeTreeNodes = mostSpecificBetween(mettnces[mostIndex].nodes, mettnces[i + 1].nodes);
                if(mostTypeTreeNodes.equals(mettnces[i + 1].nodes)){
                    mostIndex = i + 1;
                }
                i++;
            } catch (AmbiguousException e) {
                i = i + 2;
                mostIndex = i;
            }
        }
        
        if(mostIndex < mettnces.length){
            return mostIndex;
        }
        
        return -1;
    }
    
    private TypeTreeNode[] mostSpecificBetween(TypeTreeNode[] methodArguments1, TypeTreeNode[] methodArguments2) throws AmbiguousException{
        Relation[] rels = new Relation[methodArguments1.length];
        
        for(int i=0; i<methodArguments1.length; i++){
            rels[i] = methodArguments1[i].relationWith(methodArguments2[i]);
        }
        
        Relation relation = null;
        for(Relation r : rels){
            if(r.equals(Relation.NON)){
                throw new AmbiguousException();
            }
            
            if(relation == null){
                relation = r;
            }else{
                if(relation.equals(Relation.EQUAL)){
                    relation = r;
                }else{
                    if(!r.equals(relation) &&
                       !r.equals(Relation.EQUAL)){
                        throw new AmbiguousException();
                    }
                }
            }
        }
        
        if(relation.equals(Relation.SUPER)){
            return methodArguments2;
        }else{
            return methodArguments1;
        }
        
    }
    
    /**
     * 
     * @param methodArguments
     * @return
     */
    protected int mostSpecificIndexForVariableVarify(List<TypeTreeNode[]> methodArguments){
        int mostIndex = 0;
        TypeTreeNode[] mostTypeTreeNodes;
        
        for(int i=0, length = methodArguments.size() - 1; i<length; ){
            try {
                mostTypeTreeNodes = mostSpecificForVariableVarifyBetween(methodArguments.get(mostIndex), methodArguments.get(i + 1));
                if(mostTypeTreeNodes.equals(methodArguments.get(i + 1))){
                    mostIndex = i + 1;
                }
                i++;
            } catch (AmbiguousException e) {
                i = i + 2;
                mostIndex = i;
            }
        }
        
        if(mostIndex < methodArguments.size()){
            return mostIndex;
        }
        
        return -1;
    }
    
    private TypeTreeNode[] mostSpecificForVariableVarifyBetween(TypeTreeNode[] methodArguments1, TypeTreeNode[] methodArguments2) throws AmbiguousException{
        TypeTreeNode[] oneMtd;
        TypeTreeNode[] oterMtd;
        int n;
        int k;
        
        if(methodArguments1.length > methodArguments2.length){
            n = methodArguments1.length;
            k = methodArguments2.length;
            oneMtd = methodArguments1;
            oterMtd = methodArguments2;
        }else{
            k = methodArguments1.length;
            n = methodArguments2.length;
            oterMtd = methodArguments1;
            oneMtd = methodArguments2;
        }
        
        boolean found = true;
        
        for(int j=0; j < k - 1; j++){
            if(!oterMtd[j].properSupertypeOf(oneMtd[j])){
                found = false;
                break;
            }
        }
        
        if(found){
            for(int j = k - 1; j < n ; j++){
                if(!oterMtd[k - 1].properSupertypeOf(oneMtd[j])){
                    found = false;
                    break;
                }
            }

            if(found){
                return oneMtd;
            }
        }

        
        //if cannot found in above
        TypeTreeNode[] temp = oneMtd;
        oneMtd = oterMtd;
        oterMtd = temp;
        
        for(int j=0; j < k - 1; j++){
            if(!oterMtd[j].properSupertypeOf(oneMtd[j])){
                found = false;
                break;
            }
        }
        
        if(found){
            for(int j = k - 1; j < n ; j++){
                if(!oterMtd[j].properSupertypeOf(oneMtd[k - 1])){
                    found = false;
                    break;
                }
            }
            
            if(found){
                return oneMtd;
            }
        }
        
        throw new AmbiguousException(); 
    }
    
    /**
     * 
     * @param invoker
     * @param javaClass
     * @param name
     * @param orgiArguTypes
     * @param allArgTypes
     * @return
     */
    protected MethodEntityTypeTreeNodeCombine[] determineMethodInJavaClass(AClass invoker, Class<?> javaClass, String name, AClass[] orgiArguTypes, List<TypeTreeNode[]> allArgTypes){
        
        String[] arguNames = new String[orgiArguTypes.length];
        
        for(int i=0; i<orgiArguTypes.length; i++){
            arguNames[i] = "arg" + i;
        }
        
        //找到的方法
        List<AMethodMeta> mes = new ArrayList<AMethodMeta>();
        
        List<TypeTreeNode[]> foundMethodArguments = new ArrayList<TypeTreeNode[]>(); 
        
        AClass invoked = AClassFactory.getProductClass(javaClass);

        for (TypeTreeNode[] ttns : allArgTypes) {

        	//java.lang.reflect.Method method = null;
            Class<?> actuallyMethodOwner = javaClass;

            Class<?>[] argclses = new Class<?>[orgiArguTypes.length];
            for(int i=0; i<argclses.length; i++){
                try{
                    argclses[i] = ((ProductClass) ttns[i].type).getReallyClass();
                }catch(ClassCastException cce){
                    try {
                        argclses[i] = ClassUtils.forName(((ArrayClass) ttns[i].type).getDescription());
                    } catch (ClassNotFoundException e) {
                    	throw new ASMSupportException("Class not found exception : " + ((ArrayClass) ttns[i].type).getDescription() ,e);
                    }
                }
            }
            
            AClass[] pcs;
            boolean sameToPass = true;
            AMethodMeta me;
            for (; actuallyMethodOwner != null; ) {
                try {
                	AClass methodReturnType = null;
                	int methodMidifiers;
                	AClass[] exceptionAclasses;
                	
                	if(name.equals(ASConstant.INIT)){
                        Constructor<?> constructor = actuallyMethodOwner.getDeclaredConstructor(argclses);
                        methodMidifiers = constructor.getModifiers();
                        exceptionAclasses = AClassUtils.convertToAClass(constructor.getExceptionTypes());
                	}else{
                        Method method = actuallyMethodOwner.getDeclaredMethod(name, argclses);
                        methodReturnType = AClassFactory.getProductClass(method.getReturnType());
                        methodMidifiers = method.getModifiers();
                        exceptionAclasses = AClassUtils.convertToAClass(method.getExceptionTypes());
                	}
                    
                    if(AClassUtils.visible(invoker, invoked, AClassFactory.getProductClass(actuallyMethodOwner),
                    		methodMidifiers)){
                        pcs = new AClass[ttns.length];
                        for(int i=0; i<ttns.length; i++){
                            pcs[i] = ttns[i].type;
                            if(!pcs[i].equals(orgiArguTypes[i])){
                                sameToPass = false;
                            }
                        }
                        me = new AMethodMeta(name, AClassFactory.getProductClass(javaClass),
                                AClassFactory.getProductClass(actuallyMethodOwner), pcs,
                                arguNames, methodReturnType, exceptionAclasses, methodMidifiers);
                        
                        mes.add(me);
                        
                        if(sameToPass){
                            return new MethodEntityTypeTreeNodeCombine[]{new MethodEntityTypeTreeNodeCombine(me, ttns)};
                        }
                        foundMethodArguments.add(ttns);
                        break;
                    }

                } catch (SecurityException e) {
                } catch (NoSuchMethodException e) {
                }
                actuallyMethodOwner = actuallyMethodOwner.getSuperclass();
            }
        }
        
        return translateToCombine(mes, foundMethodArguments);
    }
   
    /**
     * 
     * @param mettnc
     * @return
     */
    protected AMethodMeta determineMostSpecificMethodEntity(MethodEntityTypeTreeNodeCombine[] mettnc){
        if(mettnc.length > 0){
            int mostIndex = mostSpecificIndex(mettnc);
            if(mostIndex == -1){
                throw new ASMSupportException("Ambiguous ...............");
            }else{
                return mettnc[mostIndex].entity;
            }
        }else{
            return null;
        }
    }
    
    /**
     * 
     * @param m
     * @param argumentTypes
     * @return
     */
    private boolean applicableVariableVarifyMethodEntity(AMethodMeta m, AClass[] argumentTypes){
        AClass[] formalParas = m.getArgClasses();//m.getParameterTypes();
        for(int i=0, length = formalParas.length - 1; i<length; i++){
            if(!canConvertedByMethodInvocationConversion(argumentTypes[i], formalParas[i])){
                return false;
            }
        }
        //the last parameter of variable arity method must be array
        AClass lastVariableVarify = ((ArrayClass)formalParas[formalParas.length - 1]).getNextDimType();
        for(int i = formalParas.length - 1; i < argumentTypes.length; i++){
            if(!canConvertedByMethodInvocationConversion(argumentTypes[i], lastVariableVarify)){
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * 
     * @param invoker
     * @param acls
     * @param name
     * @param argumentTypes
     * @return
     */
    protected List<AMethodMeta> applicableVariableVarifyMethod(AClass invoker, AClass acls, String name, AClass[] argumentTypes){
    	
        List<AMethodMeta> allVarArities = AClassUtils.allDeclareVariableArityMethod(invoker, acls, name, argumentTypes.length);
        
        List<AMethodMeta> applicable = new ArrayList<AMethodMeta>();
        
        for(AMethodMeta m : allVarArities){
            if(applicableVariableVarifyMethodEntity(m, argumentTypes)){
                applicable.add(m);
            }
        }
        
        return applicable;
    }
    
    protected List<TypeTreeNode[]> applicableVariableVarifyMethodArgumentsNodes(List<AMethodMeta> applicable){
        List<TypeTreeNode[]> appliNodes = new ArrayList<TypeTreeNode[]>(applicable.size());
        AClass[] mtdParas;
        TypeTreeNode[] newttns;
        for(AMethodMeta m : applicable){
            mtdParas = m.getArgClasses();
            newttns = new TypeTreeNode[mtdParas.length];
            for(int i=0; i<newttns.length; i++){
                newttns[i] = translateToTypeTreeRoot(mtdParas[i]);
            }
            appliNodes.add(newttns);
        }
        
        return appliNodes;
    }
    
    public class AmbiguousException extends Exception{

        private static final long serialVersionUID = -6471924900905334787L;

    }
    
    protected abstract AMethodMeta foundMethodWithNoArguments();
    
    protected AMethodMeta foundMethodWithNoArguments(Class<?> cls){
        if(cls.isPrimitive()){
            throw new ASMSupportException("cannot invoke method in prmitive");
        }
        
        Class<?> actually = cls;
        AMethodMeta me;
        
        for(; actually != null ;){
            try {
            	AClass methodReturnType = null;
            	int methodMidifiers;
            	AClass[] exceptionTypes;
            	
            	if(name.equals(ASConstant.INIT)){
                    Constructor<?> constructor = actually.getDeclaredConstructor();
                    methodMidifiers = constructor.getModifiers();
                    exceptionTypes = AClassUtils.convertToAClass(constructor.getExceptionTypes());
            	}else{
                    Method method = actually.getDeclaredMethod(name);
                    methodReturnType = AClassFactory.getProductClass(method.getReturnType());
                    methodMidifiers = method.getModifiers();
                    exceptionTypes = AClassUtils.convertToAClass(method.getExceptionTypes());
            	}
            	
                AClass invoked =  AClassFactory.getProductClass(cls);
                AClass actuallyInvoked = AClassFactory.getProductClass(actually);
                
                if(!AClassUtils.visible(invoker, invoked, actuallyInvoked, methodMidifiers)){
                    throw new ASMSupportException("cannot invoke method by the modifiers " + Modifier.toString(methodMidifiers));
                }
                
                me = new AMethodMeta(name,invoked, actuallyInvoked,
                        null, null, methodReturnType, exceptionTypes, methodMidifiers);
                
                return me;
            } catch (SecurityException e) {
            } catch (NoSuchMethodException e) {
                actually = actually.getSuperclass();
            }
        }
        
        return null;
    }
    
    @Override
    public AMethodMeta chooseMethod() {
        if(this.argumentTypes == null || this.argumentTypes.length == 0){
            return foundMethodWithNoArguments();
        }
        
        AMethodMeta me = firstPhase();
        if(me != null){
            return me;
        }
        
        me = secondPhase();
        
        if(me != null){
            return me;
        }
        
        me = thirdPhase();
        
        return me;
    }
}
