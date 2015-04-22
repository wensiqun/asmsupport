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

import cn.wensiqun.asmsupport.client.action.AddAction;
import cn.wensiqun.asmsupport.client.action.AndAction;
import cn.wensiqun.asmsupport.client.action.ArrayLengthAction;
import cn.wensiqun.asmsupport.client.action.BandAction;
import cn.wensiqun.asmsupport.client.action.BorAction;
import cn.wensiqun.asmsupport.client.action.BxorAction;
import cn.wensiqun.asmsupport.client.action.DivAction;
import cn.wensiqun.asmsupport.client.action.EqualAction;
import cn.wensiqun.asmsupport.client.action.GreaterEqualAction;
import cn.wensiqun.asmsupport.client.action.GreaterThanAction;
import cn.wensiqun.asmsupport.client.action.LessEqualAction;
import cn.wensiqun.asmsupport.client.action.LessThanAction;
import cn.wensiqun.asmsupport.client.action.LogicAndAction;
import cn.wensiqun.asmsupport.client.action.LogicOrAction;
import cn.wensiqun.asmsupport.client.action.LogicXorAction;
import cn.wensiqun.asmsupport.client.action.ModAction;
import cn.wensiqun.asmsupport.client.action.MulAction;
import cn.wensiqun.asmsupport.client.action.NotEqualAction;
import cn.wensiqun.asmsupport.client.action.OrAction;
import cn.wensiqun.asmsupport.client.action.ShiftLeftAction;
import cn.wensiqun.asmsupport.client.action.ShiftRightAction;
import cn.wensiqun.asmsupport.client.action.SubAction;
import cn.wensiqun.asmsupport.client.action.UnsignedShiftRightAction;
import cn.wensiqun.asmsupport.client.def.value.ClientValue;
import cn.wensiqun.asmsupport.client.def.var.FieldVar;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Super;
import cn.wensiqun.asmsupport.client.def.var.This;
import cn.wensiqun.asmsupport.client.def.var.Var;
import cn.wensiqun.asmsupport.client.operations.ArrayLoad;
import cn.wensiqun.asmsupport.client.operations.ArrayStore;
import cn.wensiqun.asmsupport.client.operations.ArrayValue;
import cn.wensiqun.asmsupport.client.operations.Assign;
import cn.wensiqun.asmsupport.client.operations.Call;
import cn.wensiqun.asmsupport.client.operations.Cast;
import cn.wensiqun.asmsupport.client.operations.Crement;
import cn.wensiqun.asmsupport.client.operations.Instanceof;
import cn.wensiqun.asmsupport.client.operations.Neg;
import cn.wensiqun.asmsupport.client.operations.Not;
import cn.wensiqun.asmsupport.client.operations.Reverse;
import cn.wensiqun.asmsupport.client.operations.StrAdd;
import cn.wensiqun.asmsupport.client.operations.Ternary;
import cn.wensiqun.asmsupport.client.param.BoolParam;
import cn.wensiqun.asmsupport.client.param.NumParam;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
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
		return new LocVar(target.var(name, type, para.getTarget()));
	}

	@Override
	public LocVar var(Class<?> type, Param para) {
		return new LocVar(target.var(type, para.getTarget()));
	}

	@Override
	public LocVar var(String name, AClass type, Param para) {
		return new LocVar(target.var(name, type, para.getTarget()));
	}

	@Override
	public LocVar var(AClass type, Param para) {
		return new LocVar(target.var(type, para.getTarget()));
	}

	@Override
	public FieldVar field(String name) {
		return new FieldVar(target.field(name));
	}

	@Override
	public Assign assign(Var variable, Param val) {
		return new Assign(target.assign((IVariable) ((Param)variable).getTarget(), val.getTarget()));
	}

	@Override
	public Call call(Param objRef, String methodName, Param... arguments) {
		return new Call(target.call(objRef.getTarget(), methodName, ParamPostern.getTarget(arguments)));
	}

	@Override
	public Call call(String methodName, Param... args) {
		return new Call(target.call(methodName, ParamPostern.getTarget(args)));
	}

	@Override
	public Call call(AClass owner, String methodName, Param... arguments) {
		return new Call(target.call(owner, methodName, ParamPostern.getTarget(arguments)));
	}
    
	@Override
    public Call call(Class<?> owner, String methodName, Param... arguments) {
    	return new Call(target.call(owner, methodName, ParamPostern.getTarget(arguments)));
    }

	@Override
	public Call new_(Class<?> owner, Param... arguments) {
		return new Call(target.new_(owner, ParamPostern.getTarget(arguments)));
	}

	@Override
	public Call new_(AClass owner, Param... arguments) {
		return new Call(target.new_(owner, ParamPostern.getTarget(arguments)));
	}

	@Override
	public Call callOrig() {
		return new Call(target.callOrig());
	}

	@Override
	public ArrayValue makeArray(AClass aClass, Param... allocateDims) {
		return new ArrayValue(target.makeArray(aClass, ParamPostern.getTarget(allocateDims)));
	}

	@Override
	public ArrayValue makeArray(Class<?> arraytype, Param... dimensions) {
		return new ArrayValue(target.makeArray(arraytype, ParamPostern.getTarget(dimensions)));
	}

	@Override
	public ArrayValue newarray(AClass aClass, Object arrayObject) {
		return new ArrayValue(target.newarray(aClass, ParamPostern.getTarget(arrayObject)));
	}

	@Override
	public ArrayValue newarray(Class<?> type, Object arrayObject) {
		return new ArrayValue(target.newarray(type, ParamPostern.getTarget(arrayObject)));
	}

	@Override
	public ArrayLoad arrayLoad(Param arrayReference,
	        Param pardim, Param... parDims) {
		return new ArrayLoad(target.arrayLoad(arrayReference.getTarget(), pardim.getTarget(), ParamPostern.getTarget(parDims)));
	}
	
	@Override
	public ArrayStore arrayStore(Param arrayReference,
	        Param value, Param dim, Param... dims) {
		return new ArrayStore(target.arrayStore(arrayReference.getTarget(), value.getTarget(), dim.getTarget(), ParamPostern.getTarget(dims)));
	}

	@Override
	public NumParam arrayLength(Param arrayReference, Param... dims) {
	    Param[] operands = unionParam(arrayReference, dims);
		return new NumParam(target, new ArrayLengthAction(target, operands.length), operands);
	}
	
	@Override
	public NumParam add(Param factor1, Param factor2) {
	    return new NumParam(target, new AddAction(target), factor1, factor2);
	}

	@Override
	public NumParam sub(Param factor1, Param factor2) {
        return new NumParam(target, new SubAction(target), factor1, factor2);
	}

	@Override
	public NumParam mul(Param factor1, Param factor2) {
        return new NumParam(target, new MulAction(target), factor1, factor2);
	}

	@Override
	public NumParam div(Param factor1, Param factor2) {
        return new NumParam(target, new DivAction(target), factor1, factor2);
	}

	@Override
	public NumParam mod(Param factor1, Param factor2) {
        return new NumParam(target, new ModAction(target), factor1, factor2);
	}

	@Override
	public Reverse reverse(Param factor) {
		return new Reverse(target.reverse(factor.getTarget()));
	}

	@Override
	public NumParam band(Param factor1, Param factor2) {
		return new NumParam(target, new BandAction(target), factor1, factor2);
	}

	@Override
	public NumParam bor(Param factor1, Param factor2) {
        return new NumParam(target, new BorAction(target), factor1, factor2);
	}

	@Override
	public NumParam bxor(Param factor1, Param factor2) {
        return new NumParam(target, new BxorAction(target), factor1, factor2);
	}

	@Override
	public NumParam shl(Param factor1, Param factor2) {
		return new NumParam(target, new ShiftLeftAction(target), factor1, factor2);
	}

	@Override
	public NumParam shr(Param factor1, Param factor2) {
        return new NumParam(target, new ShiftRightAction(target), factor1, factor2);
	}

	@Override
	public NumParam ushr(Param factor1, Param factor2) {
        return new NumParam(target, new UnsignedShiftRightAction(target), factor1, factor2);
	}

	@Override
	public Crement predec(Param crement) {
		return new Crement(target.predec(crement.getTarget()));
	}

	@Override
	public Crement postdec(Param crement) {
		return new Crement(target.postdec(crement.getTarget()));
	}

	@Override
	public Crement preinc(Param crement) {
		return new Crement(target.preinc(crement.getTarget()));
	}

	@Override
	public Crement postinc(Param crement) {
		return new Crement(target.postinc(crement.getTarget()));
	}

	@Override
	public BoolParam gt(Param factor1, Param factor2) {
		return new BoolParam(target, new GreaterThanAction(target), factor1, factor2);
	}

	@Override
	public BoolParam ge(Param factor1, Param factor2) {
        return new BoolParam(target, new GreaterEqualAction(target), factor1, factor2);
	}

	@Override
	public BoolParam lt(Param factor1, Param factor2) {
        return new BoolParam(target, new LessThanAction(target), factor1, factor2);
	}

	@Override
	public BoolParam le(Param factor1, Param factor2) {
        return new BoolParam(target, new LessEqualAction(target), factor1, factor2);
	}

	@Override
	public BoolParam eq(Param factor1, Param factor2) {
        return new BoolParam(target, new EqualAction(target), factor1, factor2);
	}

	@Override
	public BoolParam ne(Param factor1, Param factor2) {
        return new BoolParam(target, new NotEqualAction(target), factor1, factor2);
	}

	@Override
	public BoolParam logicalAnd(Param factor1, Param factor2) {
		return new BoolParam(target, new LogicAndAction(target), factor1, factor2);
	}

	@Override
	public BoolParam logicalOr(Param factor1, Param factor2) {
        return new BoolParam(target, new LogicOrAction(target), factor1, factor2);
	}

	@Override
	public BoolParam logicalXor(Param factor1, Param factor2) {
        return new BoolParam(target, new LogicXorAction(target), factor1, factor2);
	}

	@Override
	public BoolParam and(Param factor1, Param factor2, Param... otherFactor) {
        return new BoolParam(target, new AndAction(target), factor1, factor2);
	}

	@Override
	public BoolParam or(Param factor1, Param factor2, Param... otherFactor) {
        return new BoolParam(target, new OrAction(target), factor1, factor2);
	}

	@Override
	public Not no(Param factor) {
		return new Not(target.no(factor.getTarget()));
	}

	@Override
	public Cast checkcast(Param cc, AClass to) {
		return new Cast(target.checkcast(cc.getTarget(), to));
	}

	@Override
	public Cast checkcast(Param cc, Class<?> to) {
		return new Cast(target.checkcast(cc.getTarget(), to));
	}

	@Override
	public Neg neg(Param factor) {
		return new Neg(target.neg(factor.getTarget()));
	}

	@Override
	public Ternary ternary(Param exp1, Param exp2,
	        Param exp3) {
		return new Ternary(target.ternary(exp1.getTarget(), exp2.getTarget(), exp3.getTarget()));
	}

	@Override
	public StrAdd stradd(Param par1, Param... pars) {
		return new StrAdd(target.stradd(par1.getTarget(), ParamPostern.getTarget(pars)));
	}

	@Override
	public Instanceof instanceof_(Param obj, AClass type) {
		return new Instanceof(target.instanceof_(obj.getTarget(), type));
	}

	@Override
	public Instanceof instanceof_(Param obj, Class<?> type) {
		return new Instanceof(target.instanceof_(obj.getTarget(), type));
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
		target.throw_(exception.getTarget());
	}

	@Override
	public void return_() {
		target.return_();
	}

	@Override
	public void return_(Param parame) {
		target.return_(parame.getTarget());
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
    
    Param[] unionParam(Param p, Param... ps) {
        if(ps == null || ps.length == 0) {
            return new Param[]{p};
        } else {
            Param[] operands = new Param[1 + ps.length];
            operands[0] = p;
            System.arraycopy(ps, 0, operands, 1, ps.length);
            return operands;
        }
    } 
    
}
