/*
 * Node Class is a helper class of BST.Java
 * used to declare node values
 */

public class Node {

	Patient pKey;
	Node leftChild;
	Node rightChild;
	Node parent;
	String color;

	public Node() {
		this.pKey = null;
		leftChild = null;
		rightChild = null;
		parent = null;
		 color = "";
	}

	public Node(Patient pKey) {
		this.pKey = pKey;
		leftChild = null;
		rightChild = null;
		parent = null;
		color = "";
	}
}
             