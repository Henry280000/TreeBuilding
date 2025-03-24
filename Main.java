import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Predicción de Deportes al Aire Libre con ID3 ===\n");
        
        // Cargar datos
        DataSet dataSet = new DataSet("outdoor_sport.csv");
        
        // Crear y entrenar el clasificador
        ID3Classifier classifier = new ID3Classifier(dataSet);
        
        // Mostrar el árbol de decisión
        System.out.println("\n=== Árbol de Decisión Generado ===");
        classifier.printTree();
        
        // Probar clasificación
        System.out.println("\n=== Pruebas de Clasificación ===");
        List<List<String>> testCases = Arrays.asList(
            Arrays.asList("Caliente", "Alta", "No"),
            Arrays.asList("Frio", "Baja", "Si"),
            Arrays.asList("Templado", "Media", "No")
        );
        
        for (int i = 0; i < testCases.size(); i++) {
            List<String> caso = testCases.get(i);
            String prediccion = classifier.classify(caso);
            System.out.println("\nCaso " + (i + 1) + ":");
            System.out.println("Condiciones:");
            System.out.println("- Temperatura: " + caso.get(0));
            System.out.println("- Humedad: " + caso.get(1));
            System.out.println("- Viento: " + caso.get(2));
            System.out.println("Predicción: " + prediccion);
        }
    }
}