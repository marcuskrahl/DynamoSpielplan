package de.marcuskrahl.dynamospielplan;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class DynamoMatchPlanURL implements MatchPlanURL {

    private final URL url;

    public DynamoMatchPlanURL(URL url) {
        this.url = url;
    }


    @Override
    public String getContent()  {
        try {
            BufferedInputStream stream = new BufferedInputStream((InputStream) url.getContent());
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            StringBuilder output = new StringBuilder();
            while ((bytesRead = stream.read(buffer)) != -1) {
                output.append(new String(buffer,0,bytesRead));
            }
            return output.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
