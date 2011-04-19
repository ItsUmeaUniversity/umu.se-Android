package se.umu.campus.overlays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import se.umu.campus.constants.GeoJSONConstants;
import se.umu.campus.constants.JSONConstants;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CampusItem extends OverlayItem implements Parcelable {

	public CampusItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}

	public static CampusItem fromJSON(JSONObject json) throws JSONException {

		JSONObject geo = json.getJSONObject(JSONConstants.GEO);
		JSONArray coordinates = geo.getJSONArray(GeoJSONConstants.COORDINATES);

		int longitude = (int) (coordinates.getDouble(0) * 1000000);
		int latitude = (int) (coordinates.getDouble(1) * 1000000);
		String name = json.getString(JSONConstants.NAME);
		String description = json.getString(JSONConstants.DESCRIPTION);
		GeoPoint point = new GeoPoint(longitude, latitude);
		return new CampusItem(point, name, description);
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mPoint.getLatitudeE6());
		dest.writeInt(mPoint.getLongitudeE6());
		dest.writeString(mTitle);
		dest.writeString(mSnippet);
	}

	public static final Parcelable.Creator<CampusItem> CREATOR = new Parcelable.Creator<CampusItem>() {
		public CampusItem createFromParcel(Parcel in) {
			GeoPoint point = new GeoPoint(in.readInt(), in.readInt());
			String title = in.readString();
			String snippet = in.readString();
			return new CampusItem(point, title, snippet);
		}

		public CampusItem[] newArray(int size) {
			return new CampusItem[size];
		}
	};
}
