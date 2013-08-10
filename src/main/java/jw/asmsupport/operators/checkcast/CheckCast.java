/**
 * 
 */
package jw.asmsupport.operators.checkcast;

import jw.asmsupport.Parameterized;
import jw.asmsupport.block.ProgramBlock;
import jw.asmsupport.clazz.AClass;
import jw.asmsupport.operators.AbstractOperator;
import jw.asmsupport.operators.numerical.crement.AbstractCrement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class CheckCast extends AbstractOperator implements Parameterized {

    private static Log log = LogFactory.getLog(CheckCast.class);
    private AClass to;
    private Parameterized orginal;
    
    protected CheckCast(ProgramBlock block, Parameterized orgi, AClass to) {
        super(block);
        this.orginal = orgi;
        this.to = to;
    }
    
    @Override
    protected void verifyArgument() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void checkOutCrement() {
        if(orginal instanceof AbstractCrement){
            allCrement.add((AbstractCrement) orginal);
        }
    }

    @Override
    protected void checkAsArgument() {
        orginal.asArgument();
    }

    @Override
    public void executing() {
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
    public void loadToStack(ProgramBlock block) {
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
