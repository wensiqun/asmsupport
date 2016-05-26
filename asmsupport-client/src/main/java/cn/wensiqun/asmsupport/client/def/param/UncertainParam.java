package cn.wensiqun.asmsupport.client.def.param;

/**
 * If asmsupport can't determined what's the behavior about a parameter, will
 * use this behavior, this behavior extends all behavior.
 * 
 * @author WSQ
 *
 */
public interface UncertainParam extends ArrayParam, BoolParam, NumParam, ObjectParam {
    
}
