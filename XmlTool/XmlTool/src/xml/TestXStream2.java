package xml;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * XStream对JSON转换的支持
 * @author X-rapido 
 * @description
 */
public class TestXStream2 {

	/**
	 * JavaBean 转换 JSON
	 * JettisonMappedXmlDriver
	 * @param person
	 */
	public static void BeanToJson(Person person){
		XStream xStreamJ = new XStream(new JettisonMappedXmlDriver());
		xStreamJ.setMode(XStream.NO_REFERENCES);
		xStreamJ.alias("person", Person.class);
		xStreamJ.alias("profile", Profile.class);
		xStreamJ.alias("address", Address.class);
		String xml = xStreamJ.toXML(person);
		System.out.println("JSON："+ xml);
	}
	
	/**
	 * JavaBean 转换 JSON
	 * JettisonMappedXmlDriver
	 * @param person
	 */
	public static void JsonToXml(Person person){
		XStream xStreamJ = new XStream(new JsonHierarchicalStreamDriver());
		xStreamJ.alias("person", Person.class);
		xStreamJ.alias("profile", Profile.class);
		xStreamJ.alias("address", Address.class);
		String xml = xStreamJ.toXML(person);
		System.out.println("JSON："+ xml);
	}
	
	/**
	 * JavaBean 转换 JSON 删除根节点
	 * JettisonMappedXmlDriver
	 * @param person
	 */
	public static void JsonToXml2(Person person){
		XStream xStreamJ = new XStream(new JsonHierarchicalStreamDriver());
		xStreamJ.alias("person", Person.class);
		xStreamJ.alias("profile", Profile.class);
		xStreamJ.alias("address", Address.class);
		 //删除根节点
		xStreamJ = new XStream(new JsonHierarchicalStreamDriver() {
	        public HierarchicalStreamWriter createWriter(Writer out) {
	            return new JsonWriter(out, JsonWriter.DROP_ROOT_MODE);
	        }
	    });
		String xml = xStreamJ.toXML(person);
		System.out.println("JSON："+ xml);
	}
	
	/**
	 * JSON转换Bean对象
	 * 
	 * JettisonMappedXmlDriver
	 */
	public static void JsonToBean(){
		String json = "{\"person\":{\"name\":\"X-rapido\",\"age\":22,\"profile\":{\"job\":\"软件工程师\",\"tel\":13051594850,\"remark\":\"备注说明\"},\"addlist\":[{\"address\":[{\"add\":\"郑州市经三路\",\"zipcode\":450001},{\"add\":\"北京市海淀区\",\"zipcode\":100000}]}]}}";
		XStream xStreamJ = new XStream(new JettisonMappedXmlDriver());
		
		xStreamJ.alias("person", Person.class);
		xStreamJ.alias("profile", Profile.class);
		xStreamJ.alias("address", Address.class);
		
		Person person = (Person) xStreamJ.fromXML(json);
		System.out.println(person.toString());
		
	}
	
	public static void main(String args[]) {
		Address address1 = new Address("郑州市经三路", "450001");
		Address address2 = new Address("北京市海淀区", "100000");
		List<Address> addList = new ArrayList<Address>();
		addList.add(address1);
		addList.add(address2);
		Profile profile = new Profile("软件工程师", "13051594850", "备注说明");
		Person person = new Person("X-rapido", "22", profile, addList);
		
		BeanToJson(person);		// JavaBean转换JSON JettisonMappedXmlDriver驱动
//		JsonToXml(person);		// JavaBean转换JSON JsonHierarchicalStreamDriver驱动
//		JsonToXml2(person);		// JavaBean转换JSON JsonHierarchicalStreamDriver驱动，删除根节点
//		JsonToBean();			// JSON转换Bean对象  JettisonMappedXmlDriver驱动
	}
	
}

