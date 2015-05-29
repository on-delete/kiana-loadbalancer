package de.hszg.gce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hszg.gce.util.GCE;
import de.hszg.gce.util.GCEProjectLocationParameter;
import de.hszg.gce.util.NotAuthenticatedException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel on 04.05.2015.
 */
public class GoogleComputeEngineClient {
    public static final int SUCCESS_CODE = 200;
    public static final String IMAGE_NAME = "jetty-server";
    private static GoogleComputeEngineClient ourInstance = new GoogleComputeEngineClient();

    public static GoogleComputeEngineClient getInstance() {
        return ourInstance;
    }

    private GoogleComputeEngineClient() {
    }

    public boolean resetGCE(String name, GCEProjectLocationParameter gceProjectLocationParameter) throws NotAuthenticatedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.googleapis.com/compute/v1/projects/" + gceProjectLocationParameter.getProject() + "/zones/" + gceProjectLocationParameter.getZone() + "/instances/" + name + "/reset?access_token=" + gceProjectLocationParameter.getAccessToken());
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

            JsonNode rootNode = readStringAsJson(readInputStreamAsString(httpEntity.getContent()));

            checkIfAuthenticated(rootNode);

            httpResponse.close();
            httpClient.close();

            return rootNode.path("httpErrorStatusCode").asInt() == SUCCESS_CODE;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NotAuthenticatedException e) {
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw e;
        }
    }

    public boolean startGCE(String name, GCEProjectLocationParameter gceProjectLocationParameter) throws NotAuthenticatedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.googleapis.com/compute/v1/projects/" + gceProjectLocationParameter.getProject() + "/zones/" + gceProjectLocationParameter.getZone() + "/instances/" + name + "/start?access_token=" + gceProjectLocationParameter.getAccessToken());
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

            JsonNode rootNode = readStringAsJson(readInputStreamAsString(httpEntity.getContent()));

            checkIfAuthenticated(rootNode);

            httpResponse.close();
            httpClient.close();

            return rootNode.path("httpErrorStatusCode").asInt() == SUCCESS_CODE;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NotAuthenticatedException e) {
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw e;
        }
    }

    public boolean stopGCE(String name, GCEProjectLocationParameter gceProjectLocationParameter) throws NotAuthenticatedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.googleapis.com/compute/v1/projects/" + gceProjectLocationParameter.getProject() + "/zones/" + gceProjectLocationParameter.getZone() + "/instances/" + name + "/stop?access_token=" + gceProjectLocationParameter.getAccessToken());
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

            JsonNode rootNode = readStringAsJson(readInputStreamAsString(httpEntity.getContent()));

            checkIfAuthenticated(rootNode);

            httpResponse.close();
            httpClient.close();

            return rootNode.path("httpErrorStatusCode").asInt() == SUCCESS_CODE;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NotAuthenticatedException e) {
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw e;
        }
    }

    public boolean createGCE(String name, GCEProjectLocationParameter gceProjectLocationParameter) throws NotAuthenticatedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.googleapis.com/compute/v1/projects/" + gceProjectLocationParameter.getProject() + "/zones/" + gceProjectLocationParameter.getZone() + "/instances?access_token=" + gceProjectLocationParameter.getAccessToken());


        CloseableHttpResponse httpResponse = null;

        try {
            StringEntity request = new StringEntity("{\"name\": " + name + ", \"machineType\": \"zones/us-central1-a/machineTypes/f1-micro\", \"disks\": [\"initializeParams\": {\"sourceImage\": " + IMAGE_NAME + "}]");
            request.setContentType("application/json");

            httpPost.setEntity(request);

            httpResponse = httpClient.execute(httpPost);

            HttpEntity httpEntity = httpResponse.getEntity();

            JsonNode rootNode = readStringAsJson(readInputStreamAsString(httpEntity.getContent()));

            checkIfAuthenticated(rootNode);

            httpResponse.close();
            httpClient.close();

            return rootNode.path("httpErrorStatusCode").asInt() == SUCCESS_CODE;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NotAuthenticatedException e) {
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw e;
        }
    }

    public boolean deleteGCE(String name, GCEProjectLocationParameter gceProjectLocationParameter) throws NotAuthenticatedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete("https://www.googleapis.com/compute/v1/projects/" + gceProjectLocationParameter.getProject() + "/zones/" + gceProjectLocationParameter.getZone() + "/instances/" + name + "?access_token=" + gceProjectLocationParameter.getAccessToken());
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpDelete);

            HttpEntity httpEntity = httpResponse.getEntity();

            JsonNode rootNode = readStringAsJson(readInputStreamAsString(httpEntity.getContent()));

            checkIfAuthenticated(rootNode);

            httpResponse.close();
            httpClient.close();

            return rootNode.path("httpErrorStatusCode").asInt() == SUCCESS_CODE;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NotAuthenticatedException e) {
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw e;
        }
    }

    public List<GCE> getGCEs(GCEProjectLocationParameter gceProjectLocationParameter) throws NotAuthenticatedException {
        List<GCE> gces = new ArrayList<>();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.googleapis.com/compute/v1/projects/" + gceProjectLocationParameter.getProject() + "/zones/" + gceProjectLocationParameter.getZone() + "/instances?access_token=" + gceProjectLocationParameter.getAccessToken());
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpGet);

            HttpEntity httpEntity = httpResponse.getEntity();

            JsonNode rootNode = readStringAsJson(readInputStreamAsString(httpEntity.getContent()));

            checkIfAuthenticated(rootNode);

            Iterator<JsonNode> instanceIterator = rootNode.path("items").elements();

            while (instanceIterator.hasNext()) {
                JsonNode instance = instanceIterator.next();
                GCE gce = new GCE();

                gce.setName(instance.path("name").textValue());
                gce.setIp(instance.path("networkInterfaces").path("networkIP").textValue());

                gces.add(gce);
            }

            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotAuthenticatedException e) {
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            throw e;
        }

        return gces;
    }

    public String getAuthenticationToken() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://metadata/computeMetadata/v1/instance/service-accounts/default/token");
        httpGet.setHeader("Metadata-Flavor", "Google");
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpGet);

            HttpEntity httpEntity = httpResponse.getEntity();

            JsonNode rootNode = readStringAsJson(readInputStreamAsString(httpEntity.getContent()));

            httpResponse.close();
            httpClient.close();

            return rootNode.path("access_token").textValue();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Maps the string to JSON
     *
     * @param jsonString String containing a JSON object
     * @return JSON object
     */
    private JsonNode readStringAsJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            rootNode = mapper.readTree(jsonString);
        } catch (IOException e) {
        }

        return rootNode;
    }

    /**
     * Converts a input stream to a string.
     *
     * @param inputStreamReader a input stream
     * @return string from input stream
     * @throws IOException
     */
    private static String readInputStreamAsString(InputStream inputStreamReader)
            throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStreamReader);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            byte b = (byte) result;
            buf.write(b);
            result = bis.read();
        }

        return buf.toString();
    }

    private void checkIfAuthenticated(JsonNode rootNode) throws NotAuthenticatedException {
        if (rootNode.has("error")) {
            if (rootNode.path("error").path("code").asInt() == 401) {
                throw new NotAuthenticatedException();
            }
        }
    }
}
