package se.umu.campus.feed;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import se.umu.campus.R;
import se.umu.campus.constants.CampusIntent;
import se.umu.campus.converters.JSONArrayAdapter;
import se.umu.campus.util.ImageUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FacebookArrayAdapter extends JSONArrayAdapter {
	private static final String baseURL = "http://www.facebook.com/%s/posts/%s";
	private final Activity activity;
	private final long umuEn = 232470350150L;
	private final long umuSv = 185459445735L;
	private final Drawable profilePicture;
	private final Map<Integer, View> items = new WeakHashMap<Integer, View>();

	private static SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public FacebookArrayAdapter(Context context, InputStream is) throws JSONException, IOException {
		super(context, is, "data");

		activity = (Activity) context;
		profilePicture = getDrawableFromUrl(context.getString(R.string.facebook_picture));
	}

	@Override
	public void put(Object o) {
		JSONObject json = (JSONObject) o;
		try {

			Long id = json.getJSONObject("from").getLong("id");

			if (umuEn == id.longValue() || umuSv == id.longValue()) {
				super.put(json);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String getFBLink(int position, View view) throws JSONException {
		JSONObject item = getItem(position);
		String id = item.getString("id");
		String[] uidAndId = id.split("\\_");
		return String.format(baseURL, uidAndId[0], uidAndId[1]);
	}

	private String getLink(int position, View view) throws JSONException {
		JSONObject item = getItem(position);
		String type = item.getString("type");
		if ("link".equals(type)) {
			return item.getString("link");
		} else if ("photo".equals(type)) {
			return item.getString("link");
		} else if ("video".equals(type)) {
			return item.getString("link");
		}
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (position < items.size()) {
			view = items.get(Integer.valueOf(position));
		}
		if (view == null) {
			view = createView(parent, position);
		}

		return view;
	}

	private Drawable getDrawableFromUrl(String address) throws MalformedURLException {
		URL url = new URL(address);
		Drawable drawable = null;
		try {
			drawable = Drawable.createFromStream((InputStream) url.getContent(), "src");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return drawable;
	}

	private ViewGroup createView(ViewGroup parent, final int position) {
		Log.d("FacebookArrayAdapter", "createView: " + position);
		ViewGroup view = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.fb_item, parent, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.icon);
		imageView.setImageDrawable(profilePicture);

		JSONObject item = getItem(position);

		TextView titleView = (TextView) view.findViewById(R.id.item_title);
		TextView textView = (TextView) view.findViewById(R.id.item_text);
		TextView innerTitleView = (TextView) view.findViewById(R.id.item_inner_title);
		TextView innerTextView = (TextView) view.findViewById(R.id.item_inner_text);

		try {
			String type = item.getString("type");

			if ("photo".equals(type) || "video".equals(type) || "link".equals(type)) {
				ImageView photoView = (ImageView) view.findViewById(R.id.item_photo);
				View innerView = view.findViewById(R.id.item_inner);
				innerView.setVisibility(View.VISIBLE);
				try {
					if (item.optString("picture") != null) {
						photoView.setImageDrawable(ImageUtil.getDrawableFromUrl(item.optString("picture")));
					}
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}

				innerTitleView.setText(item.optString("name").replaceAll("\\s+", " "));
				innerTextView.setText(item.optString("description").replaceAll("\\s+", " "));
				final String url = getLink(position, innerTitleView);
				setupListener(innerTitleView, position, url);
			}

			StringBuilder message = new StringBuilder("");
			message.append(item.optString("message"));

			String date = "";
			try {
				Date d = inputSdf.parse(item.getString("created_time"));

				date = sdf.format(d);
			} catch (ParseException e) {
			}
			JSONObject likesJSON = item.optJSONObject("likes");
			int likes = likesJSON != null ? likesJSON.optInt("count") : 0;

			JSONObject commentsJSON = item.optJSONObject("comments");
			int comments = commentsJSON != null ? commentsJSON.optInt("count") : 0;

			titleView.setText(item.getJSONObject("from").getString("name"));

			final String url = getFBLink(position, titleView);

			setupListener(titleView, position, url);

			textView.setText(message);

			TextView footer = (TextView) view.findViewById(R.id.item_footer);
			footer.setText(date + ",  " + likes + " " + view.getContext().getString(R.string.likes) + ", " + comments + " "
					+ view.getContext().getString(R.string.comments));

		} catch (JSONException e) {
			Log.e(this.getClass().toString(), e.getMessage());
			e.printStackTrace();
		}

		items.put(Integer.valueOf(position), view);
		return view;
	}

	private void setupListener(View view, final int position, final String url) {
		Log.d("FacebookArrayAdapter", "setupListener: " + position);
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("FacebookArrayAdapter", "onClick");
				if (url != null) {
					Intent browseIntent = new Intent(CampusIntent.ACTION_BROWSE);
					browseIntent.putExtra("url", url);
					v.getContext().startActivity(browseIntent);
				}
			}

		});

	}
}
