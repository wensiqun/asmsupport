package cn.wensiqun.asmsupport.utils.chooser;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wensiqun.asmsupport.clazz.AClass;
import cn.wensiqun.asmsupport.clazz.AClassFactory;
import cn.wensiqun.asmsupport.entity.MethodEntity;

public class AbstractMethodChooserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIdentifyPotentiallyApplicableMethods() {
		MethodChooser am = new MethodChooser(AClassFactory.getProductClass(this.getClass()), 
				AClassFactory.getProductClass(Child.class), 
				"work", new AClass[]{AClass.OBJECT_ACLASS}){

			@Override
			public MethodEntity firstPhase() {
				return null;
			}

			@Override
			public MethodEntity secondPhase() {
				return null;
			}

			@Override
			public MethodEntity thirdPhase() {
				return null;
			}
			
		};
		Map<AClass, List<MethodEntity>> map = am.identifyPotentiallyApplicableMethods();
		System.out.println(map);
	}

}
