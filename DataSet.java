import java.io.*;
import java.util.*;

public class DataSet {
    private List<String> attributes;
    private List<List<String>> data;
    private int targetIndex;

    public DataSet(String csvFile) {
        this.data = new ArrayList<>();
        loadCSVFile(csvFile);
        this.targetIndex = attributes.size() - 1;
    }

    private void loadCSVFile(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();
            attributes = Arrays.asList(line.split(","));

            while ((line = br.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(","));
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<List<String>> getData() {
        return data;
    }

    public int getTargetIndex() {
        return targetIndex;
    }
}