package net.zylk.sinadura.cloud.rest.cors;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RestCorsResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
		responseCtx.getHeaders().add("Access-Control-Allow-Origin", requestCtx.getHeaderString("Origin"));
		responseCtx.getHeaders().add("Access-Control-Allow-Credentials", true);
		responseCtx.getHeaders().add("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Cache-Control");
		responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
	}

}
