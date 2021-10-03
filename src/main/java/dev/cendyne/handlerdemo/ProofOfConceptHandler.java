package dev.cendyne.handlerdemo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class ProofOfConceptHandler extends AbstractHandlerMapping implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        setOrder(11);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        if (uri.equals("/proof-of-concept")) {
            return new SimpleStaticHandler("text/plain", "Proof!");
        }
        return null;
    }
}
