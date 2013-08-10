package jw.asmsupport.block.operator;

import jw.asmsupport.block.Synchronized;
import jw.asmsupport.block.control.DoWhileLoop;
import jw.asmsupport.block.control.ForEachLoop;
import jw.asmsupport.block.control.IF;
import jw.asmsupport.block.control.Try;
import jw.asmsupport.block.control.WhileLoop;

public interface CreateBlockOperator {

    /**
     * 
     * @param ifs
     * @return
     */
    public IF ifthan(IF ifs);
    
    /**
     * 
     * @param wl
     * @return
     */
    public WhileLoop whileloop(WhileLoop wl);
    
    /**
     * 
     * @param dwl
     * @return
     */
    public WhileLoop dowhile(DoWhileLoop dwl);
    
    /**
     * 
     * @param forEach
     * @return
     */
    public ForEachLoop forEach(final ForEachLoop forEach);
    
    
    /**
     * 
     * @param t
     * @return
     */
    public Try tryDo(final Try t);
    
    /**
     * 
     * @param s
     * @return
     */
    public Synchronized syn(Synchronized s);
	
}
