package se.umu.campus;

import se.umu.campus.constants.CampusIntent;
import android.app.Activity;
import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view);
		WebView view = (WebView) findViewById(R.id.web_view);
		view.getSettings().setJavaScriptEnabled(true);
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				if (url.startsWith("mailto:")) {

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
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);

	}

	private void handleIntent(Intent intent) {

		if (CampusIntent.ACTION_BROWSE.equals(intent.getAction())) {
			Log.d("WebView.browse", intent.getStringExtra("url"));
			WebView view = (WebView) findViewById(R.id.web_view);
			view.loadUrl(intent.getStringExtra("url"));
			view.clearHistory();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		WebView view = (WebView) findViewById(R.id.web_view);
		if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
			view.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
