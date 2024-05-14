package quarkus;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
class AccountResourceNativeTest extends AccountResourceTest {
    // Execute the same tests but in packaged mode.
}
