import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
public class RedBlackTree{

	private RedBlackNode root;
	private final static String BLACK = "BLACK";
	private final static String RED = "RED";
	int check = 0;

	/**
	 * method to get root
	 * 
	 * @return root
	 */
	public RedBlackNode getRoot() {
		while (root.getParent() != null) {
			root = root.getParent();
		}
		return root;
	}

	/**
	 * method to get granpa node
    * @param node
	 * @return granpa node or null
	 */
	private RedBlackNode getGrandpa(RedBlackNode node) {
		if ((node != null) && (node.getParent() != null))
			return node.getParent().getParent();
		else
			return null;
	}

	/**
	 * method to get uncle node
	 * 
	 * @param node
	 * @return uncle node or null
	 */
	private RedBlackNode getUncle(RedBlackNode node) {
		RedBlackNode temp = getGrandpa(node);
		if (temp == null)
			return null;
		if (node.getParent() == temp.getLeft()) {
			return temp.getRight();
		} else
			return temp.getLeft();
	}

	/**
	 * Before make RedBlackTree,declear relationtionship between BinarySearch and node.
	 * 
	 * @param node
	 */
	public void insert(int key, Object data) throws Exception {

		RedBlackNode newNode;
		RedBlackNode exist;

		if (root == null) {
			root = makeNewNode(key, data);
			root.setParent(null);
			mkRB_Start(root);
		} else {
			RedBlackNode temp = getRoot();
			RedBlackNode tempParent = getRoot();
			// screening test prevent duplication .
			exist = findNodeExist(key);
			if (exist == null) {

				while (temp != null) {
					tempParent = temp;
					if (key < temp.getKey()) {
						temp = temp.getLeft();
					} else {
						temp = temp.getRight();
					}
				}
				if (key == tempParent.getKey()) {
					throw new Exception(
							getClass().getName()
									+ "-insert error- ");
				} else if (key < tempParent.getKey()) {
					newNode = makeNewNode(key, data);
					tempParent.setLeft(newNode);
					newNode.setParent(tempParent);
					mkRB_Start(newNode);
				} else if (key > tempParent.getKey()) {
					newNode = makeNewNode(key, data);
					tempParent.setRight(newNode);
					newNode.setParent(tempParent);
					mkRB_Start(newNode);
				}
			} else {
				exist.setData(data);
			}
		}
	}

	/**
	 * If there is no node in tree, make node.
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	private RedBlackNode makeNewNode(int key, Object data) {
		RedBlackNode newNode = new RedBlackNode(key);
		newNode.setData(data);
		return newNode;
	}

	/**
	 * BinarySearch Method to find node with the key value 
	 * 
	 * @param key
	 * @return
	 */
	private RedBlackNode findNodeExist(int key) {

		RedBlackNode temp = getRoot();
		while (temp != null) {

			if (key == temp.getKey()) {
				return temp;
			}
			if (key < temp.getKey()) {
				if (temp.getLeft() == null)
					return null;
				temp = temp.getLeft();

			} else {
				if (temp.getRight() == null)
					return null;
				temp = temp.getRight();
			}
		}
		return null;

	}

	/**
	 * Method to get find BinarySearch type key value.
	 * 
	 * 
	 * @param key
	 * @return
	 */
	private RedBlackNode findNode(int key) throws Exception {

		RedBlackNode temp = getRoot();
		while (temp != null) {

			if (key == temp.getKey()) {
				return temp;
			}

			if (key < temp.getKey()) {
				if (temp.getLeft() != null) {
					temp = temp.getLeft();
				} else {
					return temp;
				}

			} else {
				if (temp.getRight() != null) {
					temp = temp.getRight();
				} else {
					try {
						while (key > temp.getKey()) {
							temp = temp.getParent();
						}
					} catch (Exception e) {
						throw new Exception(getClass().getName()
								+ "-findNode-Could not find purchase amount larger than typed amount ");
					}

					return temp;

				}

			}
		}
		return temp;

	}

	/**
	 * Method that bring key from outside and access to the datalist. If there is no suitable value, the key will be close value, not return null) 
	 * 
    * 
	 * @param key
	 * @return
	 */
	public Object findData(int key) throws Exception {
		if (key < 0)
			throw new Exception(getClass().getName()
					+ "-findDataAdjacency- Total amount cannot be negative.");
		RedBlackNode temp = findNode(key);
		if (temp != null) {
			return temp.getData();
		} else {
			return null;
		}
	}

	/**
	 * RedBlackTree start
     1. root color set to black
	 * 
	 * @param node
	 */
	private void mkRB_Start(RedBlackNode node) {
		if (node.getParent() == null) {
			node.setColor(BLACK);
		} else {
			mkRB_PB(node);
		}
	}

	/**
	 * 2. 
	 * 
	 * @param node
	 */
	private void mkRB_PB(RedBlackNode node) {
		if (node.getParent().getColor() == BLACK) {
			return;
		} else
			mkRB_PR_UR(node);
	}

	/**
	 * 3. PR = parent red UR = uncle red
	 * 
	 * 
	 * 
	 * @param node
	 */
	private void mkRB_PR_UR(RedBlackNode node) {
		RedBlackNode grandpa = getGrandpa(node);
		RedBlackNode uncle = getUncle(node);

		// operate recoloring and then operate again from grandpa
		if ((uncle != null) && (uncle.getColor() == RED)) {

			node.getParent().setColor(BLACK);
			uncle.setColor(BLACK);
			grandpa.setColor(RED);

			mkRB_Start(grandpa);
		} else {
			mkRB_PR_UB(node);
		}
	}

	/**
	 * 
	 * 4. PR = parent red UB = uncle Black 
  	 * @param node
	 */
	private void mkRB_PR_UB(RedBlackNode node) {

		RedBlackNode grandpa = getGrandpa(node);

		if ((node.getParent().getRight() == node)
				&& (node.getParent() == grandpa.getLeft())) {
			change_left(node);
			node = node.getLeft();

		} else if ((node.getParent().getLeft() == node)
				&& (node.getParent() == grandpa.getRight())) {
			change_right(node);
			node = node.getRight();
		}

		grandpa = getGrandpa(node);

		node.getParent().setColor(BLACK);
		grandpa.setColor(RED);

		if (node.getParent().getLeft() == node)
			rotate_right(node);
		else
			rotate_left(node);

	}

	/**
	 * If the shape is '>', refactoring to new shape
	 * 
	 * @param node
	 */
	private void change_left(RedBlackNode node) {
		RedBlackNode grandpa = getGrandpa(node);
		RedBlackNode parent = node.getParent();
		RedBlackNode nl = node.getLeft();

		grandpa.setLeft(node);
		node.setParent(grandpa);
		node.setLeft(parent);
		parent.setParent(node);
		parent.setRight(nl);

		if (nl != null) {
			nl.setParent(parent);
		}

	};

	/**
	 * If the shape is ' < ', refactoring to new shape
	 * 
	 * @param node
	 */
	private void change_right(RedBlackNode node) {
		RedBlackNode grandpa = getGrandpa(node);
		RedBlackNode parent = node.getParent();
		RedBlackNode nr = node.getRight();

		grandpa.setRight(node);
		node.setParent(grandpa);
		node.setRight(parent);
		parent.setParent(node);
		parent.setLeft(nr);

		if (nr != null) {
			nr.setParent(parent);
		}

	};

	/**
	 * When rotating to the left, process node to right down side, and PR UB, refactoring.
	 * 
	 * @param node
	 */
	private void rotate_left(RedBlackNode node) {

		RedBlackNode grandpa = getGrandpa(node);
		RedBlackNode parent = node.getParent();

		grandpa.setRight(parent.getLeft());
		if (parent.getLeft() != null)
			parent.getLeft().setParent(grandpa);

		if (grandpa.getParent() == null) {
			parent.setParent(grandpa.getParent());
		} else if (grandpa.getParent() != null
				&& grandpa.getParent().getRight() == grandpa) {
			grandpa.getParent().setRight(parent);
			parent.setParent(grandpa.getParent());
		} else if (grandpa.getParent() != null
				&& grandpa.getParent().getLeft() == grandpa) {
			grandpa.getParent().setLeft(parent);
			parent.setParent(grandpa.getParent());
		}

		parent.setLeft(grandpa);
		grandpa.setParent(parent);

	};

	/**
	 * When rotating to right, process node ot left down side, and PR UB, refactoring.
	 * 
	 * @param node
	 */
	private void rotate_right(RedBlackNode node) {

		RedBlackNode grandpa = getGrandpa(node);
		RedBlackNode parent = node.getParent();
		grandpa.setLeft(parent.getRight());

		if (parent.getRight() != null)
			parent.getRight().setParent(grandpa);

		if (grandpa.getParent() == null) {
			parent.setParent(grandpa.getParent());
		} else if (grandpa.getParent() != null
				&& grandpa.getParent().getRight() == grandpa) {
			grandpa.getParent().setRight(parent);
			parent.setParent(grandpa.getParent());

		} else if (grandpa.getParent() != null
				&& grandpa.getParent().getLeft() == grandpa) {
			grandpa.getParent().setLeft(parent);
			parent.setParent(grandpa.getParent());
		}

		parent.setRight(grandpa);
		grandpa.setParent(parent);

	};

	/**
	 * Function for realization rank
	 * 
	 * @param currentNode
	 * @param targetNode
	 * @return
	 */
	private int getRank(RedBlackNode currentNode,
			RedBlackNode targetNode) throws Exception {
		// If It is centerNode, delete left side size of nodes.
		if (currentNode == targetNode) {
			return size(currentNode) - size(currentNode.getLeft())
					- currentNode.getSize() + 1;
		} else if (currentNode.getKey() < targetNode.getKey()) {
			return getRank(currentNode.getRight(), targetNode);
		} else {
			return size(currentNode.getRight()) + currentNode.getSize()
					+ getRank(currentNode.getLeft(), targetNode);
		}
	}

	/**
	 * Function of return rank. If the node of key is not exist, return rank with higher close node)
	 * 
	 * @param key
	 * @return
	 */
	public int getRank(int key) throws Exception {
		RedBlackNode temp = findNode(key);
		return getRank(getRoot(), temp);
	}

	/**
	 * Function of searching data with input rank
    * 
	 * @param rank
	 * @return
	 * @throws Exception
	 */
	public Object findDataByRank(int rank) throws Exception {

		RedBlackNode temp = findNodeByRank(getRoot(), rank);
		if (temp != null) {
			return temp.getData();
		} else {
			return null;
		}
	}

	/**
	 * Function of searching with realization of recursively rank
    * 
	 * @param node
	 * @param rank
	 * @return
	 * @throws Exception
	 */
	private RedBlackNode findNodeByRank(RedBlackNode node, int rank)
			throws Exception {
		if (node == null) {
			throw new Exception(getClass().getName()
					+ "-findNodeByRank- Value is Null after searching.");
		}
		int tempRank = getRank(getRoot(), node);
		if (rank >= tempRank && rank < tempRank + node.getSize())
			return node;
		else if (rank > getRank(getRoot(), node)) {
			return findNodeByRank(node.getLeft(), rank);
		} else {
			return findNodeByRank(node.getRight(), rank);
		}
	}

	/**
	 * Return entire size of tree 
	 * @return
	 */
	public int size() {
		return (size(root));
	}

	/**
	 * Return the value of adding node and node size of children
  	 * 
	 * @param node
	 * @return
	 */
	private int size(RedBlackNode node) {
		if (node == null)
			return 0;
		else {
			return (size(node.getLeft()) + node.getSize() + size(node
					.getRight()));
		}
	}

}
