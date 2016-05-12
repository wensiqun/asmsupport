![asmsupport](http://asmsupport.github.io/images/logo.png)

[![Coverage Status](https://coveralls.io/repos/github/wensiqun/asmsupport/badge.svg?branch=master)](https://coveralls.io/github/wensiqun/asmsupport?branch=master)

[![Build Status](https://travis-ci.org/wensiqun/asmsupport.svg?branch=master)](https://travis-ci.org/wensiqun/asmsupport) [![Maven central](https://maven-badges.herokuapp.com/maven-central/cn.wensiqun/asmsupport/badge.svg)](http://search.maven.org/#search|ga|1|g%3A%22cn.wensiqun%22%20AND%20a%3A%22asmsupport%22)

[![openhub](https://www.openhub.net/p/asmsupport/widgets/project_thin_badge.gif)](https://www.openhub.net/p/asmsupport)

[中文README](./README_CN.md)


A java class byte code operate framework
---

The asmsupport is a java class byte code operate library, it make easier to write or modify a class at runtime. This framework developed base on [asm](http://asm.ow2.org/) framework, but different from asm that avoid use original jvm instruction and avoid maintain stack and local variables.

## Modules

| Module|Description|
|:-------------|:-------------|
|asmsupport-thrid|This modules is a set of third open source code, the benefit is to avoid third library confilict when you use asmsupport.|
|asmsupport-standard|This module is a standard apid definition of asmsupport, you can implement a new framework of asmsupport will be following this standard api.|
|asmsupport-core|This module is the core implement of asmsupport-standard.|
|asmsupport-client|This module is the wrapper of asmsupport-core, and it's also implement the asmsupport-standard api|
|asmsupport-issues|This module is all of the test code for each issue test, the benefit of this modules is make the issue never reproduce again when you run 'mvn test'|
|asmsupport-sample|Some simple sample use asmsupport, such as dynamic proxy, json serialize and so on.|
      
## Maven Dependency
    
    <dependency>
        <groupId>cn.wensiqun</groupId>
        <artifactId>asmsupport-client</artifactId>
        <version>x.x.x</version>
    </dependency>
    
## License

Asmsupport is licensed under the GNU Lesser General Public License (LGPL)

## First Case

The following code will generate.


    public class FirstCase {
        
        public static void main(String[] args) {
            System.out.println("Hello ASMSupport.");
        }
        
    }

The following is code to generate preceding case.

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
    
    
## Sample : JSON & Proxy 

The JSON sample under the package "cn.wensiqun.asmsupport.sample.client.json下", run the "cn.wensiqun.asmsupport.sample.client.json.demo.Runner" main method, and you can get the generated class in folder "target/asmsupport-test-generated".


The Proxy sample under the package "cn.wensiqun.asmsupport.sample.client.proxy", run the cn.wensiqun.asmsupport.sample.client.proxy.demo.Runner main method, and you can get the generated class in folder "target/asmsupport-test-generated".
