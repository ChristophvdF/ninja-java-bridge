package com.ninjablocks.client.driver;

public interface NinjaDevice extends JsonEventEmitter {
	public int getV();
	public int getG();
	public String getD();
	public String getName();
	
	public String getGuid();
}
