package data;

import java.io.File;
import java.io.IOException;

import weka.clusterers.SimpleKMeans;
import weka.clusterers.EM;
import weka.clusterers.Cobweb;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaCluster {

	public static void main(String[] args) throws Exception {
		//path anpassen
		String path = "/home/Oj/Desktop/Informatik/Softwareprojektmanagement/Testdaten/";
		
		String csvDatKlein = path + "SPM_TestdatensatzKlein_2017.csv";
		String csvDatMittel = path + "SPM_TestdatensatzMittel_2017.csv";
		String csvDatGross = path + "SPM_TestdatensatzGross_2017.csv";
		
		String arffDat = path + "SPM_Testdatensatz.arff";
		
		// CSV-Datei laden
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvDatKlein));
		Instances data = loader.getDataSet();
		
		// und als ARFF-Datei speichern
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffDat));
		saver.writeBatch();
		
		// Cluster Simple KMeans
		DataSource source = new DataSource(arffDat);

		data = source.getDataSet();

		SimpleKMeans modelKM = new SimpleKMeans();
		modelKM.setNumClusters(5); // Anzahl der Cluster festlegen
		modelKM.buildClusterer(data);
		System.out.println(modelKM);
		
		// Cluster EM
		EM modelEM = new EM();
		modelEM.buildClusterer(data);
		System.out.println(modelEM);
		
		// Cluster Cobweb
		Cobweb modelCob	= new Cobweb();
		modelCob.buildClusterer(data);
		System.out.println(modelCob);
	}
}
