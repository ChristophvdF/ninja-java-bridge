package com.ninjablocks.client.driver.test;

import com.ninjablocks.client.driver.BaseNinjaDevice;
import com.ninjablocks.client.driver.NinjaDevice;

public class ElliotsTestDevice extends BaseNinjaDevice implements NinjaDevice {
	
	public ElliotsTestDevice() {
		super(0, 14, "test123", "Test Device");
	}

	public boolean readable() {return true;}

	public boolean writable() {return true;}

	public boolean configurable() {return false;}

}
