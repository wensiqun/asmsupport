package cn.wensiqun.asmsupport.core.utils;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class CommonUtils {

    public static int getSystemJDKVersion() {
        String str = System.getProperty("java.version");
        if(str.startsWith("1.7")) {
            return Opcodes.V1_7;
        }
        if(str.startsWith("1.6")) {
            return Opcodes.V1_6;
        }
        if(str.startsWith("1.5")) {
            return Opcodes.V1_5;
        }
        if(str.startsWith("1.4")) {
            return Opcodes.V1_4;
        }
        if(str.startsWith("1.3")) {
            return Opcodes.V1_3;
        }
        if(str.startsWith("1.2")) {
            return Opcodes.V1_2;
        }
        if(str.startsWith("1.1")) {
            return Opcodes.V1_1;
        }
        return Opcodes.V1_6;
    }
    
}
