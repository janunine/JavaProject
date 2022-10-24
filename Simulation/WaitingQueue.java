package Simulation;

import java.util.*;

public class WaitingQueue <E> {
	
	// data members
		private int front;
		private int rear;
		private int size;
		private int capacity;
		private E[] theQueue;
		private static final int DEFAULT_CAPACITY = 10;
		private boolean finite = true;

		
		public WaitingQueue () {
			this(DEFAULT_CAPACITY);
		}
		
		public WaitingQueue (int cap) {
			if (cap < 0) {  // infinite queue
				finite = false;
				capacity = DEFAULT_CAPACITY;
			} else
				capacity = cap;
			front = 0;
			rear = capacity -1;
			size = 0;
			theQueue = (E[]) new Object[capacity];
		}
		
		/** 
		 * Insert an item at the rear of the queue
		 * @param  item  The element to add
		 * @return true 
		 */
		public boolean offer (E item) {
			if (size == capacity)
				if (finite)   // queue is full, don't add
					return false;
				else          // infinite queue, allocate more space
					reallocate();
			 
			rear = (rear +1) % capacity;
			theQueue[rear] = item;
			size ++;
			return true;
		}
		
		/** 
		 * Remove the entry at the front of the queue
		 * @param  None  
		 * @return the item removed or null if queue is empty
		 */
		public E poll() {
			E result = peek();
			if (result != null) {
				front = (front+1) % capacity;
				size --;
			}
			return result;
		}
		
		/** 
		 * Remove the entry at the front of the queue
		 * @param  None  
		 * @return the item removed or NoSuchElementException if queue is empty
		 */
		public E remove() {
			if (size == 0) 
				throw new NoSuchElementException("Queue is empty");
			E result = theQueue[front];
			front = (front+1) % capacity;
			size --;
			return result;
		}
		
		/** 
		 * Return the entry at the front of the queue
		 * @param  None  
		 * @return the item at the front of the queue or null if queue is empty
		 */
		public E peek() {
			if (size == 0) 
				return null;
			else
				return theQueue[front];
		}
		
		/** 
		 * Return the entry at the front of the queue
		 * @param  None  
		 * @return the item at the front of the queue or throw NoSuchElementException if queue is empty
		 */
		public E element() {
			if (size == 0) 
				throw new NoSuchElementException("Queue is empty");
			else
				return theQueue[front];
		}
		
		/** 
		 * Return a string representing the queue
		 * @param  None  
		 * @return a string representing the queue  	
		 */
		public String toString() {
			String result = "[";
			int temp = front;
			for (int i = 0; i < size -1; i++) {
				result = result + theQueue[temp] + " ";
				temp = (temp +1) % capacity;
			}
			return result + theQueue[temp] + "]";
		}
		
		/** 
		 * Double the capacity of the Queue and reallocate the data
		 * @param  None  
		 * @return None  	
		 */
		public void reallocate() {
			int newCap = 2 *capacity;
			E [] newQueue = (E []) new Object[newCap];
			int index = front;
			for (int i =0; i < size; i++) {
				newQueue[i] = theQueue[index];
				index = (index +1) % capacity;
			}
			capacity = newCap;
			theQueue = newQueue;
			front = 0;
			rear = size - 1;
		}
		
		/** 
		 * Determine whether or not queue is empty
		 * @param  None  
		 * @return true if the queue is empty, and false otherwise 	
		 */
		public boolean empty(){
			/*
			if (size == 0)
				return true;
			else
				return false;
			*/
			return (size ==0) ? true : false;
		}
		
		/** 
		 * Determine the number of elements stored in the queue
		 * @param  None  
		 * @return the number of elements in the queue
		 */
		public int size() {
			return size;
		}

}
