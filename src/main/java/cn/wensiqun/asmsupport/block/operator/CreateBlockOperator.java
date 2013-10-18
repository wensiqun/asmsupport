package cn.wensiqun.asmsupport.block.operator;

import cn.wensiqun.asmsupport.block.Synchronized;
import cn.wensiqun.asmsupport.block.control.DoWhileLoop;
import cn.wensiqun.asmsupport.block.control.ForEachLoop;
import cn.wensiqun.asmsupport.block.control.IF;
import cn.wensiqun.asmsupport.block.control.Try;
import cn.wensiqun.asmsupport.block.control.WhileLoop;

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
