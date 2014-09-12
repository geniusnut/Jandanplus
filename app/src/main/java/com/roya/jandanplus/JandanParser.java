package com.roya.jandanplus;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    final String URL  = "http://i.jandan.net/page/";

    public JandanParser(Context context){
        this.context = context;
    }

    public List<Map<String, Object>> JandanHomePage(int Page){

        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();

        try {
            document = Jsoup.connect(URL+Page).timeout(5000).get();
        }
        catch (IOException e){
            Log.e(TAG,e.toString());
            Toast.makeText(context,"无法连接到服务器，请稍后再试",Toast.LENGTH_SHORT).show();
            return null;
        }

        Elements posts = document.getElementsByClass("post");

        for(Element i:posts){
            Map<String, Object> item = new HashMap<String, Object>();

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
            }

            //by
            pattern = Pattern.compile("<b>(.*)</b>");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("by",matcher.group().substring(3,matcher.group().length()-4));
            }

            //tag
            pattern = Pattern.compile(">(.*)</a>");
            matcher = pattern.matcher(indexs.toString());
            if (matcher.find()){
                item.put("tag"," / "+matcher.group().substring(1,matcher.group().length()-4));
            }

            Log.e(TAG,item.toString());
        }


        return items;
    }
}
