package org.jboss.rusheye.suite;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "min", "max" })
public class Rectangle {
	private Point min;
	private Point max;

	@XmlElement
	@XmlJavaTypeAdapter(Adapter.class)
	public Point getMin() {
		return min;
	}

	public void setMin(Point point) {
		this.min = point;
	}

	@XmlElement
	@XmlJavaTypeAdapter(Adapter.class)
	public Point getMax() {
		return max;
	}

	public void setMax(Point point) {
		this.max = point;
	}

	private static class Adapter extends XmlAdapter<XmlPoint, Point> {
		@Override
		public XmlPoint marshal(Point v) throws Exception {
			XmlPoint r = new XmlPoint();
			r.x = new Double(v.getX()).intValue();
			r.y = new Double(v.getY()).intValue();
			return r;
		}

		@Override
		public Point unmarshal(XmlPoint v) throws Exception {
			return new Point(v.x, v.y);
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	private static class XmlPoint {
		@XmlAttribute
		protected int x;
		@XmlAttribute
		protected int y;
	}
}
