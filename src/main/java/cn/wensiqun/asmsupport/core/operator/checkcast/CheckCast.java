/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.checkcast;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.core.Parameterized;
import cn.wensiqun.asmsupport.core.block.classes.common.ProgramBlockInternal;
import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class CheckCast extends AbstractOperator implements Parameterized {

    private static Log log = LogFactory.getLog(CheckCast.class);
    private AClass to;
    private Parameterized orginal;
    
    protected CheckCast(ProgramBlockInternal block, Parameterized orgi, AClass to) {
        super(block);
        this.orginal = orgi;
        this.to = to;
    }
    
    @Override
    protected void verifyArgument() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void checkAsArgument() {
        orginal.asArgument();
    }

    @Override
    public void doExecute() {
        orginal.loadToStack(block);
        AClass from = orginal.getParamterizedType();
        if(to.equals(from)){
            return;
        }
        
        if(from.isPrimitive() && to.isPrimitive()){
            log.debug("checkcast from " + from + " to " + to );
            if(from.getCastOrder() > to.getCastOrder() ||
               (from.equals(AClass.CHAR_ACLASS) && to.equals(AClass.SHORT_ACLASS)) || 
               (to.equals(AClass.CHAR_ACLASS) && from.equals(AClass.SHORT_ACLASS))){
                insnHelper.cast(from.getType(), to.getType());
                return;
            }
        }
        log.debug("checkcast from " + from + " to " + to );
        insnHelper.checkCast(to.getType());
    }

    @Override
    public void loadToStack(ProgramBlockInternal block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return to;
    }

    @Override
    public void asArgument() {
        block.removeExe(this);
    }



}
