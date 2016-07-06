package cn.wensiqun.asmsupport.core.block.loop;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;

import java.util.ArrayList;
import java.util.List;


public class ForEachBlockGeneratorSample {
    
    private static void test()
    {
        List<String> list = new ArrayList<>();
        list.add("ForEach ");
        list.add("Test ");
        for(String l : list) {
            TesterStatics.expectedPrintln(l);
        }
        
    }

    public static void main(String[] args)
    {
      test();
    }
}
