package io.opentelemetry.javainstrumentationissue7344;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomMdcW3cTraceFilter implements Filter {
    public static final String W3C_TRACE_HEADER = "traceparent";
    public static final String W3C_TRACE_VERSION = "00";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String header = httpServletRequest.getHeader(W3C_TRACE_HEADER);
            if (Strings.isNotBlank(header)) {
                String[] traceFields = header.split("-");
                if (W3C_TRACE_VERSION.equals(traceFields[0]) && traceFields.length == 4) {
                    setMyMdc(traceFields[1], traceFields[2]);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private void setMyMdc(String traceId, String parentId) {
        MDC.put("myTraceId", traceId);
        MDC.put("mySpanId", parentId);
        log.info("MDC set from received W3C Trace Context");
    }
}
