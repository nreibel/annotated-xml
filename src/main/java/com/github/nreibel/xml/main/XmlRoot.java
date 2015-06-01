package com.github.nreibel.xml.main;

import org.w3c.dom.Document;

import com.github.nreibel.xml.AnnotatedXmlItem;
import com.github.nreibel.xml.IXmlItem;
import com.github.nreibel.xml.XmlCollection;
import com.github.nreibel.xml.annotations.XmlAttribute;
import com.github.nreibel.xml.annotations.XmlChild;
import com.github.nreibel.xml.impl.LeafCollectionFactory;
import com.github.nreibel.xml.impl.XmlLeaf;


public class XmlRoot extends AnnotatedXmlItem {

	private final Document xmlDoc;

	@XmlAttribute
	private String version = "default";

	@XmlAttribute
	private String name = "defaultName";

	@XmlChild(Factory=Child.Factory.class)
	private IXmlItem child;

	@XmlChild(Factory=XmlLeaf.Factory.class)
	private XmlLeaf leaf;

	@XmlChild(Factory=LeafCollectionFactory.class)
	private XmlCollection<XmlLeaf> leafCollection;

	@XmlChild(Factory=ChildCollectionFactory.class)
	private XmlCollection<Child> childCollection;

	public XmlRoot(Document doc) {
		super(doc.getDocumentElement(), null);
		xmlDoc = doc;
	}

	@Override
	public Document getDocument() {
		return xmlDoc;
	}
}
