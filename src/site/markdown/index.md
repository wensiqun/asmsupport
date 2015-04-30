## About ASMSupport

The asmsupport is a java class byte code operate library, it make easier to write or modify a class at runtime. This framework developed base on [asm](http://asm.ow2.org/) framework, but different from asm that avoid use original jvm instruction and avoid maintain stack and local variables.

## Get it now

To use ASMSupport in your project, add it to your pom.xml:

```xml
<skin>
  <groupId>cn.wensiqun</groupId>
  <artifactId>asmsupport</artifactId>
  <version>0.4.1</version>
</skin>
```

## Modules

| Module|Description|
|:-------------|:-------------|
|asmsupport-thrid|This modules is a set of third open source code, the benefit is to avoid third library confilict when you use asmsupport.|
|asmsupport-standard|This module is a standard apid definition of asmsupport, all implements of asmsupport will be following this standard api.|
|asmsupport-core|This module is the core implement of asmsupport-standard.|
|asmsupport-issues|This module is all of the test code for each issue test.|
|asmsupport-sample|Some simple sample use asmsupport, such as dynamic proxy, json serialize and so on.|
