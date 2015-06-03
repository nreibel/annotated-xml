# annotated-xml
Build and read XML files using annotations

Built especially for parsing configuration files where the information is mostly divided into XML leafs (nodes with text content and no children) and collection of nodes

[See example](src/main/java/com/github/nreibel/xml/main)

- [x] Handle XML attributes
- [x] Handle XML leafs `<nodeName>Some text...</nodeName>`
- [x] Handle XML collections
- [x] Handle default value for attributes
- [ ] Handle default value for nodes
- [x] Uses Java annotations, very few code required
- [x] Can be used to write XML files back

## Example of an XML node with one attribute and one leaf
This XML sample :
````xml
<node name="myNode">
  <leaf>Some text...</leaf>
  <users>
    <user name="user1" />
    <user name="user2" />
    <user name="user3" />
  </users>
</node>
````
Can be represented by this Java class :
````java
public class Node extends AnnotatedXmlItem {

  @XmlAttribute
  private String name = "defaultName";
	
  @XmlChild(Factoy=XmlLeaf.Factory.class)
  private XmlLeaf leaf;
  
  @XmlChild(Factory=UserCollectionFactory.class)
  private XmlCollection<User> users;

  private Node(Element el, IXmlItem parent) {
    super(el, parent);
  }

  public static class Factory implements IXmlItemFactory<Node> {
    @Override public Node createItem(Element el, IXmlItem parent) {
      return new Node(el, parent);
    }
  }

  public class UserCollectionFactory implements IXmlItemFactory<XmlCollection<User>> {
    @Override public XmlCollection<User> createItem(Element el, IXmlItem parent) {
      return new XmlCollection<User>(User.Factory.class, el, parent);
    }
  }
}
````
