/**
 * 
 */
package cn.wensiqun.asmsupport.operators.array;

import java.lang.reflect.Array;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wensiqun.asmsupport.Parameterized;
import cn.wensiqun.asmsupport.asm.InstructionHelper;
import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.ArrayClass;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.utils.AClassUtils;

/**
 * @author 温斯群(Joe Wen)
 *
 */
public class ArrayValue extends AbstractOperator implements Parameterized  {

    private static Log log = LogFactory.getLog(ArrayValue.class);
    
    private abstract class EachValue{
        
        private Object array;
        
        private EachValue(Object array){
            this.array = array;
        }
        
        int process(){
        	if(array != null){
            	return loopProcess(this.array, 0);
            }
            return 0;
        }
        
        private int loopProcess(Object object, int over){
        	if(object.getClass().isArray()){
            	int len = Array.getLength(object);
        		int preArrayDoor = -1;
            	for(int i=0; i<len; i++){
        			Object o = Array.get(object, i);
        			int curArrayDoor = loopProcess(o, over + 1);
        			if(preArrayDoor == -1 || preArrayDoor == curArrayDoor){
        				preArrayDoor = curArrayDoor;
        			}else if(preArrayDoor != curArrayDoor){
                        throw new IllegalArgumentException("This array exist different dim sub-array : " + ArrayUtils.toString(this.array));
        			}
        		}
        		return preArrayDoor == - 1 ? over + 1 : preArrayDoor;
        	}else{
        		try{
                    process((Parameterized) object);
                }catch(ClassCastException e){
                	throw new IllegalArgumentException("exception occur when " + ArrayValue.this.toString(), e);
                }
            	return over;
        	}
        }
        
        abstract void process(Parameterized para);
        
    }
    
    private ArrayClass arrayCls;
    private Parameterized[] allocateDims;
    //it's array by reflect to parse
    private Object values;
    
    private boolean useByOther;
    
    private void batchAsArgument(Object values){
        new EachValue(values){
            @Override
            void process(Parameterized para) {
                para.asArgument();    
            }
            
        }.process();
    }
    
    protected ArrayValue(ProgramBlock block, ArrayClass arrayCls, Parameterized... allocateDims) {
        super(block);
        if(arrayCls.getDimension() < allocateDims.length){
            throw new IllegalArgumentException("dimension not enough: array type is " + arrayCls + " and allocate dims is " + ArrayUtils.toString(allocateDims));
        }
        this.arrayCls = arrayCls;
        this.allocateDims = allocateDims;
    }
    
    /**
     * 
     * @param block
     * @param arrayCls
     * @param values Parameterized array
     */
    protected ArrayValue(ProgramBlock block, ArrayClass arrayCls, Object values) {
        super(block);
        this.arrayCls = arrayCls;
        this.values = values;
    	if(values != null && !values.getClass().isArray()){
    		throw new IllegalArgumentException("values must be an array");
    	}else{
    		//check verify
    		int dim = new EachValue(values){
				@Override
				void process(Parameterized para) {}
    		}.process();
    		if(arrayCls.getDimension() != dim){
                throw new IllegalArgumentException("different dimension : value dimension is " + dim + ", class(" + arrayCls + ") dimension is " + arrayCls.getDimension());
    		}
    	}
    }

    @Override
    protected void verifyArgument() {
    	//当调用ArrayValue(ProgramBlock block, ArrayClass arrayCls, Parameterized... allocateDims)构造方法
    	if(allocateDims != null){
            for(Parameterized dim : allocateDims){
                int order = AClassUtils.getPrimitiveAClass(dim.getParamterizedType()).getCastOrder();
                if(order > AClass.INT_ACLASS.getCastOrder() ||
                   order <= AClass.BOOLEAN_ACLASS.getCastOrder()){
                    throw new RuntimeException("the allcate dim number must be byte, char, short or int type!");
                }
            }
        }

    	//当调用其他构造方法
    	final AClass rootComp = arrayCls.getRootComponentClass();
        new EachValue(values){
            @Override
            void process(Parameterized para) {
            	AClassUtils.autoCastTypeCheck(para.getParamterizedType(), rootComp);
            }
            
        }.process();
    }

    @Override
    protected void checkAsArgument() {
        batchAsArgument(allocateDims);
        batchAsArgument(values);
    }
    
    private void loopArray(AClass acls, Object arrayOrElement){
        InstructionHelper ih = block.getInsnHelper();
        if(arrayOrElement.getClass().isArray()){
            int len = Array.getLength(arrayOrElement);
            ih.push(len);
            AClass nextDimType = ((ArrayClass)acls).getNextDimType();
            ih.newArray(nextDimType.getType());
            if(len > 0){
                ih.dup();	
            }
            for(int i=0; i<len ;i++){
                ih.push(i);
                loopArray(nextDimType, Array.get(arrayOrElement, i));
                ih.arrayStore(acls.getType());
                if(i < len - 1){
                    ih.dup();
                }
            }
        }else{
            ((Parameterized) arrayOrElement).loadToStack(block);
            //auto cast each value for array
            autoCast(((Parameterized)arrayOrElement).getParamterizedType(), acls);
        }
    }

    @Override
    protected void executing() {
        if(!useByOther){
            throw new RuntimeException("this array value not use by other operator");
        }
        
        if(allocateDims != null){
            log.debug("start new a array!");
            InstructionHelper ih = block.getInsnHelper();
            if(allocateDims == null || allocateDims.length == 0){
                ih.push(arrayCls.getType());
                ih.checkCast(arrayCls.getType());
                return;
            }
            
            if(allocateDims.length == 1){
                allocateDims[0].loadToStack(block);
                ih.unbox(allocateDims[0].getParamterizedType().getType());
                ih.newArray(arrayCls.getNextDimType().getType());
            }else{
                for(Parameterized allocate : allocateDims){
                    allocate.loadToStack(block);
                    ih.unbox(allocate.getParamterizedType().getType());
                }
                ih.multiANewArrayInsn(arrayCls.getType(), allocateDims.length);
            }
        }else{
            loopArray(arrayCls, values);
        }
    }

    @Override
    public void loadToStack(ProgramBlock block) {
        this.execute();
    }

    @Override
    public AClass getParamterizedType() {
        return arrayCls;
    }

    @Override
    public void asArgument() {
        useByOther = true;
        block.removeExe(this);
    }

	@Override
	public String toString() {
		return "new " + arrayCls.toString() + " " + super.toString();
	}

    
}
