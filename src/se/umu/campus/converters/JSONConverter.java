package se.umu.campus.converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONConverter {

	public static JSONObject getJSONFromStream(InputStream is)
			throws JSONException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		return new JSONObject(sb.toString());
	}
}
