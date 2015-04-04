package proxy;

public abstract class MethodInvocation {

	public static final Object VOID_OBJ = new Object();
	
	public abstract Object invoke(Class<?> owner, String method, Object... args);
	
	protected Object callOrigin(Object... args){
		return null;
	}
}
