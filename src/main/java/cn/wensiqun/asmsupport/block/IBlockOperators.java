package cn.wensiqun.asmsupport.block;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.block.operator.ArithmeticOperator;
import cn.wensiqun.asmsupport.block.operator.ArrayOperator;
import cn.wensiqun.asmsupport.block.operator.Bitwise;
import cn.wensiqun.asmsupport.block.operator.CreateBlockOperator;
import cn.wensiqun.asmsupport.block.operator.CrementOperator;
import cn.wensiqun.asmsupport.block.operator.LogicalOperator;
import cn.wensiqun.asmsupport.block.operator.MethodInvokeOperator;
import cn.wensiqun.asmsupport.block.operator.RelationalOperator;
import cn.wensiqun.asmsupport.block.operator.ThisVariableable;
import cn.wensiqun.asmsupport.block.operator.ValueOperator;
import cn.wensiqun.asmsupport.block.operator.VariableOperator;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.operators.Return;
import cn.wensiqun.asmsupport.operators.checkcast.CheckCast;
import cn.wensiqun.asmsupport.operators.numerical.posinegative.Negative;
import cn.wensiqun.asmsupport.operators.ternary.TernaryOperator;

public interface IBlockOperators extends 
ThisVariableable, 
ValueOperator, 
VariableOperator, 
MethodInvokeOperator, 
ArrayOperator, 
ArithmeticOperator, 
Bitwise, 
CrementOperator,
RelationalOperator,
LogicalOperator,
CreateBlockOperator {
    
    /**
     * checkcast a value to target type.
     * 
     * @param cc value
     * @param to target type
     * @return
     */
    public CheckCast checkCast(Parameterized cc, AClass to);
    
    /**
     * 
     * 
     * @param factor
     * @return
     */
    public Negative neg(Parameterized factor);
    
    
    /**
     * 
     * @param exp1
     * @param exp2
     * @param exp3
     * @return
     */
    public TernaryOperator ternary(Parameterized exp1, Parameterized exp2, Parameterized exp3);

    /**
     * 
     * @param par1
     * @param pars
     * @return
     */
    public Parameterized append(Parameterized par1, Parameterized... pars);
    
    /**
     * 
     * @param obj
     * @param type
     * @return
     */
    public Parameterized instanceOf(Parameterized obj, AClass type);

    
    /**
     * 
     */
    public void breakout();
    
    /**
     * 
     */
    public void continueout();
    
    /**
     * 
     * @param exception
     */
    public void throwException(Parameterized exception);

    
    /**
     * run return statement
     * @return
     */
    public Return runReturn();
    
    /**
     * run return statement with return value
     * 
     * @param parame return value
     */
    public Return runReturn(Parameterized parame);
}
