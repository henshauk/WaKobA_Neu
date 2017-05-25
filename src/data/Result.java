package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Klasse zum speichern der Ergebnisse
 *
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<List<String>> diagrammData = new ArrayList<List<String>>();
	
	public Result(List<List<String>> list){
		diagrammData = list;
	}
	
	public List<List<String>> getDiagrammData(){
		return diagrammData;
	}

}
