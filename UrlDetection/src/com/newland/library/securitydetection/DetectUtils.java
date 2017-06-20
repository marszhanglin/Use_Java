package com.newland.library.securitydetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class DetectUtils {
	public static final String ADDRESS_REXP="(http|https)://[a-zA-Z0-9][a-zA-Z0-9-.]*[a-zA-Z0-9](:[0-9]+)?/"; 
	public static final String ADDRESS_IP_REXP="(http|https)://(((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))(:[0-9]+)?/";
	public static final String IP_REXP="(((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
	
	public static final String containUrlsKey="containUrls";
	public static final String containIpportsKey="containIpports";
	public static final String constraintRulesKey="constraintRules";
	
	private static final String ipportKey="ipport";
	private static final String urlKey="url";
	private static final String causeKey="cause";
	private static final String detailKey="detail";
	
	public class cause{
		private static final String ABNORMAL_URL="ABNORMAL_URL";
		private static final String NON_DOMESTIC_IP="NON_DOMESTIC_IP";
		private static final String ON_BLACKLIST="ON_BLACKLIST";
	}

	private static Pattern address;
	
	
	
	public static String addressDetection(List<String> addressList,List<String> blackList){
		HashMap<String, Object> rstObj= new HashMap<String, Object>();
		if(null==addressList||addressList.size()==0){
			rstObj.put(containUrlsKey, new ArrayList<String>());
			rstObj.put(containIpportsKey, new ArrayList<String>());
			rstObj.put(constraintRulesKey, new ArrayList<String>());
			return new Gson().toJson(rstObj);
		}
		List<String> currentAddressList=new ArrayList<String>();
		for(String address : addressList){
			if(validate(address, ADDRESS_REXP)){//验证address的正确性
				System.out.println("current address:"+address);
				currentAddressList.add(address);
			}else{
				System.out.println("wrong address:"+address);
			}
		}
		List<String> currentIpList=new ArrayList<String>();
		List<String> currentDomainNameList=new ArrayList<String>();
		for(String currentAddress : currentAddressList){
			if(validate(currentAddress, ADDRESS_IP_REXP)){//是否为ip
				currentIpList.add(getMatchList(currentAddress,ADDRESS_REXP).get(0));
				System.out.println("with ip:"+currentAddress);
			}else{ 
				currentDomainNameList.add(getMatchList(currentAddress,ADDRESS_REXP).get(0));
				System.out.println("with domainName:"+currentAddress);
			}
		}
		
		List<HashMap<String, Object>> constraintRuleList=new ArrayList<HashMap<String,Object>>();
		
		//是否国内ip校验
		if(Url2IpUtil.isNetworkConnect()){
			// TODO 网络已连接 
			//域名
			for(String address:currentDomainNameList){
				String domain =(address.split("://"))[1].split("/")[0]; 
				if(null==domain)continue;
				List<String> ips=Url2IpUtil.getIpsByDomain(domain);
				if(null==ips)continue;
				for(String ip:ips){
					if(!IpUtil.isChinaIp(ip)){
						HashMap<String, Object> rule = new HashMap<String, Object>();
						rule.put(ipportKey, address);
						rule.put(causeKey, cause.NON_DOMESTIC_IP);
						rule.put(detailKey, "该域名映射的ip("+ip+")非中国大陆ip");
						constraintRuleList.add(rule);
						break;
					}
				} 
			}
			//ip
			for(String address:currentIpList){
				String ip=getMatchList(address, IP_REXP).get(0);
				if(null==ip)continue;
				if(!IpUtil.isChinaIp(ip)){
					HashMap<String, Object> rule = new HashMap<String, Object>();
					rule.put(ipportKey, address);
					rule.put(causeKey, cause.NON_DOMESTIC_IP);
					rule.put(detailKey, "该ip非中国大陆ip");
					constraintRuleList.add(rule);
				}
			}
		}else{
			// TODO 网络未连接 
			//ip
			for(String address:currentIpList){
				String ip=getMatchList(address, IP_REXP).get(0);
				if(null==ip)continue;
				if(IpUtil.isChinaIp(ip)){
					HashMap<String, Object> rule = new HashMap<String, Object>();
					rule.put(ipportKey, address);
					rule.put(causeKey, cause.NON_DOMESTIC_IP);
					rule.put(detailKey, "该ip非中国大陆ip");
					constraintRuleList.add(rule);
				}
			}
		}
		
		//黑名单正则
		if(null!=blackList){
			for(String black : blackList){
				for(String address:currentIpList){
					if(validate(address, black)){
						HashMap<String, Object> rule = new HashMap<String, Object>();
						rule.put(ipportKey, address);
						rule.put(causeKey, cause.ON_BLACKLIST);
						rule.put(detailKey, black);
						constraintRuleList.add(rule);
					}
				}
				for(String address:currentDomainNameList){
					if(validate(address, black)){
					HashMap<String, Object> rule = new HashMap<String, Object>();
						rule.put(urlKey, address);
						rule.put(causeKey, cause.ON_BLACKLIST);
						rule.put(detailKey, black);
						constraintRuleList.add(rule);
					}
				}
			}
		}
		
		
		rstObj.put(containUrlsKey, currentDomainNameList);
		rstObj.put(containIpportsKey, currentIpList);
		rstObj.put(constraintRulesKey, constraintRuleList);
		return new Gson().toJson(rstObj);
	}
	
	/**
	 * 正则校验
	 * @param input 输入
	 * @param regex 正则
	 * @return 匹配返回true
	 */
	public static boolean validate(String input,String regex){
		if(null==input||input.length()==0){
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher mat = pattern.matcher(input);
		return mat.find(); 
	} 
	
	public static List<String> getMatchList(String input,String regex){
        List<String> ls=new ArrayList<String>();
        if(null==input||null==regex){
        	 return ls;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(input);
        while(mat.find())
            ls.add(mat.group());
        return ls;
    }
}
