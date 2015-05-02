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
package cn.wensiqun.asmsupport.client.block;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.action.AddAction;
import cn.wensiqun.asmsupport.client.def.action.AndAction;
import cn.wensiqun.asmsupport.client.def.action.ArrayLengthAction;
import cn.wensiqun.asmsupport.client.def.action.BandAction;
import cn.wensiqun.asmsupport.client.def.action.BorAction;
import cn.wensiqun.asmsupport.client.def.action.BxorAction;
import cn.wensiqun.asmsupport.client.def.action.DivAction;
import cn.wensiqun.asmsupport.client.def.action.EqualAction;
import cn.wensiqun.asmsupport.client.def.action.GreaterEqualAction;
import cn.wensiqun.asmsupport.client.def.action.GreaterThanAction;
import cn.wensiqun.asmsupport.client.def.action.InstanceofAction;
import cn.wensiqun.asmsupport.client.def.action.LessEqualAction;
import cn.wensiqun.asmsupport.client.def.action.LessThanAction;
import cn.wensiqun.asmsupport.client.def.action.LogicAndAction;
import cn.wensiqun.asmsupport.client.def.action.LogicOrAction;
import cn.wensiqun.asmsupport.client.def.action.LogicXorAction;
import cn.wensiqun.asmsupport.client.def.action.ModAction;
import cn.wensiqun.asmsupport.client.def.action.MulAction;
import cn.wensiqun.asmsupport.client.def.action.NegAction;
import cn.wensiqun.asmsupport.client.def.action.NotAction;
import cn.wensiqun.asmsupport.client.def.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.def.action.OrAction;
import cn.wensiqun.asmsupport.client.def.action.PostDecAction;
import cn.wensiqun.asmsupport.client.def.action.PostIncAction;
import cn.wensiqun.asmsupport.client.def.action.PreDecAction;
import cn.wensiqun.asmsupport.client.def.action.PreIncAction;
import cn.wensiqun.asmsupport.client.def.action.ReverseAction;
import cn.wensiqun.asmsupport.client.def.action.ShiftLeftAction;
import cn.wensiqun.asmsupport.client.def.action.ShiftRightAction;
import cn.wensiqun.asmsupport.client.def.action.SubAction;
import cn.wensiqun.asmsupport.client.def.action.UnsignedShiftRightAction;
import cn.wensiqun.asmsupport.client.def.param.ArrayParam;
import cn.wensiqun.asmsupport.client.def.param.BoolParam;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.client.def.param.NullParam;
import cn.wensiqun.asmsupport.client.def.param.NumParam;
import cn.wensiqun.asmsupport.client.def.param.ObjectParam;
import cn.wensiqun.asmsupport.client.def.param.UncertainParam;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Super;
import cn.wensiqun.asmsupport.client.def.var.This;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.variable.IVariable;
import cn.wensiqun.asmsupport.core.definition.variable.LocalVariable;
import cn.wensiqun.asmsupport.standard.action.ActionSet;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

/**
 * A wrapper of {@link KernelProgramBlock}
 * 
 * @author WSQ
 *
 * @param <B>
 */
public class ProgramBlock<B extends KernelProgramBlock> implements ActionSet<
Param, Var, FieldVar,
IF, While, DoWhile, ForEach, Try, Sync> {

	B targetBlock;

	KernelProgramBlockCursor cursor;
	
	ProgramBlock<? extends KernelProgramBlock> parent;
	
	public LocalVariable[] getMethodArguments() {
		return targetBlock.getMethodArguments();
	}

	/**
	 * get current method owner.
	 * 
	 * @return
	 */
	public AClass getMethodOwner() {
		return targetBlock.getMethodOwner();
	}

	@Override
	public This this_() {
		return new This(cursor, targetBlock.this_());
	}

	@Override
	public FieldVar this_(String name) {
		return new FieldVar(cursor, targetBlock.this_(name));
	}

	@Override
	public Super super_() {
		return new Super(cursor, targetBlock.super_());
	}

	@Override
	public LocVar var(String name, Class<?> type, Param para) {
		return new LocVar(cursor, targetBlock.var(name, type, ParamPostern.getTarget(para)));
	}

	@Override
	public LocVar var(Class<?> type, Param para) {
		return new LocVar(cursor, targetBlock.var(type, ParamPostern.getTarget(para)));
	}

	@Override
	public LocVar var(String name, AClass type, Param para) {
		return new LocVar(cursor, targetBlock.var(name, type, ParamPostern.getTarget(para)));
	}

	@Override
	public LocVar var(AClass type, Param para) {
		return new LocVar(cursor, targetBlock.var(type, ParamPostern.getTarget(para)));
	}

	@Override
	public FieldVar field(String name) {
		return new FieldVar(cursor, targetBlock.field(name));
	}

	@Override
	public UncertainParam assign(Var variable, Param val) {
		return new UncertainParam(cursor, targetBlock.assign((IVariable) ParamPostern.getTarget(variable), ParamPostern.getTarget(val)));
	}

	@Override
	public UncertainParam call(Param objRef, String methodName, Param... arguments) {
		return new UncertainParam(cursor, targetBlock.call(ParamPostern.getTarget(objRef), methodName, ParamPostern.getTarget(arguments)));
	}

	@Override
	public UncertainParam call(String methodName, Param... args) {
		return new UncertainParam(cursor, targetBlock.call(methodName, ParamPostern.getTarget(args)));
	}

	@Override
	public UncertainParam call(AClass owner, String methodName, Param... arguments) {
		return new UncertainParam(cursor, targetBlock.call(owner, methodName, ParamPostern.getTarget(arguments)));
	}
    
	@Override
    public UncertainParam call(Class<?> owner, String methodName, Param... arguments) {
    	return new UncertainParam(cursor, targetBlock.call(owner, methodName, ParamPostern.getTarget(arguments)));
    }

	@Override
	public UncertainParam new_(Class<?> owner, Param... arguments) {
		return new UncertainParam(cursor, targetBlock.new_(owner, ParamPostern.getTarget(arguments)));
	}

	@Override
	public UncertainParam new_(AClass owner, Param... arguments) {
		return new UncertainParam(cursor, targetBlock.new_(owner, ParamPostern.getTarget(arguments)));
	}

	@Override
	public UncertainParam callOrig() {
		return new UncertainParam(cursor, targetBlock.callOrig());
	}

	@Override
	public ArrayParam makeArray(AClass aClass, Param... allocateDims) {
		return new ArrayParam(cursor, targetBlock.makeArray(aClass, ParamPostern.getTarget(allocateDims)));
	}

	@Override
	public ArrayParam makeArray(Class<?> arraytype, Param... dimensions) {
		return new ArrayParam(cursor, targetBlock.makeArray(arraytype, ParamPostern.getTarget(dimensions)));
	}

	@Override
	public ArrayParam newarray(AClass aClass, Object arrayObject) {
		return new ArrayParam(cursor, targetBlock.newarray(aClass, ParamPostern.getTarget(arrayObject)));
	}

	/**
	 * The second parameter must be a array and element type of array is {@link Param} type
	 */
	@Override
	public ArrayParam newarray(Class<?> type, Object arrayObject) {
		return new ArrayParam(cursor, targetBlock.newarray(type, ParamPostern.getTarget(arrayObject)));
	}

	@Override
	public UncertainParam arrayLoad(Param arrayReference, Param pardim, Param... parDims) {
		return new UncertainParam(cursor, targetBlock.arrayLoad(ParamPostern.getTarget(arrayReference), ParamPostern.getTarget(pardim), ParamPostern.getTarget(parDims)));
	}
	
	@Override
	public UncertainParam arrayStore(Param arrayReference, Param value, Param dim, Param... dims) {
		return new UncertainParam(cursor, targetBlock.arrayStore(ParamPostern.getTarget(arrayReference), 
		        ParamPostern.getTarget(value), ParamPostern.getTarget(dim), ParamPostern.getTarget(dims)));
	}

	@Override
	public NumParam arrayLength(Param arrayReference, Param... dims) {
	    Param[] operands = ParamPostern.unionParam(arrayReference, dims);
		return new NumParam(cursor, new ArrayLengthAction(cursor, operands.length - 1), operands);
	}
	
	@Override
	public NumParam add(Param factor1, Param factor2) {
	    return new NumParam(cursor, new AddAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam sub(Param factor1, Param factor2) {
        return new NumParam(cursor, new SubAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam mul(Param factor1, Param factor2) {
        return new NumParam(cursor, new MulAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam div(Param factor1, Param factor2) {
        return new NumParam(cursor, new DivAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam mod(Param factor1, Param factor2) {
        return new NumParam(cursor, new ModAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam reverse(Param factor) {
		return new NumParam(cursor, new ReverseAction(cursor), factor);
	}

	@Override
	public NumParam band(Param factor1, Param factor2) {
		return new NumParam(cursor, new BandAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam bor(Param factor1, Param factor2) {
        return new NumParam(cursor, new BorAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam bxor(Param factor1, Param factor2) {
        return new NumParam(cursor, new BxorAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam shl(Param factor1, Param factor2) {
		return new NumParam(cursor, new ShiftLeftAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam shr(Param factor1, Param factor2) {
        return new NumParam(cursor, new ShiftRightAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam ushr(Param factor1, Param factor2) {
        return new NumParam(cursor, new UnsignedShiftRightAction(cursor), factor1, factor2);
	}

	@Override
	public NumParam predec(Param crement) {
        return new NumParam(cursor, new PreDecAction(cursor), crement);
	}

	@Override
	public NumParam postdec(Param crement) {
        return new NumParam(cursor, new PostDecAction(cursor), crement);
	}

	@Override
	public NumParam preinc(Param crement) {
        return new NumParam(cursor, new PreIncAction(cursor), crement);
	}

	@Override
	public NumParam postinc(Param crement) {
        return new NumParam(cursor, new PostIncAction(cursor), crement);
	}

	@Override
	public BoolParam gt(Param factor1, Param factor2) {
		return new BoolParam(cursor, new GreaterThanAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam ge(Param factor1, Param factor2) {
        return new BoolParam(cursor, new GreaterEqualAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam lt(Param factor1, Param factor2) {
        return new BoolParam(cursor, new LessThanAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam le(Param factor1, Param factor2) {
        return new BoolParam(cursor, new LessEqualAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam eq(Param factor1, Param factor2) {
        return new BoolParam(cursor, new EqualAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam ne(Param factor1, Param factor2) {
        return new BoolParam(cursor, new NotEqualAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam logicalAnd(Param factor1, Param factor2) {
		return new BoolParam(cursor, new LogicAndAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam logicalOr(Param factor1, Param factor2) {
        return new BoolParam(cursor, new LogicOrAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam logicalXor(Param factor1, Param factor2) {
        return new BoolParam(cursor, new LogicXorAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam and(Param factor1, Param factor2, Param... otherFactor) {
        return new BoolParam(cursor, new AndAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam or(Param factor1, Param factor2, Param... otherFactor) {
        return new BoolParam(cursor, new OrAction(cursor), factor1, factor2);
	}

	@Override
	public BoolParam no(Param factor) {
        return new BoolParam(cursor, new NotAction(cursor), factor);
	}

	@Override
	public UncertainParam checkcast(Param cc, AClass to) {
		return new UncertainParam(cursor, targetBlock.checkcast(ParamPostern.getTarget(cc), to));
	}

	@Override
	public UncertainParam checkcast(Param cc, Class<?> to) {
		return new UncertainParam(cursor, targetBlock.checkcast(ParamPostern.getTarget(cc), to));
	}

	@Override
	public NumParam neg(Param factor) {
		return new NumParam(cursor, new NegAction(cursor), factor);
	}

	@Override
	public DummyParam ternary(Param exp1, Param exp2, Param exp3) {
		return new DummyParam(cursor, targetBlock.ternary(ParamPostern.getTarget(exp1), ParamPostern.getTarget(exp2), ParamPostern.getTarget(exp3)));
	}

	@Override
	public UncertainParam stradd(Param par1, Param... pars) {
		return new UncertainParam(cursor, targetBlock.stradd(ParamPostern.getTarget(par1), ParamPostern.getTarget(pars)));
	}

	@Override
	public BoolParam instanceof_(Param obj, AClass type) {
		return new BoolParam(cursor, new InstanceofAction(cursor, type), obj);
	}

	@Override
	public BoolParam instanceof_(Param obj, Class<?> type) {
        return new BoolParam(cursor, new InstanceofAction(cursor, getType(type)), obj);
	}

	@Override
	public void break_() {
		targetBlock.break_();
	}

	@Override
	public void continue_() {
		targetBlock.continue_();
	}

	@Override
	public void throw_(Param exception) {
		targetBlock.throw_(ParamPostern.getTarget(exception));
	}

	@Override
	public void return_() {
		targetBlock.return_();
	}

	@Override
	public void return_(Param param) {
		targetBlock.return_(ParamPostern.getTarget(param));
	}

	@Override
	public IF if_(IF ifBlock) {
	    ifBlock.cursor = cursor;
	    ifBlock.parent = this;
        cursor.setPointer(ifBlock.targetBlock);
        
		targetBlock.if_(ifBlock.targetBlock);
		
        cursor.setPointer(targetBlock);
		return ifBlock;
	}

	@Override
	public While while_(While whileLoop) {
	    whileLoop.cursor = cursor;
	    whileLoop.parent = this;
        cursor.setPointer(whileLoop.targetBlock);
        
		targetBlock.while_(whileLoop.targetBlock);
		
        cursor.setPointer(targetBlock);
		return whileLoop;
	}

	@Override
	public DoWhile dowhile(DoWhile doWhile) {
	    doWhile.cursor = cursor;
	    doWhile.parent = this;
        cursor.setPointer(doWhile.targetBlock);
        
		targetBlock.dowhile(doWhile.targetBlock);
		
        cursor.setPointer(targetBlock);
		return doWhile;
	}

	@Override
	public ForEach for_(ForEach forEach) {
	    forEach.cursor = cursor;
	    forEach.parent = this;
        cursor.setPointer(forEach.targetBlock);
        
		targetBlock.for_(forEach.targetBlock);
		
        cursor.setPointer(targetBlock);
		return forEach;
	}

	@Override
	public Try try_(Try tryClient) {
	    tryClient.cursor = cursor;
	    tryClient.parent = this;
        cursor.setPointer(tryClient.targetBlock);
        
		targetBlock.try_(tryClient.targetBlock);
		
        cursor.setPointer(targetBlock);
		return tryClient;
	}

	@Override
	public Sync sync(Sync sync) {
	    sync.cursor = cursor;
	    sync.parent = this;
	    cursor.setPointer(sync.targetBlock);
	    
		targetBlock.sync(sync.targetBlock);
		
		cursor.setPointer(targetBlock);
		return sync;
	}

	@Override
	public NumParam val(Integer val) {
		return new NumParam(cursor, new DummyParam(cursor, targetBlock.val(val)));
	}

	@Override
	public NumParam val(Short val) {
		return new NumParam(cursor, new DummyParam(cursor, targetBlock.val(val)));
	}

	@Override
	public NumParam val(Byte val) {
		return new NumParam(cursor, new DummyParam(cursor, targetBlock.val(val)));
	}

	@Override
	public DummyParam val(Boolean val) {
		return new DummyParam(cursor, targetBlock.val(val));
	}

	@Override
	public NumParam val(Long val) {
		return new NumParam(cursor, new DummyParam(cursor, targetBlock.val(val)));
	}

	@Override
	public NumParam val(Double val) {
		return new NumParam(cursor, new DummyParam(cursor, targetBlock.val(val)));
	}

	@Override
	public NumParam val(Character val) {
		return new NumParam(cursor, new DummyParam(cursor, targetBlock.val(val)));
	}

	@Override
	public NumParam val(Float val) {
		return new NumParam(cursor, new DummyParam(cursor, targetBlock.val(val)));
	}

	@Override
	public ObjectParam val(AClass val) {
		return new ObjectParam(cursor, targetBlock.val(val));
	}

	@Override
	public ObjectParam val(Class<?> val) {
		return new ObjectParam(cursor, targetBlock.val(val));
	}

	@Override
	public ObjectParam val(String val) {
		return new ObjectParam(cursor, targetBlock.val(val));
	}

	@Override
	public NullParam null_(AClass type) {
		return new NullParam(cursor, targetBlock.null_(type));
	}

	@Override
	public NullParam null_(Class<?> type) {
		return new NullParam(cursor, targetBlock.null_(type));
	}

	@Override
	public AClass getType(Class<?> cls) {
		return targetBlock.getType(cls);
	}

	@Override
	public AClass getArrayType(Class<?> cls, int dim) {
		return targetBlock.getArrayType(cls, dim);
	}

	@Override
	public AClass getArrayType(AClass rootComponent, int dim) {
		return targetBlock.getArrayType(rootComponent, dim);
	}
	
    LocVar[] internalVar2ClientVar(LocalVariable... pars) {
        if(pars == null) {
            return null;
        }
        LocVar[] paras = new LocVar[pars.length];
        for(int i=0; i<pars.length; i++) {
            paras[i] = new LocVar(cursor, pars[i]);
        }
        return paras;
    } 
    
}
