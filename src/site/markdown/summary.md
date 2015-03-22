# 关于ASM

ASM 是一个 Java 字节码操纵框架。它可以直接以二进制形式动态地生成 stub 类或其他代理类，或者在装载时动态地修改类。ASM 提供类似于 BCEL 和 SERP 之类的工具包的功能，但是被设计得更小巧、更快速，这使它适用于实时代码插装。

# ASMSupport

asmsupport实在asm的基础上做的一封装，使用asmsupport将最大限度的降低字节码操作的成本，屏蔽了使用asm的时候对堆栈操作，以及原生的字节码指令。同时增加了更多的灵活性，采用匿名类的方式是方法块更清晰。

# 对比

这里我们简单的使用asm和asmsupport来生成如下代码：

## java源

    public class Test {
        public static void main(String name) {
            System.out.println("Hello : " + name);
        }
    }
    
## asm

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
    19. mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l2, 0);
    20. mv.visitMaxs(4, 1);
    21. mv.visitEnd();
    22. cw.visitEnd();
    23. byte[] classBytes = cw.toByteArray();
    24. //convert class Bytes to class
      
从上面可以看到我们想要创建一个class不仅需要使用复杂的字节码指令(比如第5，6,9...行)，而且还要关注堆栈操作(比如第10行就是从局部变量中获取第0个位置，就是方法的参数name)，同时在最后需要确定好方法所需要的堆栈数目(第20行)。对一些隐式的方法字节码还需特殊处理，比如第6~12行的代码对应的就是("Hello : " + name),但实际根据字节码生成的内容实际是调用了new StringBuilder("Hello : ").append(name).toString().这样就大大的增加了开发难度。

## asmsupport

    DummyClass dc = new DummyClass().public_().name("TestCommon").setClassOutPutPath(".//target//dummy-generated");
    dc.newMethod("main").public_().static_()
      .argTypes(String.class).argNames_("name")
      .body_(new MethodBody(){
        @Override
        public void body(LocalVariable... args) {
            call_(defType(System.class).field("out"), "println", stradd(val("Hello : "), args[0]));
            return_();
        }
      }
    );
    Class cls = dc.build();
    
使用asmsupport完全屏蔽了字节码指令和堆栈操作，更重要的一点是采用匿名类的方式，是的程序的结构更清晰，倾向与原始的java代码结构，使用简单更灵活。