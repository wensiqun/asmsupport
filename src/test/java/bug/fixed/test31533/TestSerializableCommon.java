package bug.fixed.test31533;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class TestSerializableCommon {

    public static void main(String[] args) throws Exception{

       /*****************序列化***************/
       User user=new User();
       user.setUserName("asmsupport");
       Utils.writeObject(user);
      
       /*****************反序列化***************/
       System.out.println("before:" + user);
       InputStream is = new FileInputStream(new File(".//target//bug.fixed.Test31533_tmp"));
	   ObjectInputStream ois = new ObjectInputStream(is);
       Object user2 = ois.readObject();
       System.out.println("after:" + user2);

    }
    
    @org.junit.Test
	public void test() throws Exception{
		main(null);
	}
}
