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

import java.util.Iterator;

public class InstructionList implements Iterable<InstructionNode>, Cloneable {

    /**
     * The head of the list
     */
    private InstructionNode head;

    /**
     * The tail of the list
     */
    private InstructionNode tail;
    
    private int size = 0;

    /**
     * Get size
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Check the list contains a specify node
     * @param node
     * @return
     */
    public boolean contains(InstructionNode node) {
        if (node.next() != null || node.previous() != null) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<InstructionNode> iterator() {
        return new Itr(head) ;
    }

    /**
     * Add a node.
     * @param node
     * @return
     */
    public boolean add(InstructionNode node) {
        return commonAdd(tail, node);
    }

    /**
     * Remove a specify node.
     * @param node
     * @return
     */
    public void remove(InstructionNode node) {
        if (node == head) {
            head = node.next();
        }

        if (node == tail) {
            tail = node.previous();
        }

        node.remove();
        size--;
    }

    /**
     * Remove node from the start to tail.
     *
     * @param start
     * @return
     */
    public boolean removeFrom(InstructionNode start) {
        if (head == start) {
            clear();
        } else {
            tail = start.previous();
            tail.setNext(null);
            while (start != null) {
                size--;
                start = start.next();
            }
        }
        return true;
    }

    /**
     * Clear the list.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /**
     * Add a node after the specify node.
     * @param node
     * @param added
     * @return
     */
    public boolean addAfter(InstructionNode node, InstructionNode added) {
        return commonAdd(node, added);
    }

    /**
     * Set the head of the list
     * @param head
     * @return
     */
    public boolean setHead(InstructionNode head) {
        return commonAdd(null, head);
    }

    /**
     * Set the tail of the list
     *
     * @param tail
     * @return
     */
    public boolean setTail(InstructionNode tail) {
        return commonAdd(this.tail, tail);
    }

    /**
     * 
     * 
     * @param orig
     *            null : insert to head
     * @param add
     * @return
     */
    private boolean commonAdd(InstructionNode orig, InstructionNode add) {
        InstructionNode cursor = add;
        size++;
        while (cursor.hasNext()) {
            cursor = cursor.next();
            size++;
        }

        if (head == null) {
            head = add;
            tail = cursor;
        } else {
            if (orig == null) {
                cursor.setNext(head);
                head = add;
            } else if (orig == tail) {
                orig.setNext(add);
                tail = cursor;
            } else {
                orig.setNext(add);
            }
        }
        return true;
    }

    /**
     * Get the tail of the list.
     * @return
     */
    public InstructionNode getTail() {
        return tail;
    }

    /**
     * Replace the new node instead of old node
     * @param older
     * @param newer
     * @return
     */
    public boolean replace(InstructionNode older, InstructionNode newer) {
        older.replace(newer);
        return true;
    }

    private class Itr implements Iterator<InstructionNode> {

        private InstructionNode cursor;

        private Itr(InstructionNode root) {
            cursor = root;
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public InstructionNode next() {
            InstructionNode curr = cursor;
            cursor = cursor.next();
            return curr;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


    /*public boolean isEmpty() {
        return head == null;
    }*/

    /*public boolean addBefore(InstructionNode desc, InstructionNode addNode) {
        if (desc == null) {
            return commonAdd(desc, addNode);
        } else {
            return commonAdd(desc.previous(), addNode);
        }
    }*/

    /*public boolean move(InstructionNode src, InstructionNode desc) {
        remove(src);
        addAfter(desc, src);
        return true;
    }*/
}
