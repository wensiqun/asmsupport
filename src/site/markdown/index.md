## 关于ASMSupport

ASMSupport是一个字节码操作类库, 他使得程序员更简单的能够在运行时动态修改和创建类. 该框架是基于[asm](http://asm.ow2.org/)开发的, 但是不同于直接使用asm的是，它避免了繁杂的jvm指令，以及对栈和局部变量的操作。

## 如何获取

添加一下依赖到你的pom.xml:

```xml
<dependency>
  <groupId>cn.wensiqun</groupId>
  <artifactId>asmsupport</artifactId>
  <version>0.4.1</version>
</dependency>
```

## 模块

| 模块|描述|
|:-------------|:-------------|
|asmsupport-thrid|这个模块的包含了asmsupport使用的第三方依赖包的源码，这些源码在asmsupport中都重新定义了命名空间，这样的好处就是实现零依赖，同时在开发过程中避免了因为使用asmsupport而产生包冲突|
|asmsupport-standard|这个模块是asmsupport的标准，可以基于这个api标准完成一个新的asmsupport的实现|
|asmsupport-core|这个模块是asmsupport-standard的一个核心实现|
|asmsupport-client|这个模块是对asmsupport-core的一个高层封装，实现了很多简便的方式，比如链式调用，同时也实现了asmsupport-standard标准.|
|asmsupport-issues|这个模块包含了所以asmsupport以往的issue的测试类，这个包的作用可以在后续开发中确保运行'maven test'的时候同一问题不再出现。|
|asmsupport-sample|这个模块包含了一些asmsupport的实例, 比如动态代理，json序列化等等.|

