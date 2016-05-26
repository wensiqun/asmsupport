![asmsupport](http://asmsupport.github.io/images/logo.png)

[![Build Status](https://travis-ci.org/wensiqun/asmsupport.svg?branch=master)](https://travis-ci.org/wensiqun/asmsupport) 
[![Coverage Status](https://coveralls.io/repos/github/wensiqun/asmsupport/badge.svg?branch=master)](https://coveralls.io/github/wensiqun/asmsupport?branch=master)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/cn.wensiqun/asmsupport/badge.svg)](http://search.maven.org/#search|ga|1|g%3A%22cn.wensiqun%22%20AND%20a%3A%22asmsupport%22)
[![openhub](https://www.openhub.net/p/asmsupport/widgets/project_thin_badge.gif)](https://www.openhub.net/p/asmsupport)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/91ea7bb3c0f7444a94899e45b9fad662)](https://www.codacy.com/app/wensiqun/asmsupport?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=wensiqun/asmsupport&amp;utm_campaign=Badge_Grade)
[![License (LGPL version 3.0)](https://img.shields.io/badge/license-GNU%20LGPL%20version%203.0-yellow.svg?style=flat-square)](http://opensource.org/licenses/LGPL-3.0)

[English Version](./README_EN.md)

Java字节码操作类库
---
asmsupport是一个字节码操作类库，它能够让程序员非常简单的在动态创建和修改类，该框架是基于[asm](http://asm.ow2.org/)开发的，不同与asm的是，它避免了直接操作jvm指令，栈和局部变量。更多内容可见[文档](http://asmsupport.github.io)

## 模块

| 模块|描述|
|:-------------|:-------------|
|asmsupport-thrid|这个模块的包含了asmsupport使用的第三方依赖包的源码，这些源码在asmsupport中都重新定义了命名空间，这样的好处就是实现零依赖，同时在开发过程中避免了因为使用asmsupport而产生包冲突|
|asmsupport-standard|这个模块是asmsupport的标准，可以基于这个api标准完成一个新的asmsupport的实现|
|asmsupport-core|这个模块是asmsupport-standard的一个核心实现|
|asmsupport-client|这个模块是对asmsupport-core的一个高层封装，实现了很多简便的方式，比如链式调用，同时也实现了asmsupport-standard标准.|
|asmsupport-grammar|这个模块是支持对java语法直接编译。|
|asmsupport-issues|这个模块包含了所有asmsupport以往的issue的测试类，这个包的作用可以在后续开发中确保运行'maven test'的时候同一问题不再出现。|
|asmsupport-sample|这个模块包含了一些asmsupport的实例, 比如动态代理，json序列化等等.|
|asmsupport-lesson|这个模块包含了一些asmsupport基本教程实例.|

## Maven坐标
    
    <dependency>
        <groupId>cn.wensiqun</groupId>
        <artifactId>asmsupport-client</artifactId>
        <version>x.x.x</version>
    </dependency>

## 特点

ASMSupport最大的特点是将JAVA语法中的每一个操作和概念都赋予了一个具体的抽象（比如if程序块，四则运算，局部变量，都抽象出具体的类），最终构建出一个类似语法树的结构，这样带来有如下好处：

1. 屏蔽JVM指令
2. 以面向对象的方式兼容JAVA语法，开发简单
3. 由于将操作和概念都抽象成类，方便将一个大的字节码构建工程模块化。

## 不同

### [ASM](http://asm.ow2.org/)

ASMSupport是基于asm开发的，相比于ASM，ASMSupport屏蔽JVM字节码相关的细节，对于开发人员来说无需了解JVM字节码相关内容。

比如在ASM中你要使用局部变量是需要通过下标来访问的，相同位置的下标在不同的指令位置可能对应不同的局部变量，这样使用ASM就非常困难，而在ASMSupport中将每一个局部变量抽象成一个具体的对象，在任何位置使用该变量都能够轻松访问到。

### [BECL](http://commons.apache.org/proper/commons-bcel/)

BECL同样是基于asm开发的，他本身也是指令级别的字节码操作类库，所以ASMSupport和BECL的不同点和ASM一样。

### [javassist](http://jboss-javassist.github.io/javassist/)

javassist是源码级别的字节码操作类库，ASMSupport则是介于字节码和源码级别中间的字节码操作类库。使用javassist的好处就是非常直观易懂，基本没有门槛，但由于是源码级别，很多问题在编译时候无法暴露出来，并且对字节码构建过程的模块化也不是特别方便，由于javassit是基于源码的，所以其字节码生成过程中首先要做词法分析，语法分析，语义分析三个步骤后得到语法树，再通过语法树构建字节码。ASMSupport其实是在编码的过程中就已经将语法树结构确定下来，但在ASMSupport中并不是标准的语法树，所以为什么说ASMSupport则是介于字节码和源码级别中间的字节码操作类库。

## 第一个例子

在[代码](./asmsupport-sample/src/main/java/cn/wensiqun/asmsupport/sample/client/helloworld/HelloWorldMain.java)中我们将使用ASMSupport生成如下代码:

    public class FirstCase {
        
        public static void main(String[] args) {
            System.out.println("Hello ASMSupport.");
        }
        
    }

## 字节码构建流程图

![字节码构建流程图](http://asmsupport.github.io/images/bytecode-generate-flow.uml.svg)

## 进阶实例

在asmsupport-sample模块下提供了两种实例，1.json序列化，2.实现动态代理。

### 字节码实现json序列化

JSON的实例在包[cn.wensiqun.asmsupport.sample.client.json](asmsupport-sample/src/main/java/cn/wensiqun/asmsupport/sample/client/json)下，运行cn.wensiqun.asmsupport.sample.client.json.demo.Runner类，在target/asmsupport-test-generated下查看生成的类。

### 字节码实现动态代理

Proxy的实例在包[cn.wensiqun.asmsupport.sample.client.proxy](asmsupport-sample/src/main/java/cn/wensiqun/asmsupport/sample/client/proxy)下，运行cn.wensiqun.asmsupport.sample.client.proxy.demo.Runner类，在target/asmsupport-test-generated下查看生成的类。
    
## 许可

ASMSupport使用的是GNU Lesser General Public License (LGPL)许可。