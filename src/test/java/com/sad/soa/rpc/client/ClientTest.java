package com.sad.soa.rpc.client;

import com.sad.soa.rpc.client.RPCClient;
import com.sad.soa.rpc.message.TransportMessage;

public class ClientTest {
	public static void main(String[] args) {
		String serverAddress = "127.0.0.1";
		int serverPort = 4321;
		final RPCClient client = new RPCClient(serverAddress, serverPort);
		TransportMessage transportMessage = buildTransportMessage(1, 1);
		Object result = client.sendAndReceive(transportMessage);
		System.out.println(result);
		
		transportMessage = buildTransportMessage(2, 2);
		result = client.sendAndReceive(transportMessage);
		System.out.println(result);
	}

	private static TransportMessage buildTransportMessage(int a, int b) {

		String interfaceName = "com.sad.soa.rpc.service.MathService";
		Class[] paramsTypes = { int.class, int.class, String.class };
		Object[] parameters = { a, b, "Tianyalan" };
		String methodName = "getSum";

		TransportMessage transportMessage = new TransportMessage(interfaceName,
				methodName, paramsTypes, parameters);

		return transportMessage;
	}

}
