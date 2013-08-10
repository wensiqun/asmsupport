package jw.asmsupport.block.operator;

import jw.asmsupport.Parameterized;
import jw.asmsupport.operators.numerical.arithmetic.Addition;
import jw.asmsupport.operators.numerical.arithmetic.Division;
import jw.asmsupport.operators.numerical.arithmetic.Modulus;
import jw.asmsupport.operators.numerical.arithmetic.Multiplication;
import jw.asmsupport.operators.numerical.arithmetic.Subtraction;

public interface ArithmeticOperator {
    
    /**
     * generate the instruction that correspond to factor1 + factor2
     * @param factor1
     * @param factor2
     * @return
     */
    public Addition add(Parameterized factor1, Parameterized factor2);

    /**
     * generate the instruction that correspond to factor1 - factor2
     * @param factor1
     * @param factor2
     * @return
     */
    public Subtraction sub(Parameterized factor1, Parameterized factor2);
    
    /**
     * generate the instruction that correspond to factor1 * factor2
     * @param factor1
     * @param factor2
     * @return
     */
    public Multiplication mul(Parameterized factor1, Parameterized factor2);
    
    /**
     * generate the instruction that correspond to factor1 / factor2
     * @param factor1
     * @param factor2
     * @return
     */
    public Division div(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public Modulus mod(Parameterized factor1, Parameterized factor2);
    
}
