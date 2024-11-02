package first;
import java.util.LinkedList;
import java.util.HashMap;

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
	 * @funcs: 非递归形式的中序遍历一颗二叉树。先访问左子节点:自己:右子节点
	 *   都需要使用类似于二叉树的做分支分解的方法
	 */
	public static void inorder_traversal(Node head) {
		if (head == null)
			return;
		
		Node cur;
		LinkedList<Node> stack = new LinkedList<Node>();
		
		cur = head;
		while (cur != null) {
			stack.addFirst(cur);
			cur = cur.left;
		}
	}
	
	public static void preoder_traversal(Node head) {
		
	}
	
	public static void postoder_traversal(Node head) {
		
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
		String mytree = "A(B,C(E(G,H),F))";
		
		Node head = stack_construct_tree(mytree);
		
		show_tree_info(head);
	}

}
