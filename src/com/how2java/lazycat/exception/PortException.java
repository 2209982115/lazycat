package com.how2java.lazycat.exception;

public class PortException extends Exception{

	int port;
	public PortException(int port){
		this.port = port;
	}
	
	public String toString(){
		
		
		String result = String.format("%s �˿ڱ�ռ��", String.valueOf(port));
		
		return result;
	}
}
