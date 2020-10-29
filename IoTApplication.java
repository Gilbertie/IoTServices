import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class IoTApplication {

    public static void main(String[] args) throws JSONException, IOException {
        String input = args[0];
        if (!isValid(input)) {
            System.out.println("Invalid input.");
            return;
        }

        String serviceName = getServiceName(input);
        JSONObject json = new JSONObject();
        json.put("Tweet Type", "Service call");
        json.put("Thing ID", "RaspberryPi_QH");
        json.put("Space ID", "SmartSpace_QH");
        json.put("Service Name", serviceName);
        json.put("Service Inputs", "()");

        Socket s = new Socket("192.168.0.77", 6668);
        System.out.println("Socket is created.");

        try (OutputStreamWriter out = new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));) {
            // send
            out.write(json.toString());
            out.flush();
            System.out.println("Request to service is sent.");

            // receive and print
            System.out.println("Waiting for response...");
            StringBuilder sb = new StringBuilder();
            String line;
            while ( (line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }

            printResult(sb.toString(), serviceName);
        }

        try {
            s.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    static boolean isValid(String input) {
        if (input == null) {
            return false;
        }
        return input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4");
    }

    static String getServiceName(String input) {
        switch (input) {
            case "1":
                return "lightFor5Seconds";
            case "2":
                return "buzzFor5Seconds";
            case "3":
                return "countPushOfButtonFor5Seconds";
            case "4":
                return "countTiltOfSwitchFor5Seconds";
            default:
                throw new IllegalStateException("Unexpected value: " + input);
        }
    }

    private static void printResult(String response, String serviceName) {
//        System.out.println(response);
        String result = response.substring(response.indexOf("Service Result") + 19, response.length() - 5);
        System.out.println("service [" + serviceName + "] finished with result: " + result);
    }
}
