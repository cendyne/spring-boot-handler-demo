package dev.cendyne.handlerdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasicController {
    private final DynamicFrontendService dynamicFrontendService;

    @GetMapping("/api/endpoint")
    public EndpointResponse endpoint() {
        return new EndpointResponse("hello");
    }

    @PutMapping("/api/html")
    public EndpointResponse putHtml(@RequestBody AddHtmlRequest addHtmlRequest) {
        dynamicFrontendService.addHtml(addHtmlRequest.uri(), addHtmlRequest.html());
        return new EndpointResponse("OK");
    }

    public record EndpointResponse(String message) {}

    public record AddHtmlRequest(String uri, String html) {}
}
