package dev.thestaticvoid.capejs;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CapeWebHelper {
    public static List<CapeData> capeData = new ArrayList<>();

    public static void addCapesFromWeb(String url) throws Exception {
        URLConnection connection = URI.create(url).toURL().openConnection();
        connection.setConnectTimeout(30000); // 30 seconds
        try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            List<String[]> csvData = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] values = line.split(",", -1);
                csvData.add(values);
            }

            if (csvData.getFirst()[0].equals("Name")) {
                // Remove the header line if it exists
                csvData.removeFirst();
            }

            for (String[] row : csvData) {
                capeData.add(new CapeData(row));
            }
        } catch (Exception e) {
            CapeJS.LOGGER.error("Error occurred while fetching CSV, {}", e.getMessage());
        }

        if (!capeData.isEmpty()) {
            for (CapeData data : capeData) {
                CapeRegistry.addCapeToMap(data.uuid, CapeRegistry.createCapeResource(data.capeType));
            }
        }
    }

    public static class CapeData {
        public String userName;
        public String uuid;
        public String capeType;

        public CapeData(String[] csvRow) {
            this.userName = csvRow[0];
            this.uuid = csvRow[1];
            this.capeType = csvRow[2];
        }
    }
}
