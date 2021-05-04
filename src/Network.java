import java.util.Arrays;

public class Network {
	double[][] output; //layer, neuron
	double[][][] weights; //layer, neuron, previous neuron
	double[][] bias; //layer, neuron
	

	public final int[] NETWORK_LAYER_SIZES;
	public final int INPUT_SIZE;
	public final int OUTPUT_SIZE;
	public final int NETWORK_SIZE;
	
	/**
	 * Sets up the neural network by initializing the output, weights, and bias arrays.
	 * @param NETWORK_LAYER_SIZES an integer array of each layer's size.
	 */
	public Network(int ...NETWORK_LAYER_SIZES) {
		this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
		this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
		this.INPUT_SIZE= NETWORK_LAYER_SIZES[0]; //the first network layer is the input
		this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[NETWORK_SIZE-1]; //the last network layer is the output
		
		this.output = new double [NETWORK_SIZE][];
		this.weights = new double [NETWORK_SIZE][][];
		this.bias = new double [NETWORK_SIZE][];
		
		for(int i =0;i<NETWORK_SIZE;i++) {
			this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.bias[i] = new double[NETWORK_LAYER_SIZES[i]];
			
			//there are no weights for the input layer
			if(i>0) {
				this.weights[i] = new double[NETWORK_LAYER_SIZES[i]][NETWORK_LAYER_SIZES[i-1]];
			}
		}
		
	}
	//feed forward process
	public double[] calculate(double ... input) {
		if(input.length!=this.INPUT_SIZE)return null;
		this.output[0]=input;
		for(int layer =1;layer<NETWORK_SIZE;layer++) {
			for(int neuron =0;neuron<NETWORK_LAYER_SIZES[layer];neuron++) {
				double sum =bias[layer][neuron];
				for(int prevNeuron =0;prevNeuron<NETWORK_LAYER_SIZES[layer-1];prevNeuron++) {
					sum += weights[layer][neuron][prevNeuron]*output[layer-1][prevNeuron];
				}
				output[layer][neuron] = sigmoid(sum);
			}
		}
		return output[NETWORK_SIZE-1]; //final output
		
	}
	
	//sigmoid function used to compress values to between 0 and 1.
	private double sigmoid(double x) {
		return 1d/(1+Math.exp(-x));
	}
	
	public static void main(String[] args) {
		Network net = new Network(4,3,3,2);
		System.out.println(Arrays.toString(net.calculate(0.2,0.6,0.2,0.4)));
		
	}
	
	
}
