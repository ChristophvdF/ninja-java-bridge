package com.ninjablocks.client.driver;

public abstract class BaseNinjaDriver {
	
	public void register(NinjaDevice device) {
		NodeBridge.register(device);
	}
	
	public void on(String event, JsonEventHandler handler) {
		NodeBridge.on(null, event, handler);
	}

	public void emit(String event, Object... payload) {
		NodeBridge.emit(null, event, payload);
	}

}