#Release Note

## 0.4.1

### 1. 项目结构模块化
    
将项目拆分成如下模块

|模块|描述|依赖|
|:-------------|:-------------|:-------------|
|asmsupport-thrid|这个模块的包含了asmsupport使用的第三方依赖包的源码，这些源码在asmsupport中都重新定义了命名空间，这样的好处就是实现零依赖，同时在开发过程中避免了因为使用asmsupport而产生包冲突|NONE|
|asmsupport-standard|这个模块是asmsupport的标准，可以基于这个api标准完成一个新的asmsupport的实现|asmsupport-thrid|
|asmsupport-core|这个模块是asmsupport-standard的一个核心实现|asmsupport-standard|
|asmsupport-client|这个模块是对asmsupport-core的一个高层封装，实现了很多简便的方式，比如链式调用，同时也实现了asmsupport-standard标准.|asmsupport-core|
|asmsupport-issues|这个模块包含了所以asmsupport以往的issue的测试类，这个包的作用可以在后续开发中确保运行'maven test'的时候同一问题不再出现。|asmsupport-client|
|asmsupport-sample|这个模块包含了一些asmsupport的实例, 比如动态代理，json序列化等等.|asmsupport-client|

拆分模块主要做作用就是标准化接口，后续能够针对统一的接口实现不同版本的封装，逐步依赖使得结构更清晰，作为使用者可直接引用core或者client模块。

### 2. 支持方法的链式调用

java代码：

    String message = new StringBuilder().append("Hello ASMSupport")

老版本：

    Var message = var("message", StringBuilder.class, call(new_(StringBuilder.class), "append", val("Hello, ASMSupport")))
    
新版本：

    Var message = _new(StringBuilder.class).call("append", val("Hello, ASMSupport")).asVar("message", StringBuilder.class)
    
新版本同样支持老版本的方式。
    
### 3. 底层支持jls 4.10支持

### 4. 添加部分接口

### 5. 修复Bug
