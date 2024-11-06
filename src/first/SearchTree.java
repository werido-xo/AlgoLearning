package first;
import java.util.LinkedList;
import java.util.Scanner;


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
	
	public static void main(String[] args) {
		Node head;
		String tree = new String("5(3(2,4),7(6,8)");
		
		head = stack_construct_tree(tree);
		show_tree_info(head);
		System.out.println();
		
		BST_Info info;
		info = isrBST(head);
		if (info.state) {
			System.out.println("The Tree is BST!");
		} else {
			System.out.println("The Tree isn't BST!");
		}
	}
}