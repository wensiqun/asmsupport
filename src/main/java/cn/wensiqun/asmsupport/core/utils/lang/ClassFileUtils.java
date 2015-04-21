/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.utils.lang;

import java.io.File;
import java.io.FileOutputStream;

import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;


public class ClassFileUtils {
	
    public static void toLocal(byte[] b, String location, String name){
        
        if (!StringUtils.isBlank(location)) {
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
                throw new ASMSupportException(e);
            }
        }
    	
    }
    
}
