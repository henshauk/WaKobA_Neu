package servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

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

		response.sendRedirect("output.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String algorithmus = "";
		int anzahlCluster = 0;
		List<Integer> kategorien = new ArrayList<Integer>();

		if (ServletFileUpload.isMultipartContent(request)) {

			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// read ServletPath and build upload repository
			ServletContext servletContext = this.getServletConfig().getServletContext();
			String servletPath = servletContext.getRealPath("/WEB-INF");
			String uploadPath = servletPath + File.separator + UPLOAD_DIRECTORY;
			File repository = new File (uploadPath);
			if (!repository.exists()) {
				repository.mkdir();
			}
			factory.setRepository(repository);
	
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			String filePath = null;

			// Parse the request
			try {
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (!item.isFormField()) {
						if (item.getSize() != 0) {

							String fileName = item.getName();
							long sizeInBytes = item.getSize();
							System.out.println(fileName + " size:" + sizeInBytes);

							Date d = new Date();
							SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
							filePath = uploadPath + File.separator + ft.format(d) + "__" + fileName;
							File storeFile = new File(filePath);
							System.out.println("Upload " + filePath);

							// saves the file on disk
							item.write(storeFile);
						}
					}
					if (item.isFormField()) {
						String name = item.getFieldName();
						if (name.equals("radio")) {
							algorithmus = item.getString();
							System.out.println("algorithmus " + algorithmus);

						}
						if (name.equals("anzahl")) {
							anzahlCluster= Integer.parseInt(item.getString());
							System.out.println("anzahl: " + anzahlCluster);
						}
						if (name.equals("kategorie")) {						
							kategorien.add(Integer.parseInt(item.getString()));		//add number if checkbox checked				
						}
					}

					// System.out.println("input " + item.getFieldName());				 
				}
				
				Wekabuilder wb = new Wekabuilder(filePath, servletPath);
				
				 int[] kategorienArray = ArrayUtils.toPrimitive(kategorien.toArray(new Integer[kategorien.size()])); 
				 System.out.println("Kategoriefilter: "+Arrays.toString(kategorienArray));
				 wb.filter(kategorienArray);
			
				 if(algorithmus.equals("a")){
					 try {
							wb.buildSKM(anzahlCluster);
						} catch (Exception e) {
							e.printStackTrace();
						}
				 }
				 else if(algorithmus.equals("b")){
					 try {
							wb.buildFF(anzahlCluster);
						} catch (Exception e) {
							e.printStackTrace();
						}
				 }
				 else if(algorithmus.equals("c")){
					 try {
							wb.buildEM(anzahlCluster);
						} catch (Exception e) {
							e.printStackTrace();
						}
				 }
				 
				 clearFiles(repository);
				 
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
	
	private void clearFiles(File repository){
		String[] files = repository.list();
		 for(String s: files){
			    File currentFile = new File(repository.getPath(),s);
			    System.out.println(s);
			    System.out.println("löschen: "+currentFile.delete());
			    
			}
	}

}
