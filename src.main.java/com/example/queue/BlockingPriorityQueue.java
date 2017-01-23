package com.example.queue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BlockingPriorityQueue<T> {

	private List<T> queue = new LinkedList<T>();
	private int limit;
	private Map<T, LinkedList<Integer>> registerMap = new HashMap();

	/*
	 * Limit defines the maximum number of objects that can be stored in the
	 * queue.
	 */
	public BlockingPriorityQueue(int limit) {
		this.limit = limit;
	}

	/**
	 * This method produces a new item object & queues it for consumption
	 * 
	 * @param item
	 *            This is the item object to be produced.
	 */
	public synchronized void produce(T item) throws InterruptedException {
		while (this.queue.size() == this.limit) {
			wait();
		}
		if (this.queue.size() == 0) {
			notifyAll();
		}
		this.queue.add(item);
		System.out.println("Item " + item + " is produced & added to the queue.");
	}

	/**
	 * This method is used to consume an object. It removes the object from the
	 * queue after consumption
	 * 
	 * @return Returns the consumed item object.
	 */
	public synchronized T consume() throws InterruptedException {
		while (this.queue.size() == 0) {
			wait();
		}
		if (this.queue.size() == this.limit) {
			notifyAll();
		}
	//	System.out.println("***** removed " + queue.get(0));
		// System.out.println("consume elem lock is with "+this.toString());
		return this.queue.remove(0);
	}

	/**
	 * This method returns the first element of the queue, without removing it.
	 */
	public synchronized T peek() throws InterruptedException {
		return queue.isEmpty() ? null : this.queue.get(0);
		// return this.queue.get(0);
	}

	/**
	 * This method register a consumer thread, to mark it ready for consumption.
	 * The map stores Object item as key and a Linked List of consumers
	 * registered to consume that object. Elements occurring first in the list
	 * get higher preference.
	 * 
	 * @param item
	 *            Object item against which the given consumer is registered
	 * @param consumerNumber
	 *            Uniquely identifies the consumer
	 */
	public synchronized void registerToConsume(T item, int consumerNumber) {

		System.out.println("  Consumer thread " +item.getClass().getSimpleName() + " "+consumerNumber +" registered");

		if (registerMap.containsKey(item)) {
			registerMap.get(item).add(consumerNumber);
		} else {
			LinkedList<Integer> newList = new LinkedList<Integer>();
			newList.add(consumerNumber);
			registerMap.put(item, newList);

		}
	}

	/**
	 * This method is used by consumer thread to consume an object. A thread is
	 * successful to consume only if below criteria is met 1. The object the
	 * consumer is requesting is present 2. The requesting consumer is first in
	 * queue ( that is has registered before any other consumer for same object)
	 * 
	 * @param objectType
	 *            Object item requested for consumption
	 * @param consumerNumber
	 *            Uniquely identifies the consumer
	 * @return Object if successfully consumed. Else returns null
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public synchronized T consumeObjectOnCriteria(T objectType, int consumerNumber) throws InterruptedException {

		if (!queue.isEmpty() && queue.get(0) != null && consumerNumber == peekFirstElement(objectType)
				&& objectType.getClass().equals(queue.get(0).getClass())) {
		//	System.out.println("peekFirstElement(objectType)"+peekFirstElement(objectType)+" objectType.getClass()"+objectType.getClass()+ " registerMap "+registerMap.get(objectType) + " registerMap.get(objectType)  "+registerMap.get(objectType).get(0) +" consumerNumber "+consumerNumber);
			System.out.println(
					"      Removed " + queue.get(0) + " by Consumer thread" + objectType.getClass().getSimpleName() + " " + consumerNumber);
			Object consumed = consume();
			removeElementFromMap(objectType);
			return (T) consumed;
		}

		return null;

	}

	/**
	 * This method returns the consumer Number of the first consumer thread in
	 * priority to consume the given item object.
	 * 
	 * @param item
	 *            Object item against which the consumer is to found
	 * @return consumer Number of the highest priority thread. If no threads
	 *         registered, returns -1.
	 */

	public synchronized int peekFirstElement(T item) {

		// System.out.println("peak first element lock is with
		// "+this.toString());
		if (registerMap.containsKey(item)) {
			return registerMap.get(item).get(0);
		}
		return -1;
	}

	/**
	 * This method remove the element from the from the linked list after the
	 * object is consumed. Subsequently registered thread gets the priority now.
	 * 
	 * @param item
	 *            Object item against which the consumer is to removed.
	 */
	public synchronized void removeElementFromMap(T item) {
		// System.out.println("remove elem from queue lock is with
		// "+this.toString());
		if (registerMap.containsKey(item)) {
			registerMap.get(item).remove(0);
		}

	}

	/*public synchronized T consumeIfCriteriaMet(T item, int consumerNumber) throws InterruptedException {

		// Peek if first element is this consumer
		if (registerMap.containsKey(item)) {
			try {

				if (consumerNumber == registerMap.get(item).get(0) && !queue.isEmpty() && queue.get(0) != null
						&& item.getClass().equals(queue.get(0).getClass())) {
					System.out.println("Now to be consumed holding lock is " + item + " consumerNumber  "
							+ consumerNumber + " head of queue " + queue.get(0));
					while (this.queue.size() == 0) {
						wait();
					}
					if (this.queue.size() == this.limit) {
						notifyAll();
					}
					System.out.println(
							"removed " + queue.get(0) + " from " + item.getClass().getName() + " " + consumerNumber);
					registerMap.get(item).remove(0);
					return this.queue.remove(0);
				}

			}
			// return null;

			catch (Exception e) {
				// System.out.println("queue.get(0) "+queue.get(0) + "
				// registerMap.get(item)" +registerMap.get(item)+
				// "registerMap.get(item).get(0) "+registerMap.get(item).get(0)+
				// " item.getClass() "+item.getClass() +"
				// queue.get(0).getClass() "+ queue.get(0).getClass());
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}*/
	

}
