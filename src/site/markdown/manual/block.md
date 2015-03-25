#创建程序块

之前介绍过如何创建方法，通过DummyClass/DummyEmum/DummyModifiedClass的newMethod/newConstructor/newStaticBlock/modifyMethod/modifyConstructor/modifyConstructor方法编辑方法体。而我们创建方法体都是通过匿名类的方式，而我们希望创建的方法的逻辑内容就是通过实现匿名类的body方法，在该方法中完成。

## if...else if...else

在body方法内通过调用CreateBlockAction的if_方法传入一个cn.wensiqun.asmsupport.client.IF的匿名类对象。如果下

    @Override
    public void body(LocalVariable... args) {
        if_(new IF(call("isTrue")){
            @Override
            public void body() {
                //Do if branch.
            }
        });
        return_();
    }
    
上面代码对应的java代码如下：

    if(isTrue()){
        //Do if branch.
    } 
    
上述代码我们构造IF对象的时候传入了一个参数，这个参数就是当前if语句的条判断。if_放方法返回的是我们传入的IF对象。通过这个对象我们就能够添加else if分支和else分支。

    @Override
    public void body(LocalVariable... args) {
        if_(new IF(call("isTrue")){
            @Override
            public void body() {
                //Do if branch.
            }
        }).elseif(new ElseIF(call("isTrue")){
            @Override
            public void body() {
                //Do else...if branch.
            }
        });
        
        if_(new IF(call("isTrue")){
            @Override
            public void body() {
                //Do if branch.
            }
        }).else(new Else(){
            @Override
            public void body() {
                //Do else branch.
            }
        });
        return_();
    }
    
上面代码对应的java代码如下：

    if(isTrue()){
        //Do if branch.
    } else if (isTrue()) {
        //Do else...if branch.
    }
    
    if(isTrue()){
        //Do if branch.
    } else {
        //Do else branch.
    }

上面我们通过调用IF对象的elseif方法和else方法引出了else..if分支和else分支，这个两个分支所产生的对象分别是cn.wensiqun.asmsupport.client.ElseIF和cn.wensiqun.asmsupport.client.Else, 构造ElseIF对象和构造IF类似同样要传入一个判断条件，而Else则没有。ElseIF对象继续可以展出else...if和else分支。而else则是终点站了。

    @Override
    public void body(LocalVariable... args) {
        if_(new IF(call("isTrue")){
            @Override
            public void body() {
                //Do if branch.
            }
        }).elseif(new ElseIF(call("isTrue")){
            @Override
            public void body() {
                //Do else...if branch.
            }
        }).elseif(new ElseIF(call("isTrue")){
            @Override
            public void body() {
                //Do else...if branch.
            }
        }).else(new Else(){
            @Override
            public void body() {
                //Do else branch.
            }
        });
        return_();
    }
    
上面代码对应的java代码如下：

    if(isTrue()){
        //Do if branch.
    } else if (isTrue()) {
        //Do else...if branch.
    } else if (isTrue()) {
        //Do else...if branch.
    } else if {
        //Do else branch.
    }

## try...catch...finally

在body方法内通过调用CreateBlockAction的try_方法传入一个cn.wensiqun.asmsupport.client.Try的匿名类对象。
try_方法将会返回Try对象，我们同样通过该对象能够展出cn.wensiqun.asmsupport.client.Catch对象和cn.wensiqun.asmsupport.client.Finally对象。和if有所不同，Try对象至少要匹配一个Catch或Finally对象，否则将会报异常，这个和java语言一致的。

    
    @Override
    public void body(LocalVariable... args) {
        try_(new Try(){
            @Override
            public void body() {
                //Do try branch.
            }
        }).catch_(new Catch(RuntimeException.class){
            @Override
            public void body(LocalVariable e) {
                //Do catch block
            }
        }).catch_(new Catch(Exception.class){
            @Override
            public void body(LocalVariable e) {
                //Do catch block
            }
        }).finally_(new Finally(){
            @Override
            public void body() {
                //Do finally block
            }
        });;
        
        try_(new Try(){
            //Do try branch.
        }).finally_(new Finally(){
            @Override
            public void body() {
                //Do finally block
            }
        });
        return_();
    }
    
上面的代码可以看出，和是使用java代码一样，通过Catch对象同样能够展出catch和finally分支。这里要注意下Catch类的body方法，该方法有一个参数"LocalVariable e", 这个参数就是捕获到的异常。上面的代码部分转换成java如下：

    try{
        //Do try branch.
    } catch (RuntimeException e) {
        // do catch block.
    } catch (Exception e) {
        // do catch block.
    } finally {
        //Do finally block
    }

    try{
        //Do try branch.
    } finally {
        //Do finally block
    }
 
## do...while循环

在body方法内通过调用CreateBlockAction的dowhile方法传入一个cn.wensiqun.asmsupport.client.DoWhile的匿名类对象。如果下
    
    @Override
    public void body(LocalVariable... args) {
        dowhile(new DoWhile(call("isTrue")){
            @Override
            public void body(){
                //Do do..while block.
            }
        });
        return_();
    }

对应java代码如下：

    do {
        //Do do...while block
    } while(isTrue())   
    
## while循环

在body方法内通过调用CreateBlockAction的while_方法传入一个cn.wensiqun.asmsupport.client.While的匿名类对象,和do...while类似：
    
    @Override
    public void body(LocalVariable... args) {
        while_(new While(call("isTrue")){
            @Override
            public void body(){
                //Do while block.
            }
        });
        return_();
    }
    
对应java代码如下：

    while(isTrue()) {
        //Do do...while block
    } 

## for...each循环

在body方法内通过调用CreateBlockAction的foreach方法传入一个cn.wensiqun.asmsupport.client.ForEach的匿名类对象

    @Override
    public void body(LocalVariable... args) {
        foreach(new ForEach(arrayOrIterable, String.class){
            @Override
            public void body(LocalVariable ele){
                //Do for each loop
            }
        });
        return_();
    }

通过上面代码可以看出，首先构造ForEach对象，这个对象传入两个参数，第一个是集合或是数组，第二个是该集合或者数组的每个元素的类型。ForEach的body方法有个参数，就是我们遍历的每一个数组元素，而这个数组元素我们强制转换成了String类型，就是构造方法的第二个参数所指代的类型。这样在body中我们就对每一个数组元素执行相应代码。

ForEach有三个构造方法

- ForEach(ExplicitVariable iteratorVar) : 构造ForEach，使用默认的元素类型Object类型
- ForEach(ExplicitVariable iteratorVar, AClass elementType) : 构造ForEach并且指代元素类型
- ForEach(ExplicitVariable iteratorVar, Class elementType) : 构造ForEach并且指代元素类型

如果我们在上面的代码中使用第一个构造方法，那么在body方法内如果需要使用每个元素则先要强制转换如下：

    @Override
    public void body(LocalVariable... args) {
        foreach(new ForEach(arrayOrIterable){
            @Override
            public void body(LocalVariable ele){
                LocalVariable stringObj = var("stringObj", String.class, checkcast(ele, String.class))
                //Do for each loop
            }
        });
        return_();
    }

所以建议使用后两个方法。上面的代码转换成java代码如下：

    for(String ele : arrayOrIterable) {
        //Do for each loop
    }

考虑到传统的while语句已经可以完成for循环的操作等其他因素，asmsupport并没有实现传统的for循环，后期考虑添加传统for循环。

## synchronized块

相对来说synchronized比较简单，通过CreateBlockAction的sync方法完成。

    @Override
    public void body(LocalVariable... args) {
        sync(new Synchronized(lockObj){
            @Override
            public void body(Parameterized lock){
                //Do synchronized loop
            }
        });
        return_();
    }

在构造Synchronized的时候需要指定一个同步对象。上述代码如下:

    synchronized(lockObj) {
        //Do synchronized loop
    }
