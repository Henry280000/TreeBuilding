import java.util.*;

public class ID3Classifier {
    private Node root;
    private DataSet dataSet;

    public ID3Classifier(DataSet dataSet) {
        this.dataSet = dataSet;
        TreeBuilder treeBuilder = new TreeBuilder(dataSet);
        
        List<Integer> availableAttributes = new ArrayList<>();
        for (int i = 0; i < dataSet.getAttributes().size() - 1; i++) {
            availableAttributes.add(i);
        }
        
        this.root = treeBuilder.buildTree(dataSet.getData(), availableAttributes);
    }

    public String classify(List<String> instance) {
        return classifyRecursive(root, instance);
    }

    private String classifyRecursive(Node node, List<String> instance) {
        if (node.isLeaf()) {
            return node.getPrediction();
        }

        String attributeValue = instance.get(
            dataSet.getAttributes().indexOf(node.getAttribute()));
        Node child = node.getChildren().get(attributeValue);
        
        if (child == null) {
            return getMajorityClass(dataSet.getData());
        }

        return classifyRecursive(child, instance);
    }

    private String getMajorityClass(List<List<String>> data) {
        Map<String, Integer> classCounts = new HashMap<>();
        for (List<String> row : data) {
            String classValue = row.get(dataSet.getTargetIndex());
            classCounts.put(classValue, classCounts.getOrDefault(classValue, 0) + 1);
        }
        return Collections.max(classCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void printTree() {
        printTreeRecursive(root, "");
    }

    private void printTreeRecursive(Node node, String indent) {
        if (node.isLeaf()) {
            System.out.println(indent + "Predicción: " + node.getPrediction());
            return;
        }

        System.out.println(indent + "Atributo: " + node.getAttribute());
        
        for (Map.Entry<String, Node> entry : node.getChildren().entrySet()) {
            System.out.println(indent + "├── Valor: " + entry.getKey());
            printTreeRecursive(entry.getValue(), indent + "│   ");
        }
    }
}