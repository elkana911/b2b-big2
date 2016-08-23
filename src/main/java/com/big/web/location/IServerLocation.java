package com.big.web.location;

public interface IServerLocation {
	ServerLocation getLocation(String ipAddress) throws Exception;
}
