package com.sad.soa.rpc.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sad.soa.rpc.server.RPCServer;
import com.sad.soa.rpc.service.impl.MathServiceImpl;

public class ServerTest {
	
	public static void main(String[] args){
		Map<String,Object> servicePool = new  HashMap<String, Object>();
		servicePool.put("com.sad.soa.rpc.service.MathService", new MathServiceImpl());
		RPCServer server = new RPCServer(servicePool,4, 4321);
		try {
			server.service();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
