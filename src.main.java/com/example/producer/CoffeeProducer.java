package com.example.producer;

import java.util.ArrayList;
import java.util.List;

import com.example.item.Coffee;
import com.example.queue.BlockingPriorityQueue;

public class CoffeeProducer<T> extends Producer<T> {
	
	private static int itemNumber = 0;
	public CoffeeProducer(BlockingPriorityQueue<T> sharedQueue, int limit, int number) {
		super(sharedQueue, limit, number);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getItems() {
		List<T> itemList = new ArrayList<>();
		for (int i = 1; i <= this.limit; i++) {
			itemList.add((T) new Coffee(++itemNumber));
		}
		return itemList;
	}

}
