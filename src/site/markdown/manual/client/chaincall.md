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

