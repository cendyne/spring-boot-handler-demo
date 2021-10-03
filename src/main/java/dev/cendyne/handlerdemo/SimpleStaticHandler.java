package dev.cendyne.handlerdemo;

import lombok.Value;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Value
public class SimpleStaticHandler implements HttpRequestHandler {
    String contentType;
    String responseContent;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(200);
        response.setContentType(contentType);
        try (var writer = response.getWriter()) {
            writer.write(responseContent);
        }
    }
}
