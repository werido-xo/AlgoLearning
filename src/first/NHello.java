package first;
import java.util.LinkedList;
//import java.util.HashMap;

public class NHello {
	static class Node {
		int val;
		Node left;
		Node right;
		
		Node() {
			val = 0;
			left = null;
			right = null;
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
	
	
	public static void push_left(LinkedList<Node> stack, Node node, int ptflag) {
		while (node != null) {
			if (ptflag == 1) 
				System.out.printf("%c", node.val);
			stack.addFirst(node);
			node = node.left;
		}
	}
	
	/* inorder_traversal(head)
	 * @head: the root node of the two-branch tree
	 * 
	 * @funcs: non-recursively traversal a binary tree. Stack base operations as 
	 *  follows:
	 *   1. print all left nodes and push to the stack
	 *   2. pop a data from stack, and do same thing for the data.right
	 */
	public static void inorder_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur = head;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		push_left(stack, head, 0);
		while (stack.size() != 0) {
			cur = stack.pollFirst();
			System.out.printf("%c", cur.val);
			
			if (cur.right == null)
				continue;
			
			push_left(stack, cur.right, 0);
		}
	}
	
	public static void preorder_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur = head;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		push_left(stack, head, 1);
		while (stack.size() != 0) {
			cur = stack.pollFirst();
			if (cur.right == null)
				continue;
			
			push_left(stack, cur.right, 1);
		}		
	}
	
	public static void postorder_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur = head, prev = null;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		push_left(stack, cur, 0);
		while (stack.size() != 0) {
			cur = stack.getFirst();
			
			if (cur.right != null && cur.right != prev)
				push_left(stack, cur.right, 0);
			
			cur = stack.pollFirst();
			System.out.printf("%c", cur.val);
			prev = cur;
		}
	}
	
	
	public static void level_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur;
		LinkedList<Node> fifo = new LinkedList<Node>();
		
		fifo.addFirst(head);
		while (fifo.size() != 0) {
			cur = fifo.pollLast();
			System.out.printf("%c", cur.val);
			
			if (cur.left != null)
				fifo.addFirst(cur.left);
			if (cur.right != null)
				fifo.addFirst(cur.right);
		}
	}
	
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String mytree = "A(B(M,N),C(E(G,H),F))";
		
		Node head = stack_construct_tree(mytree);
		
		show_tree_info(head);
		System.out.println();
		
		System.out.printf("The preoder output: \t");
		preorder_traversal(head);
		System.out.println();
		
		System.out.printf("The inorder output: \t");
		inorder_traversal(head);
		System.out.println();
		
		System.out.printf("The postorder output: \t");
		postorder_traversal(head);
		System.out.println();
		
		System.out.printf("The based output: \t");
		level_traversal(head);
		System.out.println();
		
	}

}
