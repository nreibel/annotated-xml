import org.w3c.dom.Document;

import com.github.nreibel.xml.XmlCollection;
import com.github.nreibel.xml.AnnotatedXmlItem;
import com.github.nreibel.xml.XmlLeaf;
import com.github.nreibel.xml.IXmlItem;
import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;


public class XmlRoot extends AnnotatedXmlItem {

	private final Document xmlDoc;

	@XmlAttribute
	private String version = "default";

	@XmlChild(Factory=Child.FactoryImpl.class)
	private IXmlItem child;

	@XmlChild(Factory=XmlLeaf.FactoryImpl.class)
	private XmlLeaf leaf;
	
	@XmlChild(Factory=XmlCollection.FactoryImpl.class)
	private XmlCollection collection;

	public XmlRoot(Document doc) {
		super(null);
		xmlDoc = doc;
	}

	public String getVersion() {
		return version;
	}

	public IXmlItem getChild() {
		return child;
	}

	public String getLeaf() {
		return leaf.getValue();
	}
	
	public XmlCollection getCollection() {
		return collection;
	}

	@Override
	public String getNodeName() {
		return "root";
	}

	@Override
	public Document getDocument() {
		return xmlDoc;
	}
}
