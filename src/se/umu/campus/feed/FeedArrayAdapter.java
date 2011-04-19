package se.umu.campus.feed;

import java.text.DateFormat;
import java.util.Map;
import java.util.WeakHashMap;

import se.umu.campus.R;
import se.umu.campus.constants.CampusIntent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class FeedArrayAdapter<T> extends ArrayAdapter<T> {
	protected final Activity activity;
	protected final int textViewResourceId;
	protected final Map<Integer, View> items = new WeakHashMap<Integer, View>();

	//private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public FeedArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);

		this.textViewResourceId = textViewResourceId;
		activity = (Activity) context;
	}

	private String getLink(int position, View view) {
		SyndEntry item = (SyndEntry) getItem(position);

		return item.getLink();
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

	protected View createView(ViewGroup parent, int position) {
		//Log.d("FeedArrayAdapter", "createView: " + position);
		View view = activity.getLayoutInflater().inflate(textViewResourceId, parent, false);

		SyndEntry item = (SyndEntry) getItem(position);

		TextView titleView = (TextView) view.findViewById(R.id.item_title);
		titleView.setText(item.getTitle());

		TextView textView = (TextView) view.findViewById(R.id.item_text);

		String date = DateFormat.getDateTimeInstance().format(item.getPublishedDate());

		textView.setText(item.getDescription().getValue());

		TextView footer = (TextView) view.findViewById(R.id.item_footer);
		footer.setText(date);
		setupListener(titleView, position);
		items.put(Integer.valueOf(position), view);
		return view;
	}

	private void setupListener(View view, final int position) {
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Log.d("FeedArrayAdapter", "onClick");
				String url = getLink(position, v);
				if (url != null) {

					Intent browseIntent = new Intent(CampusIntent.ACTION_BROWSE);
					browseIntent.putExtra("url", url);
					v.getContext().startActivity(browseIntent);
				}
			}

		});

	}
}
