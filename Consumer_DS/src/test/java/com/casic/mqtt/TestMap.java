package com.casic.mqtt;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
//i++是非线程安全的

public class TestMap {
	private static AtomicInteger ai=new AtomicInteger(0);
	private static Map map=new HashMap<>();
	private int iCount=0;
	public static void main(String[] args) {
		testHashMap();
	}
	public static void testHashMap() {
		 Thread t1 = new Thread() {
	            public void run() {
	                for (int i = 0; i < 500000000; i++) {
	                    map.put(new Integer(i), i);
	                }
	                System.out.println("t1 over");
	            }
	        };

	        Thread t2 = new Thread() {
	            public void run() {
	                for (int i = 0; i < 500000000; i++) {
	                    map.put(new Integer(i), i);
	                }

	                System.out.println("t2 over");
	            }
	        };

	    

	        t1.start();
	        t2.start();
	    
	}
	public void testI() throws Exception {
		Iadd iadd=new TestMap().new Iadd();
		Thread t1=new Thread(iadd);
		Thread t2=new Thread(iadd);
		t1.start();
		t2.start();
	}
	
	class Iadd implements Runnable{
		@Override
		public void run() {
	       for (int i = 0; i < 100; i++) {
	    	   iCount++;
	    	   try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	   System.out.println(iCount+"---:"+Thread.currentThread().getName());
		}	
		}
		
	}
	
}

