package cn.wensiqun.asmsupport.sample.client.proxy.demo;

public class FooService {

	public void sendMessage(String message) {
		System.out.println("Send Message : " + message);
	}
	
	public String getProducer(String domain, String name) {
		return "[Domain : " + domain + ", Name : " + name + "]";
	}
	
	public void getConsumer() {
		System.out.println("Consumer : c1, c2");
	}
}
