package data;

import java.io.File;

import weka.clusterers.Clusterer;
import weka.clusterers.DensityBasedClusterer;
import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.DensityBasedClustererSplitEvaluator;


public class Wekabuilder {


/* Beispielprogramm, um WeKa Cluster Simple KMeans in eclipse zu verwenden.
 Die Rohdaten liegen im CSV-Format vor. WeKa benötigt das arff-Format.

 Hier wird nur die Anzahl der Cluster im Algorithmus Simple KMeans verändert.
 Weitere Einstellungen (falls nötig) selbst recherchieren!
 */

	public static void main(String[] args) throws Exception {
		// Eigenen Dateipfad eintragen, nicht meinen nehmen ;-)
		String path = "res/";

		String csvDat = path + "SPM_TestdatensatzMittel_2017.csv";  // Rohdaten
		String arffDat = path + "kd.arff"; // arff-Daten für WeKa

		// CSV-Datei laden
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvDat));
		Instances data = loader.getDataSet();

		// und als ARFF-Datei speichern
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffDat));
		saver.writeBatch();

		// Cluster Simple KMeans
		DataSource source = new DataSource(arffDat);

		data = source.getDataSet();
		
		FarthestFirst ff = new FarthestFirst();
		ff.setNumClusters(3);
		ff.buildClusterer(data);
		System.out.println(ff);
		
		EM em = new EM();
		em.setNumClusters(3);
		em.buildClusterer(data);
		System.out.println(em);
		
		SimpleKMeans model = new SimpleKMeans();
		model.setNumClusters(3); // Anzahl der Cluster festlegen
		model.buildClusterer(data);
		System.out.println(model);

	}

}


