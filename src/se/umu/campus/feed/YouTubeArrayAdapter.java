package se.umu.campus.feed;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;

import se.umu.campus.R;
import se.umu.campus.util.ImageUtil;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

public class YouTubeArrayAdapter extends FeedArrayAdapter<SyndEntry> {

	public YouTubeArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	private String getLink(int position, View view) {
		SyndEntry item = getItem(position);

		return item.getLink();
	}

	
	@Override
	protected View createView(ViewGroup parent, int position) {

		View view = activity.getLayoutInflater().inflate(textViewResourceId, parent, false);

		SyndEntry item = getItem(position);

		TextView titleView = (TextView) view.findViewById(R.id.item_title);
		titleView.setText(item.getTitle());

		TextView textView = (TextView) view.findViewById(R.id.item_text);

		String date = DateFormat.getDateInstance().format(item.getPublishedDate());

		textView.setText(date + "\n" + item.getDescription().getValue());

		ImageView imageView = (ImageView) view.findViewById(R.id.icon);

		@SuppressWarnings("unchecked")
		List<Element> foreignMarkup = (List<Element>) item.getForeignMarkup();

		for (Element e : foreignMarkup) {
			if ("group".equalsIgnoreCase(e.getName())) {
				try {
					Element child = e.getChild("thumbnail", Namespace.getNamespace("media", "http://search.yahoo.com/mrss/"));

					if (child != null) {
						String url = child.getAttributeValue("url");
						if (imageView != null) {
							imageView.setImageDrawable(ImageUtil.getDrawableFromUrl(url));
						}
					}
				} catch (MalformedURLException mue) {
					mue.printStackTrace();
				}
				break;
			}
		}

		setupListener(view, position);
		items.put(Integer.valueOf(position), view);
		return view;
	}

	private void setupListener(View view, final int position) {

		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				String url = getLink(position, view);
				if (url != null) {

					Intent browseIntent = new Intent(Intent.ACTION_VIEW);
					browseIntent.addCategory(Intent.CATEGORY_BROWSABLE);
					browseIntent.setData(Uri.parse(url));

					view.getContext().startActivity(browseIntent);
				}
			}
		});

	}
}
