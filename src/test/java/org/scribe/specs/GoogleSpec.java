package org.scribe.specs;

import java.net.URLEncoder;
import java.util.Scanner;

import org.scribe.http.Request;
import org.scribe.http.Response;
import org.scribe.http.Request.Verb;
import org.scribe.oauth.Scribe;
import org.scribe.oauth.Token;

public class GoogleSpec extends Spec {
  private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
  private static final String PROTECTED_RESOURCE_URL = "http://www.google.com/m8/feeds/contacts/default/thin?max-results=0";
  private static final String PROPERTIES_FILE = "google.properties";

  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(System.in);
    Scribe scribe = createFrom(PROPERTIES_FILE);

    System.out.println("Fetching the Request Token...");
    Token requestToken = scribe.getRequestToken();
    System.out.print("Got request token!");

    System.out.println("Now go and authorize Scribe here:");
    System.out.println(AUTHORIZE_URL
        + URLEncoder.encode(requestToken.getToken(), "UTF-8"));
    System.out.print(">>");
    String verifier = in.nextLine();

    br();

    // Trade the Request Token and Verfier for the Access Token
    System.out.println("Trading the Request Token for an Access Token...");
    Token accessToken = scribe.getAccessToken(requestToken, verifier);
    System.out.println("Got the Access Token!");
    System.out.println("(if your curious it looks like this: " + accessToken
        + " )");

    br();

    // Now let's go and ask for a protected resource!
    System.out.println("Now we're going to access a protected resource...");
    Request request = new Request(Verb.GET, PROTECTED_RESOURCE_URL);
    scribe.signRequest(request, accessToken);
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    br();
    System.out.println(response.getBody());

    br();
    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
  }

}
