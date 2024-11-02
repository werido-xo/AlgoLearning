package first;
import java.util.Scanner;
import java.util.Random;

public class hello {
	
	static class Node {
		int val;
		Node next;
		
		Node(int val) {
			this.val = val;
			this.next = null;
		}
	}
	
	public static void main(String[] args) {
		int num, val;
		Node header = null, cur=null;
		Scanner input = new Scanner(System.in);
		Random rand = new Random();
		
		System.out.print("Please intput the number: ");
		num = input.nextInt();
		
		header = new Node(0);
		cur = header;
		for (int index = 0; index < num; index++) {
			val = rand.nextInt(100);
			cur.next = new Node(val);
			header.val++;
			cur = cur.next;
		}
		
		cur = header.next;
		while (cur != null) {
			System.out.println(cur.val);
			cur = cur.next;
		}
		
		input.close();
	}
}
