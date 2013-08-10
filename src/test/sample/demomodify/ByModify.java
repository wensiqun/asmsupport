package demomodify;

public class ByModify implements IByModify{
    
	public static String name = "name";
	
	static{
		System.out.println(name);
	}
	
	public String String(){
    	System.out.println("String");
    	return "test string";
    }
	
	public void helloWorld(){
    	System.out.println("helloWorld");
    }
	
	private void say(){}
}
