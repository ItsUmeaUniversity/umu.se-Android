package se.umu.campus;

import se.umu.campus.util.DonutHelper;
import se.umu.campus.util.OptionsMenuHandler;
import se.umu.campus.util.VersionHelper;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CampusWebMapActivity extends Activity implements LocationListener {
	private WebView mapWebView;
	private Location location = null;

	@Override
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getLocation();
		setupWebView();

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	public void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			performJavascriptSearch(query);
		}
	}

	private void performJavascriptSearch(String query) {

		String q = "javascript:usearch('" + query + "');";
		Log.d("search", q);
		mapWebView.loadUrl(q);
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

	private void getLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);

		locationManager.requestLocationUpdates(provider, 10, 0, this);
		location = locationManager.getLastKnownLocation(provider);
		/*if (loc != null) {
			location = new GeoPoint((int) (loc.getLatitude() * 1000000),
					(int) (loc.getLongitude() * 1000000));
		}*/
	}

	/** Sets up the WebView object and loads the URL of the page **/
	private void setupWebView() {
		VersionHelper versionHelper;

		mapWebView = (WebView) findViewById(R.id.mapview);
		mapWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		mapWebView.getSettings().setJavaScriptEnabled(true);

		if (Build.VERSION.SDK_INT >= 5) {
			try {
				versionHelper = (VersionHelper) Class.forName("EclairHelper")
						.newInstance();
			} catch (Exception e) {
				versionHelper = new DonutHelper();
			}
		} else {
			versionHelper = new DonutHelper();
		}

		versionHelper.setUpMapView(mapWebView);

		mapWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http://")) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				} else {
					view.loadUrl(url);
				}
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				//focusOnCampus();
			}
		});

		mapWebView.loadUrl(getString(R.string.map_page));
		mapWebView.addJavascriptInterface(new CampusJavaScriptInterface(),
				"android");

	}

	private void focusOnCampus() {
		mapWebView
				.loadUrl("javascript:setCenter(new google.maps.LatLng(63.819849,30.304794));");
		mapWebView.loadUrl("javascript:map.setZoom(16);");
	}

	private class CampusJavaScriptInterface {

		@SuppressWarnings("unused")
		public double getLatitude() {
			return location.getLatitude();
		}

		@SuppressWarnings("unused")
		public double getLongitude() {
			return location.getLatitude();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i(this.toString(), "onLocationChanged");
		Log.i(this.toString(), "Longitude: " + location.getLongitude()
				+ " latitude: " + location.getLatitude());
		/*this.location = new GeoPoint((int) (location.getLatitude() * 1000000),
				(int) (location.getLongitude() * 1000000));*/
		//markPosition();
		/*mapWebView
				.loadUrl("javascript:map.panTo(new google.maps.LatLng("
						+ location.getLatitude() + ","
						+ location.getLongitude() + "))");
		mapWebView.loadUrl("javascript:map.setZoom(14)");*/
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
