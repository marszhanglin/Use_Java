package com.newland.library.securitydetection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * 
 * <B style="color:#00f"> 是否中国大陆IP工具类</B>
 * <br>http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest 更新日期：20170619
 * <br>规则:http://ftp.apnic.net/apnic/stats/apnic/README.TXT
 * @author zhanglin  2017年6月19日
 */
public class IpUtil {
    private static final String FILE_NAME = File.separator+"delegated-apnic-latest"; 
    // 只存放属于中国大陆的ip段 
    static int[][][][] chinaIpDict = new int[256][256][256][3];
    
    static { 
        initIpDict(FILE_NAME);
    }
 
    /**
     * 解析起始ip 及块长度
     * 
     * @param line 单行记录
     * @return
     */
    private static int[] analyseLine(String line){
    	String[] strs = line.split("\\|");
    	if(strs.length<5){
    		return new int[]{0,0,0,0,0};
    	}
    	if(!"CN".equals(strs[1])||!"ipv4".equals(strs[2])){
    		return new int[]{0,0,0,0,0};
    	}
    	String[] ip_addr = strs[3].split("\\.");
    	if(null==ip_addr||ip_addr.length<4){
    		return new int[]{0,0,0,0,0};
    	}
		return new int[]{Integer.parseInt(ip_addr[0]),
						Integer.parseInt(ip_addr[1]),
						Integer.parseInt(ip_addr[2]),
						Integer.parseInt(ip_addr[3]),
						Integer.parseInt(strs[4])};
    }
    
    /**
     * 初始化IP字典
     * @param fromFile
     */
    private static void initIpDict(String fromFile){
    	long currentTime  = System.currentTimeMillis();
    	InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fromFile);
    	List<String> lines = null;
		try {
			lines = IOUtils.readLines(input);
		} catch (IOException e) { 
			e.printStackTrace();
		} 
    	for(String line : lines){
    		int[] values=analyseLine(line);
    		int addr1=values[0];
    		int addr2=values[1];
    		int addr3=values[2];
    		int addr4=values[3];
    		int number_sum=values[4];
    		if(number_sum == 0)
    			continue;
    		int offset = 0;
    		while(number_sum > 0){ 
    			int start = 0;
    			int end = 0;
    			int number = 0; 
    			if(number_sum >= 65536){
    				start = 0;
    				end = 65536;
    				number = 65536;
    			}else{
    				start = addr3*256 + addr4;
					end = start + number_sum -1;
					number = number_sum;
    			}
    			chinaIpDict[addr1][addr2+offset][offset]=new int[]{start,end,number};//offset的最大值为(256-addr2)
    			number_sum -= 65536; 
    			offset += 1;
    		}
    	} 
    	System.out.println("解析用时："+(System.currentTimeMillis()-currentTime)+"ms"); 
    }
    
    /**
     * 是否中国大陆IP
     * @param ip
     * @return
     */
    public static boolean isChinaIp(String ip) {
    	if(null==ip||ip.length()==0){
            return false;
        }
        String[] strs = ip.split("\\.");
        if (strs.length != 4) {
            return false;
        }
        int addr1 = Integer.valueOf(strs[0]);
        int addr2 = Integer.valueOf(strs[1]);
        int addr3 = Integer.valueOf(strs[2]);
        int addr4 = Integer.valueOf(strs[3]);
        if ((chinaIpDict[addr1][addr2]).length == 0) {//为0表示没有
            return false;
        }
        int ipValue = addr3 * 256 + addr4; 
        for (int[] item : chinaIpDict[addr1][addr2]) {
            if (ipValue >= item[0] && ipValue <= item[1]) {
                return true;
            }
        }  
        return false;
    }
 
}