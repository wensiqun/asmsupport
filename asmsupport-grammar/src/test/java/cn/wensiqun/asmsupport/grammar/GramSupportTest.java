package cn.wensiqun.asmsupport.grammar;

import java.io.ByteArrayInputStream;

import org.junit.Test;

public class GramSupportTest {

	@Test
	public void test() {
		GramSupport gs = new GramSupport(new ByteArrayInputStream("System.out.println(100);".getBytes()), "UTF-8"); 
	}

}
