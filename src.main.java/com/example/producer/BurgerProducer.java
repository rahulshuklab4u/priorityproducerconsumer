package com.example.producer;

import java.util.ArrayList;
import java.util.List;
import com.example.item.Burger;
import com.example.queue.BlockingPriorityQueue;

public class BurgerProducer<T> extends Producer<T>  {

	private static int itemNumber = 0;
	public BurgerProducer(BlockingPriorityQueue<T> sharedQueue, int limit, int number) {
		super(sharedQueue, limit, number);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getItems() {
		List<T> itemList = new ArrayList<>();
		for (int i = 1; i <= this.limit; i++) {
			itemList.add((T) new Burger(++itemNumber));
		}
		return itemList;
	}

}
