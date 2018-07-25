package com.mingrn.zuul.extend;

import com.mingrn.zuul.util.ServerWeightMap;

import java.util.*;

/**
 * 轮询法
 *
 * @author MingRn
 */
public class RollPollingAlgorithm {

	public static void main(String[] args) {

		//重新创建一个Map，避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>(ServerWeightMap.getServerWeightMap());

		//取得IP地址的list
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<>(keySet);

		int pos = 0;

		for (int i = 0; i < 20; i++) {
			if (pos >= keySet.size()) {
				pos = 0;
			}
			String server = keyList.get(pos);
			System.out.println("第" + (i + 1) + "次请求的服务器为：" + server);
			pos++;
		}
	}
}