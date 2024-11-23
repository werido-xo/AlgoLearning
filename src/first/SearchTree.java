package first;
import java.util.HashMap;
import java.util.LinkedList;


public class SearchTree {
	
	/* define for BST tree */
	static int prev = -1;
	
	static class Node {
		int val;
		Node left;
		Node right;
		
		Node(int val) {
			this.val = val;
			this.left = null;
			this.right = null;
		}
		
		Node() {
			left = null;
			right = null;
		}
	}
	
	static class BST_Info {
		boolean state;
		int max, min;
		
		BST_Info(boolean sta, int max, int min) {
			this.state = sta;
			this.max = max;
			this.min = min;
		}
	}
	
	
	static class BInfo {
		int depth;
		boolean state;
		
		BInfo(int depth, boolean state) {
			this.depth = depth;
			this.state = state;
		}
	}
	
	
	
	/* stack_construct_tree(tree)
	 * @tree: the string format tree representation
	 * 
	 * @funcs: construct the tree by self-managed stack
	 */
	public static Node stack_construct_tree(String tree) {
		if (tree == null)
			return null;
		
		char ch;		
		int pos, left;
		Node parent, cur;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		Node head = new Node();
		head.val = tree.charAt(0);
		head.left = null;
		head.right = null;
		
		left = 1;
		cur = head;
		for (pos = 1; pos < tree.length(); pos++) {
			ch = tree.charAt(pos);
			switch (ch) {
			case '(':
				stack.addFirst(cur);
				left = 1;
				break;
			case ')':
				stack.removeFirst();
				break;
			case ',':
				left = 2;
				break;
			default:
				cur = new Node();
				cur.val = ch;
				cur.left = null;
				cur.right = null;
				
				// determine left or right branch of the tree
				parent = stack.getFirst();
				if (left == 1) {
					parent.left = cur;
				} else {
					parent.right = cur;
				}
			}
		}
		
		return head;
	}
	
	/* show_tree_info(head)
	 * @head: the root node of the binary tree
	 * 
	 * @funcs: show the binary tree by brace format
	 */
	public static void show_tree_info(Node head) {
		if (head == null)
			return;
		
		/* 开始使用先序遍历访问二叉树 */
		System.out.printf("%c", head.val);
		System.out.printf("(");
		show_tree_info(head.left);
		System.out.printf(",");
		show_tree_info(head.right);
		System.out.printf(")");
	}
	

	public static boolean isBST(Node head) {
		if (head == null)
			return true;
		
		boolean ret;
		ret = isBST(head.left);
		if (!ret || (prev > head.val))
			return false;

		prev = head.val;
		ret = isBST(head.right);
		
		return ret;
	}
	
	public static BST_Info isrBST(Node head) {
		if (head == null)
			return null;
		
		int max = 0, min = 0;
		BST_Info left, right;
		left = isrBST(head.left);
		right = isrBST(head.right);
		
		/* situation 1: leaf nodes
		 * lowest level leaf-node operations 
		 */
		if (left == null && right == null) {
			max = head.val;
			min = head.val;
			return new BST_Info(true, max, min);
		}
		
		/* situation 2: have left nodes
		 * left branch operations 
		 */
		if (left != null) {
			if (left.state == false)
				return left;
			
			if (left.max <= head.val) {
				min = left.min;
				max = head.val;
			} else {
				return new BST_Info(false, 0, 0);
			}
		}
		
		/* situation 3: have right nodes
		 * right branch operations 
		 */
		if (right != null) {
			if (right.state == false)
				return right;
			
			if (right.min < head.val) {
				return new BST_Info(false, 0, 0);
			} else {
				max = right.max;
				if (left == null)
					min = head.val;
			}
		}
		
		System.out.printf("Node: %c. [%b, %c, %c] \n", head.val, true, max, min);
		return new BST_Info(true, max, min);
	}
	
	
	public static boolean isComplete(Node head) {
		if (head == null)
			return true;
		
		
		return false;
	}
	
	public static boolean isFull(Node head) {
		if (head == null)
			return true;
		
		int haschild = 1;			//full flags of node
		Node cur = head;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		System.out.printf("Visit History: ");
		stack.addFirst(head);
		while (stack.size() != 0) {
			cur = stack.pollFirst();
			
			// haschild表示是否遇到右节点没有双全
			if (haschild == 1) {
				if (cur.left != null && cur.right != null) {
					stack.addLast(cur.left);
					stack.addLast(cur.right);
					continue;
				}				
				
				if (cur.left == null && cur.right != null)
					return false;
				
				haschild = 0;
				if (cur.left != null)	
					stack.addLast(cur.left);
		
				continue;
			}
			
			if (cur.left != null || cur.right != null)
				return false;
		}
		
		return true;
	}
	
	
	public static BInfo bprocess(Node head) {
		if (head ==  null)
			return new BInfo(0, true);
		
		BInfo left = bprocess(head.left);
		BInfo right = bprocess(head.right);
		
		boolean state;
		state = left.state | right.state;
		if (Math.abs(left.depth - right.depth) > 1)
			state = false;
		
		return new BInfo(Math.max(left.depth, right.depth)+1, state);	
	}
	
	public static boolean isBalance(Node head) {
		if (head == null)
			return true;
		
		BInfo info = bprocess(head);
		System.out.println("Tree max depth: " + info.depth);
		
		return info.state;
	}
	
	
	public static Node lowestCP_process(Node head, Node node1, Node node2) {
		if (head == null || head == node1 || head == node2)
			return head;
		
		Node left, right;
		left = lowestCP_process(head.left, node1, node2);
		right = lowestCP_process(head.right, node1, node2);
		
		if ((left != null) && (right != null))
			return head;
		
		return (left != null)? left : right;
	}
	
	/* lowestCP(head, node1, node2)
	 * @head: the root of a binary tree
	 * @node1: input parameter 1: Node
	 * @node2: input parameter 2: Node
	 * 
	 * @funcs: found the lowest common parant of node1 and node2
	 */
	public static Node lowestCP(Node head, Node node1, Node node2) {
		if (head == null)
			return null;
		
		if (node1 == node2)
			return node1;
		
		Node common = lowestCP_process(head, node1, node2);
		System.out.println("The value of the common: " + common.val);
		
		return common;
	}
	
	
	public static Node getNode(Node head, int val) {
		if (head == null)
			return null;
		
		Node cur;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		stack.addFirst(head);
		while (stack.size() != 0) {
			cur = stack.pollFirst();
			if (cur.val == val)
				return cur;
			
			if (cur.left != null)
				stack.addLast(cur.left);
			
			if (cur.right != null)
				stack.addLast(cur.right);
		}
		
		return null;
	}
	
	
	
	public static Node lowestCP_Hashmap(Node head, Node node1, Node node2) {
		if (head == null)
			return null;
		
		if (node1 == node2)
			return node1;
		
		Node cur;
		HashMap<Node, Node> parentMap = new HashMap<Node, Node>();
		LinkedList<Node> stack = new LinkedList<Node>();
		
		/* 1. Construct the parent Hashmap for all nodes */
		stack.addFirst(head);
		parentMap.put(head, null);
		while (stack.size() != 0) {
			cur = stack.pollFirst();
			
			if (cur.left != null) {
				stack.addLast(cur.left);
				parentMap.put(cur.left, cur);
			}
			
			if (cur.right != null) {
				stack.addLast(cur.right);
				parentMap.put(cur.right, cur);
			}
		}
		
		/* 2. Build path for the node1 and node2 */
		LinkedList<Node> n1p = new LinkedList<Node>();
		LinkedList<Node> n2p = new LinkedList<Node>();
		
		cur = node1;
		Node parent = parentMap.get(cur);
		while(parent != null) {
			n1p.addFirst(parent);
			cur = parent;
			parent = parentMap.get(cur);
		}
		
		cur = node2;
		parent = parentMap.get(cur);
		while (parent != null) {
			n2p.addFirst(parent);
			cur = parent;
			parent = parentMap.get(cur);
		}
		
		/* 3. Find the lowest Common parent */
		Node p1, p2;
		p1 = n1p.pollFirst();
		p2 = n2p.pollFirst();
		while (p1 == p2) {
			parent = p1;
			p1 = n1p.pollFirst();
			p2 = n2p.pollFirst();
			
			if (p1 == null || p2 == null)
				break;
		}
		
		return parent;
	}
	
	
	public static void main(String[] args) {
		Node head;
		String tree = new String("5(3(2(A(B,0),1),4),7(6,8)");
		
		head = stack_construct_tree(tree);
		show_tree_info(head);
		System.out.println();
		
		
		Node node1 = getNode(head, 0 + 0x30);
		Node node2 = getNode(head, 4 + 0x30);
		if (node1 == null || node2 == null) {
			System.out.println("Failed to find the node");
			return;
		}
		
		System.out.printf("Find: node1.val = %c\n", node1.val);
		System.out.printf("Find: node2.val = %c\n", node2.val);
		
		Node common = lowestCP(head, node1, node2);
		System.out.printf("The common node val = %c \n", common.val);
		
		common = lowestCP_Hashmap(head, node1, node2);
		System.out.printf("The common node val = %c \n", common.val);
		
	}
}