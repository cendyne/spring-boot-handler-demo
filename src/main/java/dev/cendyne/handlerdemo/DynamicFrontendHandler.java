package dev.cendyne.handlerdemo;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DynamicFrontendHandler extends AbstractHandlerMapping implements InitializingBean {
    private final DynamicFrontendService dynamicFrontend;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    @org.springframework.beans.factory.annotation.Value("${frontend.static-paths:}")
    String staticPathsStr;

    List<String> staticPaths;

    @Override
    public void afterPropertiesSet() throws Exception {
        setOrder(10);

        if (staticPathsStr != null && !staticPathsStr.isBlank()) {
            staticPaths = Arrays.asList(staticPathsStr.split(","));
        } else {
            staticPaths = Collections.emptyList();
        }
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        // Do not consider static paths
        for (String staticPath : staticPaths) {
            if (antPathMatcher.match(staticPath, uri)) {
                return null;
            }
        }

        return dynamicFrontend.getHtmlForRoute(uri)
                .map(Handler::new)
                .orElse(null);
    }

    @Value
    private static class Handler implements HttpRequestHandler {
        String responseContent;

        @Override
        public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setStatus(200);
            response.setContentType("text/html");
            try (var writer = response.getWriter()) {
                writer.write(responseContent);
            }
        }
    }
}
