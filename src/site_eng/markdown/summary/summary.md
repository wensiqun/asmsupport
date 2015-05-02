# About ASM

ASM is an all purpose Java bytecode manipulation and analysis framework. It can be used to modify existing classes or dynamically generate classes, directly in binary form. Provided common transformations and analysis algorithms allow to easily assemble custom complex transformations and code analysis tools.

# ASMSupport

The asmsupport is a java class byte code operate library, it make easier to write or modify a class at runtime. This framework developed base on [asm](http://asm.ow2.org/) framework, but different from asm that avoid use original jvm instruction and avoid maintain stack and local variables.

# Compare ASM & ASMSupport

We compare two method to generate class between asm framework and asmsupport framework.

## java source code

    public class Test {
        public static void main(String name) {
            System.out.println("Hello : " + name);
        }
    }
    
## asm framework code

    1.  ClassWriter mv = cw.visitMethod(Opcodes.ACCpublic_ + Opcodes.ACC_STATIC, "main", "(Ljava/lang/String;)V", null, null);
    2.  mv.visitCode();
    3.  Label l0 = new Label();
    4.  mv.visitLabel(l0);
    5.  mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    6.  mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
    7.  mv.visitInsn(DUP);
    8.  mv.visitLdcInsn("Hello : ");
    9.  mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
    10. mv.visitVarInsn(ALOAD, 0);
    11. mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
    12. mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
    13. mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    14. Label l1 = new Label();
    15. mv.visitLabel(l1);
    16. mv.visitInsn(RETURN);
    17. Label l2 = new Label();
    18. mv.visitLabel(l2);
    19. mv.visitLocVar("name", "Ljava/lang/String;", null, l0, l2, 0);
    20. mv.visitMaxs(4, 1);
    21. mv.visitEnd();
    22. cw.visitEnd();
    23. byte[] classBytes = cw.toByteArray();
    24. //convert class Bytes to class

As you see, there have most jvm instruction that's you need to know, and will be careful stack and local variable operate.

## asmsupport

    DummyClass dc = new DummyClass().public_().name("TestCommon").setClassOutPutPath(".//target//dummy-generated");
    dc.newMethod("main").public_().static_()
      .argTypes(String.class).argNames_("name")
      .body_(new MethodBody(){
        @Override
        public void body(LocVar... args) {
            call_(defType(System.class).field("out"), "println", stradd(val("Hello : "), args[0]));
            return_();
        }
      }
    );
    Class cls = dc.build();
    
