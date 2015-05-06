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
/**
 * 
 */
package cn.wensiqun.asmsupport.core;


/**
 * 
 * Indicate an executable, all executable object will save in
 * a execute queue. the queue will twice loop for method {@link #prepare()}
 * and {@link #execute()}
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public interface Executable {
    
    /**
     * Do some prepare before execute.
     */
    void prepare();
    
    /**
     * Do execute
     * 
     * @return
     */
    void execute();
    
}
