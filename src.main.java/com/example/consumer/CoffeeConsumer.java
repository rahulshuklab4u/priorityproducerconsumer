package com.example.consumer;

import com.example.item.Coffee;
import com.example.queue.BlockingPriorityQueue;

public class CoffeeConsumer<T> extends Consumer {

	public T objectType = (T) Coffee.getReferenceObject();
	public volatile boolean isConsumed = false;

	@SuppressWarnings("unchecked")
	public CoffeeConsumer(BlockingPriorityQueue<T> sharedQueue, int consumerNumber) {
		super(sharedQueue, consumerNumber);
	}

	@Override
	public Object getObjectType() {
		return objectType;
	}

}
