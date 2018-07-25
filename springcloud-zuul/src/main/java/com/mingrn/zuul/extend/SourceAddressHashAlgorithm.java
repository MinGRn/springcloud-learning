package com.mingrn.zuul.extend;

import com.mingrn.zuul.util.ServerWeightMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 源地址 Hash 发
 *
 * @author MinGRn <br > 2018/7/24 17:36
 */
public class SourceAddressHashAlgorithm {

	public static void main(String[] args) {

		getServer("10.10.1.100", 1);
		getServer("10.10.1.101", 1);
		getServer("10.10.1.102", 1);

		getServer("10.10.1.102", 2);
		getServer("10.10.1.101", 2);
		getServer("10.10.1.100", 2);

	}

	private static void getServer(String ip, int count) {

		//重新创建一个Map，避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>(ServerWeightMap.getServerWeightMap());

		//取得IP地址的list
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<>(keySet);

		int hashCode = ip.hashCode();
		int serverListSize = keyList.size();
		int pos = hashCode % serverListSize;

		System.out.println(ip + " 第" + count + "次请求的服务器地址为: " + keyList.get(pos));

	}
}