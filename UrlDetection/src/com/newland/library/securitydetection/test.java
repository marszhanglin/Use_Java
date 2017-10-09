package com.newland.library.securitydetection;

import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		//t1();
		//t2(); 
		//System.out.println(IpUtil.isChinaIp("14.215.177.38")); //218.66.48.233
		//System.out.println(IpUtils.isChinaIp("14.215.177.38"));//百度 广东
		//System.out.println(IpUtils.isChinaIp("203.128.1.255"));//巴基斯坦
		//System.out.println(IpUtils.isChinaIp("218.66.48.233"));//福州
		//System.out.println(IpUtils.isChinaIp("59.125.39.5"));//台湾
		//System.out.println(IpUtil.isChinaIp("66.102.251.33"));//新浪国内
		//System.out.println(IpUtil.isChinaIp("120.25.135.47"));//新浪国内
		System.out.println(IpUtil.isChinaIp("117.27.139.124"));
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
		
		
		addresslist.add("0.0.0.0"); 
		addresslist.add("127.0.0.1"); 
		addresslist.add("21.22.11.33"); 
		addresslist.add("Base.TextAppearance.AppCompat.Menu"); 
		addresslist.add("Base.TextAppearance.AppCompat.Widget.ActionBar.Menu"); 
		addresslist.add("Base.Widget.AppCompat.ListView.Menu"); 
		addresslist.add("Base.Widget.Design"); 
		addresslist.add("CharMatcher.is"); 
		addresslist.add("Gravity.NO"); 
		addresslist.add("MANIFEST.MF"); 
		addresslist.add("Predicates.in"); 
		addresslist.add("Q.ZW"); 
		addresslist.add("TextAppearance.AppCompat.Menu"); 
		addresslist.add("TextAppearance.AppCompat.Widget.ActionBar.Menu"); 
		addresslist.add("TextAppearance.Design"); 
		addresslist.add("TextAppearance.Design.Tab"); 
		addresslist.add("TextAppearance.Material.Menu"); 
		addresslist.add("TextAppearance.Material.Widget.ActionBar.Menu"); 
		addresslist.add("TextAppearance.StatusBar.EventContent.Info"); 
		addresslist.add("W.uk"); 
		addresslist.add("Widget.AppCompat.ListView.Menu"); 
		addresslist.add("Widget.Design"); 
		addresslist.add("Widget.ListView.Menu"); 
		addresslist.add("adroh.so"); 
		addresslist.add("android.R.id"); 
		addresslist.add("android.app"); 
		addresslist.add("android.car"); 
		addresslist.add("android.content.pm"); 
		addresslist.add("android.graphics"); 
		addresslist.add("android.intent.action.MEDIA"); 
		addresslist.add("android.intent.action.NEWLAND.ALIPAY"); 
		addresslist.add("android.intent.action.NEWLAND.CASH"); 
		addresslist.add("android.intent.action.NEWLAND.MOBILE"); 
		addresslist.add("android.intent.action.NEWLAND.PAY"); 
		addresslist.add("android.intent.extra.CC"); 
		addresslist.add("android.intent.extra.EMAIL"); 
		addresslist.add("android.intent.extra.STREAM"); 
		addresslist.add("android.media"); 
		addresslist.add("android.media.browse.extra.PAGE"); 
		addresslist.add("android.media.extra.BT"); 
		addresslist.add("android.media.metadata.ART"); 
		addresslist.add("android.media.metadata.AUTHOR"); 
		addresslist.add("android.media.metadata.BT"); 
		addresslist.add("android.media.metadata.DATE"); 
		addresslist.add("android.media.metadata.MEDIA"); 
		addresslist.add("android.net"); 
		addresslist.add("android.net.Network"); 
		addresslist.add("android.os.Build"); 
		addresslist.add("android.permission.READ"); 
		addresslist.add("android.service.media"); 
		addresslist.add("android.support"); 
		addresslist.add("android.support.design"); 
		addresslist.add("android.support.graphics"); 
		addresslist.add("android.support.v4.app"); 
		addresslist.add("android.support.v4.graphics"); 
		addresslist.add("android.support.v4.media"); 
		addresslist.add("android.support.v4.media.description.MEDIA"); 
		addresslist.add("android.support.v4.media.session.action.PLAY"); 
		addresslist.add("android.support.v4.widget.Space"); 
		addresslist.add("android.support.v7.view.menu"); 
		addresslist.add("builder.name"); 
		addresslist.add("capk.app"); 
		addresslist.add("com.android"); 
		addresslist.add("com.android.org"); 
		addresslist.add("com.google"); 
		addresslist.add("com.google.android.gms.org"); 
		addresslist.add("com.newland.me"); 
		addresslist.add("com.newland.pospp.paymententry.App"); 
		addresslist.add("com.newland.pospp.paymententry.cash"); 
		addresslist.add("d.ad"); 
		addresslist.add("dark.pn"); 
		addresslist.add("http://schemas.android.com"); 
		addresslist.add("http://www.apache.org"); 
		addresslist.add("http:/117.27.139.124"); 
		addresslist.add("info.properties"); 
		addresslist.add("java.io"); 
		addresslist.add("java.net"); 
		addresslist.add("java.specification.name"); 
		addresslist.add("java.util.Properties"); 
		addresslist.add("java.vm.name"); 
		addresslist.add("java.vm.specification.name"); 
		addresslist.add("libcore.icu.ICU"); 
		addresslist.add("libcore.io"); 
		addresslist.add("light.9.pn"); 
		addresslist.add("light.pn"); 
		addresslist.add("org.robovm.apple.foundation"); 
		addresslist.add("os.name"); 
		addresslist.add("pre.pn");  
		addresslist.add("sdk.properties"); 
		addresslist.add("sun.security"); 
		addresslist.add("t.cn"); 
		addresslist.add("user.name"); 
		addresslist.add("vnd.android"); 
		addresslist.add("vnd.microsoft"); 
		addresslist.add("vnd.oasis.opendocument.graphics"); 
		addresslist.add("water.channel"); 
		
		
		List<String> blackList=new ArrayList<String>();
		blackList.add("developer.baidu.com");
		blackList.add("www.google.co.jp");
		System.out.println(DetectUtils2.addressDetection(addresslist,blackList));
	}

	private static void t1() {
		String url = "http://256.0.0.0:0/？？？";
		System.out.println(DetectUtils.validate("https://256.0.0.0:8888/？？？", DetectUtils.ADDRESS_IP_REXP));
	}

}
