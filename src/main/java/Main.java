import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.github.nreibel.xml.XmlLeaf;


public class Main {

	public static void main(String[] args) throws Exception {
		File f = new File("sample.xml");

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		XmlRoot root = new XmlRoot(doc);
		root.doInitFields(doc.getDocumentElement());

		System.out.println(root);

		for(XmlLeaf it : root.getCollection().getChildrenByTag("item")) {
			System.out.println(it);
		}
	}
}
