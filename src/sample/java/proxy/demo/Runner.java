package proxy.demo;

import proxy.IProxyPool;
import proxy.ProxyGenerator;

public class Runner {

	public static void main(String... a) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		ProxyGenerator proxyGenerator = new ProxyGenerator();
		proxyGenerator.register(FooService.class, FooService.class.getMethod("sendMessage", String.class), PerformancePrinterAdvice.class);
		proxyGenerator.register(FooService.class, FooService.class.getMethod("getProducer", String.class, String.class), PerformancePrinterAdvice.class);
		IProxyPool proxyPool = proxyGenerator.start();
		FooService service = proxyPool.getProxy(new FooService());
		service.sendMessage("Hello ASMSupport");
		System.out.println(service.getProducer("cn.wensiqun", "asmsupport"));
		service.getConsumer();
	}
	
}
