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

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class EpisodeBlock<T extends SerialBlock> extends KernelProgramBlock {
    
    private T serial;
    
	/*private EpisodeBlock previousEpisode;
	
	private EpisodeBlock nextEpisode;

	
    public EpisodeBlock getPreviousEpisode()
    {
        return previousEpisode;
    }

    public void setPreviousEpisode(EpisodeBlock previousEpisode)
    {
        this.previousEpisode = previousEpisode;
    }

    
    public EpisodeBlock getNextEpisode()
    {
        return nextEpisode;
    }
    
    public void setNextEpisode(EpisodeBlock nextEpisode)
    {
        this.nextEpisode = nextEpisode;
    }*/

    /**
     * 
     * @return
     */
    public T getSerial() {
        return serial;
    }

    /**
     * 
     * @param serial
     */
    void setSerial(T serial) {
        this.serial = serial;
    }

}
