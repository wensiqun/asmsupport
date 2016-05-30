package cn.wensiqun.asmsupport.sample;

import cn.wensiqun.asmsupport.sample.core.CoreRunner;
import org.junit.Test;

public class JunitRunTest {

    @Test
    public void test() throws Exception {
        CoreRunner.main();
        cn.wensiqun.asmsupport.sample.client.proxy.demo.Runner.main();
        cn.wensiqun.asmsupport.sample.client.json.demo.Runner.main();
    }

}
