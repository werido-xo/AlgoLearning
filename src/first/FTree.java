package first;
import java.util.Scanner;
import java.util.LinkedList;


public class FTree {

	public static final int ENTRY_SIZE = 26;
	
	static class Node {
		char val;
		int pass;
		int end;
		Node[] entry = new Node[ENTRY_SIZE];
		
		Node() {
			val = 0;
			pass = 0;
			end = 0;
		}
		
		Node(char val) {
			this.val = val;
			this.pass = 0;
			this.end = 0;
		}
	}
	
	public static void insert(Node head, String str) {
		if (head == null)
			return;
		
		int pos;
		char dat;
		Node pnode, cur;
		
		cur = head;
		cur.pass += 1;
		for(int id = 0; id < str.length(); id++) {
			dat = str.charAt(id);
			pos = dat - 0x41;
			
			if (cur.entry[pos] != null) {
				cur = cur.entry[pos];
			} else {
				pnode = new Node(dat);
				cur.entry[pos] = pnode;
				cur = pnode;
			}
			
			cur.pass += 1;
		}
		
		cur.end += 1;
	}
	
	public static void output_data(int repeat, char[] dat) {
		for(int index = 0; index < repeat; index++) {
			System.out.println(dat);
		}
	}
	
	public static void show_ftree(Node head) {
		if (head == null)
			return;
		
		int length, flags = 0;
		char[] path = new char[20];
		LinkedList<Node> nstack = new LinkedList<Node>();
		LinkedList<Integer> istack = new LinkedList<Integer>();
		
		char dat;
		int start = 0;
		length = 0;
		Node cur = head;
		
		while (true) {
			if (cur.end != 0 && start == 0)
				output_data(cur.end, path);		
			
			for(int index = start; index < ENTRY_SIZE; index++) {
				if (cur.entry[index] == null)
					continue;
				
				// insert data to the path
				dat = (char) (index + 0x41);
				path[length] = dat;
				
				nstack.addFirst(cur);
				istack.addFirst(index+1);				
				cur = cur.entry[index];

				flags = 1;
				break;
			}		
			
			if (flags == 1) {
				flags = 0;
				start  = 0;
				length += 1;
				continue;
			}
						
			length = (length != 0) ? (length - 1): 0;
			path[length] = 0;
			cur = nstack.pollFirst();
			if (cur == null)
				return;
			
			start = istack.pollFirst();
		}
	}
	
	
	/* check_end_num (head, str)
	 * @head: the root of the tree of prefix tree
	 * @str: the string to be check
	 * 
	 * @funcs: check the insert times of the str
	 */
	public static int check_end_num(Node head, String str) {
		if (head == null || str == null)
			return 0;
		
		int id, pos;
		Node cur = head;
		for(id = 0; id < str.length(); id++) {
			pos = str.charAt(id) - 0x41;
			cur = cur.entry[pos];
			if (cur == null)
				return 0;
		}
		
		return cur.end;
	}
	
	/* check_pass_num (head, str)
	 * @head: the root of the tree of prefix tree
	 * @str: the string to be check
	 * 
	 * @funcs: check the insert times of the str
	 */
	public static int check_pass_num(Node head, String str) {
		if (head == null || str == null)
			return 0;
		
		int id, pos;
		Node cur = head;
		for(id = 0; id < str.length(); id++) {
			pos = str.charAt(id) - 0x41;
			cur = cur.entry[pos];
			if (cur == null)
				return 0;
		}
		
		return cur.pass;
	}
	
	
	public static int delete_string(Node head, String str) {
		if (head == null)
			return -1;
		
		if (str == null)
			return 0;
		
		int num;
		num = check_end_num(head, str);
		if (num == 0)
			return -1;
		
		int id, pos, pass;
		Node cur = head;
		head.pass -= 1;
		for(id = 0; id < str.length(); id++) {
			pos = str.charAt(id) - 0x41;
			pass = (cur.entry[pos]).pass - 1;
			if (pass == 0) {
				cur.entry[pos] = null;
				return 1;
			}
			
			cur = cur.entry[pos];
			cur.pass = pass;
		}
		
		cur.end -= 1;
		return 1;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num, ret;
		Scanner input = new Scanner(System.in);
		
		System.out.printf("Please Input the number of String: ");
		num = input.nextInt();
		
		String dat;
		Node head = new Node();
		for(int index = 0; index < num; index++) {
			dat = input.next();
			
			insert(head, dat);
		}
		
		System.out.println("The number of string: " + head.pass);
		
		show_ftree(head);
		
		ret = check_end_num(head, "ABC");
		System.out.println("The number of string (ABC) inserted: " + ret);
		
		ret = check_pass_num(head, "ACD");
		System.out.println("The number of the string prefix with (ACD): " + ret);
		
		ret = delete_string(head, "ABC");
		System.out.println("The number of delete: " + ret);
		
		show_ftree(head);
		
		ret = check_end_num(head, "ABC");
		System.out.println("The number of string (ABC) inserted: " + ret);
		
		input.close();
	}

}
