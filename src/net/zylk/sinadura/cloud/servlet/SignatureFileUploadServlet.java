package net.zylk.sinadura.cloud.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import net.zylk.sinadura.cloud.dao.DaoTransactionFactory;
import net.zylk.sinadura.cloud.exceptions.DaoCoreException;


/**
 * 
 * El mappgin del servlet es:
 * /rest/v1/transactions/signaturefile-upload/*
 * 
 * Pero el path completo es:  
 * /rest/v1/transactions/signaturefile-upload/{transaction-id}/add"
 *
 */
public class SignatureFileUploadServlet extends HttpServlet {

	private static final Logger _LOG = Logger.getLogger(SignatureFileUploadServlet.class.getName());

	private static final long serialVersionUID = 1L;

	public SignatureFileUploadServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			String url = request.getRequestURL().toString();
			_LOG.info("request url: " + url);
			String[] array = url.split("/");
			String uuid = array[array.length - 2];
			_LOG.info("uuid: " + uuid);
			
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			
			
			String idDocument = null; 
			
			for (FileItem item : items) {
			
			    if (item.isFormField()) {

			    	if (item.getFieldName().equals("idDocument")) {
			    		
						// Este parametro esta siempre antes que el file (aunque en el spec no sea así realmente). Testar de todas formas.			    		
			    		idDocument = item.getString();
			    		_LOG.info("idDocument: " + idDocument);
			    	}
			    	
			    } else {
			    	
					if (item.getFieldName().equals("file")) {
						
						if (idDocument != null) {
							
							String fileName = FilenameUtils.getName(item.getName());
							InputStream fileContent = item.getInputStream();

							DaoTransactionFactory.getInstance().addSignatureFile(uuid, idDocument, fileContent);
						
							_LOG.info("fichero subido correctamente");

							break;
							
						} else {
							throw new ServletException("orden incorrecto en los parametros de la request multipart");
						}
					}	
			    }   				
			}
			
		} catch (FileUploadException e) {
			_LOG.error(e);
			throw new ServletException(e);
		} catch (DaoCoreException e) {
			_LOG.error(e);
			throw new ServletException(e);
		} catch (IOException e) {
			_LOG.error(e);
			throw e;
		} catch (ServletException e) {
			_LOG.error(e);
			throw e;	
		} catch (RuntimeException e) {
			_LOG.error(e);
			throw e;
		}

	}

}
