package com.ninjablocks.client.driver;

import com.fasterxml.jackson.databind.node.ArrayNode;

public interface JsonEventHandler {
	
	public void handle(String event, ArrayNode args);

}
