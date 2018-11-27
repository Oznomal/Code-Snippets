package com.lamonzo.snippets.multithreading.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton class to represent a DataModel that would be shared amongst all the 
 * classes in an MVC pattern such as the one commonly used in JavaFX Applications
 */
public class SharedDataModel {
	//========================================================================================//
	//== FIELDS ==
	private static SharedDataModel dataModel;
	private AtomicInteger successCount;
	private AtomicInteger failCount;
	
	
	//========================================================================================//
	//== CONSTRUCTORS ==
	private SharedDataModel() {
		successCount = new AtomicInteger(0);
		failCount = new AtomicInteger(0);
	}
	
	
	//========================================================================================//
	//== PUBLIC ACCESS METHODS
	public static SharedDataModel getDataModel() {
		if(dataModel == null)
			dataModel = new SharedDataModel();
		return dataModel;
	}
	
	public AtomicInteger getSuccessCount() {
		return successCount;
	}
	
	public AtomicInteger getFailCount() {
		return failCount;
	}
}
