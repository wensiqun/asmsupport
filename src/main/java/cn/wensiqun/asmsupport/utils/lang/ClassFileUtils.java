package cn.wensiqun.asmsupport.utils.lang;

import java.io.File;
import java.io.FileOutputStream;


public class ClassFileUtils {
	
    public static void toLocal(byte[] b, String location, String name){
        
        if (StringUtils.isNotBlank(location)) {
        	String fileSeparator = System.getProperty("file.separator");
        	StringBuilder path = new StringBuilder(location);
        	if(!location.endsWith(fileSeparator)){
        		path.append(fileSeparator);
        	}
        	
            // optional: stores the adapted class on disk
            try {
                File f = new File(path.append(name.replace(".", fileSeparator)).append(".class").toString());
                f.getParentFile().mkdirs();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(b);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    	
    }
    
}
