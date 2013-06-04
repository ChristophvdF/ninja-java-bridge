package com.ninjablocks.client.driver;

public abstract class BaseNinjaDevice {
	
	private int V,G;
	private String D, name;
	
	public BaseNinjaDevice(int V, int G, String D, String name) {
		this.V = V;
		this.G = G;
		this.D = D;
		this.name = name;
	}
	
	public String getContext() {
		return getGuid();
	}
	
	public void on(String event, JsonEventHandler handler) {
		NodeBridge.on(getContext(), event, handler);
	}

	public void emit(String event, Object... payload) {
		NodeBridge.emit(getContext(), event, payload);
	}
	
	public String getGuid() {
		return V + "_" + G + "_" + D;
	}

	public int getV() {
		return V;
	}

	public int getG() {
		return G;
	}

	public String getD() {
		return D;
	}
	
	public String getName() {
		return name;
	}

}
