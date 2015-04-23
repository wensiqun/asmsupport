package cn.wensiqun.asmsupport.core.utils.chooser;

import java.util.Date;
import java.util.List;

public class Father extends GrandFather {

	/**
	 * 
	 * @param time
	 */
	public void work(Object task){
	
	}

	@Override
	protected void work(Date... anyTime) {
		System.out.println("father work(Date[] dates)");
	}
	

	/**
	 * 
	 * @param times
	 */
	protected void work(Date morning, Date noon, Date night){
		
	}
	
	public List work(String s){
		return null;
	}
	
	
}