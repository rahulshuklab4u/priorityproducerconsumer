package com.example.consumer;

import com.example.queue.BlockingPriorityQueue;

public abstract class Consumer<T> implements Runnable {

	protected BlockingPriorityQueue<T> sharedQueue; // shared instance
	protected int consumerNumber; // each consumer thread is assigned an unique consumerNumber
	public T objectType = null; // Type of consumer class. Eg- Coffee, Burger
	public volatile boolean isConsumed = false; // Used to register the current consumer thread

	public Consumer(BlockingPriorityQueue<T> sharedQueue, int consumerNumber) {
		this.sharedQueue = sharedQueue;
		this.consumerNumber = consumerNumber;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				consumeItem();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public abstract T getObjectType();

	/**
	 * This method is used to consume object of its type.
	 * 
	 * @return consumed object
	 * @throws InterruptedException
	 */
	public Object consumeItem() throws InterruptedException {

		// Start the process only if the queue is not empty.
		if (sharedQueue.peek() != null) {
			isConsumed = false;
			objectType = getObjectType();

			// Register the consumer thread to mark it ready for consumption.
			sharedQueue.registerToConsume(objectType, consumerNumber);
			Thread.sleep(1000);
			Object consumedItem = null;
			
			// Loop that waits for the object to be available in the queue. Once
			// consumed, the condition falsifies, and consumeItem method
			// is re-invoked, and the thread gets registered again.
			while (!isConsumed) {
				consumedItem = sharedQueue.consumeObjectOnCriteria(objectType, consumerNumber);
				if (consumedItem != null)
					isConsumed = true;
			}
			return consumedItem;
		}
		return null;
	}
}
