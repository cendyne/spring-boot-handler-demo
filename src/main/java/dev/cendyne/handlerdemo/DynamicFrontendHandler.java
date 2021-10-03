package dev.cendyne.handlerdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DynamicFrontendHandler extends AbstractHandlerMapping implements InitializingBean {
    private final DynamicFrontendService dynamicFrontend;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    @Value("${frontend.static-paths:}")
    String staticPathsStr;

    List<String> staticPaths = Collections.emptyList();

    @Override
    public void afterPropertiesSet() throws Exception {
        setOrder(10);

        if (staticPathsStr != null && !staticPathsStr.isBlank()) {
            staticPaths = Arrays.asList(staticPathsStr.split(","));
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
                .map(content -> new SimpleStaticHandler("text/html", content))
                .orElse(null);
    }
}
