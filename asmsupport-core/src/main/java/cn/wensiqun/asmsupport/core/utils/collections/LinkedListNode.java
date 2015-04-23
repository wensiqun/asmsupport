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
package cn.wensiqun.asmsupport.core.utils.collections;


public abstract class LinkedListNode implements Cloneable {

    private LinkedListNode next;

    private LinkedListNode previous;

    public boolean hasNext() {
        return next != null;
    }

    public LinkedListNode next() {
        return next;
    }

    public LinkedListNode previous() {
        return previous;
    }

    void remove() {
        if (previous != null) {
            previous.next = next;
        }

        if (next != null) {
            next.previous = previous;
        }

        next = previous = null;
    }

    void setNext(LinkedListNode subHead) {
        if (subHead == null) {
            if (next != null) {
                next.previous = null;
                next = null;
            }
        } else {
            if (next == null) {
                this.next = subHead;
                subHead.previous = this;
            } else {
                LinkedListNode subLast = subHead;
                while (subLast.hasNext()) {
                    subLast = subLast.next();
                }

                subLast.next = next;
                next.previous = subLast;

                next = subHead;
                subHead.previous = this;
            }
        }
        
    }

    void replace(LinkedListNode newly) {
        previous.next = newly;
        newly.previous = previous;

        next.previous = newly;
        newly.next = next;

        previous = next = null;
    }
}
