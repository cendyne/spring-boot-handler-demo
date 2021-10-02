package dev.cendyne.handlerdemo;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class DynamicFrontendHandler extends AbstractHandlerMapping implements InitializingBean {
    private final DynamicFrontendService dynamicFrontend;
    @Override
    public void afterPropertiesSet() throws Exception {
        setOrder(10);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        return dynamicFrontend.getHtmlForRoute(request.getRequestURI())
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
