ASMSupport[English Version](./README.md)
===

一个Java字节码操作类库
---
asmsupport是一个字节码操作类库，它能够让程序员非常简单的在动态创建和修改类，该框架是基于[asm](http://asm.ow2.org/)开发的，不同与asm的是，它避免了直接操作jvm指令，栈和局部变量。更多内容可见[文档](http://asmsupport.github.io)

## 文件夹结构

    asmsupport
      |-asmsupport-third           : ASMSupport的第三方依赖的源码
      |-asmsupport-standard        : ASMSupport的标准接口
      |-asmsupport-core            : ASMSupport的核心实现
      |-asmsupport-client          ： ASMSupport的client实现，是core的高一层的封装
      |-asmsupport-issues          ： ASMSupport的一些bug修复的测试类
      |-asmsupport-sample          : 使用ASMSupport实现的一些例子
      |-src/site                   : 项目标准的site文档
      |-LICENSE.txt                : LGPL license
      |-README.md                  : README英文版
      |-README_CN.md               : README中文版本

## Maven坐标
    
    <dependency>
        <groupId>cn.wensiqun</groupId>
        <artifactId>asmsupport</artifactId>
        <version>x.x.x</version>
    </dependency>
    
目前稳定版本 0.4
    
## 许可

ASMSupport使用的是GNU Lesser General Public License (LGPL)许可。

## 第一个例子

我们将使用ASMSupport生成如下代码:

    public class FirstCase {
        
        public static void main(String[] args) {
            System.out.println("Hello ASMSupport.");
        }
        
    }

下面是ASMSupport的代码:

    DummyClass dummy = new DummyClass("FirstCase").public_()
           .newMethod("main").public_().static_().argTypes(String[].class)
           .body(new MethodBody(){
					public void body(LocalVariable... args) {
						getType(System.class).field("out").call("println", val("Hello ASMSupport."))
						return_();
					}
           });
    Class<?> FirstCaseClass = dummy.build();
    Method mainMethod = FirstCaseClass.getMethod("main", String[].class);
    mainMethod.invoke(FirstCaseClass, mainMethod);
    
## 实例

在asmsupport-sample模块下提供了两种实例，1.json序列化，2.实现动态代理。

JSON的实例在包cn.wensiqun.asmsupport.sample.client.json下，运行cn.wensiqun.asmsupport.sample.client.json.demo.Runner类，在target/asmsupport-test-generated下查看生成的类。

Proxy的实例在包cn.wensiqun.asmsupport.sample.client.proxy下，运行cn.wensiqun.asmsupport.sample.client.proxy.demo.Runner类，在target/asmsupport-test-generated下查看生成的类。

