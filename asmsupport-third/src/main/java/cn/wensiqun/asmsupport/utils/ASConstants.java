package cn.wensiqun.asmsupport.utils;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

import java.util.UUID;

public interface ASConstants {

	String CLINIT = "<clinit>";
	String CLINIT_PROXY = "&clinit&";

	String INIT = "<init>";
	String INIT_PROXY = "&init&";
	String SUPER = "super";
	String THIS = "this";
	String METHOD_PROXY_SUFFIX = "@original_" + UUID.randomUUID().toString().replace('-', '$');
	String CLASS_PROXY_SUFFIX = "";

	int ASM_VERSION = Opcodes.ASM5;
	
}
