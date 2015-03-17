package cn.wensiqun.asmsupport.standard.action;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitAnd;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitOr;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitXor;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.Reverse;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftLeft;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.ShiftRight;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.UnsignedShiftRight;


/**
 * 
 * 位操作
 * 
 * @author wensiqun(at)163.com
 */
public interface BitwiseAction {
	
    /**
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">～factor1</b>
     * </p>
     * 
     * @param factor
     * @return {@link Reverse}
     */
    public Reverse _reverse(Parameterized factor);
    
    /**
     * The bit and operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 & factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitAnd}
     */
    public BitAnd _band(Parameterized factor1, Parameterized factor2);
	
    /**
     * 
     * The bit or operator, the following code is the sample :
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 | factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitOr}
     */
    public BitOr _bor(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * The xor operator, the following code is the sample.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 ^ factor2</b>
     * </p>
     * 
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitXor}
     */
    public BitXor _bxor(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * The shift left operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 << factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link ShiftLeft}
     */
    public ShiftLeft _shl(Parameterized factor1, Parameterized factor2);
    
    /**
     * The bitwise shift right operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >> factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link ShiftRight}
     */
    public ShiftRight _shr(Parameterized factor1, Parameterized factor2);
    
    /**
     * The unsigned shift right operator.
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >>> factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link UnsignedShiftRight}
     */
    public UnsignedShiftRight _ushr(Parameterized factor1, Parameterized factor2);
    
}
