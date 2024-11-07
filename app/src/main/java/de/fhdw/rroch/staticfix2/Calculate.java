package de.fhdw.rroch.staticfix2;

import java.util.ArrayList;
import java.util.List;

public class Calculate {

    public String outPutData(ArrayList<Integer> input) {
        String result;
        if (!input.isEmpty()) {
            result = input.toString();
            //result = result.substring(result.length() - 2);
        } else {
            result = "Bitte gehen Sie zurück und tragen Sie Werte ein!";
        }
        return result;
    }

    public String organizedData(ArrayList<Integer> input) {
        String result;
        if (!input.isEmpty()) {
            input.sort(Integer::compareTo);
            result = outPutData(input);
        } else {
            result = "Bitte gehen Sie zurück und tragen Sie Werte ein!";
        }
        return result;
    }

    public List<Double[]> crateTableData(ArrayList<Integer> input) {
        List<Double[]> result = new ArrayList<>();
        if (!input.isEmpty()) {
            ArrayList<Integer> tableHeaderData = tabelHeader(input);
            int rowSize = tableHeaderData.size();
            ArrayList<Integer> tableAbsoluteFrequenciesData = absoluteFrequencies(input);
            ArrayList<Double> tableRelativeFrequenciesData = relativeFrequencies(tableAbsoluteFrequenciesData, rowSize);
            ArrayList<Integer> absoluteCumulativeFrequenciesData = absoluteCumulativeFrequencies(tableAbsoluteFrequenciesData);
            ArrayList<Double> relativeCumulativeFrequenciesData = relativeCumulativeFrequencies(tableRelativeFrequenciesData);

            for (int i = 0; i < 5; i++) {
                result.add(new Double[rowSize]);
            }

            for (int i = 0; i < rowSize; i++) {
                result.get(0)[i] = (double) tableHeaderData.get(i);
                result.get(1)[i] = (double) tableAbsoluteFrequenciesData.get(i);
                result.get(2)[i] = tableRelativeFrequenciesData.get(i);
                result.get(3)[i] = (double) absoluteCumulativeFrequenciesData.get(i);
                result.get(4)[i] = relativeCumulativeFrequenciesData.get(i);
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
            for (int i = 0; i < coutObject.size(); i++) {
                int counter = 0;
                for (int j = 0; j < input.size(); j++) {
                    if (coutObject.get(i).equals(input.get(j))) {
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
                helper = Math.round(helper * 100.0) / 100.0;
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
                result.add(sum);
            }
        } else {
            result.add(-1.0);
        }
        return result;
    }
}
