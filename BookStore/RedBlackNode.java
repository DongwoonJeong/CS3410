import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class RedBlackNode {
	public final static String BLACK = "BLACK";
	public final static String RED = "RED";

	private RedBlackNode parent;
	private RedBlackNode left = null;
   
   
	private RedBlackNode right = null;

	private int key;
	private String color;
	private ArrayList<Object> dataList;
	
	
	public RedBlackNode(int key) {
		this.key = key;
		this.color = RED;
		dataList = new ArrayList<Object>();
	}

	public int getSize(){
		return dataList.size();
	}
	public Object getData() {
		return dataList;
	}

	public void setData(Object data) {
		dataList.add(data);

	}

	public RedBlackNode getLeft() {
		return left;
	}

	public void setLeft(RedBlackNode left) {
		this.left = left;
	}

	public RedBlackNode getRight() {
		return right;
	}

	public void setRight(RedBlackNode right) {
		this.right = right;
	}

	public RedBlackNode getParent() {
		return parent;
	}

	public void setParent(RedBlackNode parent) {
		this.parent = parent;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
