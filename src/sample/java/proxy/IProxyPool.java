package proxy;



public interface IProxyPool {

	public <T> T getProxy(Object target);
	
}
