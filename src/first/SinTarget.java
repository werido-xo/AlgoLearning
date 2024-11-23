package first;
import java.lang.Math;

public class SinTarget {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int uplim = 60, downlim = 30;
		
		int angle;
		float data, len, dist;
		for (angle = downlim; angle < uplim; angle += 1) {
			data = (float) angle / 57;
			len = (float) (7*Math.sin(data));
			dist = (float) (7*Math.cos(data));
			System.out.printf("angle = %d, length = %f, dist = %f \n", angle, len, dist);
		}
	}

}
