package se.umu.campus.converters;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import se.umu.campus.R;
import se.umu.campus.constants.JSONConstants;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JSONArrayAdapter extends android.widget.BaseAdapter {
	private final JSONArray json = new JSONArray();
	private final LayoutInflater inflater;

	public JSONArrayAdapter(Context context, InputStream is, String arrayName) throws JSONException, IOException {
		JSONArray unfilteredJson = JSONConverter.getJSONFromStream(is).getJSONArray(arrayName);

		for (int i = 0; i < unfilteredJson.length(); i++) {
			put(unfilteredJson.get(i));
		}

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		return json.length();
	}

	protected void put(Object o) {
		json.put(o);
	}

	@Override
	public JSONObject getItem(int position) {
		try {
			return json.getJSONObject(position);
		} catch (JSONException e) {
			Log.e(this.toString(), "JSONException: position out of bounds!");
			throw new ArrayIndexOutOfBoundsException(position);
		}
	}

	@Override
	public long getItemId(int position) {
		long itemId = 0;
		try {
			itemId = json.getJSONObject(position).getInt(JSONConstants.ID);
		} catch (JSONException e) {
			Log.e(this.toString(), "JSONException: Error reading id!");
		}
		return itemId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) inflater.inflate(R.layout.list_item, parent, false);

		try {
			view.setText(getItem(position).getString(JSONConstants.NAME));
		} catch (JSONException e) {
			Log.e(this.toString(), "JSONException: Error reading name!");
			view.setText("Name not found!");
		}
		return view;
	}
}
