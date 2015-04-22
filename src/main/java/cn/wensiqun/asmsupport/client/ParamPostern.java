package cn.wensiqun.asmsupport.client;

import java.lang.reflect.Array;

import cn.wensiqun.asmsupport.core.definition.KernelParameterized;

public class ParamPostern {

    public static KernelParameterized getTarget(Param param) {
        return param.getTarget();
    }
    
    public static KernelParameterized[] getTarget(Param[] params) {
        if(params == null) {
            return null;
        }
        KernelParameterized[] paras = new KernelParameterized[params.length];
        for(int i=0; i<params.length; i++) {
            paras[i] = params[i].getTarget();
        }
        return paras;
    }
    
    public static Object getTarget(Object clientArray) {
        if(clientArray == null) {
            throw new NullPointerException("Client is null.");
        }
        if(clientArray.getClass().isArray()) {
            int len = Array.getLength(clientArray);
            Object[] internalArray = new Object[len];
            for(int i=0; i<len; i++) {
                internalArray[i] = getTarget(Array.get(clientArray, i));
            }
            return internalArray;
        } else if (clientArray instanceof Param){
            return ((Param)clientArray).getTarget();
        }
        throw new IllegalArgumentException();
    }
}
