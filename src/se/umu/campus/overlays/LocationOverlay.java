package se.umu.campus.overlays;

import se.umu.campus.R;
import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class LocationOverlay extends MyLocationOverlay {
	private final Activity activity;

	public LocationOverlay(Activity activity, MapView mapView) {
		super(activity, mapView);
		this.activity = activity;
	}

	@Override
	public synchronized void onLocationChanged(Location location) {
		super.onLocationChanged(location);
		Log.i(this.toString(), "onLocationChanged");
		updateLocation(activity, location);

	}

	private void updateLocation(Activity activity, Location location) {
		if (location != null) {
			MapView mapView = (MapView) activity.findViewById(R.id.mapview);

			GeoPoint point = new GeoPoint(
					(int) (location.getLongitude() * 1000000),
					(int) (location.getLatitude() * 1000000));

			mapView.getController().animateTo(point);
			mapView.getController().setZoom(16);
		}
	}
}
