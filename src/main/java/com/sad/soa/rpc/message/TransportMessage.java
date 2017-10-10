package com.sad.soa.rpc.message;

import java.io.Serializable;

public class TransportMessage implements Serializable {
	//包名+接口名称  
	private String interfaceName;
	//调用方法名
	private String methodName;
	//参数类型 按照接口参数顺序 
	private Class[] paramsTypes;
	//参数  按照接口参数顺序 
	private Object[] parameters;

	public TransportMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransportMessage(String interfaceName, String methodName,
			Class[] paramsTypes, Object[] parameters) {
		super();
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.paramsTypes = paramsTypes;
		this.parameters = parameters;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParamsTypes() {
		return paramsTypes;
	}

	public void setParamsTypes(Class[] paramsTypes) {
		this.paramsTypes = paramsTypes;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

}
