package cn.wensiqun.asmsupport.client.operations;

public class NumberSample {
	
	public static String excepted(int t1, int t2, int t3, int t4, int t5, int t6, long t7) {
        StringBuilder sb = new StringBuilder();
        double ta = getTriangleAcreage(10, 9, 3);
        sb.append(ta) //* 2 + getSquareAcreage(8, 5) + getTixingArea(5, (short)4, (byte)3))
        .append('|');
		sb.append(ta > 10 && 20 > ta).append('|');
		sb.append(getSquareAcreage(8, 5) < 20 && 10 < ta).append('|');
		float tixingArea = getTixingArea(6, (short)3, (byte)2);
		sb.append(tixingArea == 9 && 10 != tixingArea).append('|');
		sb.append(tixingArea != 9 && 10 == tixingArea).append('|');
		//sb.append(getSquareAcreage(2, 4) << 2 | 5 ^ 2 >> 1 & 7 >>> 3).append('|');
		sb.append(getSquareAcreage(2, 4) << t1 | t2 ^ t3 >> t4 & t5 >>> t6).append('|');
		sb.append(t7 >> 1);
		return sb.toString();
	}

	public static double getTriangleAcreage(double a, double b, double c) {
		double m = (a + b + c) / 2;
		return Math.sqrt(m * (m - a) * (m - b) * (m - c));
	}

	public static long getSquareAcreage(int a, long b) {
		return a * b;
	}

	public static float getTixingArea(int up, short down, byte height) {
		return (up + down) * height / 2;
	}

	public static int getResult(int x, int y, int z) {
		return z + (8 + 2)*3*x/y%2-(5-3)*4/2;
	}

}
