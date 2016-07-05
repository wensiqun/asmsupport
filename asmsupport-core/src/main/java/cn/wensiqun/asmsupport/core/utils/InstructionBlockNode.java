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
package cn.wensiqun.asmsupport.core.utils;


public abstract class InstructionBlockNode extends InstructionNode {

    /**
     * The children of the node
     */
    private InstructionList children = new InstructionList();

    /**
     * Get the children.
     * @return
     */
    public InstructionList getChildren() {
        return children;
    }

    /**
     * Remove an executor from execute queue.
     *
     * @param exe
     */
    public void removeChild(InstructionNode exe) {
        getChildren().remove(exe);
    }

}
