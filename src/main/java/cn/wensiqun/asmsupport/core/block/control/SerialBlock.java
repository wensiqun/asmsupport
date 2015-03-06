package cn.wensiqun.asmsupport.core.block.control;

import cn.wensiqun.asmsupport.core.block.AbstractBlockInternal;
import cn.wensiqun.asmsupport.core.block.ProgramBlockInternal;
import cn.wensiqun.asmsupport.org.objectweb.asm.Label;


/**
 * The serial block, following block belong to condition serial block. 
 * <ul>
 * <li>if</li>
 * <li>else if</li>
 * <li>else</li>
 * </ul>
 * 
 * following block belong to condition exception block. 
 * 
 * <ul>
 * <li>try</li>
 * <li>catch</li>
 * <li>finally</li>
 * </ul>
 * 
 * @author wensiqun(at)163.com
 *
 */
public abstract class SerialBlock extends AbstractBlockInternal
{
    protected ProgramBlockInternal targetParent;
    
    protected SerialBlock(ProgramBlockInternal targetParent)
    {
        this.targetParent = targetParent;
        targetParent.getQueue().add(this);
    }
    
    @SuppressWarnings("unchecked")
    protected void initEpisode(@SuppressWarnings("rawtypes") EpisodeBlock block)
    {
        block.setParent(targetParent);
        block.setSerial(this);
    }

    /**
     * Get serial start label
     * 
     * @return
     */
    public abstract Label getSerialStart();

    /**
     * Get serial end label
     * 
     * @return
     */
    public abstract Label getSerialEnd();
}

