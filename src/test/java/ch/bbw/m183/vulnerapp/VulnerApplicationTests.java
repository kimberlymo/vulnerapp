package ch.bbw.m183.vulnerapp;

import ch.bbw.m183.vulnerapp.datamodel.BlogEntity;
import ch.bbw.m183.vulnerapp.service.LoginAttemptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VulnerApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void createBlogCsrfTest() {
		var csrfToken = "bobby123";

		webTestClient.post().uri("/api/blog")
				.headers(WebTestClientHelper::addAdminCredentialsToHeader)
				.header("X-XSRF-TOKEN", csrfToken)
				.cookie("XSRF-TOKEN", csrfToken)
				.bodyValue(new BlogEntity().setTitle("testasdf").setBody("from unit test"))
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void testLoginWithValidCredentials() {
		webTestClient.get().uri("/api/user/whoami")
				.headers(WebTestClientHelper::addUserCredentialsToHeader)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void testLoginWithInvalidCredentials() {
		webTestClient.get().uri("/api/user/whoami")
				.headers(WebTestClientHelper::addInvalidCredentialsToHeader)
				.exchange()
				.expectStatus()
				.isUnauthorized();
	}

	@Test
	public void getBlogPostsArePublic() {
		webTestClient.get().uri("/api/blog")
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
	}

	@Test
	public void getAllUsersWithoutAdminRights() {
		webTestClient.get().uri("/api/admin123/users")
				.headers(WebTestClientHelper::addUserCredentialsToHeader)
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	public void getAllUsersWithAdminRights() {
		webTestClient.get().uri("/api/admin123/users")
				.headers(WebTestClientHelper::addAdminCredentialsToHeader)
				.exchange()
				.expectStatus()
				.is2xxSuccessful();
	}

	@Test
	public void testLimitForLoginTrys() {
		for (int i = 0; i < LoginAttemptService.MAX_ATTEMPT; i++) {
			webTestClient.get().uri("/api/user/whoami")
					.headers(WebTestClientHelper::addInvalidPasswordCredentialsToHeader)
					.exchange()
					.expectStatus()
					.isUnauthorized();
		}

		webTestClient.get().uri("/api/user/whoami")
				.headers(WebTestClientHelper::addUserCredentialsToHeader)
				.exchange()
				.expectStatus()
				.isUnauthorized();
	}

}
