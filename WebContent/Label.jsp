<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="data.Label"%>

<%
	StringBuffer sb = new StringBuffer();
	StringBuffer sb1 = new StringBuffer();
	Label label = new Label();
	label.loadCombo();
	label.addLabel("Test");
	label.addLabel(request.getParameter("neu"));

	for (String s : label.listItems) {
		sb.append("<option value=\"" + s + "\"><" + s + "></option>\n");
	}
	System.out.println(sb.toString());

	/*sb1.append("<label for=\"select1\">Label</label>"			
			+ "<select id=\"select1\" name=\"auswahl\">"
			+ "<option value=\"0\">-- Auswahl --</option>"
			+ sb.toString()
			+ "</select><br>"
			+ "<input type=\"text\"  placeholder=\"neues Label\">"
			+ "<input type=\"submit\" value=\"Hinzufügen\">");
	System.out.println(sb1);*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- Select -->

	<label for="select1">Label</label>
	<select id="select1" name="auswahl">
		<option value="0">-- Auswählen --</option>
		<%=sb.toString()%>
	</select>
	<br>
	<form action="Label.jsp" method="post">
		<input type="text" placeholder="neues Label" name="neu"> <input
			type="submit" value="Hinzufügen">
	</form>
</body>
</html>