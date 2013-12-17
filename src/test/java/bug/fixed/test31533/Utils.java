package bug.fixed.test31533;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Utils {

	public static void writeObject(Object o) throws Exception {

		File f = new File(".//target//bug.fixed.Test31533_tmp");
		if (f.exists()) {
			f.delete();
		}
		FileOutputStream os = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(o);
		oos.close();
		os.close();
	}

}
