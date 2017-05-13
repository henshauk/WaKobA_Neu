package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.shape.Line;
import weka.clusterers.Clusterer;
import weka.clusterers.AbstractClusterer;
import weka.clusterers.DensityBasedClusterer;
import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.DensityBasedClustererSplitEvaluator;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

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
	Instances trainingSubset;
	String datapath="";

	public Wekabuilder(String path, String dataDir) throws Exception {
		// CSV-Datei laden
		this.datapath=dataDir;
		loader = new CSVLoader();
		loader.setSource(new File(path));
		data = loader.getDataSet();

		String arffDat = path + ".arff";

		BufferedWriter writer = new BufferedWriter(new FileWriter(arffDat));
	    writer.write(data.toString());
	    writer.flush();
	    writer.close();

		source = new DataSource(arffDat);
		data = source.getDataSet();
		
		
	}

	public void filter(int[] array) throws Exception {
		int[] indicesOfColumnsToUse = array;
		Remove remove = new Remove();
		remove.setAttributeIndicesArray(indicesOfColumnsToUse);
		remove.setInvertSelection(false);
		remove.setInputFormat(data);
		
		trainingSubset = Filter.useFilter(data, remove);
		
	}
	
	public String storeData(Clusterer clusterer) throws IOException{
	
		Date d = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String result = datapath + File.separator + "store";
		
		File storeDir = new File(result);
		if (!storeDir.exists()) {
			storeDir.mkdir();
		}
		
		System.out.println("store "+storeDir.getAbsolutePath());
		
		String storedata = result + File.separator + ft.format(d);
		BufferedWriter writer = new BufferedWriter(new FileWriter(storedata));
	    writer.write(clusterer.toString());
	    writer.flush();
	    writer.close();

	    return storedata;
		
	}
	
	public void getStoredData(String storeDir) throws IOException{
		
		StringBuilder sb = new StringBuilder();
		
	    String line="";
	    BufferedReader reader = new BufferedReader(new FileReader(storeDir));
	    while((line = reader.readLine()) !=null){
	    	sb.append(line + "\n");
			
	    }
	    reader.close();
	    
	    System.out.println("Datei inhalt:");
		System.out.println(sb.toString());
	}

	public void buildSKM(int anzahl) throws Exception {
		SimpleKMeans skm = new SimpleKMeans();
		skm.setNumClusters(anzahl); // Anzahl der Cluster festlegen
		skm.buildClusterer(trainingSubset);
		System.out.println(skm);
		
		getStoredData(storeData(skm));
	}

	public void buildFF(int anzahl) throws Exception {
		FarthestFirst ff = new FarthestFirst();
		ff.setNumClusters(anzahl);
		ff.buildClusterer(trainingSubset);
		System.out.println(ff);
	}

	public void buildEM(int anzahl) throws Exception {
		EM em = new EM();
		em.setNumClusters(anzahl);
		em.buildClusterer(trainingSubset);
		System.out.println(em);
	}

}
