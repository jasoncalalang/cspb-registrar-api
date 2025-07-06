package ph.edu.cspb.registrar.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            if (log.isDebugEnabled()) {
                String reqBody = getPayload(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());
                String resBody = getPayload(wrappedResponse.getContentAsByteArray(), response.getCharacterEncoding());
                log.debug("Request path: {} body: {}", request.getRequestURI(), reqBody);
                log.debug("Response body: {}", resBody);
            }
            wrappedResponse.copyBodyToResponse();
        }
    }

    private String getPayload(byte[] buf, String encoding) {
        if (buf == null || buf.length == 0) {
            return "";
        }
        Charset charset = (encoding != null) ? Charset.forName(encoding) : Charset.defaultCharset();
        return new String(buf, charset);
    }
}
