package se.umu.campus.util;

import se.umu.campus.R;
import se.umu.campus.constants.CampusIntent;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

public class OptionsMenuHandler {
	public static boolean handleOptionsMenu(Activity activity, MenuItem item) {
		Intent intent = null;
		spinner(activity);
		switch (item.getItemId()) {
		case R.id.map:
			intent = new Intent(CampusIntent.ACTION_MAP);
			activity.startActivityIfNeeded(intent, 0);
			return true;
		case R.id.map_search:
			activity.onSearchRequested();
			/*intent = new Intent(Intent.ACTION_SEARCH);
			activity.startActivityIfNeeded(intent, 0);*/
			return true;
		case R.id.feed:
			intent = new Intent(CampusIntent.ACTION_FEED);
			activity.startActivityIfNeeded(intent, 0);
			return true;
		case R.id.fb:
			intent = new Intent(CampusIntent.ACTION_FB);
			activity.startActivityIfNeeded(intent, 0);
			return true;
		case R.id.yt:
			intent = new Intent(CampusIntent.ACTION_YT);
			activity.startActivityIfNeeded(intent, 0);
			return true;
		case R.id.welcome:
			intent = new Intent(CampusIntent.ACTION_WELCOME);
			activity.startActivityIfNeeded(intent, 0);
			return true;
		default:
			return true;
		}
	}

	private static void spinner(Context c) {
		ProgressDialog pbarDialog;
		pbarDialog = new ProgressDialog(c);
		pbarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pbarDialog.setMessage("Loading...");
		pbarDialog.setCancelable(false);
	}
}
