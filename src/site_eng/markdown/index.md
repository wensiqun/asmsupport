## About ASMSupport

The asmsupport is a java class byte code operate library, it make easier to write or modify a class at runtime. This framework developed base on [asm](http://asm.ow2.org/) framework, but different from asm that avoid use original jvm instruction and avoid maintain stack and local variables.

## Get it now

To use ASMSupport in your project, add it to your pom.xml:

```xml
<dependency>
  <groupId>cn.wensiqun</groupId>
  <artifactId>asmsupport</artifactId>
  <version>0.4.1</version>
</dependency>
```

## Modules

| Module|Description|
|:-------------|:-------------|
|asmsupport-thrid|This modules is a set of third open source code, the benefit is to avoid third library confilict when you use asmsupport.|
|asmsupport-standard|This module is a standard apid definition of asmsupport, you can implement a new framework of asmsupport will be following this standard api.|
|asmsupport-core|This module is the core implement of asmsupport-standard.|
|asmsupport-client|This module is the wrapper of asmsupport-core, and it's also implement the asmsupport-standard api|
|asmsupport-issues|This module is all of the test code for each issue test, the benefit of this modules is make the issue never reproduce again when you run 'mvn test'|
|asmsupport-sample|Some simple sample use asmsupport, such as dynamic proxy, json serialize and so on.|
