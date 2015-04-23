package cn.wensiqun.asmsupport.core.utils.chooser;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.utils.jls15_12_2.MethodChooser;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class MethodChooserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIdentifyPotentiallyApplicableMethods() {
		MethodChooser am = new MethodChooser(AClassFactory.getType(this.getClass()), 
				AClassFactory.getType(Child.class), 
				"work", new AClass[]{AClassFactory.getType(Object.class)}){

			@Override
			public AMethodMeta firstPhase() {
				return null;
			}

			@Override
			public AMethodMeta secondPhase() {
				return null;
			}

			@Override
			public AMethodMeta thirdPhase() {
				return null;
			}
			
		};
		Map<AClass, List<AMethodMeta>> map = am.identifyPotentiallyApplicableMethods();
		System.out.println(map);
	}

}
