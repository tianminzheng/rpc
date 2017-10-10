package com.sad.soa.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sad.soa.rpc.message.TransportMessage;

public class RPCServer {
	private int threadSize = 10;
	private ExecutorService threadPool;
	private Map<String, Object> servicePool;
	private int port = 4321;

	public RPCServer() {
		super();
		synchronized (this) {
			threadPool = Executors.newFixedThreadPool(this.threadSize);
		}
	}
	
	public RPCServer(int threadSize, int port) {
		this.threadSize = threadSize;
		this.port = port;
		synchronized (this) {
			threadPool = Executors.newFixedThreadPool(this.threadSize);
		}
	}
	
	public RPCServer(Map<String, Object> servicePool, int threadSize, int port) {
		this.threadSize = threadSize;
		this.servicePool = servicePool;
		this.port = port;
		synchronized (this) {
			threadPool = Executors.newFixedThreadPool(this.threadSize);
		}
	}

	public void service() throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			Socket receiveSocket = serverSocket.accept();
			final Socket socket = receiveSocket;
			threadPool.execute(new Runnable() {				
				public void run() {
					try {
						process(socket);
						socket.close();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
		}
	}

	private void process(Socket receiveSocket) throws IOException,
			ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {

		ObjectInputStream objectinputStream = new ObjectInputStream(
				receiveSocket.getInputStream());
		TransportMessage message = (TransportMessage) objectinputStream
				.readObject();

		// 调用服务
		Object result = call(message);

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(receiveSocket.getOutputStream());
		objectOutputStream.writeObject(result);
		objectOutputStream.close();
	}

	/**
	 * 服务处理函数 通过包名+接口名在servicePool中找到对应服务 通过调用方法参数类型数组获取Method对象
	 * 通过Method.invoke(对象,参数)调用对应服务
	 */
	private Object call(TransportMessage message) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			InstantiationException {
		if (servicePool == null) {
			synchronized (this) {
				servicePool = new HashMap<String, Object>();
			}
		}
		String interfaceName = message.getInterfaceName();
		Object service = servicePool.get(interfaceName);
		Class<?> serviceClass = Class.forName(interfaceName);
		// 检查servicePool中对象,若没有着生产对象
		if (service == null) {
			synchronized (this) {
				service = serviceClass.newInstance();
				servicePool.put(interfaceName, service);
			}
		}
		Method method = serviceClass.getMethod(message.getMethodName(),
				message.getParamsTypes());
		Object result = method.invoke(service, message.getParameters());
		return result;
	}

	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public Map<String, Object> getServicePool() {
		return servicePool;
	}

	public void setServicePool(Map<String, Object> servicePool) {
		this.servicePool = servicePool;
	}
}
