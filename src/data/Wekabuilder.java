package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weka.clusterers.Clusterer;
import weka.clusterers.AbstractClusterer;
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

	/*
	 * Beispielprogramm, um WeKa Cluster Simple KMeans in eclipse zu verwenden.
	 * Die Rohdaten liegen im CSV-Format vor. WeKa ben�tigt das arff-Format.
	 * 
	 * Hier wird nur die Anzahl der Cluster im Algorithmus Simple KMeans
	 * ver�ndert. Weitere Einstellungen (falls n�tig) selbst recherchieren!
	 */
	public Instances data;
	CSVLoader loader;
	ArffSaver saver;
	DataSource source;

	public Wekabuilder(String path) throws Exception {
		// CSV-Datei laden
		loader = new CSVLoader();
		loader.setSource(new File(path));
		data = loader.getDataSet();

		String arffDat = path + ".arff";
		// und als ARFF-Datei speichern
		/*saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffDat));
		saver.writeBatch();
		*/
		BufferedWriter writer = new BufferedWriter(new FileWriter(arffDat));
	    writer.write(data.toString());
	    writer.flush();
	    writer.close();

		// Cluster Simple KMeans
		source = new DataSource(arffDat);

		data = source.getDataSet();
	}

	public void getData(String path) throws Exception {

	}

	public void buildSKM(int anzahl) throws Exception {
		SimpleKMeans skm = new SimpleKMeans();
		skm.setNumClusters(anzahl); // Anzahl der Cluster festlegen
		skm.buildClusterer(data);
		System.out.println(skm);
	}

	public void buildFF(int anzahl) throws Exception {
		FarthestFirst ff = new FarthestFirst();
		ff.setNumClusters(anzahl);
		ff.buildClusterer(data);
		System.out.println(ff);
	}

	public void buildEM(int anzahl) throws Exception {
		EM em = new EM();
		em.setNumClusters(anzahl);
		em.buildClusterer(data);
		System.out.println(em);
	}

	public static void main(String[] args) throws Exception {
		// Eigenen Dateipfad eintragen, nicht meinen nehmen ;-)
		/*
		 * String path =
		 * "/home/Oj/Desktop/Informatik/Softwareprojektmanagement/Testdaten/";
		 * 
		 * String csvDatKlein = path + "SPM_TestdatensatzKlein_2017.csv"; String
		 * csvDatMittel = path + "SPM_TestdatensatzMittel_2017.csv"; String
		 * csvDatGross = path + "SPM_TestdatensatzGross_2017.csv";
		 * 
		 * String arffDat = path + "SPM_Testdatensatz.arff";
		 * 
		 * // CSV-Datei laden CSVLoader loader = new CSVLoader();
		 * loader.setSource(new File(csvDatKlein)); Instances data =
		 * loader.getDataSet();
		 * 
		 * // und als ARFF-Datei speichern ArffSaver saver = new ArffSaver();
		 * saver.setInstances(data); saver.setFile(new File(arffDat));
		 * saver.writeBatch();
		 * 
		 * // Cluster Simple KMeans DataSource source = new DataSource(arffDat);
		 * 
		 * data = source.getDataSet();
		 * 
		 * SimpleKMeans skm = new SimpleKMeans(); skm.setNumClusters(3); //
		 * Anzahl der Cluster festlegen skm.buildClusterer(data);
		 * System.out.println(skm);
		 * 
		 * 
		 * FarthestFirst ff = new FarthestFirst(); ff.setNumClusters(3);
		 * ff.buildClusterer(data); System.out.println(ff);
		 * 
		 * EM em = new EM(); em.setNumClusters(3); em.buildClusterer(data);
		 * System.out.println(em);
		 */

	}

}
