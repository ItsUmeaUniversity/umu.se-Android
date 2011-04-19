package se.umu.campus;

import se.umu.campus.feed.FeedArrayAdapter;
import se.umu.campus.feed.FeedUtil;
import se.umu.campus.util.OptionsMenuHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ArrayAdapter;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class FeedActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayAdapter<SyndEntry> arrayAdapter = new FeedArrayAdapter<SyndEntry>(
				this, R.layout.feed_item);

		setListAdapter(arrayAdapter);
		FeedUtil.getRSS(getString(R.string.news_page), arrayAdapter, this);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		WebView view = (WebView) findViewById(R.id.web_view);
		if ((keyCode == KeyEvent.KEYCODE_BACK) && view != null
				&& view.canGoBack()) {
			view.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
