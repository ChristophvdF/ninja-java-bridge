package com.ninjablocks.client.driver;

public class NinjaClient {
	
	static NinjaClient instance = new NinjaClient();
	
	private NinjaClient() {}
	
	public void on(String event, JsonEventHandler handler) {
		NodeBridge.on("app", event, handler);
	}

	public void emit(String event, Object... payload) {
		NodeBridge.emit("app", event, payload);
	}
	
}
