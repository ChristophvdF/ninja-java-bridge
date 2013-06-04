package com.ninjablocks.client.driver;

import java.io.IOException;


public interface NinjaDriver extends JsonEventEmitter {

	//public void config(JsonNode rpc, NinjaCallback cb);

	public void register(NinjaDevice device) throws IOException;
}
