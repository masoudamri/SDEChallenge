package com.ors.masoud.interview.paytm;

import java.math.MathContext;

/**Holds a certain amount of elements
 * and reports a moving average of
 * a particular measure of those elements
 * 
 * @author masoud
 *
 * @param <E>
 */
public interface AveragingBuffer<E> {
	
	public int getElementCount();
	
	public int getBufferSize();

	public Number getAverage();
	
	public Number getAverage(MathContext context);

	public void addElement(E element);
	
	/**
	 * 0 gives you the last element added,
	 * 1 the element before that etc...
	 * @param reverseIndex
	 * @return requested element
	 * @throws IllegalArgumentException
	 */
	public E getLastElement(int reverseIndex);
}
