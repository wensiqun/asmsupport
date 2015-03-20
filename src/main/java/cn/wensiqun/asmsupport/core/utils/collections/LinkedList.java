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



public interface LinkedList<E> extends Iterable<E>, Cloneable
{

    /**
     * 
     * @return
     */
    int size();
    
    /**
     * 
     * @return
     */
    boolean isEmpty();
    
    /**
     * 
     * @param e
     * @return
     */
    boolean add(E el);
    
    /**
     * 
     * @param o
     * @return
     */
    boolean remove(Object o);
    
    /**
     * 
     * @param o
     * @return
     */
    public boolean contains(Object o);
    
    /**
     * Add element after a special node
     * 
     * @param dest
     * @param addNode
     * @return
     */
    boolean addAfter(E dest, E addNode);
    
    /**
     * Add element before a special node
     * 
     * @param dest
     * @param addNode
     * @return
     */
    boolean addBefore(E dest, E addNode);
    
    
    boolean setHead(E head);
    
    boolean setLast(E last);
    
    /**
     * 
     * @param start
     * @return
     */
    boolean removeFrom(E start);
    
    /**
     * 
     * @param old
     * @param newly
     * @return
     */
    boolean replace(E old, E newly);
    
    /**
     * move src node to after desc node
     * 
     * @param src
     * @param desc
     * @return
     */
    boolean move(E src, E desc);
    
    /**
     * move src node to last
     * 
     * @param src
     * @return
     */
    boolean moveToLast(E src);
    
    
    void clear();
    
    /**
     * 
     * @return
     */
    E getHead();
    
    /**
     * 
     * @return
     */
    E getLast();
    
}
