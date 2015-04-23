package cn.wensiqun.asmsupport.core.block.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.Assert;

import org.junit.Test;

public class SynchronizedGeneratorSampleTestJunit {
	
	@Test
	public void testSyncThis() throws Exception{
		SynchronizedGeneratorSampleTest sgst = new SynchronizedGeneratorSampleTest();
		ExecutorService es = Executors.newFixedThreadPool(10);
		List<Future> objs = new ArrayList<Future>();
		int i = 0;
		while(i<10) {
			objs.add(es.submit(new SyncThisThread(sgst)));
			i++;
		}
		es.shutdown();
		while(!es.isTerminated());
		Assert.assertEquals(100, sgst.list.size());
		i = 0;
		while(i<100) {
			Assert.assertEquals(i % 10, sgst.list.get(i).intValue());
			i++;
		}
	}

	@Test
	public void testSyncLock() throws Exception{
		SynchronizedGeneratorSampleTest sgst = new SynchronizedGeneratorSampleTest();
		ExecutorService es = Executors.newFixedThreadPool(10);
		List<Future> obj = new ArrayList<Future>();
		int i = 0;
		while(i<10) {
			obj.add(es.submit(new SyncLockThread(sgst)));
			i++;
		}
		es.shutdown();
		while ((es.isTerminated() ? 0 : 1) != 0);;
		Assert.assertEquals(100, sgst.list.size());
		i = 0;
		while(i<10) {
			Assert.assertEquals(i % 10, sgst.list.get(i).intValue());
			i++;
		}
	}
}

class SyncThisThread extends Thread {
	
	private SynchronizedGeneratorSampleTest sgst;

	public SyncThisThread(SynchronizedGeneratorSampleTest sgst) {
		this.sgst = sgst;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(100);
			sgst.syncThis();
		} catch (InterruptedException e) {
			
		}
	}
} 

class SyncLockThread extends Thread {
	
	private SynchronizedGeneratorSampleTest sgst;

	public SyncLockThread(SynchronizedGeneratorSampleTest sgst) {
		this.sgst = sgst;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(100);
			sgst.syncLock();
		} catch (InterruptedException e) {
			
		}
	}
} 
