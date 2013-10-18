package cn.wensiqun.asmsupport.operators.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;

import cn.wensiqun.asmsupport.block.ProgramBlock;
import cn.wensiqun.asmsupport.exception.ASMSupportException;
import cn.wensiqun.asmsupport.operators.AbstractOperator;
import cn.wensiqun.asmsupport.operators.listener.ReturnOperatoInCatchWithFinallyListener;

public abstract class OperatorFactory {
	
	private static List<NewOperatorListener> oneTimeListeners = new Vector<NewOperatorListener>();
	
	private static List<NewOperatorListener> multiTimeListeners = new Vector<NewOperatorListener>();

	static{
		NewOperatorListener cfListener = new ReturnOperatoInCatchWithFinallyListener();
        OperatorFactory.addMultiTimeListener(cfListener);
	}
	
	public static void resetListener(){
		//oneTimeListeners = new Vector<NewOperatorListener>();
		//multiTimeListeners = new Vector<NewOperatorListener>();
	}
	
	public static void addOneTimeListener(NewOperatorListener linstener){
		oneTimeListeners.add(linstener);
	}

	public static boolean removeOneTimeListener(NewOperatorListener linstener){
		return oneTimeListeners.remove(linstener);
	}
	
	public static void addMultiTimeListener(NewOperatorListener linstener){
		multiTimeListeners.add(linstener);
	}
	
	public static boolean removeMultiTimeListener(NewOperatorListener linstener){
		return multiTimeListeners.remove(linstener);
	}

	/**
	 * 通过反射创建字节码操作
	 * @param <T>
	 * @param clazz
	 * @param parameterTypes
	 * @param arguments
	 * @return
	 */
	public static <T extends AbstractOperator> T newOperator(Class<T> clazz, Class<?>[] parameterTypes, Object... arguments){
		if(parameterTypes != null && arguments != null){
			if(!ArrayUtils.isSameLength(parameterTypes, arguments)){//parameterTypes.length != arguments.length){
				throw new ASMSupportException();
			}
		}else if(ArrayUtils.isEmpty(parameterTypes) || ArrayUtils.isEmpty(arguments)){
			throw new NullPointerException();
		}else if(!ProgramBlock.class.equals(parameterTypes[0])){
		    throw new ASMSupportException("first argument type must be ProgramBlock");	
		}
		
		ProgramBlock executeBlock = (ProgramBlock) arguments[0];
		
		executeBlock.tiggerTryCatchPrepare();
		try {
			//触发一次性任务的监听器
			List<NewOperatorListener> foundOneTimeListeners = null;
			for(NewOperatorListener linstener : oneTimeListeners){
				linstener.setExecuteBlock(executeBlock);
				linstener.setOperatorClass(clazz);
				linstener.setParameterTypes(parameterTypes);
				linstener.setArguments(arguments);
				
				if(linstener.triggerCondition()){
					if(foundOneTimeListeners == null){
						foundOneTimeListeners = new ArrayList<NewOperatorListener>();
					}
					linstener.beforeNew();
					foundOneTimeListeners.add(linstener);
					break;
				}else{
					linstener.setOperatorClass(null);
					linstener.setParameterTypes(null);
					linstener.setArguments(null);
				}
			}

			//触发多次性任务的监听器
			List<NewOperatorListener> foundMultiTimeListeners = null;
			for(NewOperatorListener linstener : multiTimeListeners){
				linstener.setExecuteBlock((ProgramBlock)arguments[0]);
				linstener.setOperatorClass(clazz);
				linstener.setParameterTypes(parameterTypes);
				linstener.setArguments(arguments);
				
				if(linstener.triggerCondition()){
					if(foundMultiTimeListeners == null){
						foundMultiTimeListeners = new ArrayList<NewOperatorListener>();
					}
					linstener.beforeNew();
					foundMultiTimeListeners.add(linstener);
					break;
				}else{
					linstener.setOperatorClass(null);
					linstener.setParameterTypes(null);
					linstener.setArguments(null);
				}
			}
			
			Constructor<T> constructor = parameterTypes == null ? clazz.getDeclaredConstructor() : clazz.getDeclaredConstructor(parameterTypes);
			boolean accessable = constructor.isAccessible();
			constructor.setAccessible(true);
			T instance = parameterTypes == null ? constructor.newInstance() : constructor.newInstance(arguments);
			constructor.setAccessible(accessable);
			
			Method checkAsArgument = AbstractOperator.class.getDeclaredMethod("checkAsArgument");
			accessable = checkAsArgument.isAccessible();
			checkAsArgument.setAccessible(true);
			checkAsArgument.invoke(instance);
			checkAsArgument.setAccessible(accessable);
			
			instance.checkUnreachableCode();
			
			instance.prepare();
			
			if(foundOneTimeListeners != null){
				for(NewOperatorListener linstener : foundOneTimeListeners){
					linstener.afterNew(instance);
					oneTimeListeners.remove(linstener);
				}
			}
			
			if(foundMultiTimeListeners != null){
				for(NewOperatorListener linstener : foundMultiTimeListeners){
					linstener.afterNew(instance);
					linstener.setOperatorClass(null);
					linstener.setParameterTypes(null);
					linstener.setArguments(null);
				}	
			}
			
			return instance;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			if(e.getTargetException() instanceof ASMSupportException){
				throw (ASMSupportException)e.getTargetException();
			}else if(e.getTargetException() instanceof RuntimeException){
				throw (RuntimeException)e.getTargetException();
			}else{
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
