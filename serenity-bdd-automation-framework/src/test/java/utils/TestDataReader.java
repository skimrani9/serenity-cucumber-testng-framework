package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Loads credential rows from {@code classpath:testdata/users.json} by test case id (e.g. TC_LOGIN_001).
 */
public final class TestDataReader {

    private TestDataReader() {
    }

    public record CredentialPair(String username, String password) {
    }

    public static CredentialPair readCredentials(String testDataKey) {
        try (InputStream in = TestDataReader.class.getResourceAsStream("/testdata/users.json")) {
            Objects.requireNonNull(in, "Missing classpath resource testdata/users.json");
            JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .getAsJsonObject();
            if (!root.has(testDataKey)) {
                throw new IllegalArgumentException("Unknown test data key in users.json: " + testDataKey);
            }
            JsonObject row = root.getAsJsonObject(testDataKey);
            return new CredentialPair(
                    row.get("username").getAsString(),
                    row.get("password").getAsString()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Could not read testdata/users.json", e);
        }
    }
}
