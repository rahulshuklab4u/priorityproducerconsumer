package com.example.producer;

import java.util.List;

import com.example.queue.BlockingPriorityQueue;

public abstract class Producer<T> implements Runnable {

	private BlockingPriorityQueue<T> sharedQueue; // shared instance
	protected int limit; // Number of items that the thread will produce before completing
	protected int producerNumber;

	public Producer(BlockingPriorityQueue<T> sharedQueue, int limit, int number) {
		this.sharedQueue = sharedQueue;
		this.limit = limit;
		this.producerNumber = number;

	}

	@Override
	public void run() {
		produceItems();
	}
	
	/**
	 *  This thread produces objects based on concrete implementation, in quantity governed by limit.
	 */
	private void produceItems() {
		List<T> items = getItems();
		for (T t : items) { 
			try {
				sharedQueue.produce(t);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * List of items produced.
	 * Each concrete class produces the items and stores them in the list.
	 * @return
	 */
	public abstract List<T> getItems();

}
