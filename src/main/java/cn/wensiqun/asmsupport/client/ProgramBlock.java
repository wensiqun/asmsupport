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
package cn.wensiqun.asmsupport.client;

import java.lang.reflect.Array;

import cn.wensiqun.asmsupport.client.def.value.ClientValue;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Super;
import cn.wensiqun.asmsupport.client.def.var.This;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.client.operations.Add;
import cn.wensiqun.asmsupport.client.operations.ArrayLength;
import cn.wensiqun.asmsupport.client.operations.ArrayLoad;
import cn.wensiqun.asmsupport.client.operations.ArrayStore;
import cn.wensiqun.asmsupport.client.operations.ArrayValue;
import cn.wensiqun.asmsupport.client.operations.Assign;
import cn.wensiqun.asmsupport.client.operations.Band;
import cn.wensiqun.asmsupport.client.operations.Bor;
import cn.wensiqun.asmsupport.client.operations.Bxor;
import cn.wensiqun.asmsupport.client.operations.Cast;
import cn.wensiqun.asmsupport.client.operations.Crement;
import cn.wensiqun.asmsupport.client.operations.Div;
import cn.wensiqun.asmsupport.client.operations.Equal;
import cn.wensiqun.asmsupport.client.operations.GreaterEqual;
import cn.wensiqun.asmsupport.client.operations.GreaterThan;
import cn.wensiqun.asmsupport.client.operations.Instanceof;
import cn.wensiqun.asmsupport.client.operations.LessEqual;
import cn.wensiqun.asmsupport.client.operations.LessThan;
import cn.wensiqun.asmsupport.client.operations.LogiclAnd;
import cn.wensiqun.asmsupport.client.operations.LogicOr;
import cn.wensiqun.asmsupport.client.operations.LogicXor;
import cn.wensiqun.asmsupport.client.operations.Call;
import cn.wensiqun.asmsupport.client.operations.Mod;
import cn.wensiqun.asmsupport.client.operations.Mul;
import cn.wensiqun.asmsupport.client.operations.Neg;
import cn.wensiqun.asmsupport.client.operations.Not;
import cn.wensiqun.asmsupport.client.operations.NotEqual;
import cn.wensiqun.asmsupport.client.operations.Reverse;
import cn.wensiqun.asmsupport.client.operations.ShiftLeft;
import cn.wensiqun.asmsupport.client.operations.ShiftRight;
import cn.wensiqun.asmsupport.client.operations.And;
import cn.wensiqun.asmsupport.client.operations.Or;
import cn.wensiqun.asmsupport.client.operations.StrAdd;
import cn.wensiqun.asmsupport.client.operations.Sub;
import cn.wensiqun.asmsupport.client.operations.Ternary;
import cn.wensiqun.asmsupport.client.operations.UnShiftRight;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.definition.KernelParameterized;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.action.ActionSet;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ProgramBlock<B extends ProgramBlockInternal> implements ActionSet<
Param, Var, FieldVar,
IF, While, DoWhile, ForEach, Try, Sync> {

	B target;

	public LocalVariable[] getMethodArguments() {
		return target.getMethodArguments();
	}

	/**
	 * get current method owner.
	 * 
	 * @return
	 */
	public AClass getMethodOwner() {
		return target.getMethodOwner();
	}

	@Override
	public This this_() {
		return new This(target.this_());
	}

	@Override
	public FieldVar this_(String name) {
		return new FieldVar(target.this_(name));
	}

	@Override
	public Super super_() {
		return new Super(target.super_());
	}

	@Override
	public LocVar var(String name, Class<?> type, Param para) {
		return new LocVar(target.var(name, type, para.target));
	}

	@Override
	public LocVar var(Class<?> type, Param para) {
		return new LocVar(target.var(type, para.target));
	}

	@Override
	public LocVar var(String name, AClass type, Param para) {
		return new LocVar(target.var(name, type, para.target));
	}

	@Override
	public LocVar var(AClass type, Param para) {
		return new LocVar(target.var(type, para.target));
	}

	@Override
	public FieldVar field(String name) {
		return new FieldVar(target.field(name));
	}

	@Override
	public Assign assign(Var variable, Param val) {
		return new Assign(target.assign((IVariable) variable.getTarget(), val.target));
	}

	@Override
	public Call call(Param objRef, String methodName, Param... arguments) {
		return new Call(target.call(objRef.target, methodName, client2Internal(arguments)));
	}

	@Override
	public Call call(String methodName, Param... args) {
		return new Call(target.call(methodName, client2Internal(args)));
	}

	@Override
	public Call call(AClass owner, String methodName, Param... arguments) {
		return new Call(target.call(owner, methodName, client2Internal(arguments)));
	}
    
	@Override
    public Call call(Class<?> owner, String methodName, Param... arguments) {
    	return new Call(target.call(owner, methodName, client2Internal(arguments)));
    }

	@Override
	public Call new_(Class<?> owner, Param... arguments) {
		return new Call(target.new_(owner, client2Internal(arguments)));
	}

	@Override
	public Call new_(AClass owner, Param... arguments) {
		return new Call(target.new_(owner, client2Internal(arguments)));
	}

	@Override
	public Call callOrig() {
		return new Call(target.callOrig());
	}

	@Override
	public ArrayValue makeArray(AClass aClass, Param... allocateDims) {
		return new ArrayValue(target.makeArray(aClass, client2Internal(allocateDims)));
	}

	@Override
	public ArrayValue makeArray(Class<?> arraytype, Param... dimensions) {
		return new ArrayValue(target.makeArray(arraytype, client2Internal(dimensions)));
	}

	@Override
	public ArrayValue newarray(AClass aClass, Object arrayObject) {
		return new ArrayValue(target.newarray(aClass, client2Internal(arrayObject)));
	}

	@Override
	public ArrayValue newarray(Class<?> type, Object arrayObject) {
		return new ArrayValue(target.newarray(type, client2Internal(arrayObject)));
	}

	@Override
	public ArrayLoad arrayLoad(Param arrayReference,
	        Param pardim, Param... parDims) {
		return new ArrayLoad(target.arrayLoad(arrayReference.target, pardim.target, client2Internal(parDims)));
	}
	
	@Override
	public ArrayStore arrayStore(Param arrayReference,
	        Param value, Param dim, Param... dims) {
		return new ArrayStore(target.arrayStore(arrayReference.target, value.target, dim.target, client2Internal(dims)));
	}

	@Override
	public ArrayLength arrayLength(Param arrayReference,
	        Param... dims) {
		return new ArrayLength(target.arrayLength(arrayReference.target, client2Internal(dims)));
	}
	
	@Override
	public Add add(Param factor1, Param factor2) {
		return new Add(target.add(factor1.target, factor2.target));
	}

	@Override
	public Sub sub(Param factor1, Param factor2) {
		return new Sub(target.sub(factor1.target, factor2.target));
	}

	@Override
	public Mul mul(Param factor1, Param factor2) {
		return new Mul(target.mul(factor1.target, factor2.target));
	}

	@Override
	public Div div(Param factor1, Param factor2) {
		return new Div(target.div(factor1.target, factor2.target));
	}

	@Override
	public Mod mod(Param factor1, Param factor2) {
		return new Mod(target.mod(factor1.target, factor2.target));
	}

	@Override
	public Reverse reverse(Param factor) {
		return new Reverse(target.reverse(factor.target));
	}

	@Override
	public Band band(Param factor1, Param factor2) {
		return new Band(target.band(factor1.target, factor2.target));
	}

	@Override
	public Bor bor(Param factor1, Param factor2) {
		return new Bor(target.bor(factor1.target, factor2.target));
	}

	@Override
	public Bxor bxor(Param factor1, Param factor2) {
		return new Bxor(target.bxor(factor1.target, factor2.target));
	}

	@Override
	public ShiftLeft shl(Param factor1, Param factor2) {
		return new ShiftLeft(target.shl(factor1.target, factor2.target));
	}

	@Override
	public ShiftRight shr(Param factor1, Param factor2) {
		return new ShiftRight(target.shr(factor1.target, factor2.target));
	}

	@Override
	public UnShiftRight ushr(Param factor1, Param factor2) {
		return new UnShiftRight(target.ushr(factor1.target, factor2.target));
	}

	@Override
	public Crement predec(Param crement) {
		return new Crement(target.predec(crement.target));
	}

	@Override
	public Crement postdec(Param crement) {
		return new Crement(target.postdec(crement.target));
	}

	@Override
	public Crement preinc(Param crement) {
		return new Crement(target.preinc(crement.target));
	}

	@Override
	public Crement postinc(Param crement) {
		return new Crement(target.postinc(crement.target));
	}

	@Override
	public GreaterThan gt(Param factor1, Param factor2) {
		return new GreaterThan(target.gt(factor1.target, factor2.target));
	}

	@Override
	public GreaterEqual ge(Param factor1, Param factor2) {
		return new GreaterEqual(target.ge(factor1.target, factor2.target));
	}

	@Override
	public LessThan lt(Param factor1, Param factor2) {
		return new LessThan(target.lt(factor1.target, factor2.target));
	}

	@Override
	public LessEqual le(Param factor1, Param factor2) {
		return new LessEqual(target.le(factor1.target, factor2.target));
	}

	@Override
	public Equal eq(Param factor1, Param factor2) {
		return new Equal(target.eq(factor1.target, factor2.target));
	}

	@Override
	public NotEqual ne(Param factor1, Param factor2) {
		return new NotEqual(target.ne(factor1.target, factor2.target));
	}

	@Override
	public LogiclAnd logicalAnd(Param factor1, Param factor2) {
		return new LogiclAnd(target.logicalAnd(factor1.target, factor2.target));
	}

	@Override
	public LogicOr logicalOr(Param factor1, Param factor2) {
		return new LogicOr(target.logicalOr(factor1.target, factor2.target));
	}

	@Override
	public LogicXor logicalXor(Param factor1, Param factor2) {
		return new LogicXor(target.logicalXor(factor1.target, factor2.target));
	}

	@Override
	public And and(Param factor1, Param factor2,
	        Param... otherFactor) {
		return new And(target.and(factor1.target, factor2.target, client2Internal(otherFactor)));
	}

	@Override
	public Or or(Param factor1, Param factor2,
	        Param... otherFactor) {
		return new Or(target.or(factor1.target, factor2.target, client2Internal(otherFactor)));
	}

	@Override
	public Not no(Param factor) {
		return new Not(target.no(factor.target));
	}

	@Override
	public Cast checkcast(Param cc, AClass to) {
		return new Cast(target.checkcast(cc.target, to));
	}

	@Override
	public Cast checkcast(Param cc, Class<?> to) {
		return new Cast(target.checkcast(cc.target, to));
	}

	@Override
	public Neg neg(Param factor) {
		return new Neg(target.neg(factor.target));
	}

	@Override
	public Ternary ternary(Param exp1, Param exp2,
	        Param exp3) {
		return new Ternary(target.ternary(exp1.target, exp2.target, exp3.target));
	}

	@Override
	public StrAdd stradd(Param par1, Param... pars) {
		return new StrAdd(target.stradd(par1.target, client2Internal(pars)));
	}

	@Override
	public Instanceof instanceof_(Param obj, AClass type) {
		return new Instanceof(target.instanceof_(obj.target, type));
	}

	@Override
	public Instanceof instanceof_(Param obj, Class<?> type) {
		return new Instanceof(target.instanceof_(obj.target, type));
	}

	@Override
	public void break_() {
		target.break_();
	}

	@Override
	public void continue_() {
		target.continue_();
	}

	@Override
	public void throw_(Param exception) {
		target.throw_(exception.target);
	}

	@Override
	public void return_() {
		target.return_();
	}

	@Override
	public void return_(Param parame) {
		target.return_(parame.target);
	}

	@Override
	public IF if_(IF ifBlock) {
		target.if_(ifBlock.target);
		return ifBlock;
	}

	@Override
	public While while_(While whileLoop) {
		target.while_(whileLoop.target);
		return whileLoop;
	}

	@Override
	public DoWhile dowhile(DoWhile doWhile) {
		target.dowhile(doWhile.target);
		return doWhile;
	}

	@Override
	public ForEach for_(ForEach forEach) {
		target.for_(forEach.target);
		return forEach;
	}

	@Override
	public Try try_(Try tryClient) {
		target.try_(tryClient.target);
		return tryClient;
	}

	@Override
	public Sync sync(Sync sync) {
		target.sync(sync.target);
		return sync;
	}

	@Override
	public ClientValue val(Integer val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Short val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Byte val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Boolean val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Long val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Double val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Character val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Float val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(AClass val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(Class<?> val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue val(String val) {
		return new ClientValue(target.val(val));
	}

	@Override
	public ClientValue null_(AClass type) {
		return new ClientValue(target.null_(type));
	}

	@Override
	public ClientValue null_(Class<?> type) {
		return new ClientValue(target.null_(type));
	}

	@Override
	public AClass getType(Class<?> cls) {
		return target.getType(cls);
	}

	@Override
	public AClass getArrayType(Class<?> cls, int dim) {
		return target.getArrayType(cls, dim);
	}

	@Override
	public AClass getArrayType(AClass rootComponent, int dim) {
		return target.getArrayType(rootComponent, dim);
	}
	
	/**
     * Convert ClientParameterized to InternalParameterized
     * 
     * @param pars
     * @return
     */
    KernelParameterized[] client2Internal(Param... pars) {
        if(pars == null) {
            return null;
        }
        KernelParameterized[] paras = new KernelParameterized[pars.length];
        for(int i=0; i<pars.length; i++) {
            paras[i] = pars[i].target;
        }
        return paras;
    }
    

    LocVar[] internalVar2ClientVar(LocalVariable... pars) {
        if(pars == null) {
            return null;
        }
        LocVar[] paras = new LocVar[pars.length];
        for(int i=0; i<pars.length; i++) {
            paras[i] = new LocVar(pars[i]);
        }
        return paras;
    }
    
    /**
     * Convert multiple dimension ClientParameterized array to  InternalParameterized array.
     * 
     * @param clientArray
     * @return
     */
    Object client2Internal(Object clientArray) {
        if(clientArray == null) {
            throw new NullPointerException("Client is null.");
        }
        if(clientArray.getClass().isArray()) {
            int len = Array.getLength(clientArray);
            Object[] internalArray = new Object[len];
            for(int i=0; i<len; i++) {
                internalArray[i] = client2Internal(Array.get(clientArray, i));
            }
            return internalArray;
        } else if (clientArray instanceof Param){
            return ((Param)clientArray).target;
        }
        throw new IllegalArgumentException();
    }
}
