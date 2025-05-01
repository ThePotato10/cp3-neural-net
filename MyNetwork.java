// 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyNetwork {
    List<Neuron> neurons = Arrays.asList(
        new Neuron(), new Neuron(), new Neuron(), /* input nodes */
        new Neuron(), new Neuron(),               /* hidden nodes */
        new Neuron()                              /* output node */
    ); 

    public Double predict(Integer input1, Integer input2){
        return neurons.get(5).compute(
            neurons.get(4).compute(
                neurons.get(2).compute(input1, input2),
                neurons.get(1).compute(input1, input2)
            ),
            neurons.get(3).compute(
                neurons.get(1).compute(input1, input2),
                neurons.get(0).compute(input1, input2)
            )
        );
    }

    public void train(List<List<Integer>> data, List<Double> answers){
        Double bestLoss = null;
  
        for (int iteration = 0; iteration < 2000; iteration ++) {
            // adapt neuron
            Neuron neuron = neurons.get(iteration % 6);
            neuron.mutate();

            List<Double> predictions = new ArrayList<Double>();
            for (int i = 0; i < data.size(); i++) predictions.add(i, this.predict(data.get(i).get(0), data.get(i).get(1)));
            
            Double thisLoss = meanSquareLoss(answers, predictions);

            // Logging:
            if (iteration % 10 == 0) System.out.println("Iteration: "+iteration+" best loss: "+bestLoss+" this loss: "+thisLoss);
                
            if (bestLoss == null){
                bestLoss = thisLoss;
                neuron.remember();
            } else {
                if (thisLoss < bestLoss) {
                    bestLoss = thisLoss;
                    neuron.remember();
                } else {
                    neuron.forget();
                }
            }
        }
    }

    public static Double meanSquareLoss(List<Double> correctAnswers,   List<Double> predictedAnswers){
        double sumSquare = 0;

        for (int i = 0; i < correctAnswers.size(); i++){
            double error = correctAnswers.get(i) - predictedAnswers.get(i);
            sumSquare += (error * error);
        }

        return sumSquare / (correctAnswers.size());
    }

    public static List<List<String>> readCSV(String filePath) {
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String [] args){
        Network network = new Network();

        List<List<Integer>> data = new ArrayList<List<Integer>>();
        List<Double> answers = Arrays.asList(0.0,1.0,1.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0);

        network.train(data, answers);

        //Try making some predictions:
        System.out.println("Should give no "+network.predict(167, 73));
        System.out.println("Should give yes "+network.predict(30, 25));
    }
}
