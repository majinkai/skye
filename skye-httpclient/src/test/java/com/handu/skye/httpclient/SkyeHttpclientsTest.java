package com.handu.skye.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Jinkai.Ma
 */
public class SkyeHttpclientsTest {

    @Test
    public void get1() throws IOException, InterruptedException {
        request();
        Thread.sleep(11000L);
    }

    private void request() throws IOException {
        final CloseableHttpClient httpClient = SkyeHttpclients.get().build();
        HttpUriRequest request = RequestBuilder.get("http://localhost:8888/skye-example-servlet/demoServlet").build();
        CloseableHttpResponse response = httpClient.execute(request);
        String result = EntityUtils.toString(response.getEntity());
        response.close();
        httpClient.close();
    }

}