package com.roya.jandanplus;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JandanParser {
    final String TAG = "JandanParser";

    final Context context;
    Document document = null;
    final String Home_URL = "http://i.jandan.net/page/";

    public JandanParser(Context context){
        this.context = context;
    }

    public List<Map<String, Object>> JandanHomePage(int Page){

        //Log.e("HOMEPAGE",""+Page);
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();

        try {
            document = Jsoup.connect(Home_URL +Page).timeout(5000).get();
        }
        catch (IOException e){
            Log.e(TAG,e.toString());
            Toast.makeText(context,"无法连接到服务器，请稍后再试",Toast.LENGTH_SHORT).show();
            return null;
        }

        Elements posts = document.getElementsByClass("post");

        for(Element i:posts){
            final Map<String, Object> item = new HashMap<String, Object>();

            Elements thetitle = i.getElementsByClass("thetitle");

            //link
            Pattern pattern = Pattern.compile("http://(.*)html");
            Matcher matcher = pattern.matcher(thetitle.toString());
            if (matcher.find()){
                item.put("link",matcher.group());

            }

            //title
            pattern = Pattern.compile("l\">(.*)</a>");
            matcher = pattern.matcher(thetitle.toString());
            if (matcher.find()){
                item.put("title",matcher.group().substring(3,matcher.group().length()-4));
            }

            Elements indexs = i.getElementsByClass("indexs");

            //cont
            pattern = Pattern.compile("[\\s].[0-9]");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("cont",matcher.group());
            }else {
                item.put("cont","0");
            }

            //by
            pattern = Pattern.compile("<b>(.*)</b>");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("by","by "+matcher.group().substring(3,matcher.group().length()-4));
            }

            //tag
            pattern = Pattern.compile(">(.*)</a>");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("tag","#"+matcher.group().substring(1,matcher.group().length()-4));
            }

            //image
            Elements thumb_s = i.getElementsByClass("thumb_s");
            pattern = Pattern.compile("src(.*)\"");
            matcher = pattern.matcher(thumb_s.toString());

            if (matcher.find()) {

                item.put("image",R.drawable.loading);

                final Matcher finalMatcher = matcher;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        item.put("image", getBitMap(finalMatcher.group().substring(5, finalMatcher.group().length()-1)));
                    }
                }).start();

            }

            //add item to items
            if(item.get("tag") != null) {
                items.add(item);
            }
        }
        //Log.e(TAG,items.toString());
        return items;
    }


    private Bitmap getBitMap(String strUrl) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            URL url = new URL(strUrl);
            URLConnection conn = url.openConnection();
            is = conn.getInputStream();
        } catch (IOException e) {
            return null;
        }
        bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

}
