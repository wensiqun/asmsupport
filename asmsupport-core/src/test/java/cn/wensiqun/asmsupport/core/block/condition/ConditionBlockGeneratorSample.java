package cn.wensiqun.asmsupport.core.block.condition;

import java.util.ArrayList;
import java.util.List;

import cn.wensiqun.asmsupport.core.utils.TesterStatics;

public class ConditionBlockGeneratorSample {

	public static List<String> allPossiable()
	{
		List<String> list = new ArrayList<String>();
		//1 char
		char[] starts = "ABCD".toCharArray();
		char[] charAt1s = "1234".toCharArray();
		char[] ends = "TUVWXYZ".toCharArray();
		int[]  lens = new int[]{1,2,3,4};
		
		for(int len : lens){
			if(len == 1)
			{
				for(char c : starts)
					list.add(String.valueOf(c));
				

				for(char c : ends)
					list.add(String.valueOf(c));
			}
			else
			{
				for(char s : starts)
				{
					if(len > 2)
					{
						for(char at : charAt1s)
						{
							String placed = getMutipString(len - 3);
							for(char e : ends)
							{
								list.add(new StringBuilder().append(s).append(at).append(placed).append(e).toString());
							}
						}
					}
					else
					{
						for(char e : ends)
						{
							list.add(new StringBuilder().append(s).append(e).toString());
						}
					}
				}
			}
		}
		return list;
	}
	
	private static String getMutipString(int i)
	{
		StringBuilder sb = new StringBuilder();
	    while(i-- > 0)
	    	sb.append('#');
	    return sb.toString();
	}
	
	/**
	 * startWith A,B,C,D
	 * 
	 * length : 1, 2, 3, 4
	 * 
	 * charAt(1) : 1,2,3,4
	 * 
	 * endsWith : TUVWXYZ
	 * 
	 * @param str
	 */
	private static void test(String str)
	{
		
		if(str.startsWith("A"))
		{
			TesterStatics.expectedPrintln("    startsWith A!");
		}
		
		//@print
		
		if(str.startsWith("B"))
		{
			TesterStatics.expectedPrintln("    startsWith B!");
		}
		
		if(str.startsWith("C"))
		{
			TesterStatics.expectedPrintln("    startsWith C!");
		}
		
		//============= if split ==============
		
		
		if(1 == str.length())
		{
			TesterStatics.expectedPrintln("    length is 1!");
		}
		else
		{
			TesterStatics.expectedPrintln("    length is not 1!");
		}

		//@print
		
		if(2 == str.length())
		{
			TesterStatics.expectedPrintln("    length is 2!");
		}
		else
		{ 
			TesterStatics.expectedPrintln("    length is not 2!");
		}
		
		if(3 == str.length())
		{
			TesterStatics.expectedPrintln("    length is 3!");
		}
		else
		{
			TesterStatics.expectedPrintln("    length is not 3!");
		}

		//============= if else split ==============
		
		
		if(str.endsWith("Z"))
		{
			TesterStatics.expectedPrintln("    endsWith Z!");
			if(str.startsWith("A"))
			{
				TesterStatics.expectedPrintln("        startsWith A!");
				if(str.length() == 2)
				{
					TesterStatics.expectedPrintln("            length is 2!");
				}
				else if(str.length() == 3)
				{
					TesterStatics.expectedPrintln("            length is 3!");
				}
				else
				{
					TesterStatics.expectedPrintln("            length is Other!");
				}
			}
			
			if(str.startsWith("B"))
			{
				TesterStatics.expectedPrintln("        startsWith B!");
				if(str.length() == 2)
				{
					TesterStatics.expectedPrintln("            length is 2!");
					if(str.charAt(1) == '1')
					{
						TesterStatics.expectedPrintln("                charAt 1 is '1'!");
					}
					else
					{
						TesterStatics.expectedPrintln("                charAt 1 is 'Other'!");
					}
				}
				else if(str.length() == 3)
				{
					TesterStatics.expectedPrintln("            length is 3!");
					if(str.charAt(1) == '1')
					{
						TesterStatics.expectedPrintln("                charAt 1 is '1'!");
					}
					else if(str.charAt(1) == '2')
					{
						TesterStatics.expectedPrintln("                charAt 1 is '2'!");
					}
					else if(str.charAt(1) == '3')
					{
						TesterStatics.expectedPrintln("                charAt 1 is '3'!");
					}
					else
					{
						TesterStatics.expectedPrintln("                charAt 1 is 'Other'!");
					}
				}
				else
				{
					TesterStatics.expectedPrintln("            length is Other!");
				}
				
			}
		}
		else if(str.endsWith("Y"))
		{
			TesterStatics.expectedPrintln("    endsWith Y!");
			if(str.startsWith("A"))
			{
				TesterStatics.expectedPrintln("        startsWith A!");
			}
			
			if(str.startsWith("B"))
			{
				TesterStatics.expectedPrintln("        startsWith B!");
			}
		}
		else
		{
			TesterStatics.expectedPrintln("    endsWith Other!");
			if(str.endsWith("X"))
			{
				TesterStatics.expectedPrintln("        endsWith X!");
			}
			else if(str.endsWith("W"))
			{
				TesterStatics.expectedPrintln("        endsWith W!");
			}
			else
			{
				TesterStatics.expectedPrintln("        endsWith Other!");
				if(str.endsWith("V"))
				{
					TesterStatics.expectedPrintln("            endsWith V!");
				}
				else if(str.endsWith("U"))
				{
					TesterStatics.expectedPrintln("            endsWith U!");
				}
				else
				{
					TesterStatics.expectedPrintln("            endsWith Other!");
				}
			}
		}
		
	}
	
	public static void main(String[] args){
		for(String str : allPossiable())
		{
			test(str);
		}
	}
	
}
