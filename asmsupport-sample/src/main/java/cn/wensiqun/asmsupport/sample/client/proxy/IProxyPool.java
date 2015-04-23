package cn.wensiqun.asmsupport.sample.client.proxy;



public interface IProxyPool {

	public <T> T getProxy(Object target);
	
}
