<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%
	List<String> kategorie = new ArrayList<String>();
	kategorie.add("Geschlecht");
	kategorie.add("Alter");
	kategorie.add("Bier");
	kategorie.add("Brot");
	List<String> cl1 = new LinkedList<String>();
	kategorie.add("m");
	kategorie.add("30-40");
	kategorie.add("37.3");
	kategorie.add("7.7");
	List<String> cl2 = new LinkedList<String>();
	kategorie.add("w");
	kategorie.add("50-60");
	kategorie.add("19.99");
	kategorie.add("85");

	StringBuffer bf = new StringBuffer();
	bf.append("<tr><div class=col_6><td><div class=col_6 id=container style=width: 400px; "
			+ "height: 275px; margin: 0 auto></div>" + "<script type=text/javascript language=JavaScript>"

			+ "$(document).ready(function() {" + "var chart = {" + "plotBackgroundColor: null,"
			+ "plotBorderWidth: null," + "plotShadow: false};"

			+ "var title = {text: ''};" + " var tooltip = {"
			+ " pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'};" + " var plotOptions = { pie: {"
			+ "     allowPointSelect: true,cursor: 'pointer',dataLabels: {"
			+ " enabled: true}, showInLegend: true } };"
			+ " var series= [{type: 'pie', name: 'Anteil am Einkaufswert'," + "  data: [ ['Bier',   80.0],"
			+ "['Pizza',       40.8]," + " ['Akkuschrauber', 10.8]," + "['Brot',    10.5],"
			+ " ['Lappen',     10.2]," + " ['Others',   10.7]]" + " }]; "

			+ "var json = {};   " + "		   json.chart = chart; " + "		   json.title = title;"
			+ "		   json.tooltip = tooltip;" + "		   json.series = series;"
			+ "		   json.plotOptions = plotOptions;" + "		   $('#container').highcharts(json);" + "	});"
			+ "	</script></td></div>" + "<div class=col_6><td><ul class=alt><li>Geschlecht: m</li>"
			+ "<li>Alter: 30</li><li>Kinder: 7</li><li>Familienstand: ledig</li><li>Berufstätig: ja</li><li>Nettoeinkommen: 1001"
			+ "</li><li>Fernsehkonsum: 7</li><li>Einkaufstag: Freitag</li><li>Einkaufsuhrzeit: 7 Uhr</li></ul></td></div>"
			+ "</tr>");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
		<table>
			<thead></thead>
			<tbody>
				<%=bf.toString()%>
			</tbody>
		</table>

	</div>

</body>
</html>