package org.example.mashupwikisongs.config;
/**
 * Date: 28.02.2024
 *
 * @author Nikolay Zinin
 */
public class Url {
    public static final String MUSICBRAINZ_RELATIONS_URL_PREFIX = "http://musicbrainz.org/ws/2/artist/";
    public static final String MUSICBRAINZ_RELATIONS_URL_SUFFIX = "?&fmt=json&inc=url-rels";
    public static final String MUSICBRAINZ_RELEASE_GROUPS_URL_SUFFIX = "/?&fmt=json&inc=release-groups";
    public static final String COVER_ARCHIVE_URL_PREFIX = "http://coverartarchive.org/release-group/";
    public static final String WIKIPEDIA_RELATION_TYPE = "wikipedia";
    public static final String WIKIPEDIA_SEARCH_TERM = "<a href=\"https://en.wikipedia.org/wiki/";
    public static final String MUSICBRAINZ_PAGE_PREFIX = "https://musicbrainz.org/artist/";
    public static final String WIKI_ARTICLE_URL_PREFIX = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles=";

}
