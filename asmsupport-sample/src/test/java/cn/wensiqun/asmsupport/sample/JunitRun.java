package cn.wensiqun.asmsupport.sample;

import org.junit.Test;

import cn.wensiqun.asmsupport.sample.core.CoreRunner;

public class JunitRun {

    @Test
    public void test() throws Exception {
        CoreRunner.main();
        cn.wensiqun.asmsupport.sample.client.proxy.demo.Runner.main();
        cn.wensiqun.asmsupport.sample.client.json.demo.Runner.main();
    }

}
