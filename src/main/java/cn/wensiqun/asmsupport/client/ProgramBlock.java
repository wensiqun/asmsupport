package cn.wensiqun.asmsupport.client;

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.core.definition.variable.SuperVariable;
import cn.wensiqun.asmsupport.core.definition.variable.ThisVariable;
import cn.wensiqun.asmsupport.core.operator.Return;
import cn.wensiqun.asmsupport.core.operator.array.ArrayLength;
import cn.wensiqun.asmsupport.core.operator.array.ArrayLoader;
import cn.wensiqun.asmsupport.core.operator.array.ArrayStorer;
import cn.wensiqun.asmsupport.core.operator.array.ArrayValue;
import cn.wensiqun.asmsupport.core.operator.assign.Assigner;
import cn.wensiqun.asmsupport.core.operator.checkcast.CheckCast;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalAnd;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalOr;
import cn.wensiqun.asmsupport.core.operator.logical.LogicalXor;
import cn.wensiqun.asmsupport.core.operator.logical.Not;
import cn.wensiqun.asmsupport.core.operator.logical.ShortCircuitAnd;
import cn.wensiqun.asmsupport.core.operator.logical.ShortCircuitOr;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Addition;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Division;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Modulus;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Multiplication;
import cn.wensiqun.asmsupport.core.operator.numerical.arithmetic.Subtraction;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitAnd;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitOr;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitXor;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.Reverse;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftLeft;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.UnsignedShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PostposeIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeDecrment;
import cn.wensiqun.asmsupport.core.operator.numerical.crement.PreposeIncrment;
import cn.wensiqun.asmsupport.core.operator.numerical.posinegative.Negative;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.Equal;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.GreaterThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.LessThan;
import cn.wensiqun.asmsupport.core.operator.numerical.relational.NotEqual;
import cn.wensiqun.asmsupport.core.operator.numerical.ternary.TernaryOperator;
import cn.wensiqun.asmsupport.standard.action.ActionSet;

public class ProgramBlock<B extends ProgramBlockInternal> implements ActionSet<IF , While, DoWhile, ForEach, Try, Synchronized>
{

    B target;
    
    public LocalVariable[] getMethodArguments(){
        return target.getMethodArguments();
    }
    
    /**
     * get current method owner.
     * 
     * @return
     */
    public NewMemberClass getMethodOwner() {
        return target.getMethodOwner();
    }
    
    @Override
    public ThisVariable _this()
    {
        return target._this();
    }
    
    @Override
    public GlobalVariable _this(String name) 
    {
        return target._this(name);
    }

    @Override
    public SuperVariable _super()
    {
        return target._super();
    }

    @Override
    public LocalVariable _var(String name, Class<?> type, Parameterized para) {
        return target._var(name, type, para);
    }

    @Override
    public LocalVariable _var(Class<?> type, Parameterized para) {
        return target._var(type, para);
    }

    @Override
    public LocalVariable _var(String name, AClass type, Parameterized para) {
        return target._var(name, type, para);
    }

    @Override
    public LocalVariable _var(AClass type, Parameterized para) {
        return target._var(type, para);
    }

    @Override
    public LocalVariable _var(String name, AClass aClass, boolean anonymous, Parameterized para)
    {
        return target._var(name, aClass, anonymous, para);
    }

    @Override
    public LocalVariable _createArrayVariableWithAllocateDimension(String name, ArrayClass aClass, boolean anonymous,
        Parameterized... allocateDim)
    {
        return target._createArrayVariableWithAllocateDimension(name, aClass, anonymous, allocateDim);
    }

    @Override
    public LocalVariable _createArrayVariable(String name, ArrayClass aClass, boolean anonymous, Parameterized value)
    {
        return target._createArrayVariable(name, aClass, anonymous, value);
    }

    @Override
    public LocalVariable _createArrayVariable(String name, ArrayClass aClass, boolean anonymous,
        Object parameterizedArray)
    {
        return target._createArrayVariable(name, aClass, anonymous, parameterizedArray);
    }

    @Override
    public Assigner _assign(ExplicitVariable variable, Parameterized val)
    {
        return target._assign(variable, val);
    }

    @Override
    public MethodInvoker _invoke(Parameterized objRef, String methodName, Parameterized... arguments)
    {
        return target._invoke(objRef, methodName, arguments);
    }
    
	@Override
	public MethodInvoker _invoke(String methodName, Parameterized... args) {
		return target._invoke(methodName, args);
	}

    @Override
    public MethodInvoker _invoke(AClass owner, String methodName, Parameterized... arguments)
    {
        
        return target._invoke(owner, methodName, arguments);
    }

    @Override
    public MethodInvoker _new(Class<?> owner, Parameterized... arguments)
    {
        return target._new(owner, arguments);
    }

    @Override
    public MethodInvoker _new(AClass owner, Parameterized... arguments)
    {
        return target._new(owner, arguments);
    }

    @Override
    public MethodInvoker _invokeOriginal()
    {
        return target._invokeOriginal();
    }

    @Override
    public ArrayValue _newArray(ArrayClass aClass, Parameterized... allocateDims)
    {
        return target._newArray(aClass, allocateDims);
    }

    @Override
    public ArrayValue _newArrayWithValue(ArrayClass aClass, Object arrayObject)
    {
        return target._newArrayWithValue(aClass, arrayObject);
    }

    @Override
    public ArrayValue _newArrayWithValue(ArrayClass aClass, Parameterized[] values)
    {
        return target._newArrayWithValue(aClass, values);
    }

    @Override
    public ArrayValue _newArrayWithValue(ArrayClass aClass, Parameterized[][] values)
    {
        return target._newArrayWithValue(aClass, values);
    }

    @Override
    public ArrayValue _newArrayWithValue(ArrayClass aClass, Parameterized[][][] values)
    {
        return target._newArrayWithValue(aClass, values);
    }

    @Override
    public ArrayValue _newArrayWithValue(ArrayClass aClass, Parameterized[][][][] values)
    {
        return target._newArrayWithValue(aClass, values);
    }

    @Override
    public ArrayLoader _arrayLoad(ArrayValue arrayReference, Parameterized pardim, Parameterized... parDims)
    {
        return target._arrayLoad(arrayReference, pardim, parDims);
    }

    @Override
    public ArrayLoader _arrayLoad(IVariable arrayReference, Parameterized pardim, Parameterized... parDims)
    {
        return target._arrayLoad(arrayReference, pardim, parDims);
    }

    @Override
    public ArrayLoader _arrayLoad(MethodInvoker arrayReference, Parameterized pardim, Parameterized... parDims)
    {
        return target._arrayLoad(arrayReference, pardim, parDims);
    }

    @Override
    public ArrayLoader _arrayLoad(Assigner arrayReference, Parameterized pardim, Parameterized... parDims)
    {
        return target._arrayLoad(arrayReference, pardim, parDims);
    }

    @Override
    public ArrayLoader _arrayLoad(ArrayLoader arrayReference, Parameterized pardim, Parameterized... parDims)
    {
        return target._arrayLoad(arrayReference, pardim, parDims);
    }

    @Override
    public ArrayStorer _arrayStore(ArrayValue arrayReference, Parameterized value, Parameterized dim,
        Parameterized... dims)
    {
        return target._arrayStore(arrayReference, value, dim, dims);
    }

    @Override
    public ArrayStorer _arrayStore(IVariable arrayReference, Parameterized value, Parameterized dim,
        Parameterized... dims)
    {
        return target._arrayStore(arrayReference, value, dim, dims);
    }

    @Override
    public ArrayStorer _arrayStore(MethodInvoker arrayReference, Parameterized value, Parameterized dim,
        Parameterized... dims)
    {
        return target._arrayStore(arrayReference, value, dim, dims);
    }

    @Override
    public ArrayStorer _arrayStore(Assigner arrayReference, Parameterized value, Parameterized dim,
        Parameterized... dims)
    {
        return target._arrayStore(arrayReference, value, dim, dims);
    }

    @Override
    public ArrayStorer _arrayStore(ArrayLoader arrayReference, Parameterized value, Parameterized dim,
        Parameterized... dims)
    {
        return target._arrayStore(arrayReference, value, dim, dims);
    }

    @Override
    public ArrayLength _arrayLength(ArrayValue arrayReference, Parameterized... dims)
    {
        return target._arrayLength(arrayReference, dims);
    }

    @Override
    public ArrayLength _arrayLength(IVariable arrayReference, Parameterized... dims)
    {
        return target._arrayLength(arrayReference, dims);
    }

    @Override
    public ArrayLength _arrayLength(MethodInvoker arrayReference, Parameterized... dims)
    {
        return target._arrayLength(arrayReference, dims);
    }

    @Override
    public ArrayLength _arrayLength(Assigner arrayReference, Parameterized... dims)
    {
        return target._arrayLength(arrayReference, dims);
    }

    @Override
    public ArrayLength _arrayLength(ArrayLoader arrayReference, Parameterized... dims)
    {
        return target._arrayLength(arrayReference, dims);
    }

    @Override
    public Addition _add(Parameterized factor1, Parameterized factor2)
    {
        return target._add(factor1, factor2);
    }

    @Override
    public Subtraction _sub(Parameterized factor1, Parameterized factor2)
    {
        return target._sub(factor1, factor2);
    }

    @Override
    public Multiplication _mul(Parameterized factor1, Parameterized factor2)
    {
        return target._mul(factor1, factor2);
    }

    @Override
    public Division _div(Parameterized factor1, Parameterized factor2)
    {
        return target._div(factor1, factor2);
    }

    @Override
    public Modulus _mod(Parameterized factor1, Parameterized factor2)
    {
        return target._mod(factor1, factor2);
    }

    @Override
    public Reverse _reverse(Parameterized factor)
    {
        return target._reverse(factor);
    }

    @Override
    public BitAnd _band(Parameterized factor1, Parameterized factor2)
    {
        return target._band(factor1, factor2);
    }

    @Override
    public BitOr _bor(Parameterized factor1, Parameterized factor2)
    {
        return target._bor(factor1, factor2);
    }

    @Override
    public BitXor _bxor(Parameterized factor1, Parameterized factor2)
    {
        return target._bxor(factor1, factor2);
    }

    @Override
    public ShiftLeft _shl(Parameterized factor1, Parameterized factor2)
    {
        return target._shl(factor1, factor2);
    }

    @Override
    public ShiftRight _shr(Parameterized factor1, Parameterized factor2)
    {
        return target._shr(factor1, factor2);
    }

    @Override
    public UnsignedShiftRight _ushr(Parameterized factor1, Parameterized factor2)
    {
        return target._ushr(factor1, factor2);
    }


	@Override
	public PreposeDecrment _preDec(Crementable crement) {
		return target._preDec(crement);
	}

	@Override
	public PostposeDecrment _postDec(Crementable crement) {
		return target._postDec(crement);
	}

	@Override
	public PreposeIncrment _preInc(Crementable crement) {
		return target._preInc(crement);
	}

	@Override
	public PostposeIncrment _postInc(Crementable crement) {
		return target._postInc(crement);
	}
	

    @Override
    public GreaterThan _gt(Parameterized factor1, Parameterized factor2)
    {
        return target._gt(factor1, factor2);
    }

    @Override
    public GreaterEqual _ge(Parameterized factor1, Parameterized factor2)
    {
        return target._ge(factor1, factor2);
    }

    @Override
    public LessThan _lt(Parameterized factor1, Parameterized factor2)
    {
        return target._lt(factor1, factor2);
    }

    @Override
    public LessEqual _le(Parameterized factor1, Parameterized factor2)
    {
        return target._le(factor1, factor2);
    }

    @Override
    public Equal _eq(Parameterized factor1, Parameterized factor2)
    {
        return target._eq(factor1, factor2);
    }

    @Override
    public NotEqual _ne(Parameterized factor1, Parameterized factor2)
    {
        return target._ne(factor1, factor2);
    }

    @Override
    public LogicalAnd _logicalAnd(Parameterized factor1, Parameterized factor2)
    {
        return target._logicalAnd(factor1, factor2);
    }

    @Override
    public LogicalOr _logicalOr(Parameterized factor1, Parameterized factor2)
    {
        return target._logicalOr(factor1, factor2);
    }

    @Override
    public LogicalXor _logicalXor(Parameterized factor1, Parameterized factor2)
    {
        return target._logicalXor(factor1, factor2);
    }

    @Override
    public ShortCircuitAnd _and(Parameterized factor1, Parameterized factor2, Parameterized... otherFactor)
    {
        return target._and(factor1, factor2, otherFactor);
    }

    @Override
    public ShortCircuitOr _or(Parameterized factor1, Parameterized factor2, Parameterized... otherFactor)
    {
        return target._or(factor1, factor2, otherFactor);
    }

    @Override
    public Not _not(Parameterized factor)
    {
        return target._not(factor);
    }

    @Override
    public CheckCast _checkcast(Parameterized cc, AClass to)
    {
        return target._checkcast(cc, to);
    }

    @Override
    public CheckCast _checkcast(Parameterized cc, Class<?> to) {
        return target._checkcast(cc, to);
    }

    @Override
    public Negative _neg(Parameterized factor)
    {
        return target._neg(factor);
    }

    @Override
    public TernaryOperator _ternary(Parameterized exp1, Parameterized exp2, Parameterized exp3)
    {
        return target._ternary(exp1, exp2, exp3);
    }

    @Override
    public Parameterized _append(Parameterized par1, Parameterized... pars)
    {
        return target._append(par1, pars);
    }

    @Override
    public Parameterized _instanceof(Parameterized obj, AClass type)
    {
        return target._instanceof(obj, type);
    }

    @Override
    public void _break()
    {
        target._break();
    }

    @Override
    public void _continue()
    {
        target._continue();
    }

    @Override
    public void _throw(Parameterized exception)
    {
        target._throw(exception);
    }

    @Override
    public Return _return()
    {
        return target._return();
    }

    @Override
    public Return _return(Parameterized parame)
    {
        return target._return(parame);
    }

    @Override
	public IF _if(IF ifBlock) {
		target._if(ifBlock.target);
		return ifBlock;
	}

    @Override
    public While _while(While whileLoop)
    {
    	target._while(whileLoop.target);
    	return whileLoop;
    }

    @Override
	public DoWhile _dowhile(DoWhile doWhile) {
	    target._dowhile(doWhile.target);
	    return doWhile;
	}

    @Override
	public ForEach _for(ForEach forEach) {
		target._for(forEach.target);
		return forEach;
	}

    @Override
    public Try _try(Try tryClient)
    {
        target._try(tryClient.target);
        return tryClient;
    }

    @Override
	public Synchronized _sync(Synchronized sync) {
		target._sync(sync.target);
		return sync;
	}

}
