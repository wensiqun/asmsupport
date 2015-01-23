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
