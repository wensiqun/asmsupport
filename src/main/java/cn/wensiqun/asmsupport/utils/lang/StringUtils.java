package cn.wensiqun.asmsupport.utils.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    
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
