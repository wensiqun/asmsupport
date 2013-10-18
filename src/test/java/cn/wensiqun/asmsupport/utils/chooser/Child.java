package cn.wensiqun.asmsupport.utils.chooser;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Child extends Father{

	public void work(URL net){
		
	}

	@Override
    public void work(java.util.Date[] anytime){
		System.out.println("child work(Date[] dates)");
	}

	protected void work(Date morning, Date noon, Date night) {
		
	}
	
	private void work(List<Date> dateList){
		
	}
	
    protected void work(Date... anytime) {
		
	}

	@Override
	public ArrayList work(String s) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}