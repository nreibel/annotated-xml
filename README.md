# annotated-xml
Read XML files using annotations

Built especially for parsing configuration files where the information is mostly divided into XML leafs (nodes with text content and no children) and collection of nodes

[See example](src/main/java/com/github/nreibel/xml/main)

- [x] Handle XML attributes
- [x] Handle XML leafs `<nodeName>Some text...</nodeName>`
- [x] Handle XML collections
- [x] Handle default value for attributes
- [ ] Handle default value for nodes
- [x] Uses Java annotations, very few code required