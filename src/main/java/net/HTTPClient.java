package net;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

/**
 * @author Denis
 * @version 1.1
 * Class for connecting to a server using an HTTP client and retrieve data in JSON format
 */

public class HTTPClient {

    /**
     * The method to connecting to a server using an Apache HTTP client and retrieve data in JSON format
     *
     * @param uri - server URL
     * @return data in JSON format written to String
     */
    public String getData(final String uri) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        String jsonResponse = null;
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            jsonResponse = EntityUtils.toString(entity);
        } catch (IOException | ParseException ignore) {
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonResponse;
    }
}