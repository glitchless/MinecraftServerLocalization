package ru.glitchless.serverlocalization.utils;

import javax.annotation.Nullable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http communication utilities
 */
public final class HttpUtils {
    /**
     * Executes a simple HTTP-GET request
     *
     * @param url URL to request
     * @return The result of request
     * @throws Exception I/O Exception or HTTP errors
     */
    public static String httpGet(String url) throws Exception {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = br.readLine()) != null) {
            response = response.append(line).append('\r');
        }
        connection.disconnect();
        return response.toString();
    }

    @Nullable
    public static File downloadFile(String url, File dest) {
        URL urlObj;
        InputStream is = null;
        BufferedReader br;
        String line;
        try {
            dest.getParentFile().mkdirs();
            dest.createNewFile();
            urlObj = new URL(url);
            is = urlObj.openStream();  // throws an IOExceptio

            FileOutputStream outputStream = new FileOutputStream(dest);
            DataInputStream inputStream = new DataInputStream(is);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
        } catch (IOException mue) {
            mue.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {

            }
        }
        return dest;
    }
}
