package se.umu.campus;

import se.umu.campus.feed.FeedUtil;
import se.umu.campus.feed.YouTubeArrayAdapter;
import se.umu.campus.util.OptionsMenuHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class YouTubeActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayAdapter<SyndEntry> arrayAdapter = new YouTubeArrayAdapter(this,
				R.layout.yt_item);

		setListAdapter(arrayAdapter);
		FeedUtil.getRSS(getString(R.string.youtube_page), arrayAdapter, this);
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
