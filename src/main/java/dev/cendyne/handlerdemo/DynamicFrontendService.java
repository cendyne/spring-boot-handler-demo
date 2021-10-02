package dev.cendyne.handlerdemo;

import java.util.Optional;

public interface DynamicFrontendService {
    Optional<String> getHtmlForRoute(String uri);
    void addHtml(String uri, String html);
}
