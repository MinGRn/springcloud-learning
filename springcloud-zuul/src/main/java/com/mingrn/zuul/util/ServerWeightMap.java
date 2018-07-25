package com.mingrn.zuul.util;

import java.util.HashMap;
import java.util.Map;

public final class ServerWeightMap {
	/**
	 * 服务器地址 权重
	 */
	private static Map<String, Integer> serverWeightMap = new HashMap<>();

	/*
	 *服务器地址 权重
	 */
	static {
		serverWeightMap.put("10.10.1.100", 1);
		serverWeightMap.put("10.10.1.101", 1);
		serverWeightMap.put("10.10.1.102", 1);
		serverWeightMap.put("10.10.1.103", 4);
		serverWeightMap.put("10.10.1.104", 1);
		serverWeightMap.put("10.10.1.105", 1);
		serverWeightMap.put("10.10.1.106", 3);
		serverWeightMap.put("10.10.1.107", 1);
		serverWeightMap.put("10.10.1.108", 2);
		serverWeightMap.put("10.10.1.109", 1);
		serverWeightMap.put("10.10.1.110", 1);
	}

	public static Map<String, Integer> getServerWeightMap() {
		return serverWeightMap;
	}
}
