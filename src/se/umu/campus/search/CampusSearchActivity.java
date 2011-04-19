package se.umu.campus.search;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import se.umu.campus.R;
import se.umu.campus.constants.CampusConstants;
import se.umu.campus.constants.CampusIntent;
import se.umu.campus.constants.JSONConstants;
import se.umu.campus.converters.JSONArrayAdapter;
import se.umu.campus.converters.JSONConverter;
import se.umu.campus.overlays.CampusItem;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CampusSearchActivity extends ListActivity {

	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);

		try {
			setListAdapter(new JSONArrayAdapter(this, this.getResources().openRawResource(R.raw.result), JSONConstants.RESULT));

			ListView lv = getListView();
			lv.setTextFilterEnabled(true);

			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Intent intent = new Intent(CampusIntent.ACTION_MAP_PLACE_POINT);
					JSONObject item = (JSONObject) getListAdapter().getItem(position);
					try {
						JSONObject data = getDataFromPosition(item.getInt(JSONConstants.ID));

						intent.putExtra(CampusConstants.ITEM, CampusItem.fromJSON(data));
						startActivity(intent);
					} catch (Exception e) {
						Log.e(this.toString(), e.getMessage());
					}
				}

			});

			saveRecentSearches();
		} catch (NotFoundException e) {
			Log.e(this.toString(), e.getMessage());
		} catch (JSONException e) {
			Log.e(this.toString(), e.getMessage());
		} catch (IOException e) {
			Log.e(this.toString(), e.getMessage());
		}
	}

	private JSONObject getDataFromPosition(int position) throws NotFoundException, JSONException, IOException {
		Resources resources = this.getResources();

		JSONArray json = JSONConverter.getJSONFromStream(resources.openRawResource(R.raw.buildings)).getJSONArray(
				JSONConstants.BUILDINGS);

		return json.getJSONObject(position);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(this.toString(), "onActivityResult");
		setResult(RESULT_OK, data);
		finish();
	}

	private void saveRecentSearches() {
		Intent intent = getIntent();

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, CampusSuggestionProvider.AUTHORITY,
					CampusSuggestionProvider.MODE);
			suggestions.saveRecentQuery(query, null);
		}
	}
}
