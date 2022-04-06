package com.zeusight.mobile_track.saveuser;

import java.io.Serializable;

/**
 * 存放服务器连接信息
 * @author cyhuang
 *
 */
public class Server implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String serverUrl;
	
	private String serverName;

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
}
