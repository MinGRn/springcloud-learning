package com.mingrn.zuul.extend;

import com.mingrn.zuul.util.ServerWeightMap;

import java.util.*;

/**
 * 随机法
 *
 * @author MingRn
 */
public class RandomAlgorithm {

	public static void main(String[] args) {

		//重新创建一个Map，避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>(ServerWeightMap.getServerWeightMap());

		//取得IP地址的list
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<String>(keySet);

		for (int i = 0; i < 20; i++) {
			Random random = new Random();
			int pos = random.nextInt(keySet.size());

			String server = keyList.get(pos);

			System.out.println("第" + (i + 1) + "次请求的服务器为：" + server);

			pos++;
		}
	}
}