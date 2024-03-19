## Backend - mashup: a combination of info for an artist from Music Brainz APIs, Cover Art Archive API and Wikipedia API.

MusicBrainz offers an API with, among other things, detailed information about music artists (information such as the artist's name, year of birth, country of birth, etc.). Wikipedia is a community wiki that contains descriptive information about, among other things, music artists. Cover Art Archive is a sister project to MusicBrainz that contains cover art for various albums, singles eps, etc. released by an artist. This API - receives an MBID (MusicBrainz Identifier) and returns a result consisting of
 * A description of the artist taken from Wikipedia. Wikipedia does not contain any MBIDs, but the mapping between MBIDs and Wikipedia identifiers is available via the MusicBrainz API.
 * A list of all albums released by the artist and links to images for each album. The list of albums is on MusicBrainz but the images are on the Cover Art Archive.

### External APIs:
#### MusicBrainz
 * Documentation:
http://musicbrainz.org/doc/Development/XML_Web_Service/Version_2
 * API URL: http://musicbrainz.org/ws/2
 * Example question:
http://musicbrainz.org/ws/2/artist/5b11f4ce-a62d-471e-81fc-a69a8278c7da?&fmt
=json&inc=url-rels+release-groups
#### Wikipedia
 * Documentation: https://www.mediawiki.org/wiki/API:Main_page
 * API URL: https://en.wikipedia.org/w/api.php
 * Example question:
https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exi
ntro=true&redirects=true&titles=Nirvana_(band)
Hint: In the JSON response to the example query against MusicBrainz above you will find a list named relations, check the relation whose type is wikipedia. There you will find the name that can be used for lookups against Wikipedia's API, namely Nirvana_(band)
#### Cover Art Archive
 * Documentation: https://wiki.musicbrainz.org/Cover_Art_Archive/API
 * URL to API: http://coverartarchive.org/
 * Example question:
http://coverartarchive.org/release-group/1b022e01-4da6-387b-8658-8678046e4c
Hint: In the JSON response to the sample query against MusicBrainz above, you will find a list by name release-groups. Among other things, there is an album's title (title) and its MBID (id). This MBID is then used for the lookup against the Cover Art Archive.
Requirement
error handling.
The API would handle high load which can be challenging as the underlying APIs can be quite slow and have consumption limits.
 * The API is REST-based
 * Use JSON as the transport format.
 * the result - containing description and album - is a JSON response.

#### A response from your service may look something like the following:
``` 
{
"mbid" : "5b11f4ce-a62d-471e-81fc-a69a8278c7da",
"description" : "<p><b>Nirvana</b> was an American rock band that was formed ...
etc etc ... ",
"albums" : [
{
"title" : "Nevermind",
"id": "1b022e01-4da6-387b-8658-8678046e4cef",
"image":
"http://coverartarchive.org/release/a146429a-cedc-3ab0-9e41-1aaf5f6cdc2d/3012495605.j
pg"
},
{
... more albums...
}
]
}
``` 

