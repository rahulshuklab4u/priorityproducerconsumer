package com.example.consumer;

import com.example.item.Burger;
import com.example.item.Coffee;
import com.example.queue.BlockingPriorityQueue;

public class BurgerConsumer<T> extends Consumer{
	
	public T objectType = (T) Burger.getReferenceObject();
	public volatile boolean isConsumed = false;

	@SuppressWarnings("unchecked")
	public BurgerConsumer(BlockingPriorityQueue<T> sharedQueue, int consumerNumber) {
		super(sharedQueue, consumerNumber);
	}

	@Override
	public Object getObjectType() {
		return objectType;
	}

}
