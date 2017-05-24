package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<List<String>> diagrammData = new ArrayList<List<String>>();
	private String dataTable="";
	
	public Result(List<List<String>> list,String table){
		diagrammData = list;
		dataTable = table;
	}

}
