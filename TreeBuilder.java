import java.util.*;

public class TreeBuilder {
    private EntropyCalculator entropyCalculator;
    private DataSet dataSet;

    public TreeBuilder(DataSet dataSet) {
        this.dataSet = dataSet;
        this.entropyCalculator = new EntropyCalculator();
    }

    public Node buildTree(List<List<String>> data, List<Integer> availableAttributes) {
        Node node = new Node();

        if (isAllSameClass(data)) {
            node.setLeaf(true);
            node.setPrediction(data.get(0).get(dataSet.getTargetIndex()));
            return node;
        }

        if (availableAttributes.isEmpty()) {
            node.setLeaf(true);
            node.setPrediction(getMajorityClass(data));
            return node;
        }

        int bestAttribute = findBestAttribute(data, availableAttributes);
        node.setAttribute(dataSet.getAttributes().get(bestAttribute));

        Map<String, List<List<String>>> splitData = 
            entropyCalculator.splitDataByAttribute(data, bestAttribute);

        List<Integer> newAttributes = new ArrayList<>(availableAttributes);
        newAttributes.remove(Integer.valueOf(bestAttribute));

        for (Map.Entry<String, List<List<String>>> entry : splitData.entrySet()) {
            Node childNode = buildTree(entry.getValue(), newAttributes);
            node.getChildren().put(entry.getKey(), childNode);
        }

        return node;
    }

    private boolean isAllSameClass(List<List<String>> data) {
        String firstClass = data.get(0).get(dataSet.getTargetIndex());
        return data.stream()
            .allMatch(row -> row.get(dataSet.getTargetIndex()).equals(firstClass));
    }

    private String getMajorityClass(List<List<String>> data) {
        Map<String, Integer> classCounts = new HashMap<>();
        for (List<String> row : data) {
            String classValue = row.get(dataSet.getTargetIndex());
            classCounts.put(classValue, classCounts.getOrDefault(classValue, 0) + 1);
        }
        return Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private int findBestAttribute(List<List<String>> data, List<Integer> availableAttributes) {
        double maxGain = -1;
        int bestAttribute = -1;

        for (int attribute : availableAttributes) {
            double gain = entropyCalculator.calculateInformationGain(
                data, attribute, dataSet.getTargetIndex());
            if (gain > maxGain) {
                maxGain = gain;
                bestAttribute = attribute;
            }
        }

        return bestAttribute;
    }
}