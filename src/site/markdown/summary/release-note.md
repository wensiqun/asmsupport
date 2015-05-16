#Release Note

## 0.4.1

### 1. 支持方法的链式调用

java代码：

    String message = new StringBuilder().append("Hello ASMSupport")

老版本：

    Var message = var("message", StringBuilder.class, call(new_(StringBuilder.class), "append", val("Hello, ASMSupport")))
    
新版本：

    Var message = _new(StringBuilder.class).call("append", val("Hello, ASMSupport")).asVar("String", StringBuilder.class)
    
### 2. 底层支持jls 4.10支持

### 3. 添加部分接口

### 4. 修复Bug
