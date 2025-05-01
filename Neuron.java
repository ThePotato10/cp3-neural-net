import java.math.*;
import java.util.*;

class Neuron {
    Random random = new Random();

    private Double bias = random.nextDouble() * 2 - 1; 
    public Double weight1 = random.nextDouble() * 2 - 1; 
    private Double weight2 = random.nextDouble() * 2 - 1;

    // these will be used to store the previously used weights in case those gave a better precition.
    private Double oldBias = random.nextDouble() * 2 - 1; 
    public Double oldWeight1 = random.nextDouble() * 2 - 1; 
    private Double oldWeight2 = random.nextDouble() * 2 - 1;
   
    public double compute(double input1, double input2){
        double preActivation = (this.weight1 * input1) + (this.weight2 * input2) + this.bias;
        double output = sigmoid(preActivation);
        return output;
    }

    public static double sigmoid(double in){
        return 1 / (1 + Math.exp(-in));
    }

    

//....

//this method will be used to randomly mess with the weights
    public void mutate(){
        int propertyToChange = random.nextInt(3);
        Double changeFactor = random.nextDouble() * 2 - 1;

        if (propertyToChange == 0) { 
            this.bias += changeFactor; 
        } else if (propertyToChange == 1) { 
            this.weight1 += changeFactor;
        } else { 
            this.weight2 += changeFactor; 
        };
    }

    //if our outcome was worse we forget the change we attempted.
    public void forget(){
        bias = oldBias;
        weight1 = oldWeight1;
        weight2 = oldWeight2;
    }

    //if our outcome was better we'll keep the new values and try to mutate again leter.
    public void remember(){
        oldBias = bias;
        oldWeight1 = weight1;
        oldWeight2 = weight2;
    }
}
