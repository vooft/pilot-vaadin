package com.vooft.pilot.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 25.05.13
 * Time: 0:43
 * To change this template use File | Settings | File Templates.
 */
public class MailHelper {
    public static final String PROTOCOL_VERSION = "2";
    public static final String BASIC_URL = "http://pilot-auto.net:1613/mobile/?";
    public static final String BASIC_URL_SSL = "http://pilot-auto.net:1613/mobile/?";
    private static final String WRONG_PROTOCOL = "WRONG_PROTOCOL";

    private static String buildSecureUrl(String action, Map<String, String> params) {
        return buildUrl(BASIC_URL_SSL, action, params);
    }

    public static String buildUrl(String action, Map<String, String> params) {
        return buildUrl(BASIC_URL, action, params);
    }

    private static String buildUrl(String template, String action, Map<String, String> params) {
        StringBuilder result = new StringBuilder(template);

        List<NameValuePair> httpParams = new ArrayList<NameValuePair>();

        for(String s: params.keySet())
            httpParams.add(new BasicNameValuePair(s, params.get(s)));

        httpParams.add(new BasicNameValuePair("proto", PROTOCOL_VERSION));
        httpParams.add(new BasicNameValuePair("action", action));

        result.append(URLEncodedUtils.format(httpParams, "utf-8"));

        return result.toString();
    }

    public static String makeHttpRequest(String action, Map<String, String> params)
            throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(buildSecureUrl(action, params));
        request.setHeader("User-Agent", "pilot-auto-mobile");

        HttpResponse response = client.execute(request);

        // Pull content stream from response
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();

        StringBuilder sb = new StringBuilder();

        // Read response into a buffered stream
        int readBytes = 0;
        byte[] sBuffer = new byte[512];
        while ((readBytes = inputStream.read(sBuffer)) != -1) {
            sb.append(new String(sBuffer, 0, readBytes));
        }

        return sb.toString();
    }
}
