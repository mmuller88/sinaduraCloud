package net.zylk.sinadura.cloud.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.zylk.sinadura.cloud.model.ErrorVO;

/**
 * Se traduce en un error 500 (Internal server error). Para los errores de aplicacion no controlados. Se devuelve un objeto json
 * (ErrorVO) con los detalles el error.
 * 
 */
public class RestCoreException extends WebApplicationException {

	public static final String ERR_WS_INTERNAL = "ERR_WS_INTERNAL"; // default
	
	public RestCoreException(Exception e) {
		
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorVO(ERR_WS_INTERNAL, e.toString())).type(MediaType.APPLICATION_JSON).build());
	}

	public RestCoreException(ErrorVO error) {
		
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.APPLICATION_JSON).build());
	}

}
