package cn.wensiqun.asmsupport.core.utils.chooser;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.wensiqun.asmsupport.core.loader.CachedThreadLocalClassLoader;
import cn.wensiqun.asmsupport.core.utils.jls15_12_2.MethodChooser;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.method.AMethodMeta;
import cn.wensiqun.asmsupport.standard.utils.ASMSupportClassLoader;

public class MethodChooserTest {

	public static void main(String... args) throws ClassNotFoundException {
		ASMSupportClassLoader classLoader = CachedThreadLocalClassLoader.getInstance();

		MethodChooser am = new MethodChooser(
				CachedThreadLocalClassLoader.getInstance(),
				classLoader.getType(MethodChooserTest.class),
				classLoader.getType(Child.class), "work",
				new IClass[] { classLoader.getType(Object.class) }) {

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
		Map<IClass, List<AMethodMeta>> map = am.identifyPotentiallyApplicableMethods();
		System.out.println(map);
	}

	@Test
	public void testIdentifyPotentiallyApplicableMethods() throws ClassNotFoundException {
		main();
	}

}
