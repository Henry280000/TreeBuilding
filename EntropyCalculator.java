import java.util.*;

public class EntropyCalculator {
    public double calculateEntropy(List<List<String>> data, int targetIndex) {
        if (data.isEmpty()) return 0;

        Map<String, Integer> classCounts = new HashMap<>();
        for (List<String> row : data) {
            String classValue = row.get(targetIndex);
            classCounts.put(classValue, classCounts.getOrDefault(classValue, 0) + 1);
        }

        double entropy = 0;
        int totalInstances = data.size();
        for (int count : classCounts.values()) {
            double probability = (double) count / totalInstances;
            entropy -= probability * (Math.log(probability) / Math.log(2));
        }

        return entropy;
    }

    public double calculateInformationGain(List<List<String>> data, int attributeIndex, int targetIndex) {
        double entropy = calculateEntropy(data, targetIndex);
        Map<String, List<List<String>>> splitData = splitDataByAttribute(data, attributeIndex);

        double weightedEntropy = 0;
        int totalInstances = data.size();
        for (List<List<String>> subset : splitData.values()) {
            double weight = (double) subset.size() / totalInstances;
            weightedEntropy += weight * calculateEntropy(subset, targetIndex);
        }

        return entropy - weightedEntropy;
    }

    public Map<String, List<List<String>>> splitDataByAttribute(List<List<String>> data, int attributeIndex) {
        Map<String, List<List<String>>> splitData = new HashMap<>();
        for (List<String> row : data) {
            String value = row.get(attributeIndex);
            splitData.computeIfAbsent(value, k -> new ArrayList<>()).add(row);
        }
        return splitData;
    }
}