package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
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

	public static List<List<String>> diagrammData;
	Result result;
	public static List<String> resultNames;
	private static String storeDir;
	private static int resultsToSave = 5;
	
	
	public static final String[] katNamen = {"Geschlecht","Alter","Kinder","Familienstand","Berufst�tig","Haushaltsnettoeinkommen",
										"Fernsehkonsum","Einkaufstag","Einkaufsmonat","Einkaufsuhrzeit","Einkaufssumme",
										"Fertiggerichte","Tiefk�hlware",
										 "Milchprodukte","Backwaren","Obst/Gem�se","Spirituosen","Tiernahrung",
										 "Bier","Frischfleisch","Drogerieartikel","Konserven","Kaffee/Tee","S��igkeiten"};
	
	public Wekabuilder(String file, String dataDir) throws Exception {
		// CSV-Datei laden
		storeDir = dataDir + File.separator + "store";
		diagrammData = new ArrayList<List<String>>();
		
		resultNames = new ArrayList<String>();
		builtList();
		
		loader = new CSVLoader();
		File csv = new File(file);
		loader.setSource(csv);
		data = loader.getDataSet();
		System.out.println("l�sche csv: "+csv.delete());

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
		System.out.println(Arrays.toString(array));
		trainingSubset = Filter.useFilter(data, remove);
		addKatToDiagrammData(indicesOfColumnsToUse);
	}
	
/**
 *  Speichert das Ergebnis einer Analyse ab
 * 
 * @param res  - das zu speichernde Ergebnis
 * @throws IOException
 */
	public void storeResult(Result res) throws IOException {

		File store = new File(storeDir);
		if (!store.exists()) {
			store.mkdir();
		}

		Date d = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

		String storedata = storeDir + File.separator + ft.format(d);
		
		OutputStream fos = null;
		try{
		fos = new FileOutputStream(storedata);
		ObjectOutputStream oo = new ObjectOutputStream(fos);
		oo.writeObject(res);
		}catch(IOException e){
			System.err.println(e);
		}finally{
			fos.close();
		}
	}

/**
 *  Liefert eine Liste mit dem Ergebnis einer gespeicherten Analyse zur�ck 
 * 
 * @param name  - Name der Datei, die das gesuchte Ergebnis beinhaltet
 * @return 		- List<List<String>> f�r output.jsp
 * @throws IOException
 */
	public static List<List<String>> getStoredData(String name) throws IOException {

		List<List<String>> list = new LinkedList<List<String>>();

		InputStream fis = null;
		try{

			fis = new FileInputStream(storeDir + File.separator + name);
			ObjectInputStream oo = new ObjectInputStream(fis);
			list = (((Result)oo.readObject()).getDiagrammData());

		}catch(IOException e){
				System.err.println(e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				fis.close();
			}
		return list;
	
	}
	
	


	public void buildSKM(int anzahl) throws Exception {
		SimpleKMeans skm = new SimpleKMeans();
		skm.setNumClusters(anzahl); // Anzahl der Cluster festlegen
		skm.buildClusterer(trainingSubset);
		skm.setDisplayStdDevs(false);

		Instances inst = skm.getClusterCentroids();
		resolveInstance(inst);
		Result result = new Result(diagrammData);
		storeResult(result);
	}
	
	/**
	 *  F�llt die Liste 'resultNames' mit den Namen der gespeicherten Ergebnisse
	 *  Zus�tlich werden Ergebnisse gel�scht die �ber der festgelegten Anzahl liegen
	 */
	private void builtList(){
		
		File store = new File(storeDir);
		String[] files = store.list();
		int diff = files.length - resultsToSave;
		 for(String s: files){
			 	if(diff > 0){
			 		File current = new File(storeDir + File.separator + s);
			 		current.delete();
			 		diff--;
			 	}else {
			    System.out.println("namen: "+s);
			    resultNames.add(s);
			 	}
			}
	}

	public void buildFF(int anzahl) throws Exception {
		
	    //Inintialisierungen
	    	FarthestFirst ff = new FarthestFirst();
		ff.setNumClusters(anzahl);
		ff.buildClusterer(trainingSubset);
		
		//erstelltes Set aus Clustern
		Instances inst = ff.getClusterCentroids();
		resolveInstance(inst);  // Daten aus Cluster in List<List>
		Result result = new Result(diagrammData);
		storeResult(result);

	}		
	
	public static void resolveInstance(Instances inst){
	        String cluster;
		List<String> attr = new ArrayList<String>();  //Liste f�r Aufz�hlungen
		List<List<String>> res = new ArrayList<List<String>>();		//Liste aus Clustern
		
		for(int i = 0; i < inst.numInstances(); i++){
		   System.out.println("Cluster:" + i + "\n");
		   cluster = inst.get(i).toString();			//to String aus Cluster Instanz
		   attr =  Arrays.asList(cluster.split("\\s*,\\s*"));	
		   res.add(attr);						//Cluster der ClusterListe hinzuf�gen
		 
		    }
		
		diagrammData.addAll(res);
		
	    
	    
	}
	public static void addKatToDiagrammData(int[] array){  
		
		ArrayList<String> kat = new ArrayList<String>();
		
		for(int i = 0; i < array.length; i++){
			kat.add(katNamen[array[i]]);			// f�gt die Namen der gew�hlten Kategorien hinzu
		}
		diagrammData.add(0,kat);		// f�gt die Liste der gew�hlten Kategorien den Daten hinzu
	}

	public void buildEM(int anzahl) throws Exception {
		EM em = new EM();
		em.setNumClusters(anzahl);
		em.buildClusterer(trainingSubset);
		System.out.println(em);
		
	}

}
