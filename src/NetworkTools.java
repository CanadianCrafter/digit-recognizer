
public class NetworkTools {
	
	public static double[]createArray(int size, double initValue){
		if(size<1)return null;
		double[] arr = new double[size];
		for(int i =0;i<size;i++) {
			arr[i]=initValue;
		}
		return arr;
	}
	
	public static double[] createRandomArray(int size, double lowerBound, double upperBound) {
		if(size<1)return null;
		double[] arr = new double[size];
		for(int i =0;i<size;i++) {
			arr[i]=randomValue(lowerBound, upperBound);
		}
		return arr;
	}
	
	public static double[][] createRandomArray(int sizeX, int sizeY, double lowerBound, double upperBound) {
		if(sizeX<1||sizeY<1)return null;
		double[][] arr = new double[sizeX][sizeY];
		for(int i =0;i<sizeX;i++) {
			arr[i]=createRandomArray(sizeY, lowerBound, upperBound);
		}
		return arr;
	}
	
	
	
	
	
	public static double randomValue(double lowerBound, double upperBound) {
		return Math.random()*(upperBound-lowerBound)+lowerBound;
	}
	
	
	public static int indexOfMax(double[] values) {
		double max = 0;
		int index=0;
		for(int i =0;i<values.length;i++) {
			if(values[i]>max) {
				max=values[i];
				index=i;
			}
					}
		return index;
	}
	
}
