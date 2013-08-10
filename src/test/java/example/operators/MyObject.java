package example.operators;

public class MyObject {

	public static void main(String[] args){
		MyObject obj = new MyObject();
		System.out.println("Call static method : " + MyObject.getDescription(obj));
	}

	public String description(){
		return toString();
	}

	@Override
	public String toString() {
		return "description is " + super.toString();
	}
	
	public static String getDescription(MyObject obj){
		return obj.description();
	}
}
