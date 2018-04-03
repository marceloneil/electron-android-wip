package org.electroncash.electroncash;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marcel O'Neil on 2/17/18.
 */

public class ElectronRpc {

    private static int MAX_RETRIES = 10;
    private static int DELAY = 500;

    private File configFile;
    private File daemonFile;
    private JsonRpcHttpClient rpcClient;

    public ElectronRpc(String dataDir) {
        this.configFile = new File(dataDir + "/config");
        this.daemonFile = new File(dataDir + "/daemon");

        try {
            String rpcAddress = this.parseRpcAddress();
            this.rpcClient = new JsonRpcHttpClient(new URL(rpcAddress));
//            this.rpcClient.invoke("version", );
        } catch (IOException e) {
            Log.e("python", e.toString());
        }
    }

    public void printVersion() {
        try {
            String test = this.rpcClient.invoke("version", new Object(), String.class);
            Log.i("python", test);
        } catch (Throwable e) {
            Log.e("python", e.toString());
        }
    }

    private String parseRpcAddress() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.daemonFile));
        String daemonText = br.readLine();
        br.close();

        Pattern ipPattern = Pattern.compile("(?:\\(')(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
        Matcher ipMatcher = ipPattern.matcher(daemonText);

        Pattern portPattern = Pattern.compile("(?:, )(\\d{5})(?:\\),)");
        Matcher portMatcher = portPattern.matcher(daemonText);

        String ip;
        String port;
        if (ipMatcher.find() && portMatcher.find()) {
            ip = ipMatcher.group(1);
            port = portMatcher.group(1);
        } else {
            ip = "127.0.0.1";
            port = "12345";
        }

        String auth = this.parseRpcAuth();
        if (auth.isEmpty()) {
            return String.format("http://%s:%s", ip, port);
        } else {
            return String.format("http://%s@%s:%s", auth, ip, port);
        }

    }

    private String parseRpcAuth() throws IOException {
        int size = (int) this.configFile.length();
        byte[] bytes = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(this.configFile));
        buf.read(bytes, 0, bytes.length);
        buf.close();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode config = mapper.readTree(bytes);

        String rpcUser = config.path("rpcuser").asText();
        String rpcPassword = config.path("rpcpassword").asText();

        if (!rpcUser.isEmpty() && !rpcPassword.isEmpty()) {
            return rpcUser + ":" + rpcPassword;
        } else {
            return "";
        }
    }

}
