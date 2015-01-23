package cn.wensiqun.asmsupport.core.utils.memory;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wensiqun.asmsupport.core.utils.lang.ClassUtils;
import cn.wensiqun.asmsupport.org.apache.commons.lang3.ArrayUtils;

public class ClassUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetClassUpwardsRouteClassOfQ() {
		String[] allRoutes = {
				"{class java.util.ArrayList,class java.util.AbstractList,class java.util.AbstractCollection,class java.lang.Object}",
				"{class java.util.ArrayList,class java.util.AbstractList,class java.util.AbstractCollection,interface java.util.Collection}",
				"{class java.util.ArrayList,class java.util.AbstractList,class java.util.AbstractCollection,interface java.util.Collection,interface java.lang.Iterable}",
				"{class java.util.ArrayList,class java.util.AbstractList,interface java.util.List}",
				"{class java.util.ArrayList,class java.util.AbstractList,interface java.util.List,interface java.util.Collection}",
				"{class java.util.ArrayList,class java.util.AbstractList,interface java.util.List,interface java.util.Collection,interface java.lang.Iterable}",
				"{class java.util.ArrayList,interface java.util.List}",
				"{class java.util.ArrayList,interface java.util.List,interface java.util.Collection}",
				"{class java.util.ArrayList,interface java.util.List,interface java.util.Collection,interface java.lang.Iterable}",
				"{class java.util.ArrayList,interface java.util.RandomAccess}",
				"{class java.util.ArrayList,interface java.lang.Cloneable}",
				"{class java.util.ArrayList,interface java.io.Serializable}"
		};
		List<List<Class<?>>> result = ClassUtils.getClassUpwardsRoute(java.util.ArrayList.class);
		Assert.assertEquals(allRoutes.length, result.size());
		for(List<Class<?>> l : result){
			Assert.assertTrue(ArrayUtils.contains(allRoutes, ArrayUtils.toString(l.toArray(new Class<?>[l.size()])).trim()));
		}
	}

	@Test
	public void testGetClassUpwardsRouteClassOfQClassOfQ() {
		String[] allRoutes = {
				"{class java.util.ArrayList,class java.util.AbstractList,interface java.util.List}",
				"{class java.util.ArrayList,interface java.util.List}"
		};
		List<List<Class<?>>> result = ClassUtils.getClassUpwardsRoute(java.util.ArrayList.class, java.util.List.class);
		Assert.assertEquals(allRoutes.length, result.size());
		for(List<Class<?>> l : result){
			Assert.assertTrue(ArrayUtils.contains(allRoutes, ArrayUtils.toString(l.toArray(new Class<?>[l.size()])).trim()));
		}
	}

	@Test
	public void testGetDimension(){
		int i = ClassUtils.getDimension(int.class);
		Assert.assertEquals(0, i);
		
		i = ClassUtils.getDimension(int[].class);
		Assert.assertEquals(1, i);
		
		i = ClassUtils.getDimension(int[][].class);
		Assert.assertEquals(2, i);
		
		i = ClassUtils.getDimension(String.class);
		Assert.assertEquals(0, i);
		
		i = ClassUtils.getDimension(String[].class);
		Assert.assertEquals(1, i);
		
		i = ClassUtils.getDimension(String[][].class);
		Assert.assertEquals(2, i);
		
		
	}
}
