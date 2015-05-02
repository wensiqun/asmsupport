package cn.wensiqun.asmsupport.client.operations;


public class ArraySample {
    
    public static String excepted() {
        StringBuilder sb = new StringBuilder();
        String[][] strs = new String[][]{new String[]{"Hello", "ASMSupport"}, new String[]{"Hi", "ASM"}};
        sb.append(strs.length).append(strs[0].length);
        strs[1] = new String[]{"Hey", "Java"};
        strs[1][1] =  "ByteCode";
        sb.append(strs[0].length).append(strs[0][1]);
        return sb.toString();
    }

}
