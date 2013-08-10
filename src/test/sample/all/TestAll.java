package all;

import java.lang.reflect.InvocationTargetException;

import demo.Demo;
import demoInterface.InterfaceCreatorDemo;
import demomodify.Test;
import enumdemo.WeekCreator;

public class TestAll {

	/**
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Demo.main(args);
		InterfaceCreatorDemo.main(args);
		Test.main(args);
		WeekCreator.main(args);
	}

	@org.junit.Test
	public void test() throws SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		main(null);
	}
	
}
