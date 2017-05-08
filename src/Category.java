import java.awt.Color;
import java.io.*;

public class Category implements Serializable {
	
	private String name;
	private Color color;

	public Category(String name) {
		this.name = name;
		this.color = Color.black;
	}
	public Category(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
	public String toString() {
		return name;
	}
}
