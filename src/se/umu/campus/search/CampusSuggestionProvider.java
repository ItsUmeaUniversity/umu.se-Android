package se.umu.campus.search;

import android.content.SearchRecentSuggestionsProvider;

public class CampusSuggestionProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "se.umu.campus.search.CampusSuggestionProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public CampusSuggestionProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
}
