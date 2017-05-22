package data;

import java.awt.Color;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import weka.clusterers.Clusterer;
import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.io.CSV;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Wekabuilder {

	/*
	 * Beispielprogramm, um WeKa Cluster Simple KMeans in eclipse zu verwenden.
	 * Die Rohdaten liegen im CSV-Format vor. WeKa benï¿½tigt das arff-Format.
	 * 
	 * Hier wird nur die Anzahl der Cluster im Algorithmus Simple KMeans
	 * verï¿½ndert. Weitere Einstellungen (falls nï¿½tig) selbst recherchieren!
	 */
	public Instances data;
	CSVLoader loader;
	ArffSaver saver;
	DataSource source;
	Instances trainingSubset;
	String datapath = "";
	public static List<List<String>> diagrammData;
	

	public Wekabuilder(String file, String dataDir) throws Exception {
		// CSV-Datei laden
		this.datapath = dataDir;
		diagrammData = new ArrayList<List<String>>();
		loader = new CSVLoader();
		File csv = new File(file);
		loader.setSource(csv);
		data = loader.getDataSet();
		System.out.println("lösche csv: "+csv.delete());

		String arffDat = file + ".arff";

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
		remove.setInvertSelection(true);
		remove.setInputFormat(data);

		trainingSubset = Filter.useFilter(data, remove);

	}

	public String storeData(Clusterer clusterer) throws IOException {

		Date d = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String result = datapath + File.separator + "store";

		File storeDir = new File(result);
		if (!storeDir.exists()) {
			storeDir.mkdir();
		}

		System.out.println("store " + storeDir.getAbsolutePath());

		String storedata = result + File.separator + ft.format(d);
		BufferedWriter writer = new BufferedWriter(new FileWriter(storedata));
		writer.write(clusterer.toString());
		writer.flush();
		writer.close();

		return storedata;

	}

	public void getStoredData(String storeDir) throws IOException {

		StringBuilder sb = new StringBuilder();

		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(storeDir));
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");

		}
		reader.close();

		System.out.println("Datei inhalt:");
		System.out.println(sb.toString());
	}
	private void print(List<List<String>> list){
		Iterator<List<String>> it = list.iterator();
		System.out.println("list: "+list.size());
		while(it.hasNext()){
		List<String> a = it.next();
		Iterator<String> aa = a.iterator();
		while(aa.hasNext()){
			System.out.println(aa.next());
		}
		}
	}

	public Instances buildSKM(int anzahl) throws Exception {
		SimpleKMeans skm = new SimpleKMeans();
		skm.setNumClusters(anzahl); // Anzahl der Cluster festlegen
		skm.buildClusterer(trainingSubset);
		skm.setDisplayStdDevs(false);
		Instances inst = skm.getClusterCentroids();
		System.out.println(skm);
		resolveInstance(inst);
		print(diagrammData);
		storeData(skm);
		
		return inst;
		//skm.setDisplayStdDevs(true);
		//System.out.println(skm);
	}

	public Instances buildFF(int anzahl) throws Exception {
		
	    //Inintialisierungen
	    	FarthestFirst ff = new FarthestFirst();
		ff.setNumClusters(anzahl);
		ff.buildClusterer(trainingSubset);
		
		//erstelltes Set aus Clustern
		Instances inst = ff.getClusterCentroids();
		
		resolveInstance(inst);  // Daten aus Cluster in List<List>
		return inst;
		}
		
	
	public static void resolveInstance(Instances inst){
	        String cluster;
		List attr = new ArrayList<String>();  //Liste für Aufzählungen
		List<List<String>> res = new ArrayList<List<String>>();		//Liste aus Clustern
		
		for(int i = 0; i < inst.numInstances(); i++){
		   System.out.println("Cluster:" + i + "\n");
		   cluster = inst.get(i).toString();			//to String aus Cluster Instanz
		   attr =  Arrays.asList(cluster.split("\\s*,\\s*"));	
		   res.add(attr);						//Cluster der ClusterListe hinzufügen
		 
		    }
	
		diagrammData = res;;
	    
	    
	}

	public void buildEM(int anzahl) throws Exception {
		EM em = new EM();
		em.setNumClusters(anzahl);
		em.buildClusterer(trainingSubset);
		System.out.println(em);
		
	}

}
