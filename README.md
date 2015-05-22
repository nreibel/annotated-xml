Let's take this XMl example :
<code>
```
<node uniqueId="node#1">
  <leaf>Some text...</leaf>
</node>
```
</code>
It can be represented by the code below : 
<code>
```
@XmlLeaf
private String leaf = "DefaultValue";

@XmlAttribute(Required=false)
private String uniqueId = UUID.randomUUID().toString();

public MyNode(IXmlItem parent, Element el) throws AnnotationParsingException, NodeNotFoundException, AttributeNotFoundException {
	super(parent);
	this.doInitFields(el);
}
```
</code>
It is up to the developper to navigate through the XML elements to pass the correct XML element to the constructor, for example :
<code>
```
Element el = AbstractXmlItem.getFirstElement(rootNode, "node_name");
MyNode node = new MyNode(null, el);
```
</code>
