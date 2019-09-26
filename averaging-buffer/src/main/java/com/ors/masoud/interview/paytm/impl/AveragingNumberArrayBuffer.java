package com.ors.masoud.interview.paytm.impl;

import java.math.MathContext;
import java.util.function.Function;

import com.ors.masoud.interview.paytm.AveragingNumberBuffer;

public class AveragingNumberArrayBuffer<N extends Number> extends AveragingArrayBuffer<N>
		implements AveragingNumberBuffer<N> {

	private static Function<?,?> identity=t->t;
	
	public AveragingNumberArrayBuffer(int size) {
		this(size, MathContext.DECIMAL64);
	}

	@SuppressWarnings("unchecked")
	AveragingNumberArrayBuffer(int size, MathContext mc) {
		super(size, (Function<N,Number>)identity, mc);
	}
	
}
