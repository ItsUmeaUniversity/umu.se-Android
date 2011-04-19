package se.umu.campus;

import se.umu.campus.constants.CampusIntent;
import se.umu.campus.util.OptionsMenuHandler;
import android.app.Activity;
import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WelcomeActivity extends Activity {
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupWebView();
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

	/** Sets up the WebView object and loads the URL of the page **/
	private void setupWebView() {
		setContentView(R.layout.welcome);
		webView = (WebView) findViewById(R.id.welcome);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				if (url.startsWith("intent://")) {
					Intent intent = new Intent(url.substring(9));
					startActivityIfNeeded(intent, 0);
					return true;
				} 
				else if (url.startsWith("mailto:")) {

					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("plain/text");
					MailTo mt = MailTo.parse(url);
					i.putExtra(Intent.EXTRA_EMAIL, new String[] { mt.getTo() });
					i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
					i.putExtra(Intent.EXTRA_CC, mt.getCc());
					i.putExtra(Intent.EXTRA_TEXT, mt.getBody());
					view.getContext().startActivity(i);
					view.reload();
					return true;
				}

				view.loadUrl(url);
				return true;
			}

		});

		webView.loadUrl("file:///android_asset/"
				+ getString(R.string.welcome_page));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
