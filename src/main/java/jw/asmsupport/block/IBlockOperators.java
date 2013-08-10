package jw.asmsupport.block;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.operator.ArithmeticOperator;
import jw.asmsupport.block.operator.ArrayOperator;
import jw.asmsupport.block.operator.Bitwise;
import jw.asmsupport.block.operator.CreateBlockOperator;
import jw.asmsupport.block.operator.CrementOperator;
import jw.asmsupport.block.operator.LogicalOperator;
import jw.asmsupport.block.operator.MethodInvokeOperator;
import jw.asmsupport.block.operator.RelationalOperator;
import jw.asmsupport.block.operator.ThisVariableable;
import jw.asmsupport.block.operator.ValueOperator;
import jw.asmsupport.block.operator.VariableOperator;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.operators.Return;
import jw.asmsupport.operators.checkcast.CheckCast;
import jw.asmsupport.operators.numerical.posinegative.Negative;
import jw.asmsupport.operators.ternary.TernaryOperator;

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
