package com.ninjablocks.client.driver;


public interface JsonEventEmitter {
	
	public void emit(String event, Object... payload);
	public void on(String event, JsonEventHandler handler);

}
