package cn.wensiqun.asmsupport.core.block.classes.control;

import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class EpisodeBlock<T extends SerialBlock> extends ProgramBlockInternal {
    
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
