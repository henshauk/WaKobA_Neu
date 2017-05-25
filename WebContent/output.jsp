<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="data.Wekabuilder"%>
<%
	StringBuffer auswertungen = new StringBuffer();
	List<String> stored = Wekabuilder.resultNames;

	for (String name : stored) {
		auswertungen.append("<li><a href=output.jsp?store=" + name + ">" + name + "</a></li>");
	}

	List<List<String>> data = new LinkedList<List<String>>();
	Enumeration<String> param = request.getParameterNames();
	String name = "";

	if (param.hasMoreElements()) {
		if (param.nextElement().equals("store")) { //  pr�fen ob gespeichertes Ergebnis aufgerufen wird
			name = request.getParameter("store");
		}
	}
	if (name.length() > 0) {
		data = Wekabuilder.getStoredData(name);
	} else {
		data = Wekabuilder.diagrammData; //Daten f�r die Auswertung liegen komplett in diagrammData
	}

	List<String> k�uferdaten = new LinkedList<String>(); //  Vergleichsliste um die Daten 
	k�uferdaten.add("Geschlecht"); //  zwischen Person und Ware zu trennen
	k�uferdaten.add("Alter");
	k�uferdaten.add("Kinder");
	k�uferdaten.add("Familienstand");
	k�uferdaten.add("Berufst�tig");
	k�uferdaten.add("Haushaltsnettoeinkommen");
	k�uferdaten.add("Fernsehkonsum");
	k�uferdaten.add("Einkaufstag");
	k�uferdaten.add("Einkaufsmonat");
	k�uferdaten.add("Einkaufsuhrzeit");
	k�uferdaten.add("Einkaufssumme");

	Set<String> lab = new HashSet<String>();
	lab.add("Studenten");
	lab.add("Rentner");
	lab.add("Hausfrauen");
	Iterator<String> labels = lab.iterator();
	StringBuffer label = new StringBuffer(); //  create content for Label dropdown
	int j = 0;
	while (labels.hasNext()) {
		label.append("<option value=" + (++j) + ">" + labels.next() + "</option>");
	}

	StringBuffer sB = new StringBuffer(); //  create the chart
	StringBuffer sB1; //  create the table

	int i = 0;
	Iterator<List<String>> listen = data.iterator();
	List<String> kategorie = listen.next();
	while (listen.hasNext()) {
		sB1 = new StringBuffer();
		Iterator<String> kategorie_it = kategorie.iterator();
		List<String> cluster = listen.next();
		Iterator<String> cluster_it = cluster.iterator();

		sB.append("<tr><td></td><td></td><td width=450><div id=container" + String.valueOf(i)
				+ " style=width: 450px; " + "height: 300px; margin: 0 auto></div>"
				+ "<script type=text/javascript language=JavaScript>"

				+ "$(document).ready(function() {" + "var chart = {" + "plotBackgroundColor: null,"
				+ "plotBorderWidth: null," + "plotShadow: false};"

				+ "var title = {text: ''};" + " var tooltip = {"
				+ " pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'};"
				+ " var plotOptions = { pie: {" + "     allowPointSelect: true,cursor: 'pointer',dataLabels: {"
				+ " enabled: true}, showInLegend: true } };"
				+ " var series= [{type: 'pie', name: 'Anteil am Einkaufswert', data: [");
		sB1.append("<td></td><td><ul class=alt>");

		while (kategorie_it.hasNext()) {
			String kateg = kategorie_it.next();
			String value = cluster_it.next();

			if (k�uferdaten.contains(kateg)) { //  pr�fen ob es sich um Personenbezogene Werte handelt
				sB1.append("<li>" + kateg + ":" + value + "</li>");
			} else { 
				sB.append("['" + kateg + "'," + value + "]");

				if (kategorie_it.hasNext()) {
					sB.append(",");
				} else {
					sB1.append("<li></li><li><label for=label>Label</label> <select id=label>"
							+ "<option value=0>Auswahl</option>" + label
							+ "</select></li></ul></td><td></td></tr><tr></tr>");
				}
			}

		}
		sB.append("]} ];"); //  ende der Diagrammdaten

		sB.append(" var json = {};   " + " json.chart = chart; " + " json.title = title;"
				+ " json.tooltip = tooltip;" + " json.series = series;" + " json.plotOptions = plotOptions;"
				+ " $('#container" + String.valueOf(i++) + "').highcharts(json);" + "	});"
				+ "	</script></td>" + "<td></td>");
		sB.append(sB1);

	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<!-- CSS -->
<link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />
<link rel="stylesheet" type="text/css" href="style.css" media="all" />
	<!-- Javascript -->
<script type="text/javascript" src="js/kickstart.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>

<title>Warenkorb Analyse</title>
</head>

<body>
	<h4 align=center>Ergebnis der Analyse</h4>

	<div class="grid">
		<section>
		<ul class="tabs left">
			<li><a href="#new">Grafisch</a></li>
			<li><a href="#last">Tabelle</a></li>
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
			Hallo Dude kein Tab da
			<%=auswertungen.toString()%>
		</div>
		</section>
	</div>
</body>
</html>