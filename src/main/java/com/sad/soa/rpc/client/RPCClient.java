package com.sad.soa.rpc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sad.soa.rpc.message.TransportMessage;

public class RPCClient {
	private String serverAddress;
	private int serverPort;

	public RPCClient() {
		super();
	}
	
	public RPCClient(String serverAddress, int serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}
	
	/**
	 * 同步的请求和接收结果
	 */
	public Object sendAndReceive(TransportMessage transportMessage){
		Object result = null;
		try {
			Socket socket = new Socket(serverAddress,serverPort);
			ObjectOutputStream objectOutpusStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutpusStream.writeObject(transportMessage);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			result = objectInputStream.readObject();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

}
