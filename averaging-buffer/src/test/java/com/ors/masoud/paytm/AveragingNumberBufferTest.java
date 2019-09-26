package com.ors.masoud.paytm;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import com.ors.masoud.interview.paytm.AveragingBuffer;
import com.ors.masoud.interview.paytm.AveragingNumberBuffer;
import com.ors.masoud.interview.paytm.impl.AveragingArrayBuffer;
import com.ors.masoud.interview.paytm.impl.AveragingNumberArrayBuffer;

class AveragingNumberBufferTest {
	
	static class Rectangle{
		int height;
		int width;
		public Rectangle(int height, int width) {
			this.height = height;
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public int getWidth() {
			return width;
		}
		
		public long getArea(){
			return Long.valueOf(height)*Long.valueOf(width);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(height, width);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Rectangle other = (Rectangle) obj;
			if (height != other.height)
				return false;
			if (width != other.width)
				return false;
			return true;
		}		
	}

	@Test
	void test() {
		AveragingNumberBuffer<Integer> buffer = new AveragingNumberArrayBuffer<Integer>(100);
		for (int i = 0; i < 100000; i++) {
			buffer.addElement(i);
			Assertions.assertEquals(calcAverage(Math.max(0, i - 99), i).doubleValue(), buffer.getAverage().doubleValue(),
					"failed at i=" + i);
		}
	}
	

	@Test
	void testDoubles() {
		AveragingNumberBuffer<Double> buffer = new AveragingNumberArrayBuffer<Double>(100);
		for (double i = 0; i < 100000; i += 0.5) {
			buffer.addElement((double) i);
			Assertions.assertEquals(calcDoubleAverage(i, (int) Math.min(99, i / 0.5), 0.5).doubleValue(),
					buffer.getAverage(MathContext.DECIMAL64).doubleValue(), "failed at i=" + i);
		}
	}

	@Test
	void testBigDecimals() {
		AveragingNumberBuffer<BigDecimal> buffer = new AveragingNumberArrayBuffer<BigDecimal>(200);
		for (int i = 0; i < 1000000; i++) {
			BigDecimal next = BigDecimal.valueOf(0.33d).multiply(BigDecimal.valueOf(i));
			buffer.addElement(next);
			Assertions.assertEquals(0,calcDecAverage(next, (int) Math.min(199, i ), 0.33)
					.compareTo(
					(BigDecimal) buffer.getAverage()), "failed at i=" + i);
		}
	}
	
	@Test
	void testRectangle() {
		AveragingBuffer<Rectangle> buffer = new AveragingArrayBuffer<Rectangle>(100,Rectangle::getArea);
		for (int i = 0; i < 100000; i++) {
			buffer.addElement(new Rectangle(i,14));
			Assertions.assertEquals(calcDoubleAverage(i, Math.min(99, i), 1)*14, buffer.getAverage().doubleValue(),
					"failed at i=" + i);
		}
	}


	@Test
	void testRetrieval() {
		AveragingNumberBuffer<Integer> buffer = new AveragingNumberArrayBuffer<Integer>(100);
		buffer.addElement(1);
		buffer.addElement(2);
		buffer.addElement(3);
		Assertions.assertEquals(3, buffer.getElementCount());
		Assertions.assertEquals(3, buffer.getLastElement(0).intValue());
		Assertions.assertEquals(2, buffer.getLastElement(1).intValue());
		Assertions.assertEquals(1, buffer.getLastElement(2).intValue());
		Assertions.assertThrows(IllegalArgumentException.class, ()->buffer.getLastElement(3));
	}

	private BigDecimal calcDecAverage(BigDecimal top, int length, double step) {
		return top
				.subtract(BigDecimal.valueOf(step).multiply(BigDecimal.valueOf((double)length/2d)));
	}

	private Double calcDoubleAverage(double top, int length, double step) {
		return top - ((step * length) / 2);
	}

	private Double calcAverage(int bottom, int top) {
		return (Double.valueOf(bottom) + Double.valueOf(top)) / 2d;
	}

}
