package com.roya.jandanplus;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Message;
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
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JandanParser {
    final String TAG = "JandanParser";

    final Context context;
    Document document ;
    String UA = "Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19";
    final String Home_URL = "http://i.jandan.net/page/";
    final String PIC_URL = "http://i.jandan.net/pic";
    String PIC_PAGE ;
    OnImageChangedlistener listener;

    public interface OnImageChangedlistener{
        void OnImageChanged();
    }
    public void setOnImageChangedlistener(OnImageChangedlistener onImageChangedlistener){
        this.listener = onImageChangedlistener;
    }

    public JandanParser(Context context){
        this.context = context;
    }

    public List<Map<String, Object>> JandanHomePage(int Page){

        //Log.e("HOMEPAGE",""+Page);
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();

        try {
            document = Jsoup.connect(Home_URL +Page)
                    .timeout(2500)
                    .userAgent(UA)
                    .get();
        }
        catch (Exception e){
            Log.e(TAG,e.toString());
            return items;
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
            pattern = Pattern.compile(" [0-9]* ");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("cont",matcher.group().substring(1,matcher.group().length()-1));
            }else {
                item.put("cont","0");
            }

            //by
            pattern = Pattern.compile("<b>(.*)</b>");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("by",matcher.group().substring(3,matcher.group().length()-4));
            }

            //tag
            pattern = Pattern.compile("\"tag\">(.*)</a>");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("tag",matcher.group().substring(6,matcher.group().length()-4));
            }else {
                item.put("tag","");
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
                        item.put("image", getBitMap(
                                finalMatcher.group()
                                        .substring(5, finalMatcher.group().length()-1)
                                        .replaceAll("square","small")));
                        listener.OnImageChanged();
                    }
                }).start();

            }

            //add item to items
            if(item.get("title") != null) {
                items.add(item);
            }
        }
        //Log.e(TAG,items.toString());
        return items;
    }


    public List<Map<String, Object>> JandanPicPage(int Page){

        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();



        //page
        if (Page == 0){

            try {
                document = Jsoup.connect(PIC_URL)
                        .timeout(2500)
                        .userAgent(UA)
                        .get();
            }
            catch (Exception e){
                Log.e(TAG,e.toString());
                //Toast.makeText(context,"无法连接到服务器，请稍后再试",Toast.LENGTH_SHORT).show();
                return items;
            }

            Elements comment_page = document.getElementsByClass("current-comment-page");
            PIC_PAGE = comment_page.get(0).toString().substring(36,comment_page.get(1).toString().length()-8);
        }else {
            try {
                document = Jsoup.connect("http://i.jandan.net/pic/page-"+(Integer.parseInt(PIC_PAGE)-Page))
                        .timeout(2500)
                        .userAgent(UA)
                        .get();
            }
            catch (Exception e){
                Log.e(TAG,e.toString());
                //Toast.makeText(context,"无法连接到服务器，请稍后再试",Toast.LENGTH_SHORT).show();
                return items;
            }

        }

        Elements posts = document.select("li");

        for(Element i:posts){
            final Map<String, Object> item = new HashMap<String, Object>();

            //id
            Pattern pattern = Pattern.compile("comment-[0-9]*");
            Matcher matcher = pattern.matcher(i.toString());
            if (matcher.find()){
                item.put("id",matcher.group().substring(8));
                //Log.e(TAG,item.get("id").toString());
            }

            //updater
            pattern = Pattern.compile("<b>(.*)</b>");
            matcher = pattern.matcher(i.toString());
            if (matcher.find()){
                item.put("updater",matcher.group().substring(3,matcher.group().length()-4));

            }

            Elements time = i.getElementsByClass("time");
            //time
            pattern = Pattern.compile("[0-9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9]");
            matcher = pattern.matcher(time.toString());
            if (matcher.find()){
                item.put("time",matcher.group());
            }

            //text
            Elements text = i.getElementsByClass("commenttext");
            pattern = Pattern.compile("<p>(\\S*)<br");
            matcher = pattern.matcher(text.toString());
            if (matcher.find()){
                item.put("text",matcher.group().substring(3, matcher.group().length() - 4));
            }

            //ooxx
            if (item.get("id") != null) {
                pattern = Pattern.compile("id=\"cos_support-" + item.get("id").toString() + "\">(.*)</span>");
                matcher = pattern.matcher(i.toString());
                if (matcher.find()) {
                    item.put("oo", matcher.group().substring(25, matcher.group().length() - 7));
                }
                pattern = Pattern.compile("id=\"cos_unsupport-" + item.get("id").toString() + "\">(.*)</span>");
                matcher = pattern.matcher(i.toString());
                if (matcher.find()) {
                    item.put("xx", matcher.group().substring(27, matcher.group().length() - 7));
                }
            }

            //image
            pattern = Pattern.compile("src=\"(\\S*)[^ ]\"");
            matcher = pattern.matcher(i.toString());
            if (matcher.find()){
                item.put("image",R.drawable.loading);

                final Matcher finalMatcher = matcher;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        item.put("image", getBitMap(
                                finalMatcher.group()
                                        .substring(5, finalMatcher.group().length()-1)));
                        listener.OnImageChanged();
                    }
                }).start();
            }

            //add item to items
            if(item.get("image") != null) {
                items.add(item);
            }
        }
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
