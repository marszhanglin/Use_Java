package com.newland.library.securitydetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class DetectUtils2 {
	public static final String ADDRESS_REXP="(http|https)://[a-zA-Z0-9][a-zA-Z0-9-.]*[a-zA-Z0-9](:[0-9]+)?"; 
	public static final String ADDRESS_IP_REXP="(http|https)://(((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))(:[0-9]+)?";
	public static final String IP_REXP     ="(((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
	public static final String IP_PORT_REXP="(((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))(:[0-9]+)?)";
	
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
	
	
	
	public static String addressDetection(List<String> addressList,List<String> blackList){
		long t= System.currentTimeMillis();
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
				System.out.println("with http:"+address); 
				String domain =(address.split("://"))[1].split("/")[0]; 
				currentAddressList.add(domain);
			}else{
				System.out.println("without http:"+address);
				currentAddressList.add(address);
				
			}
		}
		List<String> currentIpList=new ArrayList<String>();
		List<String> currentDomainNameList=new ArrayList<String>();
		for(String currentAddress : currentAddressList){
			if(validate(currentAddress, IP_REXP)){//是否为ip 
				System.out.println("with ip:"+currentAddress);
				currentIpList.add(getMatchList(currentAddress,IP_PORT_REXP).get(0)); 
			}else{ 
				System.out.println("with domainName:"+currentAddress); 
				currentDomainNameList.add(currentAddress);
			}
		}
		
		List<HashMap<String, Object>> constraintRuleList=new ArrayList<HashMap<String,Object>>();
		
		//是否国内ip校验
		//if(Url2IpUtil.isNetworkConnect()){
		if(Url2IpUtil.getIpsByDomain("www.baidu.com").size()>0){
			// TODO 网络已连接 
			//域名
			handleDomanAddressUseThreadPool(currentDomainNameList, constraintRuleList);
			//ip
			handleIplist(currentIpList, constraintRuleList);
		}else{
			// TODO 网络未连接 
			//ip
			handleIplist(currentIpList, constraintRuleList);
		}
		
		//黑名单正则
		handleBlackList(blackList, currentIpList, currentDomainNameList,
				constraintRuleList); 
		rstObj.put(containUrlsKey, currentDomainNameList);
		rstObj.put(containIpportsKey, currentIpList);
		rstObj.put(constraintRulesKey, constraintRuleList);
		System.out.println("分类IP用时："+(System.currentTimeMillis()-t)+"ms");
		return new Gson().toJson(rstObj);
	}

	/**
	 * 处理分析ip列表
	 * @param currentIpList
	 * @param constraintRuleList
	 */
	private static void handleIplist(List<String> currentIpList,
			List<HashMap<String, Object>> constraintRuleList) {
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

	/**
	 * 处理分析黑名单列表正则
	 * @param blackList
	 * @param currentIpList
	 * @param currentDomainNameList
	 * @param constraintRuleList
	 */
	private static void handleBlackList(List<String> blackList,
			List<String> currentIpList, List<String> currentDomainNameList,
			List<HashMap<String, Object>> constraintRuleList) {
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
	}

	
	/**
	 * 分析处理域名列表
	 * @param currentDomainNameList
	 * @param constraintRuleList
	 */
	private static void handleDomanAddressUseThreadPool(List<String> currentDomainNameList,
			List<HashMap<String, Object>> constraintRuleList) {
		if(null==currentDomainNameList)return;
		int size = currentDomainNameList.size();
		if(size==0)return;
		int nThreads = size>30?30:size;//size;//size>50?50:size;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);  
        List<Future<HashMap<String, Object>>> futures = new ArrayList<Future<HashMap<String, Object>>>(nThreads); 
        for (int i = 0; i < size; i++) {  
        	final String address = currentDomainNameList.get(i);
            Callable<HashMap<String, Object>> task = new Callable<HashMap<String, Object>>() {  
            	
                public HashMap<String, Object> call() throws Exception {  
                		HashMap<String, Object> rule =null;
            			if (null == address)
            				return null;
            			List<String> ips = Url2IpUtil.getIpsByDomain(address);
            			if (null == ips)
            				return null;
            			for (String ip : ips) {
            				if (!IpUtil.isChinaIp(ip)) {
            					rule = new HashMap<String, Object>();
            					rule.put(ipportKey, address);
            					rule.put(causeKey, cause.NON_DOMESTIC_IP);
            					rule.put(detailKey, "该域名映射的ip(" + ip + ")非中国大陆ip"); 
            					break;
            				}
            			}  
                    return rule;  
                }  
            };  
            Future<HashMap<String, Object>> tempTaskRst=executorService.submit(task);//提交至线程池
            if(null!=tempTaskRst){
            	futures.add(tempTaskRst);
            } 
        }   
        for (Future<HashMap<String, Object>> future : futures) {  
        	try {
        		HashMap<String, Object> tempItem = future.get();
        		if(null!=tempItem){
        			constraintRuleList.add(tempItem);
        		}
			} catch (InterruptedException e) { 
				e.printStackTrace();
			} catch (ExecutionException e) { 
				e.printStackTrace();
			} 
        }  
        executorService.shutdown();    
	}
	
//	private static void handleDomanAddress(List<String> currentDomainNameList,
//			List<HashMap<String, Object>> constraintRuleList) {
//		for(String address:currentDomainNameList){ 
//			if(null==address)continue;
//			List<String> ips=Url2IpUtil.getIpsByDomain(address);
//			if(null==ips)continue;
//			for(String ip:ips){
//				if(!IpUtil.isChinaIp(ip)){
//					HashMap<String, Object> rule = new HashMap<String, Object>();
//					rule.put(ipportKey, address);
//					rule.put(causeKey, cause.NON_DOMESTIC_IP);
//					rule.put(detailKey, "该域名映射的ip("+ip+")非中国大陆ip");
//					constraintRuleList.add(rule);
//					break;
//				}
//			} 
//		}
//	}
	
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
