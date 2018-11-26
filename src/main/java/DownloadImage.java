import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class DownloadImage {


    public HttpResponse executeHttpRequest(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        httpGet.setConfig(RequestConfig.custom()
                .setConnectTimeout(Constants.TIMEOUT)
                .setSocketTimeout(Constants.TIMEOUT)
                .setConnectionRequestTimeout(Constants.TIMEOUT)
                .setRedirectsEnabled(true)
                .setCircularRedirectsAllowed(true)
                .setMaxRedirects(20)
                .build()
        );

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            throw new DownloadImageException("Failed to execute http request " + url, e.getCause());
        }

        if (response.getStatusLine().getStatusCode() >= 400) {
            throw new DownloadImageException("Failed to download file " + url + ": " + response.getStatusLine());
        }
        return response;
    }

    public String parseImageLink(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map result = new ObjectMapper().convertValue(mapper.readTree(json), TreeMap.class);
            return (String) result.get("image");
        } catch (IOException e) {
            throw new DownloadImageException("Could not parse response and get link to image", e.getCause());
        }
    }


    public File downloadAndSave(String urlImg, String pathToFolder) {
        new Utils().createFolder(pathToFolder);
        String imageUniqueName = "image" + System.currentTimeMillis();
        String fullPath = pathToFolder + File.separator + imageUniqueName;
        File res = new File(fullPath + ".jpg");
        if (urlImg == null || urlImg.trim().isEmpty()) {
            throw new DownloadImageException("URL is empty");
        }

        HttpResponse response = executeHttpRequest(urlImg);

        try {
            copyInputStreamToFile(response.getEntity().getContent(), res);
        } catch (IOException e) {
            throw new DownloadImageException("Could not copy image into file", e.getCause());
        }
        return res;
    }
}
