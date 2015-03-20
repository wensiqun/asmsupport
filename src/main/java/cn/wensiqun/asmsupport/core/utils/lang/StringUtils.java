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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class StringUtils extends cn.wensiqun.asmsupport.org.apache.commons.lang3.StringUtils {
    
    public static Integer[] findAllIndexes(String str, String searchStr){
        
    	List<Integer> list = new ArrayList<Integer>();
        //int indexes[] = new int[0];
        
        int index = str.indexOf(searchStr);
        while (index >=0){
        	list.add(index);
            index = str.indexOf(searchStr, index + searchStr.length())    ;
        }

        return list.toArray(new Integer[list.size()]);
    }
    
	
	/**
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String upperCase(String str, int start, int end) {
        if (str == null) {
            return null;
        }
		return new StringBuilder(str.substring(start, end).toUpperCase()).append(str.substring(end)).toString();
	}
	
	
	/**
	 * 
	 * @param str
	 * @param len
	 * @param chr
	 * @return
	 */
	public static String appendIfBlank(String str, int len, char chr){
		if(str.length() < len){
			StringBuilder newStr = new StringBuilder(str);
			int left = len - str.length();
			while(left-- > 0){
				newStr.append(chr);
			}
			return newStr.toString();
		}else{
			return str;
		}
	}
}
