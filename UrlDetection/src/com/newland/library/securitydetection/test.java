package com.newland.library.securitydetection;

import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		//t1();
		t2(); 
		//System.out.println(IpUtil.isChinaIp("14.215.177.38")); //218.66.48.233
		//System.out.println(IpUtils.isChinaIp("14.215.177.38"));//百度 广东
		//System.out.println(IpUtils.isChinaIp("203.128.1.255"));//巴基斯坦
		//System.out.println(IpUtils.isChinaIp("218.66.48.233"));//福州
		//System.out.println(IpUtils.isChinaIp("59.125.39.5"));//台湾
		//System.out.println(IpUtil.isChinaIp("66.102.251.33"));//新浪国内
		
		
	}

	private static void t2() {
		List<String> addresslist=new ArrayList<String>();
		
		addresslist.add("https://255.0.0.0:8888/？？？");
		addresslist.add("https://255.0.0.0:/？？？");
		addresslist.add("https://256.0.0.0:8888/？？？");
		addresslist.add("http://127.0.0.1:8888/？？？");
		addresslist.add("https://localhost:8080/");
		addresslist.add("https://localhost:80/");
		addresslist.add("http://www.baidu.com/？？？");
		addresslist.add("http://www.baidu.com:80/？？？");
		addresslist.add("https://www.baidu.com:433/？？？");
		addresslist.add("https://www.baidu.com:9012/？？？");
		addresslist.add("https://www.baidu.com:9012/");
		addresslist.add("http://developer.baidu.com/");
		addresslist.add("aaahttp://blog.csdn.net/?ref=toolbar_logo");
		addresslist.add("https://www.google.co.jp/");
		
		List<String> blackList=new ArrayList<String>();
		blackList.add("developer.baidu.com");
		blackList.add("www.google.co.jp");
		System.out.println(DetectUtils.addressDetection(addresslist,blackList));
	}

	private static void t1() {
		String url = "http://256.0.0.0:0/？？？";
		System.out.println(DetectUtils.validate("https://256.0.0.0:8888/？？？", DetectUtils.ADDRESS_IP_REXP));
	}

}
