package jw.asmsupport.block.operator;

import jw.asmsupport.Parameterized;
import jw.asmsupport.definition.variable.IVariable;
import jw.asmsupport.operators.array.ArrayLength;
import jw.asmsupport.operators.array.ArrayLoader;
import jw.asmsupport.operators.array.ArrayStorer;
import jw.asmsupport.operators.array.ArrayValue;
import jw.asmsupport.operators.assign.Assigner;
import jw.asmsupport.operators.method.MethodInvoker;

public interface ArrayOperator {
	
	/**
     * get value from array according to index<br>
     * <p>
     * java code:<br>
     * i[1][2];
     * </p>
     * 
     * <p>
     * asmsupport code:<br>
     * arrayLoad(i, getValue(1), getValue(2))
     * </p>
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     */
    public ArrayLoader arrayLoad(IVariable arrayReference, Parameterized pardim, Parameterized... parDims);
    
    /**
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     */
    public ArrayLoader arrayLoad(MethodInvoker arrayReference, Parameterized pardim, Parameterized... parDims);
    
    
    /**
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     */
    public ArrayLoader arrayLoad(ArrayLoader arrayReference, Parameterized pardim, Parameterized... parDims);
    
    
    /**
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     */
    public ArrayLoader arrayLoad(ArrayValue arrayReference, Parameterized pardim, Parameterized... parDims);
    
    /**
     * 
     * @param arrayReference
     * @param pardim
     * @param parDims
     * @return
     */
    public ArrayLoader arrayLoad(Assigner arrayReference, Parameterized pardim, Parameterized... parDims);
    
    
    /**
     * set value from array according to index<br>
     * <p>
     * java code:<br>
     * i[1][2] = 10;
     * </p>
     * 
     * <p>
     * asmsupport code:<br>
     * arrayStore(i, getValue(10), getValue(1), getValue(2))
     * </p>
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(IVariable arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    

    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(MethodInvoker arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    
    
    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(ArrayLoader arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    

    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(ArrayValue arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    
    
    /**
     * 
     * @param arrayReference
     * @param value
     * @param dim
     * @param dims
     * @return
     */
    public ArrayStorer arrayStore(Assigner arrayReference, Parameterized value, Parameterized dim, Parameterized... dims);
    
    /**
     * get length of array
     * <p>
     * java code:<br>
     * i[1].length<br>
     * note: i is int[][]{{1},{2}}
     * </p>
     * 
     * <p>
     * asmsupport code:<br>
     * arrayLength(i, getValue(1))
     * </p>
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(IVariable arrayReference, Parameterized... dims);
    
    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(MethodInvoker arrayReference, Parameterized... dims);
    
    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(ArrayLoader arrayReference, Parameterized... dims);
    
    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(ArrayValue arrayReference, Parameterized... dims);
    

    /**
     * 
     * @param arrayReference
     * @param dims
     * @return
     */
    public ArrayLength arrayLength(Assigner arrayReference, Parameterized... dims);
    

}
