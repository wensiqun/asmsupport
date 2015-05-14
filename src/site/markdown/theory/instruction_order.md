# 指令顺序的控制

## 1.语句的控制

在java源文件中，各种操作通过JVM都会转换成一个或者多个字节码指令，可以说操作是指令的有序集合。按照这个思路，我们采用逆向思维思考，我们要获得一个有序的指令队列，可以先获得一个有序的操作队列，假设ASMSupport需要生成如下代码一内容：

代码1

    int i = 1;
    int j = 2;
 
对应的图如下:

<img src="http://asmsupport.github.io/images/theory_instruction_order_1.png"/>

图1

这样我们就将如何规划指令的顺序转换成如何规划操作顺序。按照面向对象的思路，首先必然的是先创建对各种操作抽象成类(比如上面例子中的复制操作)，在各个操作类中定义该操作的指令顺序和规范，这里为所有的操作创建一个父类AbstractOperator，这个类的雏形如下：

代码2

    public abstract class AbstractOperator{
        public AbstractOperator(List queue) {
            queue.add(this);
        }

        public abstract void execute();
    }
    
所有子类都要重写execute方法，并且在这个方法中定义操作的指令规则。 通过构造方法就看出每当新建一个对象就将其添加到操作队列。对于代码一中由于每一条语句只有一个操作是没问题的，如果在一条程序语句中有多个操作如何实现呢。比如：

代码3

    int i = 1;
    int j = 1 + 2;
    
上面的代码中"int j=1 + 2"，包含了两个操作:

- 加法操作
- 赋值操作

换言之，我们的在操作队列中每一个单元并不是存储单一的操作，而是java源码中的一条有意义的语句，也就是java源码中的每一个分号分隔的语句。由于每一个语句中的操作是有依赖关系和先后关系的，所以我们在定义操作某一操作类的时候，需要将依赖的操作存储的类成员变量中。这里我们将这些被依赖的操作统一定义一个接口叫Parameterized。什么样的操作需要实现这个接口呢如下：

- 常量 ： 在asmsupport中所有的常量都是通过Value【 锚|】类表示的。
- 变量 ：局部变量和成员变量。
- 具有返回类型： 比如加法操作是具有返回值的。
Parameterized接口目前来看应该需要如下方法：

- getParamterizedType ： 获取返回类型
- loadToStack  :  将当前操作的结果压入栈，供其他操作使用，大多数情况下此方法都是直接调用execute方法
- asArgument  :  如果A操作依赖B操作，那么在创建完A操作之后调用B操作的asArgument方法，该方法的作用是将当前当前操作对象从操作队列中移除。

下面我们根据代码3定义两个操作

### 加法操作

代码4

	public class Addition extends AbstractOperator implements Parameterized {
	     
	    protected Parameterized factor1;
	    protected Parameterized factor2;
	    
	    protected Addition(List queue, Parameterized factor1, Parameterized factor2) {
	        super(queue);
	        this.factor1 = factor1;
	        this.factor2 = factor2;
	    }
	
	    public void execute(){
	        factor1.loadToStack();
	        factor2.loadToStack();
	        ... //调用iadd指令
	    }
	
	    public void loadToStack(){
	        this.execute();
	    }
	
	    public void asArgument() { 
	        block.removeExe(this);
	    }
	    
	}
	
### 赋值操作

代码5

	public class LocVarAssigner extends AbstractOperator implements Parameterized {
	     
	    private LocVar var;
	    private Parameterized value;
	    
	    protected LocVarAssigner(List queue, LocVar var, Parameterized value) {
	        super(queue);
	        this.var= var;
	        this.value= value;
	    }
	
	    public void execute() {
	        value.loadToStack();
	        ... //调用istore指令
	    }
	
	    public void loadToStack() {
	        this.execute();
	    }
	
	    public void asArgument() { 
	        block.removeExe(this); 
	    }
	}
	
上面两个例子中，execute方法内首先调用被依赖的操作的loadToStack方法，通过调用这个方法执行被依赖操作并且将结果压入栈，然后再执行自身操作。这样的结构像是一个责任链的模式，操作列表的每个单元只需要存储该责任链的头部，而责任链中其他的操作的调用则交由该操作的上一节点完成。下面我们用上面两个类实现代码3的内容：

代码6

	//设已经能够存在变量i，j，执行队列queue
	LocVarAssigner i_assign = new LocVarAssigner(queue, i, Value.value(1));
	Addition add = new Addition(queue, i, Value.value(1), Value.value(2));
	LocVarAssigner j_assign = new LocVarAssigner(queue, j, add);
	
但是对于上面的代码，由于每次new一个对象的时候都会将当前对象存储到queue中，所以生成的执行队列如下图所示：

<img src="http://asmsupport.github.io/images/theory_instruction_order_2.png"/>

图2

ASMSupport拿到这个队列遍历执行execute的时候会执行两次add的execute方法，这样肯定是不对的，目前执行顺序是“i\_assign->add->add->j\_assign”。因为逻辑上add的执行应该交由j\_assign负责，我们期望的顺序是"i\_assign->add->j\_assign"。也就是上图中红线框住的部分应该移除的，上面我们已经介绍过了每个Parameterized类的asArgument方法，所以当我们创建完j_assign应该继续调用add.asArgument()方法，将其从队列中移除，我们改进下代码6：

代码7

	//设已经能够存在变量i，j，执行队列queue
	LocVarAssigner i_assign = new LocVarAssigner(queue, i, Value.value(1));
	Addition add = new Addition(queue, i, Value.value(1), Value.value(2));
	LocVarAssigner j_assign = new LocVarAssigner(queue, j, add);
	add.asArgument();
	
经过这段代码生成的执行队列如下图：

<img src="http://asmsupport.github.io/images/theory_instruction_order_3.png"/>

图3

## 2.程序块的控制

上面介绍的语句的控制的基础是在同一个程序块，也就是说不存在if～else～之类的分支，这一部分介绍程序块指令的控制，对于一个方法类说最大的程序块就是方法本身，我们在编写java源代码的时候，程序块不仅包含了一系列的java语句，而且程序块之间是可以嵌套的，因此，逻辑上程序块可以看成是一组有序语句和程序块的集合。因此在上一节中我们所说的**操作队列**应该还能够存储程序块，在这一节这个队列我们称之为**可执行队列**。

在之前的设计中，执行队列完全独立于任何操作，但这里通过上面的描述，我们逻辑上可以这样设计：每一个程序块都拥有它自己的**可执行队列**用于存储该程序块下的所有操作和它所拥有的程序块。换句话说可执行队列从原先的独立状态转换成了依附于程序块的状态。

对于这个可执行队列，操作的执行上节介绍过，是生产字节码指令的过程；对于程序块的执行，则是遍历当前的程序块的可执行队列，执行队列中的每一个元素。下面用例子来描述，我们在代码3上做进一步改进

代码8

	int i = 1;
	try{
	    int m = 3;
	    int n = 4;
	}finally{
	    int p = 5;
	}
	int j = 1 + 2;
	
这段代码所对应的可执行队列如下：

<img src="http://asmsupport.github.io/images/theory_instruction_order_4.png"/>

图3

通过上面的描述，可执行队列的角度上来说，操作和程序块同属于一个类别，所以我们抽象出一个接口Executable，在这个接口中定义两个方法：

- prepare
- execute

对于操作的execute方法，是一个生成每个操作的字节码的过程；对于程序块的prepare方法，则是完成遍历该程序块中的可执行队列，并且依次执行队列中每个元素的execute方法的过程。

这里介绍下prepare方法，这个方法是在调用execute方法之前调用的。这里并不是调用完prepare方法之后紧接着调用execute方法，而是做了两次遍历，第一次调用所有程序块和操作的prepare方法，第二次调用所有程序块和操作的execute方法。对于操作的prepare方法，主要是做一些参数校验，判断是否存在自增减操作等等.

在调用prepare方法的时候也就意味着调用**操作队列**的开始，由于程序块和操作存在着类似父子的层级关系，所以所有操作或者程序块的prepare调用过程是一个嵌套调用。可以参考ProgramBlockInternal的prepare方法如下：

代码9

    public void prepare() {
        init();
        scope.getStart().setName(this.getClass().toString() + " start");
        scope.getEnd().setName(this.getClass().toString() + " end");

        generate();

        // just trigger if the last is SerialBlock in the queue
        OperatorFactory.newOperator(BlockEndFlag.class, new Class[] { ProgramBlockInternal.class }, getExecutor());
    }
	
在这段代码中调用了generate()方法，这是个抽象方法，最终会调用到我们创建的各种程序块的匿名类的body方法中。而在body方法内，我们会创建子程序块或者操作，而在创建子程序块时会调用子程序块的prepare方法。如下是ProgramBlockInternal的if_方法：

代码10

    @Override
    public final IFInternal if_(IFInternal ifBlock) {
        ifBlock.setParent(getExecutor());
        getQueue().add(ifBlock);
        ifBlock.prepare();
        return ifBlock;
    }

在prepare方法中我们将完成了**可执行队列**的构建。再在第二次遍历执行execute的时候，就是按顺序执行可以执行队列，在这里面完成字节码的构建。

这一部分基本能够了解ASMSupport是如何控制字节码指令生成的策略的，当然以上代码都是一些比较原始的雏形，ASMSupport对其做了比较细致的封装。