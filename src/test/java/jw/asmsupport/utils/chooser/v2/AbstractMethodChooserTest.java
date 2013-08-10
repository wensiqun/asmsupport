package jw.asmsupport.utils.chooser.v2;

import java.util.List;
import java.util.Map;

import jw.asmsupport.clazz.AClass;
import jw.asmsupport.clazz.AClassFactory;
import jw.asmsupport.entity.MethodEntity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
