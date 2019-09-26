package com.ors.masoud.interview.paytm.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import com.ors.masoud.interview.paytm.AveragingBuffer;

/**Array based implementation
 * of {@link AveragingBuffer}
 * 
 * Constructor takes metric over elements
 * as a lambda argument.
 * 
 * If comparing buffers to one another(hashCode/equals),
 * please refrain from defining the metrics
 * with in line method references.
 * 
 * @author masoud
 *
 * @param <E>
 */
public class AveragingArrayBuffer<E> implements AveragingBuffer<E> {

	E[] buffer;
	int index;
	BigDecimal aggregate;
	Function<E, Number> metric;

	public AveragingArrayBuffer(int size, Function<E, Number> metric) {
		this(size, metric, MathContext.DECIMAL64);
	}

	@SuppressWarnings("unchecked")
	public AveragingArrayBuffer(int size, Function<E, Number> metric, MathContext mc) {
		this.buffer = (E[]) new Object[size];
		this.metric = metric;
		if (mc.getPrecision() == 0) {
			aggregate = new BigDecimal(0, MathContext.DECIMAL128);
		} else {
			aggregate = new BigDecimal(0, mc);
		}
	}

	@Override
	public int getBufferSize() {
		return buffer.length;
	}

	@Override
	public int getElementCount() {
		return Math.min(index, buffer.length);
	}

	@Override
	public BigDecimal getAverage() {
		return getAverage(MathContext.DECIMAL64);
	}

	@Override
	synchronized public BigDecimal getAverage(MathContext context) {
		return aggregate.divide(BigDecimal.valueOf(getElementCount()), context);
	}

	@Override
	public E getLastElement(int reverseIndex) {
		synchronized (this) {
			if (reverseIndex >= 0 && getElementCount() - reverseIndex > 0) {
				return buffer[(index - reverseIndex - 1) % buffer.length];
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public void addElement(E element) {
		BigDecimal decimalValue = getDecimalValue(element, metric);
		BigDecimal removingValue = getDecimalValue(buffer[index % buffer.length], metric);
		synchronized (this) {
			aggregate = aggregate.add(decimalValue).subtract(removingValue);
			buffer[index++ % buffer.length] = element;
		}
	}

	static <T> BigDecimal getDecimalValue(T element, Function<T, Number> metric) {
		if (element == null || metric == null) {
			return BigDecimal.ZERO;
		}
		Number value = metric.apply(element);
		if (value == null) {
			return BigDecimal.ZERO;
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else if (value instanceof BigInteger) {
			return new BigDecimal((BigInteger) value);
		} else if (value instanceof Long) {
			return BigDecimal.valueOf(value.longValue());
		} else {
			return BigDecimal.valueOf(value.doubleValue());
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(index, buffer, metric);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AveragingArrayBuffer<?> other = (AveragingArrayBuffer<?>) obj;
		return metric.equals(other.metric) && index == other.index && Arrays.deepEquals(buffer, other.buffer);
	}

}
