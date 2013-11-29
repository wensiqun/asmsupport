package cn.wensiqun.asmsupport.utils.reflet;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.objectweb.asm.Opcodes;

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
