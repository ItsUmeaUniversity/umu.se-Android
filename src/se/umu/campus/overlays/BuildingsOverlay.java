package se.umu.campus.overlays;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class BuildingsOverlay extends ItemizedOverlay<CampusItem> {

	private final List<CampusItem> buildings = new ArrayList<CampusItem>();
	private final Context context;

	public BuildingsOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		populate();
		this.context = context;
	}

	public void addBuilding(CampusItem overlay) {
		buildings.add(overlay);
		populate();
	}

	@Override
	protected CampusItem createItem(int i) {
		return buildings.get(i);
	}

	@Override
	public int size() {
		return buildings.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = buildings.get(index);

		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
}
