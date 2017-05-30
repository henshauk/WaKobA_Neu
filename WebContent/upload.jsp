<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="data.*"%>
<%@ page import="java.util.*"%>
<%
	try { //  login prüfen
		if (!Authentifi.berechtigt.get(request.getSession().getId())) {
			response.sendRedirect("login.html");
		}
	} catch (Exception e) {
		response.sendRedirect("login.html");
		return;
	}

	//  für die upload Seite
	StringBuffer auswertungen = new StringBuffer();
	List<String> stored = new ArrayList<String>();

	Wekabuilder wb = new Wekabuilder(getServletContext().getRealPath("/WEB-INF"));
	stored = Wekabuilder.resultNames;

	for (String name : stored) {
		if (!name.equals("dummy")) {
			auswertungen.append("<li><a href=output.jsp?store=" + name + ">" + name + "</a></li>");
		}
	}
	if (auswertungen.length() == 0) {
		auswertungen.append("Es sind keine gespeicherten Daten vorhanden");
	}
	//
%>

<!DOCTYPE html>
<html>
<head>
<!-- META -->
<title>Projekt WaKobA</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="css/kickstart.css"
	media="all" />
<link rel="stylesheet" type="text/css" href="style.css" media="all" />

<!-- Javascript -->
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/kickstart.js"></script>
<script>
	imgOn = new Image;
	imgOn.src = "animation.gif";
</script>
</head>
<body>
	<div class="grid">
		<div class=col_8>
			<header>
				<h4>Warenkorbanalyse Kaufdort</h4>
			</header>
		</div>
		<div class=col_4>
			<table>
				<tr>
					<td></td>
					<td align=right><form action=AuthentificationServlet
							method=GET>
							<button type="submit" value="Submit">Logout</button>
						</form>
				</tr>
			</table>
		</div>
		<section>
			<!-- Tabs Left -->

			<ul class="tabs left">
				<li><a href="#new">Neue Analyse</a></li>
				<li><a href="#last">Letzte Auswertungen</a></li>
			</ul>

			<div id="new" class="tab-content">
				<form action="DataServlet" method="POST"
					enctype="multipart/form-data">

					<!-- Ordered List -->
					<ol>
						<li>
							<p>Datensatz auswählen</p> <input id="file1" class="file"
							type="file" name="file">
						</li>
						<li>
							<p>Art der Analyse / den Algorithmus auswählen</p>
							<p>
								<!-- Radio -->
								<input type="radio" name="radio" id="radio1" value="a" checked />
								<label for="radio1" class="inline">K-Means-Algorithmus</label>
							</p>
							<p>
								<input type="radio" name="radio" id="radio2" value="b" /> <label
									for="radio1" class="inline">Farthest-First-Algorithmus</label>
							</p>
						</li>
						<li>
							<!-- Text Field -->
							<p>Wie viele Cluster sollen gebildet werden?</p> <input
							id="text1" type="text" name="anzahl" value="4" maxlength="2" />
						</li>
						<li>
							<p>Attribute auswählen</p> <input type="checkbox"
							name="kategorie" value="0" checked> Geschlecht<br> <input
							type="checkbox" name="kategorie" value="1" checked> Alter<br>
							<input type="checkbox" name="kategorie" value="2" checked>
							Kinder<br> <input type="checkbox" name="kategorie" value="3"
							checked> Familienstand<br> <input type="checkbox"
							name="kategorie" value="4" checked> Berufstätig<br>
							<input type="checkbox" name="kategorie" value="5" checked>
							Haushaltsnettoeinkommen<br> <input type="checkbox"
							name="kategorie" value="6" checked> Fernsehkonsum<br>
							<input type="checkbox" name="kategorie" value="7" checked>
							Einkaufstag<br> <input type="checkbox" name="kategorie"
							value="8" checked> Einkaufsmonat<br> <input
							type="checkbox" name="kategorie" value="9" checked>
							Einkaufsuhrzeit<br> <input type="checkbox" name="kategorie"
							value="10" checked> Einkaufssumme<br> <input
							type="checkbox" name="kategorie" value="11" checked>
							Fertiggerichte<br> <input type="checkbox" name="kategorie"
							value="12" checked> Tiefkühlwaren<br> <input
							type="checkbox" name="kategorie" value="13" checked>
							Milchprodukte<br> <input type="checkbox" name="kategorie"
							value="14" checked> Backwaren<br> <input
							type="checkbox" name="kategorie" value="15" checked>
							Obst/Gemüse<br> <input type="checkbox" name="kategorie"
							value="16" checked> Spirituosen<br> <input
							type="checkbox" name="kategorie" value="17" checked>
							Tiernahrung<br> <input type="checkbox" name="kategorie"
							value="18" checked> Bier<br> <input type="checkbox"
							name="kategorie" value="19" checked> Frischfleisch<br>
							<input type="checkbox" name="kategorie" value="20" checked>
							Drogerieartikel<br> <input type="checkbox" name="kategorie"
							value="21" checked> Konserven<br> <input
							type="checkbox" name="kategorie" value="22" checked>
							Kaffe/Tee<br> <input type="checkbox" name="kategorie"
							value="23" checked> Süßigkeiten<br>
						</li>
					</ol>

					<p>
						<button type="reset" value="Reset">Zurücksetzen</button>
						<button type="submit" value="Submit"
							onClick="document.img.src=imgOn.src;">Analyse Starten</button>
						<img src="nichts.gif" name="img" width="80" height="80">
					</p>
				</form>
			</div>
			<div id="last" class="tab-content">
				<h6>Bitte wählen Sie die gewünschte Auswertung:</h6>
				</br>
				<!-- Ordered List -->

				<form action="RestoreServlet" method="POST" enctype="text/html">
					<ol>
						<%=auswertungen.toString()%>
					</ol>
				</form>
			</div>
			<!-- Tabs Left End -->

		</section>
		<footer>
			<p align="center">Entwickelt von V-Men Software für KaufDort</p>
			<p align="center">
				<img src="images/KaufDort_V1.png" width="150" height="150" /> <img
					src="images/V-Men_V1.png" width="150" height="150" />
			</p>
		</footer>
	</div>
	<!-- End Grid -->

</body>
</html>
