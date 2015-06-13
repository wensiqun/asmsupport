package cn.wensiqun.asmsupport.utils.collections;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtils {


    /**
     * Adds all elements in the iteration to the given collection.
     * 
     * @param collection  the collection to add to, must not be null
     * @param iterator  the iterator of elements to add, must not be null
     * @throws NullPointerException if the collection or iterator is null
     */
    public static <T> void addAll(Collection<T> collection, Iterator<T> iterator) {
        while (iterator.hasNext()) {
            collection.add(iterator.next());
        }
    }    
    
    /** 
     * Adds all elements in the array to the given collection.
     * 
     * @param collection  the collection to add to, must not be null
     * @param elements  the array of elements to add, must not be null
     * @throws NullPointerException if the collection or array is null
     */
    public static <T> void addAll(Collection<T> collection, T[] elements) {
        for (int i = 0, size = elements.length; i < size; i++) {
            collection.add(elements[i]);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Null-safe check if the specified collection is empty.
     * <p>
     * Null returns true.
     * 
     * @param coll  the collection to check, may be null
     * @return true if empty or null
     * @since Commons Collections 3.2
     */
    public static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * Null-safe check if the specified collection is not empty.
     * <p>
     * Null returns false.
     * 
     * @param coll  the collection to check, may be null
     * @return true if non-null and non-empty
     * @since Commons Collections 3.2
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !CollectionUtils.isEmpty(coll);
    }
    
}
