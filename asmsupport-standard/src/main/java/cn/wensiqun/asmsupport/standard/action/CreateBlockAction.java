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
package cn.wensiqun.asmsupport.standard.action;



/**
 * 
 * 创建程序块
 * 
 * @author wensiqun(at)163.com
 *
 */
public interface CreateBlockAction<TIF, TWhile, TDoWhile, TForEach, TTry, TSynchronized> {

    /**
     * Create an if program block
     */
    public TIF if_(TIF ifBlock);
    
    /**
     * Create a while program block
     */
    public TWhile while_(TWhile whileLoop);
    
    /**
     * Create a do...while program block
     */
    public TDoWhile dowhile(TDoWhile doWhile);
    
    /**
     * Create a for each program block
     */
    public TForEach for_(final TForEach forEach);
    
    
    /**
     * Create a try program block
     */
    public TTry try_(final TTry tryPara);
    
    /**
     * Create synchronized block.
     */
    public TSynchronized sync(TSynchronized sync);
	
}
