package net.zylk.sinadura.cloud.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.zylk.sinadura.cloud.dao.DaoTransactionFactory;
import net.zylk.sinadura.cloud.exceptions.DaoCoreException;
import net.zylk.sinadura.cloud.exceptions.RestCoreException;
import net.zylk.sinadura.cloud.model.ConfigVO;
import net.zylk.sinadura.cloud.model.DocumentVO;
import net.zylk.sinadura.cloud.model.ErrorVO;
import net.zylk.sinadura.cloud.model.TransactionVO.TransactionStatus;

@Path("/v1")
public class SinaduraCloudRestServices {

	private static final Logger _LOG = Logger.getLogger(SinaduraCloudRestServices.class.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/version/get")
	public String getVersion() {

		String version = "1.0";

		return version;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/transactions/add")
	public String startTransaction(@Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			String uuid = DaoTransactionFactory.getInstance().create();

			return uuid;

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/transactions/{transaction-id}/error/add")
	public void setError(@PathParam("transaction-id") String uuid, @FormParam("code") String code,
			@FormParam("message") String message, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());

			DaoTransactionFactory.getInstance().setError(uuid, code, message);
			DaoTransactionFactory.getInstance().setStatus(uuid, TransactionStatus.TS_ERROR);

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/transactions/{transaction-id}/error/get")
	public ErrorVO getError(@PathParam("transaction-id") String uuid, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());

			ErrorVO error = DaoTransactionFactory.getInstance().getError(uuid);
			return error;

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@POST
	@Path("/transactions/{transaction-id}/remove")
	public void removeTransaction(@PathParam("transaction-id") String uuid, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			DaoTransactionFactory.getInstance().remove(uuid);

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/transactions/{transaction-id}/status/get")
	public String getStatus(@PathParam("transaction-id") String uuid, @Context UriInfo uriInfo) {

		try {
			_LOG.debug("calling: " + uriInfo.getPath());

			TransactionStatus status = DaoTransactionFactory.getInstance().getStatus(uuid);
			return status.toString();

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/transactions/{transaction-id}/status/set")
	public void setStatus(@PathParam("transaction-id") String uuid, @FormParam("status") String status, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			DaoTransactionFactory.getInstance().setStatus(uuid, TransactionStatus.valueOf(status));

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/transactions/{transaction-id}/config/get")
	public ConfigVO getConfig(@PathParam("transaction-id") String uuid, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			ConfigVO config = DaoTransactionFactory.getInstance().getConfig(uuid);
			return config;

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@POST
	@Path("/transactions/{transaction-id}/config/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void setConfig(@PathParam("transaction-id") String uuid,
			@FormParam("config") String config, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			ObjectMapper mapper = new ObjectMapper();
			ConfigVO configVO = mapper.readValue(config, ConfigVO.class);

			DaoTransactionFactory.getInstance().setConfig(uuid, configVO);

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (JsonParseException e) {
			throw handleException(e);
		} catch (JsonMappingException e) {
			throw handleException(e);
		} catch (IOException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/transactions/{transaction-id}/document/add")
	public String addDocument(@PathParam("transaction-id") String uuid, @FormParam("id") String id,
			@FormParam("type") String type, @FormParam("name") String name, @FormParam("url") String url,
			@Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			return DaoTransactionFactory.getInstance().addDocument(uuid, id, type, name, url);

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/transactions/{transaction-id}/documents/get")
	public List<DocumentVO> getDocuments(@PathParam("transaction-id") String uuid, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			return DaoTransactionFactory.getInstance().getDocuments(uuid);

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}


	@GET
	@Path("/transactions/{transaction-id}/signaturefile/get")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getSignatureFile(@PathParam("transaction-id") String uuid, @QueryParam("idDocument") String idDocument, @Context UriInfo uriInfo) {

		try {
			_LOG.info("calling: " + uriInfo.getPath());
			
			InputStream is = DaoTransactionFactory.getInstance().getSignatureFile(uuid, idDocument);
			ResponseBuilder response = Response.ok(is);
			// TODO revisar fileName (aunque no se utiliza)
			response.header("Content-Disposition", "attachment; filename=signature.pdf");

			return response.build();

		} catch (DaoCoreException e) {
			throw handleException(e);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	/**
	 * Tratamiento general para las excepciones de los servicios Rest.
	 * 
	 * Se devuelve una WebApplicationException en vez de hacer aqui dentro el throw de la excepcion, para que haya que hacer el
	 * throw fuera de forma explicita al invocar al metodo handleException, asi el codigo fuera queda mas limpio.
	 */
	private WebApplicationException handleException(Exception e) {

		_LOG.error("Error in rest service", e);
		return new RestCoreException(e);
	}

}
