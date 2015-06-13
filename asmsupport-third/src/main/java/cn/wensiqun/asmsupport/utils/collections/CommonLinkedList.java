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
package cn.wensiqun.asmsupport.utils.collections;

import java.util.Iterator;

public class CommonLinkedList<E extends LinkedListNode> implements LinkedList<E> {
    
    private E head;
    
    private E last;
    
    private int size = 0;
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof LinkedListNode) {
            LinkedListNode node = (LinkedListNode) o;
            if (node.next() != null || node.previous() != null) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) new Itr(head) ;
    }

    @Override
    public boolean add(E e) {
        return commonAdd(last, e);
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof LinkedListNode) {
            LinkedListNode node = ((LinkedListNode) o);
            if (node == head) {
                head = (E) node.next();
            }

            if (node == last) {
                last = (E) node.previous();
            }

            node.remove();
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFrom(E start) {
        if (head == start) {
            clear();
        } else {
            last = (E) start.previous();
            last.setNext(null);
            while (start != null) {
                size--;
                start = (E) start.next();
            }
        }
        return true;
    }

    @Override
    public void clear() {
        head = last = null;
        size = 0;
    }

    @Override
    public boolean addAfter(E desc, E addNode) {
        return commonAdd(desc, addNode);
    }

    @Override
    public boolean addBefore(E desc, E addNode) {
        if (desc == null) {
            return commonAdd(desc, addNode);
        } else {
            return commonAdd(desc.previous(), addNode);
        }
    }

    @Override
    public boolean setHead(E head) {
        return commonAdd(null, head);
    }

    @Override
    public boolean setLast(E last) {
        return commonAdd(this.last, last);
    }

    /**
     * 
     * 
     * @param add
     *            null : insert to head
     * @param after
     * @return
     */
    private boolean commonAdd(LinkedListNode orig, E add) {
        LinkedListNode cursor = add;
        size++;
        while (cursor.hasNext()) {
            cursor = cursor.next();
            size++;
        }

        if (head == null) {
            head = add;
            last = (E) cursor;
        } else {
            if (orig == null) {
                cursor.setNext(head);
                head = add;
            } else if (orig == last) {
                orig.setNext(add);
                last = (E) cursor;
            } else {
                orig.setNext(add);
            }
        }
        return true;
    }

    @Override
    public E getHead() {
        return (E) head;
    }

    @Override
    public E getLast() {
        return (E) last;
    }

    @Override
    public boolean replace(E old, E newly) {
        old.replace(newly);
        return true;
    }

    @Override
    public boolean move(E src, E desc) {
        remove(src);
        addAfter(desc, src);
        return true;
    }

    @Override
    public boolean moveToLast(E src) {
        move(src, last);
        return true;
    }

    private class Itr implements Iterator<LinkedListNode> {

        private LinkedListNode cursor;

        private Itr(LinkedListNode root) {
            cursor = root;
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public LinkedListNode next() {
            LinkedListNode curr = cursor;
            cursor = cursor.next();
            return curr;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
