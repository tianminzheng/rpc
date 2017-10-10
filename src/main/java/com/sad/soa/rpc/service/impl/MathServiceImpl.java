package com.sad.soa.rpc.service.impl;

import com.sad.soa.rpc.service.MathService;

public class MathServiceImpl implements MathService {
	public int getSum(int a, int b, String name) {
		System.out.println(name + " call:" + a + "+" + b);
		return a + b;
	}
}
