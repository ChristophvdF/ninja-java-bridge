package com.ninjablocks.client.driver;

import com.fasterxml.jackson.databind.JsonNode;

public interface NinjaListener {
	
	public void listen(String event, JsonNode... payload);

}
