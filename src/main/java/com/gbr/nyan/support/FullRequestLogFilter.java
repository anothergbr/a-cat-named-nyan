package com.gbr.nyan.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.util.Collections.list;

public class FullRequestLogFilter {
    private final static Logger logger = LoggerFactory.getLogger(FullRequestLogFilter.class);

    /**
     * lifted from : http://stackoverflow.com/questions/28723425/logging-controller-requests-spring-boot
     */
    public static FilterRegistrationBean aFullRequestLogFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {
                logger.info("Init logger Request Filter");
            }

            private void logRequest(HttpServletRequest request) throws IOException {
                logger.debug("### Request Headers:");
                for (String header : list(request.getHeaderNames())) {
                    logger.debug("\t* {}: {}", new Object[]{header, request.getHeader(header)});
                }

                logger.debug("### Request Parameters:");
                for (String paramName : list(request.getParameterNames())) {
                    logger.debug("\t* {}: {}", new Object[]{paramName, request.getParameter(paramName)});
                }
            }

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                if (logger.isDebugEnabled()) {
                    logRequest((HttpServletRequest) request);
                }
                chain.doFilter(request, response);
            }

            @Override
            public void destroy() {
                logger.debug("Destroy logger Request Filter");
            }
        });
        return registration;
    }
}
