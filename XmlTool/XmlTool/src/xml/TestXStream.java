package xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * XStream对XMl转换的支持
 * @author X-rapido 
 * @description
 */
public class TestXStream {

	public static void main(String args[]) {
		Address address1 = new Address("郑州市经三路", "450001");
		Address address2 = new Address("北京市海淀区", "100000");
		List<Address> addList = new ArrayList<Address>();
		addList.add(address1);
		addList.add(address2);
		Profile profile = new Profile("软件工程师", "13051594850", "备注说明");
		Person person = new Person("X-rapido", "22", profile, addList);

		// 转换装配
//		XStream xStream = new XStream(new StaxDriver()); XppDriver 
		XStream xStream = new XStream(new DomDriver());

		/************** 设置类别名，不设默认类全路径 ****************/
		xStream.alias("PERSON", Person.class);
		xStream.alias("PROFILE", Profile.class);
		xStream.alias("ADDRESS", Address.class);
		
		String xml = xStream.toXML(person);
		System.out.println("----------------第1次输出, 设置类别名---------------- \n"+ xml + "\n");

		/************* 设置类成员的别名 ***************/
		// 设置Person类的name成员别名Name
		xStream.aliasField("Name", Person.class, "name");
		/*
		 * [注意] 设置Person类的profile成员别名PROFILE,这个别名和Profile类的别名一致,
		 * 这样可以保持XStream对象可以从profile成员生成的xml片段直接转换为Profile成员,
		 * 如果成员profile的别名和Profile的别名不一致,则profile成员生成的xml片段不可
		 * 直接转换为Profile对象,需要重新创建XStream对象,这岂不给自己找麻烦?
		 */
		xStream.aliasField("PROFILE", Person.class, "profile");
		xStream.aliasField("ADDLIST", Person.class, "addlist");
		xStream.aliasField("Add", Address.class, "add");
		xStream.aliasField("Job", Profile.class, "job");
		
		String xml2 = xStream.toXML(person);
		System.out.println("----------------第2次输出, 设置类、字段别名---------------- \n"+ xml2 + "\n");

		
		/******* 设置类成员为xml一个元素上的属性 *******/
		xStream.useAttributeFor(Address.class, "zipcode");
		/************* 设置属性的别名 ***************/
		xStream.aliasAttribute(Address.class, "zipcode", "Zipcode");
		
		String xml3 = xStream.toXML(person);
		System.out.println("----------------第3次输出, 设置类、字段别名，并在xml字段节点上增加属性---------------- \n"+ xml3 + "\n");

		
		/************* 将xml转为java对象 ******× ****/
		 String person_xml = "<PERSON>\n" +  
			                "  <Name>熔岩</Name>\n" +  
			                "  <age>27</age>\n" +  
			                "  <PROFILE>\n" +  
			                "    <Job>软件工程师</Job>\n" +  
			                "    <tel>13512129933</tel>\n" +  
			                "    <remark>备注说明</remark>\n" +  
			                "  </PROFILE>\n" +  
			                "  <ADDLIST>\n" +  
			                "    <ADDRESS Zipcode=\"450001\">\n" +  
			                "      <Add>郑州市经三路</Add>\n" +  
			                "    </ADDRESS>\n" +  
			                "    <ADDRESS Zipcode=\"710002\">\n" +  
			                "      <Add>西安市雁塔路</Add>\n" +  
			                "    </ADDRESS>\n" +  
			                "  </ADDLIST>\n" +  
			                "</PERSON>";  
		String profile_xml = "<PROFILE>\n" +  
			                "    <Job>软件工程师</Job>\n" +  
			                "    <tel>13512129933</tel>\n" +  
			                "    <remark>备注说明</remark>\n" +  
			                " </PROFILE>";  
	    String address_xml = "<ADDRESS Zipcode=\"710002\">\n" +  
			                "      <Add>西安市雁塔路</Add>\n" +  
			                " </ADDRESS>";  

		// 同样使用上面的XStream对象将xml转换为Java对象
		System.out.println(xStream.fromXML(person_xml).toString());
		System.out.println(xStream.fromXML(profile_xml).toString());
		System.out.println(xStream.fromXML(address_xml).toString());
		
	}
	
}

