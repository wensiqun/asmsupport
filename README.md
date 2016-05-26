![asmsupport](http://asmsupport.github.io/images/logo.png)

[![Build Status](https://travis-ci.org/wensiqun/asmsupport.svg?branch=master)](https://travis-ci.org/wensiqun/asmsupport) 
[![Coverage Status](https://coveralls.io/repos/github/wensiqun/asmsupport/badge.svg?branch=master)](https://coveralls.io/github/wensiqun/asmsupport?branch=master)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/cn.wensiqun/asmsupport/badge.svg)](http://search.maven.org/#search|ga|1|g%3A%22cn.wensiqun%22%20AND%20a%3A%22asmsupport%22)
[![openhub](https://www.openhub.net/p/asmsupport/widgets/project_thin_badge.gif)](https://www.openhub.net/p/asmsupport)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/91ea7bb3c0f7444a94899e45b9fad662)](https://www.codacy.com/app/wensiqun/asmsupport?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=wensiqun/asmsupport&amp;utm_campaign=Badge_Grade)
[![License (LGPL version 3.0)](https://img.shields.io/badge/license-GNU%20LGPL%20version%203.0-yellow.svg?style=flat-square)](http://opensource.org/licenses/LGPL-3.0)

[中文README](./README_CN.md)


Class byte code manipulate library
---

The asmsupport is a java class byte code manipulate library, it make easier to write or modify a class at runtime. This framework is base on [asm](http://asm.ow2.org/) framework, but different from asm that avoid use original jvm instruction and avoid maintain stack and local variables.

## Modules

| Module|Description|
|:-------------|:-------------|
|asmsupport-thrid|This modules is a set of third open source code, the benefit is to avoid third library confilict when you use asmsupport.|
|asmsupport-standard|This module is a standard apid definition of asmsupport, you can implement a new framework of asmsupport will be following this standard api.|
|asmsupport-core|This module is the core implement of asmsupport-standard.|
|asmsupport-client|This module is the wrapper of asmsupport-core, and it's also implement the asmsupport-standard api|
|asmsupport-grammar|This module is support to java grammar.|
|asmsupport-issues|This module is all of the test code for each issue test, the benefit of this modules is make the issue never reproduce again when you run 'mvn test'|
|asmsupport-sample|Some simple sample use asmsupport, such as dynamic proxy, json serialize and so on.|
|asmsupport-lesson|This module contains some asmsupport tutorials codes.|

## Maven Dependency
    
    <dependency>
        <groupId>cn.wensiqun</groupId>
        <artifactId>asmsupport-client</artifactId>
        <version>x.x.x</version>
    </dependency>

## Features

## Different

### [ASM](http://asm.ow2.org/)

### [BECL](http://commons.apache.org/proper/commons-bcel/)

### [javassist](http://jboss-javassist.github.io/javassist/)

## First Case

The [source](./asmsupport-sample/src/main/java/cn/wensiqun/asmsupport/sample/client/helloworld/HelloWorldMain.java) will using ASMSupport to generate the following code.

    public class FirstCase {
        
        public static void main(String[] args) {
            System.out.println("Hello ASMSupport.");
        }
        
    }
    
    
## Advanced Sample

### Json serialization base on byte code

The JSON sample under the package [cn.wensiqun.asmsupport.sample.client.json](asmsupport-sample/src/main/java/cn/wensiqun/asmsupport/sample/client/json), run the "cn.wensiqun.asmsupport.sample.client.json.demo.Runner" main method, and you can get the generated class in folder "target/asmsupport-test-generated".

### Dynamic Proxy base on byte code

The Proxy sample under the package [cn.wensiqun.asmsupport.sample.client.proxy](asmsupport-sample/src/main/java/cn/wensiqun/asmsupport/sample/client/proxy), run the cn.wensiqun.asmsupport.sample.client.proxy.demo.Runner main method, and you can get the generated class in folder "target/asmsupport-test-generated".
    
## License

Asmsupport is licensed under the GNU Lesser General Public License (LGPL)

