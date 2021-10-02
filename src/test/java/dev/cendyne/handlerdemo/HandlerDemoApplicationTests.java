package dev.cendyne.handlerdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HandlerDemoApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private DynamicFrontendService dynamicFrontend;

	@Test
	void dynamicUnmapped() {
		// Unmapped endpoints can receive new html
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/testing", String.class)).doesNotContain("this is a test");
		// Add test information
		dynamicFrontend.addHtml("/testing", "this is a test");
		// Request again
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/testing", String.class)).isEqualTo("this is a test");
	}

	@Test
	void dynamicMapped() {
		// API endpoints are not affected
		dynamicFrontend.addHtml("/api/endpoint", "this is a test");
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/api/endpoint", String.class)).contains("hello");
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/api/endpoint", String.class)).doesNotContain("this is a test");
	}
}
