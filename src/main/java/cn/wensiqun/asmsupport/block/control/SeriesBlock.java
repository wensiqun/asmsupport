package cn.wensiqun.asmsupport.block.control;

import cn.wensiqun.asmsupport.block.ProgramBlock;

/**
 * 表示是同一个系列的程序块，即语句块是有连续性比如：
 * try,catch,finally是一个系列,if,else if,else是一个系列
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class SeriesBlock extends ProgramBlock {
    
	/**
	 * 程序块的前一个程序块
	 */
	private SeriesBlock previous;

	/**
	 * 获取前一个程序块
	 * @return
	 */
	public SeriesBlock getPrevious() {
		return previous;
	}

	/**
	 * 设置前一个程序块
	 * @param previous
	 */
	public void setPrevious(SeriesBlock previous) {
		this.previous = previous;
	}

}
