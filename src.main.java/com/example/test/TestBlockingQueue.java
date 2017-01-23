package com.example.test;

import com.example.consumer.BurgerConsumer;
import com.example.consumer.CoffeeConsumer;
import com.example.producer.BurgerProducer;
import com.example.producer.CoffeeProducer;
import com.example.queue.BlockingPriorityQueue;


/* Use Case: A restaurant serves 2 menu items : Coffee & Burger
 * Multiple chefs (producers) produce coffee & burger in parallel.
 * Multiple diners(consumers) consume in parallel.
 * Each consumer registers for an item ( coffee or burger in this case).
 * The consumer registered first will be served first when that item is produced & put in the queue.
 * A consumer waits if no registered items are present in the queue.
 * 
 * Assumptions:
 * 1. After a consumer consumes an item, it again registers itself. ( This is to simulate a factory condition where consumers thread
 * keep on consuming, as and when objects are produced.
 * 2. All sysout statements should be replaced by Logger log statements.
 */

public class TestBlockingQueue {

	private static int coffeeProduerNumber;
	private static int burgerProduerNumber;
	private static int coffeeConsumerNumber;
	private static int burgerConsumerNumber;

	public static <T> void main(String[] args) throws InterruptedException {
		BlockingPriorityQueue<T> blockingQueue = new BlockingPriorityQueue<>(10);

		/*
		 * Create One Coffee & One Burger Producer Thread. Coffee Producer
		 * produced 5 items. Burger Producer produces 3 items.
		 */
		new Thread(new CoffeeProducer<T>(blockingQueue, 5, ++coffeeProduerNumber)).start();
		new Thread(new BurgerProducer<T>(blockingQueue, 3, ++burgerProduerNumber)).start();

		/*
		 * Start 2 Burger & 3 Coffee consumer threads in parallel, each with
		 * unique consumer number.
		 */
		new Thread(new BurgerConsumer<T>(blockingQueue, ++burgerConsumerNumber)).start();
		new Thread(new CoffeeConsumer<T>(blockingQueue, ++coffeeConsumerNumber)).start();
		new Thread(new BurgerConsumer<T>(blockingQueue, ++burgerConsumerNumber)).start();
		new Thread(new CoffeeConsumer<T>(blockingQueue, ++coffeeConsumerNumber)).start();
		new Thread(new CoffeeConsumer<T>(blockingQueue, ++coffeeConsumerNumber)).start();

		/*
		 * Start another burger thread that produces 5 items, while previous
		 * consumption is in process.
		 */
		new Thread(new BurgerProducer<T>(blockingQueue, 5, ++burgerProduerNumber)).start();

		/*
		 * Start 2 burger & 1 coffee consumer thread.
		 */
		new Thread(new BurgerConsumer<T>(blockingQueue, ++burgerConsumerNumber)).start();
		new Thread(new BurgerConsumer<T>(blockingQueue, ++burgerConsumerNumber)).start();
		new Thread(new CoffeeConsumer<T>(blockingQueue, ++coffeeConsumerNumber)).start();

		/*
		 * Start another coffee thread that produces 3 items, while previous
		 * consumption is in process.
		 */
		new Thread(new CoffeeProducer<T>(blockingQueue, 3, ++coffeeProduerNumber)).start();
	}

}
