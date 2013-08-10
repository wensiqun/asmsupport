package jw.asmsupport.utils.finder.clazz;
/**
 * This is the testing interface that is used to accept or reject resources.
 */
public interface Filter {
    /**
     * The test method.
     *
     * @param   t The resource object to test.
     * @return  True if the resource should be accepted, false otherwise.
     */
    public boolean filter(String name);
}