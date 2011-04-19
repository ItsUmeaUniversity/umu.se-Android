package se.umu.campus.util;

import android.util.Log;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class EclairHelper implements VersionHelper {
	public void setUpMapView(WebView webView) {
		webView.getSettings().setGeolocationEnabled(true);

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onGeolocationPermissionsShowPrompt(String origin,
					Callback callback) {
				callback.invoke(origin, true, true);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				result.confirm();
				return true;
			}

			@Override
			public void onConsoleMessage(String message, int lineNumber,
					String sourceID) {
				Log.w("JS", message + " :" + lineNumber + " (" + sourceID + ")");
			}

		});

	}

}
