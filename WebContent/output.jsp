<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="data.Wekabuilder" %>
<%
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
	StringBuffer label = new StringBuffer();
	String current;
	int j =0;
	while(labels.hasNext()){
		label.append("<option value="+(++j)+">"+labels.next()+"</option>");
	}
	List<String> kat = new ArrayList<String>();
	kat.add("Geschlecht");
	kat.add("Alter");
	kat.add("Kinder");
	kat.add("Familienstand");
	kat.add("Berufstätig");
	kat.add("Haushaltsnettoeinkommen");
	kat.add("Fernsehkonsum");
	kat.add("Einkaufstag");
	kat.add("Einkaufsmonat");
	kat.add("Einkaufsuhrzeit");
	kat.add("Einkaufssumme");
	kat.add("Fertiggerichte");
	kat.add("Tiefkühlwaren");
	kat.add("Milchprodukte");
	kat.add("Backwaren");
	kat.add("Obst/Gemüse");
	kat.add("Spirituosen");
	kat.add("Tiernahrumg");
	kat.add("Bier");
	kat.add("Frischfleisch");
	kat.add("Drogerieartikel");
	kat.add("Konserven");
	kat.add("Kaffee/Tee");
	kat.add("Süßigkeiten");
	
	List<String> cl1 = new LinkedList<String>();
	cl1.add("1");
	cl1.add("30");
	cl1.add("85");
	cl1.add("37.3");
	cl1.add("85");
	cl1.add("7.7");
	cl1.add("1");
	cl1.add("37.3");
	cl1.add("7.7");
	cl1.add("7.7");
	cl1.add("7.7");
	List<String> cl2 = new LinkedList<String>();
	cl2.add("1");
	cl2.add("50");
	cl2.add("7.7");
	cl2.add("19.99");
	cl2.add("85");
	cl2.add("7.7");
	cl2.add("85");
	cl2.add("19.99");
	cl2.add("7.7");
	cl2.add("85");
	cl2.add("85");
	List<List<String>> data = new LinkedList<List<String>>();
	//data.add(kat);
	Wekabuilder.diagrammData.add(0,kat);
	//data.add(Wekabuilder.diagrammData.get(0));
	//data.add(Wekabuilder.diagrammData.get(1));
	//data.add(Wekabuilder.diagrammData.get(2));
	
	//data.add(cl1);
	//data.add(cl2);
	data = Wekabuilder.diagrammData;
	StringBuffer sB = new StringBuffer();
	StringBuffer sB1;
	
	
	
	int i =0;
			Iterator<List<String>> listen = data.iterator();
			List<String> kategorie = listen.next();
			while(listen.hasNext()){
				sB1 = new StringBuffer();
				Iterator<String> kategorie_it = kategorie.iterator();
				List<String> cluster = listen.next();
				Iterator<String> cluster_it = cluster.iterator();
				System.out.println(i);
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
					if(käuferdaten.contains(kateg)){
						sB1.append("<li>"+kateg+":"+value+"</li>");
					}
					else {
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
				sB.append("]} ];");
				
				sB.append(" var json = {};   " + " json.chart = chart; " + " json.title = title;"
						+ " json.tooltip = tooltip;" + " json.series = series;"
						+ " json.plotOptions = plotOptions;" + " $('#container"+String.valueOf(i++)+"').highcharts(json);" + "	});"
						+ "	</script></td>" + "<td></td>");
				sB.append(sB1);
					System.out.println(i+": "+sB);
					
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
	</div>
</body>
</html>