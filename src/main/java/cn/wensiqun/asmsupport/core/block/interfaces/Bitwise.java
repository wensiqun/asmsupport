package cn.wensiqun.asmsupport.core.block.interfaces;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitAnd;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitOr;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.BitXor;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.Inverts;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.LeftShift;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.RightShift;
import cn.wensiqun.asmsupport.core.operator.numerical.bit.UnsignedRightShift;


/**
 * 
 * 位操作
 * 
 * @author wensiqun(at)gmail
 */
public interface Bitwise {
	
    /**
     * 生成取反操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">～factor1</b>
     * </p>
     * 
     * @param factor
     * @return {@link Inverts}
     */
    public Inverts _inverts(Parameterized factor);
    
    /**
     * 生成与操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 & factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitAnd}
     */
    public BitAnd _bitAnd(Parameterized factor1, Parameterized factor2);
	
    /**
     * 
     * 生成或操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 | factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link BitOr}
     */
    public BitOr _bitOr(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * 生成异或操作指令,对应下面的红色java代码
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
    public BitXor _bitXor(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * 生成左移操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 << factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link LeftShift}
     */
    public LeftShift _leftShift(Parameterized factor1, Parameterized factor2);
    
    /**
     * 
     * 生成右移操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >> factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link RightShift}
     */
    public RightShift _rightShift(Parameterized factor1, Parameterized factor2);
    
    /**
     * 生成无符号右移操作指令,对应下面的红色java代码
     * 
     * <p style="border:1px solid;width:300px;padding:10px;">
     * <b style="color:#FF3300">factor1 >>> factor2</b>
     * </p>
     * 
     * @param factor1
     * @param factor2
     * @return {@link UnsignedRightShift}
     */
    public UnsignedRightShift _unsignedRightShift(Parameterized factor1, Parameterized factor2);
    
}
