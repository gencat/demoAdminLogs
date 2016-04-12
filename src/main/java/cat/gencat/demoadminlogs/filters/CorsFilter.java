package cat.gencat.demoadminlogs.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CORS request filter.
 */
public class CorsFilter implements Filter {

	/**
	 * This method is invoked by the web container to initialise the filter at
	 * startup.
	 * 
	 * @param filterConfig
	 *            The servlet filter configuration. Must not be {@code null}.
	 * 
	 * @throws ServletException
	 *             On a filter initialisation exception.
	 */
	public void init(final FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	/**
	 * 
	 * @param request
	 *            The servlet request.
	 * @param response
	 *            The servlet response.
	 * @param chain
	 *            The servlet filter chain.
	 * 
	 * @throws IOException
	 *             On a I/O exception.
	 * @throws ServletException
	 *             On a general request processing exception.
	 */
	private void doFilter(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers",
				"origin, content-type, accept, authorization");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.addHeader("Access-Control-Max-Age", "1209600");

		chain.doFilter(request, response);
	}

	/**
	 * Called by the servlet container each time a request / response pair is
	 * passed through the chain due to a client request for a resource at the
	 * end of the chain.
	 * 
	 * @param request
	 *            The servlet request.
	 * @param response
	 *            The servlet response.
	 * @param chain
	 *            The servlet filter chain.
	 * 
	 * @throws IOException
	 *             On a I/O exception.
	 * @throws ServletException
	 *             On a general request processing exception.
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		if (request instanceof HttpServletRequest
				&& response instanceof HttpServletResponse) {
			// Cast to HTTP
			doFilter((HttpServletRequest) request,
					(HttpServletResponse) response, chain);

		} else {
			throw new ServletException(
					"Cannot filter non-HTTP requests/responses");
		}
	}

	/**
	 * Called by the web container to indicate to a filter that it is being
	 * taken out of service.
	 */
	public void destroy() {
		// do nothing
	}
	
}