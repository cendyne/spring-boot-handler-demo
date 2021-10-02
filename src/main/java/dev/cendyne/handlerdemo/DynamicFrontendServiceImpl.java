package dev.cendyne.handlerdemo;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DynamicFrontendServiceImpl implements DynamicFrontendService {
    private ConcurrentHashMap<String, String> contents = new ConcurrentHashMap<>();

    @Override
    public Optional<String> getHtmlForRoute(String uri) {
        return Optional.ofNullable(contents.get(uri));
    }

    @Override
    public void addHtml(@NonNull String uri, @NonNull String html) {
        contents.put(uri, html);
    }
}
