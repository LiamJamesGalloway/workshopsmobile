package com.example.user.intentstests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.AsyncTask;
import android.widget.*;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Music extends AppCompatActivity {

    // global vsariable to store returned xml data from service
    static String xml = "";

    // global variable to bitmap for current number 1 single (we aren't returning the other 39 songs!)
    static Bitmap bitmap;

    // array list to store song names from  service
    ArrayList<String> songs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        new AsyncTaskParseXml().execute();
    }

    public class AsyncTaskParseXml extends AsyncTask<String, String, String> {

        // set the url of the web service to call
        String yourXmlServiceUrl = "http://35.198.83.47/teaching/CMP3034M-1718/music.xml";

        @Override
        // this method is used for......................
        protected void onPreExecute() {}

        @Override
        // this method is used for...................
        protected String doInBackground(String... arg0)  {

            try {

                String text = null;
                // create new instance of the httpConnect class
                httpConnect xmlParser = new httpConnect();

                // get xml string from service url
                xml = xmlParser.getJSONFromUrl(yourXmlServiceUrl);

                // create new instance of XmlPullParser to parse xml
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // set input to xml parser as xml string from service
                xpp.setInput( new StringReader( xml ) );

                // variable for XML parse event
                int event = xpp.getEventType();

                // variable for setting chart position of each song (the xml doesnt return this!)
                int chartPosition = 0;

                // while statement to loop through xml tags to end of xml document
                while (event != XmlPullParser.END_DOCUMENT) {

                    String name=xpp.getName();

                    switch (event){
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            text = xpp.getText();
                            break;

                        case XmlPullParser.END_TAG:

                            // when xml parser matches the <img> tag, get the 'alt' attribute value which contains the song name
                            if(name.equals("img")) {
                                chartPosition++;
                                songs.add(chartPosition + ".  " + xpp.getAttributeValue(null, "alt"));
                                // increment chart position for next song!


                                // get the first <img> tag line number value as it contains current number 1 song
                                if (chartPosition == 1) {
                                    // get the image cover art for the number 1 song from 'src' attribute in img tag
                                    String imageurl = xpp.getAttributeValue(null, "src");
                                    // parse the cover art image url to proper URL type
                                    URL u = new URL(imageurl);
                                    // download image cover art from url and save as a bitmap
                                    InputStream is = u.openConnection().getInputStream();
                                    bitmap = BitmapFactory.decodeStream(is);
                                }
                            }
                            // when the xml parser matches the <a> tag, get the text value from it which contains the artist name
                            else if(name.equals("a")) {
                                // the below simply output the Artist, it does not bind it to anything - that's your job!
                                System.out.println("Artist: " + text.trim());
                            }
                            else{
                            }
                            break;
                    }
                    event = xpp.next();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        // below method will run when service HTTP request is complete
        protected void onPostExecute(String strFromDoInBg) {
            // bind the xml from service to the textview
            ImageView singleArt = (ImageView) findViewById(R.id.singleCoverArt);
            singleArt.setImageBitmap(bitmap);

            ListView list = (ListView)findViewById(R.id.songList);
            ArrayAdapter<String> songArrayAdapter = new ArrayAdapter<String>(Music.this, android.R.layout.simple_expandable_list_item_1, songs);
            list.setAdapter(songArrayAdapter);
        }
    }
}
