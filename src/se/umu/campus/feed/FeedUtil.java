package se.umu.campus.feed;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

public class FeedUtil {

	public static void getRSS(String url, ArrayAdapter<SyndEntry> adapter, Context c) {
		// Bugfix for Android < 2.1
		Thread.currentThread().setContextClassLoader(FeedUtil.class.getClassLoader());
		try {
			URL feedUrl = new URL(url);

			SyndFeedInput input = new SyndFeedInput();

			SyndFeed feed = input.build(new XmlReader(feedUrl));
			@SuppressWarnings("unchecked")
			List<SyndEntry> entries = feed.getEntries();

			Iterator<SyndEntry> iterator = entries.listIterator();
			while (iterator.hasNext()) {
				SyndEntry ent = iterator.next();
				adapter.add(ent);
			}
			adapter.notifyDataSetChanged();

		} catch (Exception e) {
			Log.e(FeedUtil.class.toString(), e.getMessage());
			e.printStackTrace();
		}
	}

}
