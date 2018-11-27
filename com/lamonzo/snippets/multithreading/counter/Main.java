package com.lamonzo.snippets.multithreading.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The purpose of this snippet is to simulate a situation where have a task (MyCallable)
 * 	which will have multiple instances submitted by an executor service, in most situations
 * I will have a list of items which I would like to process and each MyCallable instance 
 * will be able to process 1 item from the list. I would then loop through the list of items
 * and call the task executor's submit method for each item in the list, passing it as an 
 * argument to MyCallable's constructor. 
 * 
 * While after MyCallable completes its task it should return a Future from call(), in the 
 * example below the future will be a boolean, representing whether or not the task 
 * completed successfully. This future can then be stored in a list and iterated over 
 * to update an Atomic Integer (or any other thread safe data-structure) within a shared 
 * data model. 
 * 
 * The end goal in this snippet is to have multiple threads process items in parallel 
 * and update a counter in the shared data model for each successful completion. 
 * After the list has been processed I will have a total count of how many successful 
 * tasks were completed, which I can then use throughout the application. 
 * 
 * 
 * @author lamonzo.arroyo.ctr
 *
 */
public class Main {
	
	private static final int LOOP_COUNT = 1000;
	
	public static void main(String [] Args) {
		//1. GET SHARED DATA MODEL
		SharedDataModel sdm = SharedDataModel.getDataModel();
		
		//2. CREATE THE EXECUTOR SERVICE
		ExecutorService es = Executors.newFixedThreadPool(8);
		
		//3. CREATE A PLACE TO HOLD THE RETURNED FUTURES FROM EACH CALLABLE
		List<Future<Boolean>> taskResponseList = new ArrayList<>();
		
		//4. SIMIULATE LOOPING OVER A LIST OF ITEMS AND SUBMITTING MYCALLABLE
		for(int i = 0; i < LOOP_COUNT; i++) {
			Future<Boolean> future = es.submit(new MyCallable());
			taskResponseList.add(future);
		}
		
		//5. ITERATE THROUGH THE FUTURE LIST AND UPDATE THE SDM PROPERLY
		for(Future<Boolean> response : taskResponseList) {
			try {
				if(response.get()) {
					int newValue = sdm.getSuccessCount().incrementAndGet();
					System.out.println("Updated Success Counter | New Value: " + newValue);
				}
				else {
					sdm.getFailCount().incrementAndGet();
					System.out.println("Task Failed Simulation");
				}
			}catch(InterruptedException | ExecutionException e) {
				System.out.println("Something went wrong when processing a future");
			}
		}
		
		//6. DISPLAY THE RESULTS FROM THE TASKS
		System.out.println("\n_____________________________________________________________________________");
		System.out.println("Total Attempted Submissions: " + LOOP_COUNT);
		System.out.println("Successful Submissions: " + sdm.getSuccessCount().get());
		System.out.println("Failed Submissions: " + sdm.getFailCount().get());
	}
}
