#操作的链式调用

在0.4.1版本之前的所有的操作是采用嵌套调用完成的，自0.4.1版本之后支持了链式调用，比如下面代码：

    String message = new StringBuilder().append("Hello,").append("ASMSupport")

采用嵌套调用方式：

    Var message = 
	    var("message", StringBuilder.class, 
	        call(
	            call(
	                new_(StringBuilder.class), "append", val("Hello,")
	            ), 
	            "append", val("ASMSupport")
	        )
	    )

采用链式调用方式：

    Var message = _new(StringBuilder.class).call("append", val("Hello,")).call("append", val("ASMSupport")).asVar("message", StringBuilder.class)
    
链式调用的接口均定义在包cn.wensiqun.asmsupport.client。def.behavior，我们可以通过ProgramBlock调用一个操作，这个操作将返回链式调用的接口对象。