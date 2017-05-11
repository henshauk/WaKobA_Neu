package servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import data.Wekabuilder;


/**
 * Servlet implementation class DataServlet
 */
@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "upload";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		
		response.sendRedirect("auswertung.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		if (ServletFileUpload.isMultipartContent(request)) {

			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			String path = repository.getAbsolutePath();
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(request);

				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (!item.isFormField()) {
						String fileName = item.getName();
						long sizeInBytes = item.getSize();
						System.out.println(fileName + " size:" + sizeInBytes);

						Date d = new Date();
						SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
						String filePath = path + File.separator + ft.format(d) + "__" + fileName;
						File storeFile = new File(filePath);
						System.out.println("Upload " + filePath);

						// saves the file on disk
						item.write(storeFile);

						if (item.isFormField()) {
							String name = item.getFieldName();
							String value = item.getString();

							System.out.println(name + " " + value);
						}
						if (item.getName() == "anzahl") {
							System.out.println("anzahl: " + item.getString());
						}
						
						int anzahlCluster = 3;//Integer.parseInt(request.getParameter("anzahl"));
						Wekabuilder wb = new Wekabuilder(filePath);
						
						String auswahl = request.getParameter("radio");
						switch(auswahl){
							case "a": try {
									wb.buildSKM(anzahlCluster);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} break;
							case "b": 	try {
									wb.buildFF(anzahlCluster);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										break;
							case "c":	try {
									wb.buildEM(anzahlCluster);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
										break;
							}

					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				System.err.println("parse Request failed");
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		doGet(request, response);
	}

}
