/*
 * Kevin Tieu

 * CS 146
 * Programming Assignment 3
 * Red Black Tree 
 */

/*
 * RedBlack Tree Class
 * Some code was taken from PA2 (Binary Search Tree)
 * 
 * This class has the majority of the code
 * Added LeftRotate, RightRotate, RBInsert, RBDelete
 * Made minor adjustments
 * 
 *  The Main method is also at the bottom so 
 *  that we can test the programs functionality
 *  
 *  
 *  PseudoCode implement from the CLRS
 *  Data Structures and Algorithm textbook
 */
import java.util.*;

public class RedBlackTree {

	Node nil = new Node();
	Node root = nil;

	String RED = "RED";
	String BLACK = "BLACK";

	/*
	 * The Style of traversal for the project
	 */
	public void inOrderTree(Node x) {
		// Node x not equal to null execute in order Tree Traversal
		if (x != null) {
			inOrderTree(x.leftChild);

			// if Node x priority key not equal to null execute
			// the print statement
			if (x.pKey != null)
				System.out.println(x.pKey.toString() + " ---> " + x.color + " ");
			inOrderTree(x.rightChild);
		}
	}

	/*
	 * Used Iterative TreeSearch over Tree-Search This method will help us
	 * search through the tree
	 */

	public Node iterativeTreeSearch(Node x, int pNum) {

		// root value is nil priority Number exist return x
		if (x == nil || pNum == x.pKey.getKey())
			return x;

		if (pNum < x.pKey.getKey()) {
			return iterativeTreeSearch(x.leftChild, pNum);
		} else
			return iterativeTreeSearch(x.rightChild, pNum);

	}

	/*
	 * MinTree helps locate the min value in the tree return x which represent
	 * the minimum value
	 */
	public Node minTree(Node x) {
		// while Node x left exist
		while (x.leftChild != nil)
			// set Node x to x's left child
			x = x.leftChild;
		return x;
	}

	/*
	 * MaxTree helps locate the max value in the tree Note this method is not
	 * called upon
	 */

	public Node maxTree(Node x) {
		while (x.rightChild != nil)
			x = x.rightChild;
		return x;
	}

	/*
	 * Successor will help find the successor of the current node NOTE: this
	 * method is never called upon in this program
	 */
	public Node treeSuccessor(Node x) {
		if (x.rightChild != nil) {
			return minTree(x.rightChild);
		}
		Node y = x.parent;
		while (y != nil && x == y.rightChild) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	/*
	 * LeftRotate method this method helps restructure the nodes
	 */
	public void leftRotate(RedBlackTree bsTree, Node x) {
		// Set Node y to x's right Child
		Node y = x.rightChild;

		// x's rightChild is equal to y's left Child
		x.rightChild = y.leftChild;

		// If statement checks if y's leftChild
		// is not equal to nil
		if (y.leftChild != bsTree.nil)
			y.leftChild.parent = x;

		// y's parent becomes x's parent
		y.parent = x.parent;

		// Check if x.parent is equal to nil
		if (x.parent == bsTree.nil)
			bsTree.root = y;
		else if (x == x.parent.leftChild)
			x.parent.leftChild = y;
		else
			x.parent.rightChild = y;

		// y. leftChild becomes x
		y.leftChild = x;

		// x.parent becomes y
		x.parent = y;
	}

	/*
	 * RightRotate method this method helps restructure the nodes
	 */
	public void rightRotate(RedBlackTree bsTree, Node x) {
		Node y = x.leftChild;
		x.leftChild = y.rightChild;
		if (y.rightChild != bsTree.nil)
			y.rightChild.parent = x;
		y.parent = x.parent;
		if (x.parent == bsTree.nil)
			bsTree.root = y;
		else if (x == x.parent.rightChild)
			x.parent.rightChild = y;
		else
			x.parent.leftChild = y;
		y.rightChild = x;
		x.parent = y;
	}

	/*
	 * insert method was used in the BST Program there was some minor adjustment
	 * Added a RBinsertFixup call to reorganized the RB tree
	 */
	public void insert(RedBlackTree bsTree, Node z) {
		Node y = bsTree.nil;
		Node x = bsTree.root;
		while (x != bsTree.nil) {
			y = x;
			if (z.pKey.getKey() < x.pKey.getKey())
				x = x.leftChild;
			else
				x = x.rightChild;
		}
		z.parent = y;
		if (y == bsTree.nil)
			bsTree.root = z;
		else if (z.pKey.getKey() < y.pKey.getKey())
			y.leftChild = z;
		else
			y.rightChild = z;
		z.leftChild = bsTree.nil;
		z.rightChild = bsTree.nil;
		z.color = RED;
		RBinsertFixUp(bsTree, z);
	}

	/*
	 * RBInsertFixup Method will reorganized the RB Tree There are also 3 cases
	 * that must be followed
	 */

	public void RBinsertFixUp(RedBlackTree bsTree, Node z) {

		while (z.parent.color == RED) {

			if (z.parent == z.parent.parent.leftChild) {
				Node y = z.parent.parent.rightChild;
				if (y.color == RED) {

					// CASE 1 Start
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
					// CASE 1 END

				} else {

					// CASE 2 Start
					if (z == z.parent.rightChild) {
						z = z.parent;
						leftRotate(bsTree, z);
						// CASE 2 End

					}

					// CASE 3 Start
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					rightRotate(bsTree, z.parent.parent);
					// CASE 3 End
				}

			}
			// this portion "same as then clause
			// swap "right with "left"
			else {
				Node y = z.parent.parent.leftChild;
				if (y.color == RED) {
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				} else {
					if (z == z.parent.leftChild) {

						z = z.parent;
						rightRotate(bsTree, z);
					}
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					leftRotate(bsTree, z.parent.parent);

				}
			}

		}
		// Declare tree root as black
		bsTree.root.color = BLACK;
	}

	/*
	 * Tree Delete will delete nodes using Transplant method Added RB-Delete
	 * Method to the end of this method
	 */
	public void treeDelete(RedBlackTree bsTree, Node z) {

		Node y = z;
		Node x;
		String originalColor = y.color;

		if (z.leftChild == nil) {
			x = z.rightChild;
			transplant(bsTree, z, z.rightChild);
		} else if (z.rightChild == bsTree.nil) {
			x = z.leftChild;
			transplant(bsTree, z, z.leftChild);
		} else {
			y = minTree(z.rightChild);
			originalColor = y.color;
			x = y.rightChild;

			if (y.parent == z) {
				x.parent = y;
			}

			else {
				transplant(bsTree, y, y.rightChild);
				y.rightChild = z.rightChild;
				y.rightChild.parent = z;
			}

			transplant(bsTree, z, y);
			y.leftChild = z.leftChild;
			y.leftChild.parent = z;
			y.color = z.color;
		}

		if (originalColor == BLACK) {
			RBDeleteFixUp(bsTree, x);
		}
	}

	/*
	 * RBDeleteFixup has 4 Cases that need to followed This method reorganizes
	 * the tree if the cases occur in the program
	 */

	public void RBDeleteFixUp(RedBlackTree bsTree, Node x) {

		while (x != bsTree.root && x.color == BLACK) {
			if (x == x.parent.leftChild) {

				// CASE 1 START
				Node w = x.parent.rightChild;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					leftRotate(bsTree, x.parent);
					w = x.parent.rightChild;
					// CASE 1 END

				}
				if (w.leftChild.color == BLACK && w.rightChild.color == BLACK) {

					// CASE 2 Start
					w.color = RED;
					x = x.parent;
					// CASE 2 End
				}

				// CASE 3 Start
				else if (w.rightChild.color == BLACK) {
					w.leftChild.color = BLACK;
					w.color = RED;
					rightRotate(bsTree, w);
					w = x.parent.rightChild;
					// CASE 3 End
				}

				// CASE 4 Start
				w.color = x.parent.color;
				x.parent.color = BLACK;
				w.rightChild.color = BLACK;
				leftRotate(bsTree, x.parent);
				x = bsTree.root;
				// CASE 4 End

			}

			// this portion "same as then clause
			// swap "right with "left"
			else {

				if (x == x.parent.rightChild) {
					Node w = x.parent.leftChild;
					if (w.color == RED) {
						w.color = BLACK;
						x.parent.color = RED;
						rightRotate(bsTree, x.parent);
						w = x.parent.leftChild;
					}

					if (w.rightChild.color == BLACK && w.leftChild.color == BLACK) {
						w.color = RED;
						x = x.parent;
					}

					else if (w.leftChild.color == BLACK) {
						w.rightChild.color = BLACK;
						w.color = RED;
						leftRotate(bsTree, w);
						w = x.parent.leftChild;
					}

					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.leftChild.color = BLACK;
					rightRotate(bsTree, x.parent);
					x = bsTree.root;

				}

			}

		}

		x.color = BLACK;

	}

	/*
	 * Transplant is the alternative to the deleting or replacing method for BST
	 * from textbook
	 */
	public void transplant(RedBlackTree bsTree, Node u, Node v) {

		if (u.parent == nil)
			bsTree.root = v;
		else if (u == u.parent.leftChild)

			u.parent.leftChild = v;

		// Minor adjust from the BST program
		else

			u.parent.rightChild = v;
		v.parent = u.parent;

	}

	/*
	 * This method prints the toString method from the Patient class
	 */
	public void RedBlackTreePrint(Node[] pat) {

		System.out.println();

		for (int i = 0; i < pat.length; i++) {

			System.out.println("/// " + pat[i].pKey.getName() + " ///" + pat[i].pKey.getKey() + " ///");

		}
		System.out.println();

	}

	/*
	 * Main Method This method is used for testing
	 */
	public static void main(String[] args) {
		//Create a new RBTree
		RedBlackTree RedBlackTree = new RedBlackTree();
		
		//Random Number Generator 
		Random random = new Random();
		
		//Scanner for user Input
		Scanner scanner = new Scanner(System.in);
		int input = 1;
		// Declare new Node with Parameters. Name and Random Priority Key
		//NOTE: Random Generator can take in smaller or larger values
		
		hashTable.insert(new Patient("Patient 1  ", random.nextInt(100)));
		hashTable.insert(new Patient("Patient 2  ", random.nextInt(100)));
		hashTable.insert(new Patient("Patient 3  ", random.nextInt(100)));
		hashTable.insert(new Patient("Patient 4  ", random.nextInt(100)));
		hashTable.insert(new Patient("Patient 5  ", random.nextInt(100)));
		

		// Adding the nodes into the Patient array
		Node p[] = { Node1, Node2, Node3, Node4, Node5 };

		System.out.println("Patient List");
		for (int i = 0; i < p.length; i++) {
			RedBlackTree.insert(RedBlackTree, p[i]);

		}
		RedBlackTree.RedBlackTreePrint(p);

		// The start of the testing
		while (input != 5) {

			System.out.println("1: Diplay Sorted Red Black Tree: ");
			System.out.println("2: Insert Patient: ");
			System.out.println("3: Delete Patient: ");
			System.out.println("4: Search Patient: ");
			System.out.println("5: Exit Program: ");
			System.out.println("Please Select Option: ");

			input = scanner.nextInt();

			// input 1 will Display tree
			if (input == 1) {
				// Displays entered number for feedback
				System.out.println("You Entered: " + input + "\n");
				RedBlackTree.inOrderTree(RedBlackTree.root);
				System.out.println();
			}

			// Input 2 will allow you to insert Patient
			else if (input == 2) {
				// Displays entered number for feedback
				
				System.out.println("You Entered: " + input + "\n"); 
				System.out.println("Enter Patient Name: "); 
				String name = scanner.next();
				int key = random.nextInt(500);
				Node pats = new Node(new Patient(name, key));
				RedBlackTree.insert(RedBlackTree, pats);
				System.out.println("\nPatient has been inserted: " + name);
				System.out.println("Priority Code: " + key + "\n");
				System.out.println("Red Black Tree \n");
				RedBlackTree.inOrderTree(RedBlackTree.root);
				System.out.println();
			}

			// Input 3 will allow you to delete Patient
			else if (input == 3) {
				// Displays entered number for feedback
				System.out.println("You Entered: " + input + "\n");
				RedBlackTree.inOrderTree(RedBlackTree.root);
				System.out.println("\nEnter Priority Number to Delete Patient");
				int delete = scanner.nextInt();//takes in user input
				//calls on treeDelete method to delete
				RedBlackTree.treeDelete(RedBlackTree, RedBlackTree.iterativeTreeSearch(RedBlackTree.root, delete));
				System.out.println("Patient with Priority Code: " + delete + " is deleted " + "\n");
				System.out.println("New Red Black Tree with DELETION: ");
				RedBlackTree.inOrderTree(RedBlackTree.root); 
				System.out.println();

			}

			// Input will Search for Patient will Priority Code and name
			// Input must match exactly what is in the hash index
			else if (input == 4) {
				// Displays entered number for feedback
				System.out.println("You Entered: " + input + "\n");
				System.out.println("Enter Priority Number: ");
				int num = scanner.nextInt();
				System.out.println("\nPatient is: " 
				+ RedBlackTree.iterativeTreeSearch(RedBlackTree.root, num).pKey.getName());
				System.out.println("Priority Number: " + num + "\n");
             
			}

			// Input 5 will close the program
			else {
				// Displays entered number for feedback
				System.out.println("You Entered: " + input + "\n");
				System.out.println("Thank you for your time");
				scanner.close();
			}

		}
	}
}
