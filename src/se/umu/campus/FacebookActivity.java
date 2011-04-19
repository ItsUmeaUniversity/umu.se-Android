package se.umu.campus;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import se.umu.campus.converters.JSONArrayAdapter;
import se.umu.campus.feed.FacebookArrayAdapter;
import se.umu.campus.util.OptionsMenuHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FacebookActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JSONArrayAdapter arrayAdapter = null;
		InputStream openStream = null;
		try {
			URL fbUrl = new URL(getString(R.string.facebook_page));
			openStream = fbUrl.openStream();
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		try {
			arrayAdapter = new FacebookArrayAdapter(this, openStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (openStream != null) {
				try {
					openStream.close();
				} catch (IOException e) {
				}
			}
		}

		if (arrayAdapter != null) {
			setListAdapter(arrayAdapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.xml.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return OptionsMenuHandler.handleOptionsMenu(this, item);
	}
}
