package xml;

import xml.ResponseObject.ResponseState;
import xml.ResponseObject.item;
import xml.ResponseObject.pagingInfo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * 
 * <B style="color:#00f"> xml解析</B>
 * <br>参考：http://blog.csdn.net/xiaokui_wingfly/article/details/46470145
 * @author zhanglin  2017年9月26日
 */
public class XStreamToolTestMain {

	public static void main(String[] args) {
		StringBuilder builder =new StringBuilder();
		builder.append("<response><responseState>");
		builder.append("<code>0</code>");
		builder.append("<description></description><errorList/></responseState>");
		builder.append("<pagingInfo><totalCount>2</totalCount><pageSize>100</pageSize><curPageNum>1</curPageNum><totalPage>1</totalPage></pagingInfo>");
		builder.append("<data>");
		builder.append("<item>");
		builder.append("<terminalSN>12345678</terminalSN>");
		builder.append("<equipmentNum>LANDI APOS</equipmentNum>");
		builder.append("<applyModName>D086UZFBdmfdlm</applyModName>");
		builder.append("<applyVersion>1.1.14</applyVersion>");
		builder.append("<suffix>APK</suffix>");
		builder.append("<packages>http://127.0.0.1:8080/platform/servlet/DownLoadServlet?id=20</packages>");
		builder.append("<fail>http://172.20.45.211:6565/platform//servlet/DownFailServlet?id=21</fail>");
		builder.append("<para>http://127.0.0.1:8080/platform/servlet/DownLoadServlet?id=20</para>");
		builder.append("<appUpdate>1</appUpdate>");
		builder.append("<paraUpdate>1</paraUpdate>");
		builder.append("</item>");
		builder.append("<item>");
		builder.append("<terminalSN>12345678</terminalSN>");
		builder.append("<equipmentNum>LANDI APOS</equipmentNum>");
		builder.append("<applyModName>D086UZFBdmfdlm</applyModName>");
		builder.append("<applyVersion>1.1.14</applyVersion>");
		builder.append("<suffix>APK</suffix>");
		builder.append("<packages>http://127.0.0.1:8080/platform/servlet/DownLoadServlet?id=20</packages>");
		builder.append("<fail>http://172.20.45.211:6565/platform//servlet/DownFailServlet?id=21</fail>");
		builder.append("<para>http://127.0.0.1:8080/platform/servlet/DownLoadServlet?id=20</para>");
		builder.append("<appUpdate>1</appUpdate>");
		builder.append("<paraUpdate>1</paraUpdate>");
		builder.append("</item>");
		builder.append("</data>");
		builder.append("<sign>4ee7fe3ecdff57cc3236b58d549c027ea9c6a389</sign>");
		builder.append("</response>");
		
		
		XStream xStream = new XStream(new DomDriver()); 
		/************** 设置类别名，不设默认类全路径 ****************/
		xStream.alias("response", ResponseObject.class);
		xStream.alias("responseState", ResponseObject.class);
		xStream.alias("item", item.class);
		xStream.alias("pagingInfo", pagingInfo.class);
		/************** 添加不解析的字段 ****************/
		//xStream.omitField(ResponseObject.class, "data");
		//xStream.omitField(ResponseObject.class, "pagingInfo");
		xStream.omitField(ResponseObject.class, "sign");
		xStream.omitField(ResponseState.class, "errorList");
		
		
		System.out.println(((ResponseObject)(xStream.fromXML(builder.toString()))).toString());
	}

}
