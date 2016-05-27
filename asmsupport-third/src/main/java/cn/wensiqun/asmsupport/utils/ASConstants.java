package cn.wensiqun.asmsupport.utils;

import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

import java.util.UUID;

public interface ASConstants {
	
	String CLASS_QUALIFIED_NAME_REGEX = "^[\\p{L} .'-]+$";
	
	String CLINIT = "<clinit>";
	String CLINIT_PROXY = "&clinit&";

	String INIT = "<init>";
	String INIT_PROXY = "&init&";
	String SUPER = "super";
	String THIS = "this";
	String METHOD_PROXY_SUFFIX = "@original_" + UUID.randomUUID().toString().replace('-', '$');
	String CLASS_PROXY_SUFFIX = "";

	int ASM_VERSION = Opcodes.ASM5;

	String ACCESS_TOKEN_PUBLIC = "public";

	String ACCESS_TOKEN_PRIVATE = "private";

	String ACCESS_TOKEN_PROTECTED = "protected";

	String ACCESS_TOKEN_STATIC = "static";

	String ACCESS_TOKEN_FINAL = "final";

	String ACCESS_TOKEN_SYNCHRONIZED = "synchronized";

	String ACCESS_TOKEN_VOLATILE = "volatile";

	String ACCESS_TOKEN_ABSTRACT = "abstract";
	
}
