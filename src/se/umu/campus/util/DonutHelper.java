package se.umu.campus.util;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class DonutHelper implements VersionHelper {

	@Override
	public void setUpMapView(WebView webView) {
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				result.confirm();
				return true;
			}

			@Override
			public void onConsoleMessage(String message, int lineNumber,
					String sourceID) {
				Log.w("JS", message + ":" + lineNumber + " (" + sourceID + ")");
			}
		});

	}
}
