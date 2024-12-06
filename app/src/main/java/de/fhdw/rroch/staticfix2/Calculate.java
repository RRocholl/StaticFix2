package de.fhdw.rroch.staticfix2;

import android.util.Log;

import java.util.*;

public class Calculate { //
    private final static double roundDigit = 1000.0;

    // Organize input data and return sorted list
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

    // Create frequency data: absolute, relative, cumulative, and residual frequencies
    public List<Double[]> createFrequencyData(ArrayList<Integer> input) {
        List<Double[]> result = new ArrayList<>();
        if (!input.isEmpty()) {
            ArrayList<Integer> tableHeaderData = tableHeader(input);
            int rowSize = tableHeaderData.size();
            int inputSize = input.size();
            ArrayList<Integer> tableAbsoluteFrequenciesData = absoluteFrequencies(input);
            ArrayList<Double> tableRelativeFrequenciesData = relativeFrequencies(tableAbsoluteFrequenciesData, inputSize);
            ArrayList<Integer> absoluteCumulativeFrequenciesData = absoluteCumulativeFrequencies(tableAbsoluteFrequenciesData);
            ArrayList<Double> relativeCumulativeFrequenciesData = relativeCumulativeFrequencies(tableRelativeFrequenciesData);
            ArrayList<Integer> absoluteResidualFrequenciesData = absoluteResidualFrequencies(tableAbsoluteFrequenciesData);
            ArrayList<Double> relativeResidualFrequenciesData = relativeResidualFrequencies(tableRelativeFrequenciesData);

            // Initialize the result array with 7 rows
            for (int i = 0; i < 7; i++) {
                result.add(new Double[rowSize]);
            }

            // Populate the result with the frequency data
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

    //create classification
    public String createClassificationInput(ArrayList<Integer> input, int classificationSize) {
        StringBuilder result = new StringBuilder();

        int max = input.stream().mapToInt(Integer::intValue).max().orElse(0);
        int resultSize = ((int) (double) (max / classificationSize))+2;

        for (int i = 1; i < resultSize; i++) {
            int endIndex = i * classificationSize;
            int startIndex = endIndex - classificationSize;
            int counter = 0;

            for (int j:input){
                if (j >= startIndex && j < endIndex ){
                    counter++;
                }
            }
            result.append(" "+ startIndex + " - " + endIndex +" = " + counter + "\n");
        }
        return result.toString();
    }

    // Create header for frequency table (unique values from the input)
    private ArrayList<Integer> tableHeader(ArrayList<Integer> input) {
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

    // Calculate the absolute frequencies of the input data
    private ArrayList<Integer> absoluteFrequencies(ArrayList<Integer> input) {
        ArrayList<Integer> result = new ArrayList<>();
        if (!input.isEmpty()) {
            ArrayList<Integer> countObject = tableHeader(input);
            for (Integer integer : countObject) {
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

    // Calculate relative frequencies for the given data
    private ArrayList<Double> relativeFrequencies(List<Integer> input, int n) {
        ArrayList<Double> result = new ArrayList<>();
        if (!input.isEmpty()) {
            for (Integer integer : input) {
                double helper = (double) integer / (double) n;
                helper = Math.round(helper * roundDigit) / roundDigit;
                result.add(helper);
            }
        } else {
            result.add(-1.0);
        }

        return result;
    }

    // Calculate the absolute cumulative frequencies
    private ArrayList<Integer> absoluteCumulativeFrequencies(ArrayList<Integer> input) {
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

    // Calculate the relative cumulative frequencies
    private ArrayList<Double> relativeCumulativeFrequencies(ArrayList<Double> input) {
        ArrayList<Double> result = new ArrayList<>();

        if (!input.isEmpty()) {
            double sum = 0.0;
            for (Double aDouble : input) {
                sum += aDouble;
                sum = Math.round(sum * roundDigit) / roundDigit;
                result.add(sum);
            }
        } else {
            result.add(-1.0);
        }
        return result;
    }

    // Calculate the absolute residual frequencies
    private ArrayList<Integer> absoluteResidualFrequencies(ArrayList<Integer> input) {
        ArrayList<Integer> result = new ArrayList<>();

        if (!input.isEmpty()) {
            int sum = 0;
            int counter = input.size() - 1;
            result.add(sum);
            for (int i = counter; i >= 1; i--) {
                sum += input.get(i);
                result.add(0, sum);
            }
        } else {
            result.add(-1);
        }
        return result;
    }

    // Calculate the relative residual frequencies
    private ArrayList<Double> relativeResidualFrequencies(ArrayList<Double> input) {
        ArrayList<Double> result = new ArrayList<>();

        if (!input.isEmpty()) {
            double sum = 0.0;
            int counter = input.size() - 1;
            result.add(sum);
            for (int i = counter; i >= 1; i--) {
                sum += input.get(i);
                sum = Math.round(sum * roundDigit) / roundDigit;
                result.add(0, sum);
            }
        } else {
            result.add(-1.0);
        }
        return result;
    }

    // Location Parameters
    public double[] createLocationParameters(ArrayList<Integer> input) {
        double[] result = new double[6];
        if (!input.isEmpty()) {
            result[0] = Math.round(modus(input) * roundDigit) / roundDigit;
            result[1] = Math.round(median(input) * roundDigit) / roundDigit;
            result[2] = Math.round(quantile(input, 0.25) * roundDigit) / roundDigit;
            result[3] = Math.round(quantile(input, 0.75) * roundDigit) / roundDigit;
            result[4] = Math.round(arithmeticMean(input) * roundDigit) / roundDigit;
            result[5] = Math.round(geometricMean(input) * roundDigit) / roundDigit;

        } else {
            Arrays.fill(result, -1.0);
        }
        return result;
    }

    // Calculate the mode (most frequent value)
    private double modus(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        // Count the frequency of each element
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer number : input) {
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }

        // Find the mode (element with the highest frequency)
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

    // Calculate the median (middle value)
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

    // Calculate the quantile value for a given percentile
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

    // Calculate the arithmetic mean
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

    // Calculate the geometric mean
    private double geometricMean(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        double potent = 1.0;

        for (Integer integer : input) {
            potent *= integer;
        }

        int size = input.size();

        return Math.pow(potent, 1.0 / size);
    }

    // Scattering Parameters
    public double[] createScatteringParameters(ArrayList<Integer> input) {
        double[] result = new double[6];
        if (!input.isEmpty()) {
            result[0] = Math.round(span(input) * roundDigit) / roundDigit;
            result[1] = Math.round(meanAbsoluteDeviation(input) * roundDigit) / roundDigit;
            result[2] = Math.round(empiricalVariance(input) * roundDigit) / roundDigit;
            result[3] = Math.round(empiricalStandardDeviation(input) * roundDigit) / roundDigit;
            result[4] = Math.round(coefficientsOfVariation(input) * roundDigit) / roundDigit;
            result[5] = Math.round(interquartileRange(input) * roundDigit) / roundDigit;

        } else {
            Arrays.fill(result, -1.0);
        }
        return result;
    }

    // Calculate the span (difference between max and min value)
    private double span(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        ArrayList<Integer> organised = new ArrayList<>(input);
        organised.sort(Integer::compareTo);
        double minValue = organised.get(0);
        double maxValue = organised.get(organised.size() - 1);

        return maxValue - minValue;
    }

    // Calculate the mean absolute deviation
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

    // Calculate the empirical variance
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

    // Calculate the empirical standard deviation
    private double empiricalStandardDeviation(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        return Math.sqrt(empiricalVariance(input));
    }

    // Calculate the coefficient of variation
    private double coefficientsOfVariation(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        return empiricalStandardDeviation(input) / arithmeticMean(input);
    }

    // Calculate the interquartile range
    private double interquartileRange(ArrayList<Integer> input) {
        if (input.isEmpty()) {
            return -1;
        }

        return quantile(input, 0.75) - quantile(input, 0.25);
    }
}
