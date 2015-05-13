![asmsupport](http://asmsupport.github.io/images/logo.png)

[![Build Status](https://travis-ci.org/wensiqun/asmsupport.svg?branch=master)](https://travis-ci.org/wensiqun/asmsupport) [![Maven central](https://maven-badges.herokuapp.com/maven-central/cn.wensiqun/asmsupport/badge.svg)](http://search.maven.org/#search|ga|1|g%3A%22cn.wensiqun%22%20AND%20a%3A%22asmsupport%22)

[English Version](./README.md)

一个Java字节码操作类库
---
asmsupport是一个字节码操作类库，它能够让程序员非常简单的在动态创建和修改类，该框架是基于[asm](http://asm.ow2.org/)开发的，不同与asm的是，它避免了直接操作jvm指令，栈和局部变量。更多内容可见[文档](http://asmsupport.github.io)

## 模块

| 模块|描述|
|:-------------|:-------------|
|asmsupport-thrid|这个模块的包含了asmsupport使用的第三方依赖包的源码，这些源码在asmsupport中都重新定义了命名空间，这样的好处就是实现零依赖，同时在开发过程中避免了因为使用asmsupport而产生包冲突|
|asmsupport-standard|这个模块是asmsupport的标准，可以基于这个api标准完成一个新的asmsupport的实现|
|asmsupport-core|这个模块是asmsupport-standard的一个核心实现|
|asmsupport-client|这个模块是对asmsupport-core的一个高层封装，实现了很多简便的方式，比如链式调用，同时也实现了asmsupport-standard标准.|
|asmsupport-issues|这个模块包含了所有asmsupport以往的issue的测试类，这个包的作用可以在后续开发中确保运行'maven test'的时候同一问题不再出现。|
|asmsupport-sample|这个模块包含了一些asmsupport的实例, 比如动态代理，json序列化等等.|

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

