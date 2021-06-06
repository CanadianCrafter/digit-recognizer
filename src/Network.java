import java.util.Arrays;

public class Network {
	public double[][] output; //layer, neuron
	public double[][][] weights; //layer, neuron, previous neuron
	public double[][] bias; //layer, neuron
	public double[][] errorSignal; //layer, neuron
	public double[][] outputDerivative; //layer, neuron

	public final int[] NETWORK_LAYER_SIZES; //sizes of each layer
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
		this.errorSignal = new double [NETWORK_SIZE][];
		this.outputDerivative = new double [NETWORK_SIZE][];
		
		for(int i =0;i<NETWORK_SIZE;i++) {
			this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i], 0.2, 0.8);
			this.errorSignal[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.outputDerivative[i] = new double[NETWORK_LAYER_SIZES[i]];
			
			//there are no weights for the input layer
			if(i>0) {
				this.weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i],NETWORK_LAYER_SIZES[i-1], -0.5,0.5);
			}
		}
		
	}
	
	/**
	 * The feed forward process
	 * @param input The values for the first layer.
	 * @return a double array containing the output for the final layer.
	 */
	public double[] feedForward(double... input) {
		if (input.length != this.INPUT_SIZE) return null;
		this.output[0] = input;
		for (int layer = 1; layer < NETWORK_SIZE; layer++) {
			for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {
				double sum = bias[layer][neuron];
				for (int prevNeuron = 0; prevNeuron < NETWORK_LAYER_SIZES[layer - 1]; prevNeuron++) {
					sum += weights[layer][neuron][prevNeuron] * output[layer - 1][prevNeuron];
				}
				output[layer][neuron] = sigmoid(sum);
				
				//calculates the derivative of the output
				outputDerivative[layer][neuron] = output[layer][neuron]*(1-output[layer][neuron]); 
			}
		}
		return output[NETWORK_SIZE - 1]; 

	}
	
	/**
	 * Finds the error signal for each neuron based on the back propagation algorithm.
	 * @param target is the target values for the output layer.
	 */
	public void backpropError(double[] target) {
		//Calculate the error signal for output layer. 
		//error = output derivative x (final output - target value)
		for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[NETWORK_SIZE - 1]; neuron++) {
			errorSignal[NETWORK_SIZE - 1][neuron] = outputDerivative[NETWORK_SIZE - 1][neuron]
					* (output[NETWORK_SIZE - 1][neuron] - target[neuron]);
		}
		//Recursively calculate the error signal for each hidden layer. 
		//error = output derivative x sum of (weights x error signals) from current neuron to each neuron in the next neuron.
		//Next neuron because back propagation starts from the output layer and goes through each layer until the first hidden layer.
		for (int layer = NETWORK_SIZE - 2; layer > 0; layer--) {
			for (int neuron = 0; neuron < NETWORK_LAYER_SIZES[layer]; neuron++) {

				for (int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZES[layer + 1]; nextNeuron++) {
					errorSignal[layer][neuron] += weights[layer + 1][nextNeuron][neuron]
							* errorSignal[layer + 1][nextNeuron];
				}

				errorSignal[layer][neuron] *= outputDerivative[layer][neuron];

			}
		}
		
	}
	
	/**
	 * Implementation of the sigmoid function which compresses values to between 0 and 1.
	 * @param x X value.
	 * @return double value of sigmoid function at x.
	 */
	private double sigmoid(double x) {
		return 1d/(1+Math.exp(-x));
	}
	
	public static void main(String[] args) {
		Network net = new Network(4,3,3,2);
		System.out.println(Arrays.toString(net.feedForward(0.2,0.6,0.2,0.4)));
		
	}
	
	
}
