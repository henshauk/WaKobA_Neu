<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="data.Wekabuilder" %>
<%


	List<List<String>> data = new LinkedList<List<String>>();
	data = Wekabuilder.diagrammData;  //Daten für die Auswertung liegen komplett in diagrammData

	List<String> käuferdaten = new LinkedList<String>();
	käuferdaten.add("Geschlecht");
	käuferdaten.add("Alter");
	käuferdaten.add("Kinder");
	käuferdaten.add("Familienstand");
	käuferdaten.add("Berufstätig");
	käuferdaten.add("Haushaltsnettoeinkommen");
	käuferdaten.add("Fernsehkonsum");
	käuferdaten.add("Einkaufstag");
	käuferdaten.add("Einkaufsmonat");
	käuferdaten.add("Einkaufsuhrzeit");
	käuferdaten.add("Einkaufssumme");
	

	Set<String> lab = new HashSet<String>();
	lab.add("Studenten");
	lab.add("Rentner");
	lab.add("Hausfrauen");
	Iterator<String> labels = lab.iterator();
	StringBuffer label = new StringBuffer();		//  create content for Label dropdown
	int j =0;
	while(labels.hasNext()){
		label.append("<option value="+(++j)+">"+labels.next()+"</option>");
	}


	StringBuffer sB = new StringBuffer();		//  create the chart
	StringBuffer sB1;							//  create the table
	
	
	int i =0;
			Iterator<List<String>> listen = data.iterator();
			List<String> kategorie = listen.next();
			while(listen.hasNext()){
				sB1 = new StringBuffer();
				Iterator<String> kategorie_it = kategorie.iterator();
				List<String> cluster = listen.next();
				Iterator<String> cluster_it = cluster.iterator();
	
				sB.append("<tr><td></td><td></td><td width=450><div id=container"+String.valueOf(i)+" style=width: 450px; "
						+ "height: 300px; margin: 0 auto></div>" + "<script type=text/javascript language=JavaScript>"

						+ "$(document).ready(function() {" + "var chart = {" + "plotBackgroundColor: null,"
						+ "plotBorderWidth: null," + "plotShadow: false};"

						+ "var title = {text: ''};" + " var tooltip = {"
						+ " pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'};" + " var plotOptions = { pie: {"
						+ "     allowPointSelect: true,cursor: 'pointer',dataLabels: {"
						+ " enabled: true}, showInLegend: true } };"
						+ " var series= [{type: 'pie', name: 'Anteil am Einkaufswert', data: [");
				sB1.append("<td></td><td><ul class=alt>");
	
				while(kategorie_it.hasNext()){
					String kateg = kategorie_it.next();
					String value = cluster_it.next();
					
					if(käuferdaten.contains(kateg)){			//  data about persons
						sB1.append("<li>"+kateg+":"+value+"</li>");
					}
					else {										// data on goods
					sB.append("['"+kateg+"',"+value+"]");
					
						if(kategorie_it.hasNext()){
							sB.append(",");
						}
						else{
							sB1.append("<li></li><li><label for=label>Label</label> <select id=label>"
									+"<option value=0>Auswahl</option>"+label+"</select></li></ul></td><td></td></tr><tr></tr>");
						}
					}
					
				}
				sB.append("]} ];");     //  end of diagramm data
				
				sB.append(" var json = {};   " + " json.chart = chart; " + " json.title = title;"
						+ " json.tooltip = tooltip;" + " json.series = series;"
						+ " json.plotOptions = plotOptions;" + " $('#container"+String.valueOf(i++)+"').highcharts(json);" + "	});"
						+ "	</script></td>" + "<td></td>");
				sB.append(sB1);
					
			}
			
	    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/kickstart.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="style.css" media="all" />
<script type="text/javascript" src="js/kickstart.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<title>Warenkorb Analyse</title>
</head>
<header>
<h4 align=center>Ergebnis der Analyse</h4>
</header>

<body>
	<div class="grid">
		<ul class="tabs left">
			<li><a href="#new">Grafisch</a></li>
			<li><a href="#last">Werte</a></li>
		</ul>

		<div id="new" class="tab-content">

			<table cellpadding="">
				<thead></thead>
				<tbody>
					<%=sB.toString()%>
				</tbody>
			</table>

		</div>
		<div id="last" class="tab-content">
			Hallo Dude
			<%=Wekabuilder.table%>
		</div>
	</div>
</body>
</html>