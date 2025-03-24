import java.util.*;

public class Node {
    private String attribute;
    private String value;
    private boolean isLeaf;
    private String prediction;
    private Map<String, Node> children;

    public Node() {
        this.children = new HashMap<>();
        this.isLeaf = false;
    }

    // Getters y Setters
    public String getAttribute() { return attribute; }
    public void setAttribute(String attribute) { this.attribute = attribute; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public boolean isLeaf() { return isLeaf; }
    public void setLeaf(boolean leaf) { isLeaf = leaf; }
    public String getPrediction() { return prediction; }
    public void setPrediction(String prediction) { this.prediction = prediction; }
    public Map<String, Node> getChildren() { return children; }
}