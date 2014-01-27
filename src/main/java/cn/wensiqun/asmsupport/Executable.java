/**
 * 
 */
package cn.wensiqun.asmsupport;

/**
 * 此接口表示可以执行。
 * @author 温斯群(Joe Wen)
 *
 */
public interface Executable {
    
    /**
     * <p>执行execute的预先操作
     * 在此方法中如果要对执行队列操作 那么不能对执行队列做删除和添加操作 只能做替操作
     * </p>
     * 
     * <p>除了MethodBody类型的Block是通过直接调用的方式执行Prepare方法之外, 其他Block
     * 均在调用创建该Block的时候触发Prepare方法， 所有创建非MethodBody类型Block都在
     * jw.asmsupport.block.operator.CreateBlockOperator接口中</p>
     * 
     * <p>
     * 对于一般的Block，比如if...else...
     * 比如如下代码：
     * </p>
     * <pre>
     * public void method(){
     *     if(...){
     *        while(....){
     *           ...
     *        }
     *     }else{
     *        for(...){
     *           ...
     *        }
     *     }
     * }
     * </pre>
     * <p>
     * 通常执行prepare的顺序如下:
     * </p>
     * <pre>
     * $ 表示调用 CreateBlockOperator.xxx方法
     * _\ 表示触发prepare方法
     * 
     *    MethodBody
     *         |
     *         |$
     *         |____\ IF Block
     *         |          |
     *         |          |$
     *         |          |____\ While Block
     *         |                       |
     *         |                       |
     *         |/______________________|
     *         |           
     *         |$
     *         |____\ Else Block 
     *                    |
     *                    |$
     *                    |____\ For Block
     *                                 |
     *                                 |
     *   Method Body End /_____________|
     *   
     * </pre>
     * 
     * <p>
     * 但是对于每个连续的Try...Catch...Finally...块, asmsupport会先将这一连续的
     * 块逐一创建完成，再执行各自的Prepare方法。 如下图所示
     * </p>
     * 
     * <pre>
     * $ 表示调用 createBlockOperator.xxx方法
     * _\ 表示出发prepare方法
     * 
     *    MethodBody
     *         |
     *         |$
     *         |____\ Try Block 1
     *         |          | 
     *         |          |
     *         |                         |
     *         |$                        |
     *         |____\ Catch Block 1      |
     *         |                         |
     *         |$                        |
     *         |____\ Finally Block 1    |
     *         |                         |
     *         |                         |
     *         |_________________________|
     *         
     *                                 |
     *   Method Body End /_____________|
     *   
     * </pre>
     * 
     */
    public void prepare();
    
    /**
     * 执行
     * 
     * @return
     */
    public void execute();
    
}
