package anglebaby.location.services;

import anglebaby.location.model.Location;
import anglebaby.location.model.LocationList;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class LocationService {
    private String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJwd2NXa1JObVNnWEVOVGtOVUdPNiJ9.eyJpc3MiOiJodHRwczovL2Rldi1rbmlndWk3ei51cy5hdXRoMC5jb20vIiwic3ViIjoiNUNjNnFCb0ZacWhLVkwxVmMxaXoySW42NW03S1V3ME1AY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vbWVudS9BUEkiLCJpYXQiOjE2NjY0Mjc3MzksImV4cCI6MTY2NjUxNDEzOSwiYXpwIjoiNUNjNnFCb0ZacWhLVkwxVmMxaXoySW42NW03S1V3ME0iLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.QMjiGBMVeCkr_J6dyG2lfhU6X2axrLIZYbIyZTd7Q0VyDY4e4gUUvsc0zJHHbL0cCic4KETN523_WtPmmXNF6Y__YxqbObGjlwJfuMa1o2goNYWVtguhCd3zFyXw_yLIhjbT-CHS9lSdR0wC7xhZlc_Yzk4fF5OglMBSe8336wBQXzjUjDvcw8lPajm6bs_ssV9t435EECM5yZ2LCRQt8kuu0aZ7o5JK95DioCm2iqBJrPDt4d69R4obgsx8QmbiGEHcROV7aojENAmcT2K-ZsRTWtAl42CVriZMVg5wTryajuqw1BWDRIzwwsxCSsjtzZnY0gQv-x2sWJT1cgZRXg";

    public String findLocation(String payload) throws IOException {
        JSONObject request = new JSONObject(payload);

        LocationList locationList = getRequest(new URL("http://localhost:8095/api/location"));
        Location freeLocation = locationList.findFreeLocation();
        JSONObject answer = new JSONObject();
        if (freeLocation != null) {
            request.put("locationID", freeLocation.getLocationID());
            answer = request;
            postRequest(answer, new URL("http://localhost:8095/api/log"));
        }
        else {
            answer.put("created", "fail");
            answer.put("message", "free space not found");
        }
        return answer.toString();
    }

    private LocationList getRequest(URL url) throws IOException {
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Authorization", "Bearer " + token);
        http.disconnect();
        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        LocationList locationList = new LocationList();
        JSONArray jsonArr = new JSONArray(sb.toString());
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            locationList.addLocation(jsonObj.toString());
        }
        return locationList;
    }

    public void postRequest(JSONObject jsonObject, URL url) throws IOException {
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Authorization", "Bearer " + token);
        http.setRequestProperty("Content-Type", "application/json");

        String data = jsonObject.toString();

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }
}
