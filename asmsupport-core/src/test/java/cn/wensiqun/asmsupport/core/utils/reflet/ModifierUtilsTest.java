package cn.wensiqun.asmsupport.core.utils.reflet;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;

public class ModifierUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsBridge() {
		Assert.assertTrue(ModifierUtils.isBridge(Opcodes.ACC_BRIDGE));
	}

}
