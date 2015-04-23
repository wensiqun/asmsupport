package cn.wensiqun.asmsupport.core.utils;

import java.util.ArrayList;

public class MyList extends ArrayList<String> {

	public String put(String s) {
		this.add(s);
		return s;
	}

}