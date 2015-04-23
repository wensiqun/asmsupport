package dummy;

import junit.framework.Assert;

import org.junit.Test;

import cn.wensiqun.asmsupport.client.DummyModifiedClass;
import cn.wensiqun.asmsupport.client.block.ModifiedMethodBody;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.client.def.var.Var;
import dummy.modify.ModifyEntity;

public class DummyModifyTest {
    
    private Object asmsupportTest() throws InstantiationException, IllegalAccessException{
        DummyModifiedClass dummy = new DummyModifiedClass(ModifyEntity.class).setClassOutPutPath(".//target//dummy-generated");
        dummy.modify("getConstant", null, new ModifiedMethodBody() {

            @Override
            public void body(LocVar... args) {
                Var str = var("str", StringBuilder.class, new_(StringBuilder.class, val("Append at before{")));
                call(str, "append", callOrig());
                call(str, "append", val("}append at after"));
                return_(call(str, "toString"));
            }
            
        });
        return dummy.build().newInstance();
    }
    
    @Test
    public void test() {
        try {
            Object obj = asmsupportTest();
            Assert.assertEquals("Append at before{Hello World}append at after", obj.getClass().getMethod("getConstant").invoke(obj));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    
}
