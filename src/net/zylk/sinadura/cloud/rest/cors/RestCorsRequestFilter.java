package net.zylk.sinadura.cloud.rest.cors;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RestCorsRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		// When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
		if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
			// Just send a OK signal back to the browser
			requestCtx.abortWith(Response.status(Response.Status.OK).build());
		}
	}
}
