package se.umu.campus.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;

public class ImageUtil {
	public static Drawable getDrawableFromUrl(String address) throws MalformedURLException {
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
}
