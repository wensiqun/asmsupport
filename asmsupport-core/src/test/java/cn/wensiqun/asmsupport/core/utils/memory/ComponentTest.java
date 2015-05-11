package cn.wensiqun.asmsupport.core.utils.memory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wensiqun.asmsupport.core.utils.memory.ScopeComponent;

public class ComponentTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCompareComponentOrder() {
		ScopeComponent component = new ScopeComponent(null){
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
