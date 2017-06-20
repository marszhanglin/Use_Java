package com.newland.library.securitydetection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Url2IpUtil {    
//	static public void main(String[] args) {
//		if(isNetworkConnect()){
//			getIpsByDomain("www.baidu.com"); 
//		} else{
//			
//		}
//	}
	
	protected static List<String> getIpsByDomain(String domain) {
		try { 
		    InetAddress[] addresses = InetAddress.getAllByName(domain); 
		    if(null==addresses||addresses.length==0){
		    	return new ArrayList<String>();
		    }else{
		    	List<String> ipList= new ArrayList<String>();
		    	for (int i = 0; i < addresses.length; i++) {  
		    		ipList.add(addresses[i].getHostAddress());
			        System.out.println(domain + "[" + i + "]: "  
			                + addresses[i].getHostAddress());  
			    } 
		    	return ipList;
		    }
		     
		} catch (UnknownHostException uhe) {
		    //uhe.printStackTrace(); 
			System.out.println("该域名无法正常连接："+domain);
		    return new ArrayList<String>();
		}
	}  
	
    public static boolean isNetworkConnect(){  
        boolean connect = false;  
        Runtime runtime = Runtime.getRuntime();  
        Process process;  
        try {  
            process = runtime.exec("ping " + "www.baidu.com");  
            InputStream is = process.getInputStream();   
            InputStreamReader isr = new InputStreamReader(is);   
            BufferedReader br = new BufferedReader(isr);   
            String line = null;   
            StringBuffer sb = new StringBuffer();   
            while ((line = br.readLine()) != null) {   
                sb.append(line);   
            }       
            is.close();   
            isr.close();   
            br.close();    
            if (null != sb && !sb.toString().equals("")) {    
                if (sb.toString().indexOf("TTL") > 0) {   
                    connect = true; 
                    System.out.println("网络连接正常 ");
                } else {    
                    connect = false; 
                    System.out.println("网络未连接 ");
                }   
            }   
        } catch (IOException e) {  
            e.printStackTrace();  
            return connect; 
        }
        return connect;  
    } 
  
}  