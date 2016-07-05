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


import cn.wensiqun.asmsupport.core.Executable;

public abstract class InstructionNode implements Cloneable, Executable {

    /**
     * The next node
     */
    private InstructionNode next;

    /**
     * Previous node.
     */
    private InstructionNode previous;

    private InstructionBlockNode parent;

    public boolean hasNext() {
        return next != null;
    }

    public InstructionNode next() {
        return next;
    }

    public InstructionNode previous() {
        return previous;
    }

    /**
     * Remove current node from tree.
     */
    void remove() {
        if (previous != null) {
            previous.next = next;
        }

        if (next != null) {
            next.previous = previous;
        }

        next = previous = null;
    }

    /**
     * Set the next
     * @param next
     */
    void setNext(InstructionNode next) {
        if (next == null) {
            if (this.next != null) {
                this.next.previous = null;
                this.next = null;
            }
        } else {
            if (this.next == null) {
                this.next = next;
                next.previous = this;
            } else {
                InstructionNode subLast = next;
                while (subLast.hasNext()) {
                    subLast = subLast.next();
                }

                subLast.next = this.next;
                this.next.previous = subLast;

                this.next = next;
                next.previous = this;
            }
        }
        
    }

    /**
     * Replace the newer instead of current node.
     * @param newer
     */
    void replace(InstructionNode newer) {
        previous.next = newer;
        newer.previous = previous;

        next.previous = newer;
        newer.next = next;

        previous = next = null;
    }

    public InstructionBlockNode getParent() {
        return parent;
    }

    public void setParent(InstructionBlockNode parent) {
        this.parent = parent;
    }
}
