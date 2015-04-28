# 修改类

## 构造对象

使用DummyModifiedClass修改class

    1. DummyModifiedClass dummy = new DummyModifiedClass(A.class);

传入的A.class为希望修改的类

## 新建Field/Method/构造方法

在被修改类中新建Field/Method/构造方法和创建Class的时候相同，可参考文档manual/class.md

## 修改方法

    dummy.modify("test", new Class[]{int.class, String}, new ModifiedMethodBody(){
    
        @Override
         public void body(LocVar... args) {
             //TODO
         }
    
    });

上面的代码是修改方法test(int a, String b). 目前修改方法我们只允许修改方法内容，理论上讲也是可以修改方法的修饰符参数列表的，但是这的意义并不大，因为我们修改方法的主要目的是想增加一些业务逻辑，而不是将方法签名都改了，如果真是这样还不如新建一个方法。

上面的第三个参数就是我们修改的方法方法体。我们希望修改的业务逻辑在标记"//TODO"的位置上。我们能够在这里写入自己的逻辑。比如下面：

    @Override
    public void body(LocVar... args) {
        GlobalVariable out = defType(System.class).field("out");
        call(out, "println", val("Hello ASMSupport"))
        return_();
    }

我们将方法修改成了只打印一句"Hello ASMSupport"，如果希望添加一个逻辑，就是计算方法执行时间，那么就需要调用方法被修改前的内容了，代码如下：

    @Override
    public void body(LocVar... args) {
        LocVar time = var("time", long.class, call(System.class, "currentTimeMillis"));         
        callOrig();
        GlobalVariable out = defType(System.class).field("out");
        call(out, "println", sub(call(System.class, "currentTimeMillis"), time));
        return_();
    }

上面的代码中callOrig()方法就是调用原方法的内容。如果原方法是有返回值的，就可以使用下面代码：


    @Override
    public void body(LocVar... args) {
        LocVar time = var("time", long.class, call(System.class, "currentTimeMillis"));         
        LocVar result = var("result", getOrigReturnType(), callOrig());
        GlobalVariable out = defType(System.class).field("out");
        call(out, "println", sub(call(System.class, "currentTimeMillis"), time));
        return_(result);
    }
     
其中getOrigReturnType()就是获取被修改方法返回类型。

## 修改构造方法

    dummy.modifyConstructor(new Class[]{int.class, String.class}, new ModifiedMethodBody(){
    
        @Override
        public void body(LocVar... args) {
            //TODO
        }
     
    });
    
和修改方法类似，但是没有传入方法名，因为是构造方法。但是在使用的时候注意，在修改构造方法的时候调用callOrig()是没有返回结果的。

## 修改静态程序块

    dummy.modifyConstructor(new ModifiedMethodBody(){
    
        @Override
        public void body(LocVar... args) {
            //TODO
        }
     
    });

和修改构造方法类似，但是没有传参数类型列表，同样调用callOrig()是没有返回结果的。   

## 设置ClassLoader

我们内置ClassLoader来完成我们生成的class的字节数组到class对象，当然也允许自定义classloader通过下面的方法

    dummy.setClassLoader(classloader);

## 设置class文件输出目录

    dummy.setClassOutPutPath("../classes/");
    
设置这个属性，我们生成的class将会输出到指定目录，这样我们可以通过反编译大致看下class内容是否和预期相同。

## 开始创建

    Class<?> clazz = dummy.build();   