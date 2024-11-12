package de.fhdw.rroch.staticfix2;

import java.util.*;

import static java.util.Collections.max;

public class Calculate {



    public ArrayList<Integer> organizedData(ArrayList<Integer> input) {
        ArrayList<Integer> result = new ArrayList<>();
        if (!input.isEmpty()) {
            result.addAll(input);
            result.sort((integer, t1) -> integer);
        } else {
            result.add(-1);
        }
        return result;
    }

    public List<Double[]> createFrequencyData(ArrayList<Integer> input) {
        List<Double[]> result = new ArrayList<>();
        if (!input.isEmpty()) {
            ArrayList<Integer> tableHeaderData = tabelHeader(input);
            int rowSize = tableHeaderData.size();
            int inputSize = input.size();
            ArrayList<Integer> tableAbsoluteFrequenciesData = absoluteFrequencies(input);
            ArrayList<Double> tableRelativeFrequenciesData = relativeFrequencies(tableAbsoluteFrequenciesData, inputSize);
            ArrayList<Integer> absoluteCumulativeFrequenciesData = absoluteCumulativeFrequencies(tableAbsoluteFrequenciesData);
            ArrayList<Double> relativeCumulativeFrequenciesData = relativeCumulativeFrequencies(tableRelativeFrequenciesData);
            ArrayList<Integer> absoluteResidualFrequenciesData = absoluteResidualFrequencies(tableAbsoluteFrequenciesData);
            ArrayList<Double> relativeResidualFrequenciesData = relativeResidualFrequencies(tableRelativeFrequenciesData);


            for (int i = 0; i < 7; i++) {
                result.add(new Double[rowSize]);
            }

            for (int i = 0; i < rowSize; i++) {
                result.get(0)[i] = (double) tableHeaderData.get(i);
                result.get(1)[i] = (double) tableAbsoluteFrequenciesData.get(i);
                result.get(2)[i] = tableRelativeFrequenciesData.get(i);
                result.get(3)[i] = (double) absoluteCumulativeFrequenciesData.get(i);
                result.get(4)[i] = relativeCumulativeFrequenciesData.get(i);
                result.get(5)[i] = (double) absoluteResidualFrequenciesData.get(i);
                result.get(6)[i] = relativeResidualFrequenciesData.get(i);
            }
        }

        return result;
    }
    public ArrayList<Integer> tabelHeader(ArrayList<Integer> input) {
        ArrayList<Integer> result = new ArrayList<>(input);
        result.sort(Integer::compareTo);
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i).equals(result.get(i + 1))) {
                result.remove(i + 1);
                i--;
            }
        }
        return result;
    }

    public ArrayList<Integer> absoluteFrequencies(ArrayList<Integer> input) {
        ArrayList<Integer> result = new ArrayList<>();
        if (!input.isEmpty()) {
            ArrayList<Integer> coutObject = tabelHeader(input);
            for (Integer integer : coutObject) {
                int counter = 0;
                for (Integer value : input) {
                    if (integer.equals(value)) {
                        counter++;
                    }
                }
                result.add(counter);
            }
        } else {
            result.add(-1);
        }
        return result;
    }

    private ArrayList<Double> relativeFrequencies(List<Integer> input, int n) {
        ArrayList<Double> result = new ArrayList<>();
        if (!input.isEmpty()) {
            for (int i = 0; i < input.size(); i++) {
                double helper = (double) input.get(i) / (double) n;
                helper = Math.round(helper * 100.000) / 100.000;
                result.add(helper);
            }
        }else {
            result.add(-1.0);
        }

        return result;
    }

    public ArrayList<Integer> absoluteCumulativeFrequencies(ArrayList<Integer> input) {
        ArrayList<Integer> result = new ArrayList<>();

        if (!input.isEmpty()) {
            int sum = 0;
            for (Integer integer : input) {
                sum += integer;
                result.add(sum);
            }
        } else {
            result.add(-1);
        }
        return result;
    }

    public ArrayList<Double> relativeCumulativeFrequencies(ArrayList<Double> input) {
        ArrayList<Double> result = new ArrayList<>();

        if (!input.isEmpty()) {
            double sum = 0.0;
            for (Double aDouble : input) {
                sum += aDouble;
                sum = Math.round(sum * 100.000) / 100.0000;
                result.add(sum);
            }
        } else {
            result.add(-1.0);
        }
        return result;
    }

    public ArrayList<Integer> absoluteResidualFrequencies(ArrayList<Integer> input) {
        ArrayList<Integer> result = new ArrayList<>();

        if (!input.isEmpty()) {
            int sum = 0;
            int counter = input.size() -1;
            result.add(sum);
            for (int i = counter; i >= 1; i--) {
                sum += input.get(i);
                result.add(0,sum);
            }
        } else {
            result.add(-1);
        }
        return result;
    }

    public ArrayList<Double> relativeResidualFrequencies(ArrayList<Double> input) {
        ArrayList<Double> result = new ArrayList<>();

        if (!input.isEmpty()) {
            double sum = 0.0;
            int counter = input.size() -1;
            result.add(sum);
            for (int i = counter; i >= 1; i--) {
                sum += input.get(i);
                sum = Math.round(sum * 100.000) / 100.000;                result.add(0,sum);
            }
        } else {
            result.add(-1.0);
        }
        return result;
    }

    //Lageparameter
    public double[] createLocationParameters(ArrayList<Integer> input) {
        double[] result = new double[6];
        if (!input.isEmpty()) {
            result[0] = modus(input);
            result[1] = median(input);
            result[2] = quantile(input, 0.25);
            result[3] = quantile(input, 0.75);
            result[4] = arithmeticMean(input);
            result[5] = geometricMean(input);

        }else{
            Arrays.fill(result, -1.0);
        }
        return result;
    }


    // Calculate: Location Parameters

    public double modus(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        // Count the frequency of each element
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer number : input) {
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }

        // Find the mode: the element with the highest frequency
        int maxFrequency = 0;
        double mode = -1;

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int number = entry.getKey();
            int frequency = entry.getValue();

            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mode = number;
            } else if (frequency == maxFrequency && number < mode) {
                mode = number;
            }
        }

        return mode;
    }

    private double median(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1.0;
        }

        int size = input.size();

        if (size % 2 == 0) {
            int middle1 = input.get((size / 2) - 1);
            int middle2 = input.get(size / 2);
            return (middle1 + middle2) / 2.0;
        } else {
            return (double) input.get(size / 2);
        }
    }

    private double quantile(ArrayList<Integer> input, double percentile) {
        if (input.isEmpty()) {
            return -1;
        }

        if (percentile < 0 || percentile > 1) {
            throw new IllegalArgumentException("Percentile must be between 0 and 1.");
        }

        // Sort the input list to find the quantile
        Collections.sort(input);

        // Compute the index of the quantile position
        int index = (int) Math.ceil(percentile * (input.size() - 1));

        // Return the element at the quantile index
        return input.get(index);
    }

    private double arithmeticMean(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }
        double sum = 0;
        for (Integer number : input) {
            sum += number;
        }
        return sum / input.size();
    }

    private double geometricMean(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        double potens = 1.0;

        for (Integer integer : input) {
            potens *= integer;
        }

        int size = input.size();

        return Math.pow(potens, 1.0 / size);
    }


    //Streungsparameter
    public double[] createScatteringParameters(ArrayList<Integer> input) {
        double[] result = new double[6];
        if (!input.isEmpty()) {
            result[0] = span(input);
            result[1] = meanAbsoluteDeviation(input);
            result[2] = empiricalVariance(input);
            result[3] = empiricalStandardDeviation(input);
            result[4] = coefficientsOfVariation(input);
            result[5] = interquartileRange(input);

        }else{
            Arrays.fill(result, -1.0);
        }
        return result;
    }

    // Calculate: Scattering Parameters

    private double span(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        int maxValue = Integer.MAX_VALUE;
        int minValue = Integer.MIN_VALUE;

        return (double) maxValue - minValue;
    }

    private double meanAbsoluteDeviation(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        double deviation = 0.0;
        for (Integer number : input) {
            deviation += Math.abs(number - arithmeticMean(input));
        }
        return deviation / input.size();
    }

    private double empiricalVariance(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        double variance = 0.0;

        for (Integer number : input) {
            variance += Math.pow(number - arithmeticMean(input), 2);
        }
        return variance / input.size();
    }

    private double empiricalStandardDeviation(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        return Math.sqrt(empiricalVariance(input));
    }

    private double coefficientsOfVariation(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        return empiricalStandardDeviation(input) / arithmeticMean(input);
    }

    private double interquartileRange(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        return quantile(input, 0.75) - quantile(input,0.25);
    }
}

