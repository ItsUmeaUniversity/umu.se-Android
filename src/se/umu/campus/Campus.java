package se.umu.campus;

import se.umu.campus.constants.CampusConstants;
import se.umu.campus.constants.CampusIntent;
import se.umu.campus.overlays.BuildingsOverlay;
import se.umu.campus.overlays.CampusItem;
import se.umu.campus.util.OptionsMenuHandler;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.MapActivity;

public class Campus extends MapActivity {
	private BuildingsOverlay buildingsOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onResume();
	}

	@Override
	public void onNewIntent(Intent newIntent) {
		super.onNewIntent(newIntent);
		handleIntent(newIntent);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void handleIntent(Intent data) {

		if (CampusIntent.ACTION_MAP_PLACE_POINT.equals(data.getAction())) {
			CampusItem item = (CampusItem) data
					.getParcelableExtra(CampusConstants.ITEM);

			if (item != null) {
				buildingsOverlay.addBuilding(item);
			}
		}
	}
}