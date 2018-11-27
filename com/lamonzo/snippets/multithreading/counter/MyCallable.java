package com.lamonzo.snippets.multithreading.counter;

import java.util.Random;
import java.util.concurrent.Callable;

public class MyCallable implements Callable<Boolean>{

	@Override
	public Boolean call() throws Exception {
		try {
			//SLEEP FOR UP TO 3 SECONDS TO SIMULATE PROCESSING
			Random random = new Random();
			Thread.sleep(random.nextInt(3000));
			
			//RETURN A RANDOM BOOLEAN TO SIMULATE SOME CALLS WOKING ANN SOME FAILING
			return random.nextBoolean();	
		}
		catch(InterruptedException e) {
			System.out.println("There was an error in call()");
			return false;
		}
		
	}
}
