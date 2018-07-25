package com.mingrn.zuul.extend;

import com.mingrn.zuul.util.ServerWeightMap;

import java.util.*;

/**
 * 加权轮询法
 *
 * @author MinGRn
 */
public class WeightRollPollingAlgorithm {

	public static void main(String[] args) {

		getServer();

	}

	private static void getServer() {

		//重新创建一个Map，避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>(ServerWeightMap.getServerWeightMap());

		//取得IP地址的list
		Set<String> keySet = serverMap.keySet();
		Iterator<String> it = keySet.iterator();

		List<String> serverList = new ArrayList<>();

		while (it.hasNext()) {
			String server = it.next();
			//权重
			Integer weight = serverMap.get(server);

			for (int i = 0; i < weight; i++) {
				serverList.add(server);
			}

		}


		int pos = 0;
		for (int i = 0; i < 20; i++) {
			if (pos >= serverList.size()) {
				pos = 0;
			}
			String server = serverList.get(pos);
			pos++;
			System.out.println("第" + (i + 1) + "次申请的服务器为:" + server);
		}
	}
}