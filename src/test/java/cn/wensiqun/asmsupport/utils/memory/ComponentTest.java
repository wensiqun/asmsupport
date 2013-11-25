package cn.wensiqun.asmsupport.utils.memory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wensiqun.asmsupport.utils.memory.Component;

public class ComponentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCompareComponentOrder() {
		Component component = new Component(null){
		};
		Assert.assertEquals(0, component.compareComponentOrder("1.1.2.1", "1.1.2.1"));
		Assert.assertEquals(0, component.compareComponentOrder("1.2", "1.2"));
		Assert.assertEquals(0, component.compareComponentOrder("1", "1"));
		

		Assert.assertEquals(1, component.compareComponentOrder("1.1", "1"));
		Assert.assertEquals(1, component.compareComponentOrder("1.1.2", "1.1"));
		
		Assert.assertEquals(-1, component.compareComponentOrder("1.1", "1.2.3"));
		Assert.assertEquals(-1, component.compareComponentOrder("1.1.1", "1.2.3"));
	}

}
