package group0110.googleAuth;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.oauth2.model.Userinfo;
import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.microsoft.alm.oauth2.useragent.AuthorizationResponse;
import com.microsoft.alm.oauth2.useragent.UserAgent;
import com.microsoft.alm.oauth2.useragent.UserAgentImpl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Properties;

/**
 * Authenticates the user using Google's OAuth
**/
public class GoogleAuthorizer {
    private String username;
    private String password;
    private String redirectUri = "http://localhost";
    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    public GoogleAuthorizer() {
    }

    public void open() throws AuthorizationException, URISyntaxException, IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets = getSecret();
        googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), clientSecrets,
                Collections.singleton(Oauth2Scopes.USERINFO_PROFILE))
                .build();
        String code = requestAuthCode();
        GoogleTokenResponse tokenResponse = getToken(code);
        Credentials cred = getUserInfo(tokenResponse);
        username = cred.username;
        password = cred.password;
    }

    private Credentials getUserInfo(GoogleTokenResponse tokenResponse) throws IOException, GeneralSecurityException {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setJsonFactory(new JacksonFactory())
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setClientSecrets(getSecret())
                .build()
                .setFromTokenResponse(tokenResponse);
        Oauth2 oauth2 = new Oauth2.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                credential)
                .setApplicationName("conventionplanningproject")
                .build();

        Userinfo userinfo = oauth2.userinfo().get().execute();
        Credentials cred = new Credentials();
        cred.username = "Google: " + userinfo.getName();
        cred.password = "P1%" + userinfo.getId(); // To meet min password requirements
        return cred;
    }

        private String requestAuthCode() throws URISyntaxException, AuthorizationException {
            URI authorizationEndpoint = googleAuthorizationCodeFlow
                    .newAuthorizationUrl()
                    .setRedirectUri(redirectUri)
                    .toURI();
            // Generate the auth endpoint URI to request the auth code

            //URI authorizationEndpoint = getAuthorizationEndpointUri();

            System.out.print("Authorization Endpoint URI: " + authorizationEndpoint.toString());

            final URI redirectUri = new URI(this.redirectUri);

               Properties props = System.getProperties();
            props.setProperty("org.eclipse.swt.browser.XULRunnerPath", "D:\\Program Files\\Java\\xulrunner-1.9.2.25");

            // Create the user agent and make the call to the auth endpoint
            final UserAgent userAgent = new UserAgentImpl();

            // Get the response
            final AuthorizationResponse authorizationResponse =
                    userAgent.requestAuthorizationCode(authorizationEndpoint, redirectUri);

            // Get the code to trade for token for API
            final String code = authorizationResponse.getCode();

            System.out.print("Authorization Code: ");
            System.out.println(code);
            return code;
        }

    private GoogleTokenResponse getToken(String code) throws IOException {
        return googleAuthorizationCodeFlow.newTokenRequest(code)
                .setScopes(Collections.singleton(Oauth2Scopes.USERINFO_PROFILE))
                .setRedirectUri(redirectUri)
                .execute();
    }

    private GoogleClientSecrets getSecret() throws IOException {
        return GoogleClientSecrets.load(new JacksonFactory(),
                new InputStreamReader(GoogleAuthorizer.class.getResourceAsStream("/client_secrets.json")));
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private class Credentials {
            String username;
            String password;
    }
}