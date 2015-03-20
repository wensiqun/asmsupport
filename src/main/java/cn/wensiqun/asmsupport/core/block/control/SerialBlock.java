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

