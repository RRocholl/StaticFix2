package de.fhdw.rroch.staticfix2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        double[] result = new double[5];
        if (!input.isEmpty()) {
            result[0] = modus(input);
            result[1] = median(input);
            result[2] = quantile(input);
            result[3] = arithmeticMean(input);
            result[4] = geometricMean(input);

        }else{
            Arrays.fill(result, -1.0);
        }
        return result;
    }

    private double modus (ArrayList<Integer> input) {
        double result;
        result = -1.0;
        return result;
    }

    private double median (ArrayList<Integer> input) {
        double result;
        input.sort(Integer::compareTo);
        if (input.size() % 2 == 0) {
            result = input.get(input.size() / 2);
        }else {
            int h1 = input.size();
            int h2 = input.size() -1;
            result = (double) (input.get(h1 / 2) + input.get(h2 / 2)) /2;
            result = Math.round(result * 1000.0) / 1000.0;
        }
        return result;
    }

    private double quantile(ArrayList<Integer> input) {
        return -1.0;
    }

    private double arithmeticMean(ArrayList<Integer> input) {
        return -1.0;
    }

    private double geometricMean(ArrayList<Integer> input) {
        return -1.0;
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

    private double span(ArrayList<Integer> input) {
        return -1.0;
    }

    private double meanAbsoluteDeviation(ArrayList<Integer> input) {
        return -1.0;
    }

    private double empiricalVariance(ArrayList<Integer> input) {
        return -1.0;
    }

    private double empiricalStandardDeviation(ArrayList<Integer> input) {
        return -1.0;
    }

    private double coefficientsOfVariation(ArrayList<Integer> input) {
        return -1.0;
    }

    private double interquartileRange(ArrayList<Integer> input) {
        return -1.0;
    }
}
