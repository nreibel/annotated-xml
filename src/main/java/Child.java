import com.github.nreibel.xml.AnnotatedXmlItem;
import com.github.nreibel.xml.Descriptor;
import com.github.nreibel.xml.IXmlItem;
import com.github.nreibel.xml.annotations.XmlAttribute;


public class Child extends AnnotatedXmlItem {

	@XmlAttribute
	private String name = "defaultName";

	public Child(IXmlItem parent) {
		super(parent);
	}

	public static class FactoryImpl implements Descriptor<Child> {
		@Override
		public Child createItem(IXmlItem parent) {
			return new Child(parent);
		}
	}
}
