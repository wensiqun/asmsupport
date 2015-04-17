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

import cn.wensiqun.asmsupport.core.Crementable;
import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.ArrayClass;
import cn.wensiqun.asmsupport.core.clazz.NewMemberClass;
import cn.wensiqun.asmsupport.core.definition.value.Value;
import cn.wensiqun.asmsupport.core.definition.variable.ExplicitVariable;
import cn.wensiqun.asmsupport.core.definition.variable.GlobalVariable;
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

public class ProgramBlock<B extends ProgramBlockInternal> implements
		ActionSet<IF, While, DoWhile, ForEach, Try, Synchronized> {

	B target;

	public LocalVariable[] getMethodArguments() {
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
	public ThisVariable this_() {
		return target.this_();
	}

	@Override
	public GlobalVariable this_(String name) {
		return target.this_(name);
	}

	@Override
	public SuperVariable super_() {
		return target.super_();
	}

	@Override
	public LocalVariable var(String name, Class<?> type, Parameterized para) {
		return target.var(name, type, para);
	}

	@Override
	public LocalVariable var(Class<?> type, Parameterized para) {
		return target.var(type, para);
	}

	@Override
	public LocalVariable var(String name, AClass type, Parameterized para) {
		return target.var(name, type, para);
	}

	@Override
	public LocalVariable var(AClass type, Parameterized para) {
		return target.var(type, para);
	}

	@Override
	public GlobalVariable field(String name) {
		return target.field(name);
	}

	@Override
	public Assigner assign(ExplicitVariable variable, Parameterized val) {
		return target.assign(variable, val);
	}

	@Override
	public MethodInvoker call(Parameterized objRef, String methodName,
			Parameterized... arguments) {
		return target.call(objRef, methodName, arguments);
	}

	@Override
	public MethodInvoker call(String methodName, Parameterized... args) {
		return target.call(methodName, args);
	}

	@Override
	public MethodInvoker call(AClass owner, String methodName, Parameterized... arguments) {
		return target.call(owner, methodName, arguments);
	}
    
	@Override
    public final MethodInvoker call(Class<?> owner, String methodName, Parameterized... arguments) {
    	return target.call(owner, methodName, arguments);
    }

	@Override
	public MethodInvoker new_(Class<?> owner, Parameterized... arguments) {
		return target.new_(owner, arguments);
	}

	@Override
	public MethodInvoker new_(AClass owner, Parameterized... arguments) {
		return target.new_(owner, arguments);
	}

	@Override
	public MethodInvoker callOrig() {
		return target.callOrig();
	}

	@Override
	public ArrayValue makeArray(AClass aClass, Parameterized... allocateDims) {
		return target.makeArray(aClass, allocateDims);
	}

	@Override
	public ArrayValue makeArray(Class<?> arraytype, Parameterized... dimensions) {
		return target.makeArray(arraytype, dimensions);
	}

	@Override
	public ArrayValue newarray(AClass aClass, Object arrayObject) {
		return target.newarray(aClass, arrayObject);
	}

	@Override
	public ArrayValue newarray(Class<?> type, Object arrayObject) {
		return target.newarray(type, arrayObject);
	}

	@Override
	public ArrayLoader arrayLoad(Parameterized arrayReference,
			Parameterized pardim, Parameterized... parDims) {
		return target.arrayLoad(arrayReference, pardim, parDims);
	}
	
	@Override
	public ArrayStorer arrayStore(Parameterized arrayReference,
			Parameterized value, Parameterized dim, Parameterized... dims) {
		return target.arrayStore(arrayReference, value, dim, dims);
	}

	@Override
	public ArrayLength arrayLength(Parameterized arrayReference,
			Parameterized... dims) {
		return target.arrayLength(arrayReference, dims);
	}
	
	@Override
	public Addition add(Parameterized factor1, Parameterized factor2) {
		return target.add(factor1, factor2);
	}

	@Override
	public Subtraction sub(Parameterized factor1, Parameterized factor2) {
		return target.sub(factor1, factor2);
	}

	@Override
	public Multiplication mul(Parameterized factor1, Parameterized factor2) {
		return target.mul(factor1, factor2);
	}

	@Override
	public Division div(Parameterized factor1, Parameterized factor2) {
		return target.div(factor1, factor2);
	}

	@Override
	public Modulus mod(Parameterized factor1, Parameterized factor2) {
		return target.mod(factor1, factor2);
	}

	@Override
	public Reverse reverse(Parameterized factor) {
		return target.reverse(factor);
	}

	@Override
	public BitAnd band(Parameterized factor1, Parameterized factor2) {
		return target.band(factor1, factor2);
	}

	@Override
	public BitOr bor(Parameterized factor1, Parameterized factor2) {
		return target.bor(factor1, factor2);
	}

	@Override
	public BitXor bxor(Parameterized factor1, Parameterized factor2) {
		return target.bxor(factor1, factor2);
	}

	@Override
	public ShiftLeft shl(Parameterized factor1, Parameterized factor2) {
		return target.shl(factor1, factor2);
	}

	@Override
	public ShiftRight shr(Parameterized factor1, Parameterized factor2) {
		return target.shr(factor1, factor2);
	}

	@Override
	public UnsignedShiftRight ushr(Parameterized factor1, Parameterized factor2) {
		return target.ushr(factor1, factor2);
	}

	@Override
	public PreposeDecrment predec(Crementable crement) {
		return target.predec(crement);
	}

	@Override
	public PostposeDecrment postdec(Crementable crement) {
		return target.postdec(crement);
	}

	@Override
	public PreposeIncrment preinc(Crementable crement) {
		return target.preinc(crement);
	}

	@Override
	public PostposeIncrment postinc(Crementable crement) {
		return target.postinc(crement);
	}

	@Override
	public GreaterThan gt(Parameterized factor1, Parameterized factor2) {
		return target.gt(factor1, factor2);
	}

	@Override
	public GreaterEqual ge(Parameterized factor1, Parameterized factor2) {
		return target.ge(factor1, factor2);
	}

	@Override
	public LessThan lt(Parameterized factor1, Parameterized factor2) {
		return target.lt(factor1, factor2);
	}

	@Override
	public LessEqual le(Parameterized factor1, Parameterized factor2) {
		return target.le(factor1, factor2);
	}

	@Override
	public Equal eq(Parameterized factor1, Parameterized factor2) {
		return target.eq(factor1, factor2);
	}

	@Override
	public NotEqual ne(Parameterized factor1, Parameterized factor2) {
		return target.ne(factor1, factor2);
	}

	@Override
	public LogicalAnd logicalAnd(Parameterized factor1, Parameterized factor2) {
		return target.logicalAnd(factor1, factor2);
	}

	@Override
	public LogicalOr logicalOr(Parameterized factor1, Parameterized factor2) {
		return target.logicalOr(factor1, factor2);
	}

	@Override
	public LogicalXor logicalXor(Parameterized factor1, Parameterized factor2) {
		return target.logicalXor(factor1, factor2);
	}

	@Override
	public ShortCircuitAnd and(Parameterized factor1, Parameterized factor2,
			Parameterized... otherFactor) {
		return target.and(factor1, factor2, otherFactor);
	}

	@Override
	public ShortCircuitOr or(Parameterized factor1, Parameterized factor2,
			Parameterized... otherFactor) {
		return target.or(factor1, factor2, otherFactor);
	}

	@Override
	public Not no(Parameterized factor) {
		return target.no(factor);
	}

	@Override
	public CheckCast checkcast(Parameterized cc, AClass to) {
		return target.checkcast(cc, to);
	}

	@Override
	public CheckCast checkcast(Parameterized cc, Class<?> to) {
		return target.checkcast(cc, to);
	}

	@Override
	public Negative neg(Parameterized factor) {
		return target.neg(factor);
	}

	@Override
	public TernaryOperator ternary(Parameterized exp1, Parameterized exp2,
			Parameterized exp3) {
		return target.ternary(exp1, exp2, exp3);
	}

	@Override
	public Parameterized stradd(Parameterized par1, Parameterized... pars) {
		return target.stradd(par1, pars);
	}

	@Override
	public Parameterized instanceof_(Parameterized obj, AClass type) {
		return target.instanceof_(obj, type);
	}

	@Override
	public Parameterized instanceof_(Parameterized obj, Class<?> type) {
		return target.instanceof_(obj, type);
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
	public void throw_(Parameterized exception) {
		target.throw_(exception);
	}

	@Override
	public Return return_() {
		return target.return_();
	}

	@Override
	public Return return_(Parameterized parame) {
		return target.return_(parame);
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
	public Synchronized sync(Synchronized sync) {
		target.sync(sync.target);
		return sync;
	}

	@Override
	public Value val(Integer val) {
		return target.val(val);
	}

	@Override
	public Value val(Short val) {
		return target.val(val);
	}

	@Override
	public Value val(Byte val) {
		return target.val(val);
	}

	@Override
	public Value val(Boolean val) {
		return target.val(val);
	}

	@Override
	public Value val(Long val) {
		return target.val(val);
	}

	@Override
	public Value val(Double val) {
		return target.val(val);
	}

	@Override
	public Value val(Character val) {
		return target.val(val);
	}

	@Override
	public Value val(Float val) {
		return target.val(val);
	}

	@Override
	public Value val(AClass val) {
		return target.val(val);
	}

	@Override
	public Value val(Class<?> val) {
		return target.val(val);
	}

	@Override
	public Value val(String val) {
		return target.val(val);
	}

	@Override
	public Value null_(AClass type) {
		return target.null_(type);
	}

	@Override
	public Value null_(Class<?> type) {
		return target.null_(type);
	}

	@Override
	public AClass getType(Class<?> cls) {
		return target.getType(cls);
	}

	@Override
	public ArrayClass getArrayType(Class<?> cls, int dim) {
		return target.getArrayType(cls, dim);
	}

	@Override
	public ArrayClass getArrayType(AClass rootComponent, int dim) {
		return target.getArrayType(rootComponent, dim);
	}

}
