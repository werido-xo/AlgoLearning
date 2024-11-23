package first;
import java.util.Scanner;

public class NQueen {
	
	static int counter;
	
	
	public static boolean verify_avaliable(int level, int pos, int[] pbits) {
		int index, rows, cols;
		
		for (index = 0; index < level; index++) {
			rows = Math.abs(level - index);
			cols = Math.abs(pos - pbits[index]);
			
			if (cols == rows)
				return false;
		}
		
		return true;
	}
	
	
	public static int queen_process2(int queen, int level, int tbits, int[] pbits) {
		if (level == queen) {
			counter += 1;
			return 0;
		}
		
		int pos;
		boolean check;
		for (pos = 0; pos < queen; pos++) {
			if ((tbits & (0x1 << pos)) != 0)
				continue;
			
			check = verify_avaliable(level, pos, pbits);
			if (check == false)
				continue;
			
			pbits[level] = pos;
			tbits |= (0x1 << pos);
			
			queen_process2(queen, level + 1, tbits, pbits);
			
			pbits[level] = -1;
			tbits &= ~(0x1 << pos);
		}
		
		return 0;
	}
	
	
	public static int find_possible(int queen) {
		// 1. the max number of queen is 32
		if (queen > 32 || queen <= 0)
			return 0;
		
		// invalid input of the queen
		if (queen == 2 || queen == 3)
			return 0;
		
		int index;
		counter = 0;		
		int[] pbits = new int[queen];
		for (index = 0; index < queen; index++)
			pbits[index] = -1;
		
		int tbits = 0;
		queen_process2(queen, 0, tbits, pbits);
		
		System.out.println("The value of Possible: " + counter);
		return 0;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int queen;
		
		Scanner input = new Scanner(System.in);
		System.out.printf("Please input the number of Queen: \t ");
		queen = input.nextInt();
		
		System.out.println("The number of queen: " + queen);
		
		find_possible(queen);
		
		input.close();
	}
}
