<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="data.Wekabuilder"%>
<%@ page import="data.Authentifi"%>
<%
	try {
		if (!Authentifi.berechtigt.get(request.getSession().getId())) {
			response.sendRedirect("login.html");
		}
	} catch (Exception e) {
		response.sendRedirect("login.html");
		return;
	}

	List<List<String>> data = new LinkedList<List<String>>();
	Enumeration<String> param = request.getParameterNames();
	String name = "";

	if (param.hasMoreElements()) {
		if (param.nextElement().equals("store")) { //  prüfen ob gespeichertes Ergebnis aufgerufen wird
			name = request.getParameter("store");
		}
	}
	if (name.length() > 0) {
		data = Wekabuilder.getStoredData(name);
	} else {
		data = Wekabuilder.diagrammData; //Daten für die Auswertung liegen komplett in diagrammData
	}

	StringBuffer table = new StringBuffer();
	int currentRow = 0;
	int numRows = data.get(0).size();
	int currentCluster;
	int numCluster = (data.size());
	while (currentRow < numRows) { //  Zeilenweise Tabelle mit allen Werten füllen
		table.append("<tr><td>" + data.get(0).get(currentRow) + "</td>");
		currentCluster = 1;
		while (currentCluster < numCluster) { //  Tabellenspalte für jeden weiteren Cluster erstellen
			table.append("" + "<td>" + data.get(currentCluster).get(currentRow) + "</td>");
			currentCluster++;
		}
		table.append("</tr>");
		currentRow++;
	}

	List<String> käuferdaten = new LinkedList<String>(); //  Vergleichsliste um die Daten 
	käuferdaten.add("Geschlecht"); //  zwischen Person und Ware zu trennen
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

	// Pfad
	System.out.println("JSP-Kontext: " + getServletContext().getRealPath("/WEB-INF"));

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

	StringBuffer sB = new StringBuffer(); //  erstellen des Diagramms
	StringBuffer sB1; //  erstellen der tabelle

	int i = 0;
	Iterator<List<String>> listen = data.iterator();
	List<String> kategorie = listen.next();
	while (listen.hasNext()) { //   für jeden Cluster
		sB1 = new StringBuffer();
		Iterator<String> kategorie_it = kategorie.iterator();
		List<String> cluster = listen.next();
		Iterator<String> cluster_it = cluster.iterator();

		//  Rahmen für Diagramm
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

		while (kategorie_it.hasNext()) { //  Diagramm und Tabelle füllen
			String kateg = kategorie_it.next();
			String value = cluster_it.next();

			if (käuferdaten.contains(kateg)) { //  prüfen ob es sich um Personenbezogene Werte handelt
				sB1.append("<li>" + kateg + ":" + value + "</li>");
			} else {
				sB.append("['" + kateg + "'," + value + "]");

				if (kategorie_it.hasNext()) {
					sB.append(",");
				} else {
					sB1.append("<li></li><li> <select id=label>" //  Dropbox für Label
							+ "<option value=0>Auswahl</option>" + label
							+ "</select><label for=label>--Label</label></li>");
					sB1.append("<li> <select id=marketing>" //  Dropbox für Marketingvorschläge
							+ "<option value=0>Auswahl</option>" + label
							+ "</select><label for=marketing>--Marketing</label></li></ul></td><td></td></tr><tr></tr>");
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

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="css/kickstart.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="style.css" media="all" />
<!-- Javascript -->
<script type="text/javascript" src="js/kickstart.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<meta charset="utf-8">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Warenkorb Analyse</title>
</head>

<body>
	
	<div class="grid">
	<header>
		<h3>Ergebnis der Analyse</h3><hr />
	</header>
		<table>
			<tr>
				<td>
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#grafik">Grafik</a></li>
						<li><a data-toggle="tab" href="#tabelle">Tabelle</a></li>
					</ul>
				</td>
				<td></td>
				<td align=right><ul class="nav nav-tabs">
						<li><form action=upload.jsp>
								<button type="submit" value="Submit">Neue Analyse</button>
							</form></li>
						<li><form action=AuthentificationServlet method=GET>
								<button type="submit" value="Submit">Logout</button>
							</form></li>
					</ul></td>
			</tr>
		</table>

		<div class="tab-content">
			<div id="grafik" class="tab-pane fade in active">
				<table>
					<thead></thead>
					<tbody>
						<%=sB.toString()%>
					</tbody>
				</table>
			</div>
			<div id="tabelle" class="tab-pane fade" style="overflow-y:auto ">
				<table>
					<thead></thead>
					<tbody>
						<%=table.toString()%>
					</tbody>
				</table>
			</div>
		</div>
		<footer>
			<p align="center">Entwickelt von V-Men Software für KaufDort</p>
			<p align="center">
				<img src="images/KaufDort_V1.png" width="150" height="150" /> <img
					src="images/V-Men_V1.png" width="150" height="150" />
			</p>
		</footer>
	</div>


</body>
</html>