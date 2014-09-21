package com.roya.jandanplus;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PostFormater {

    final String TAG = "PostFormater";

    final Context context;
    Document document;

    public PostFormater(Context context){
        this.context = context;
    }

    public String postFormater(String link){
        String data;
        String CSS = "<head>\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html;\" />\n" +
                "\t<title>CSS</title>\n" +
                "\t<style type=\"text/css\">\n" +
                "\t\tbody {\n" +
                "\t\t\tbackground:#fefefe;\n" +
                "\t\t\tfont-family:Arial, Helvetica, sans-serif;\n" +
                "\t\t\tfont-size:1em;\n" +
                "\t\t\tcolor: #42454c; \n" +
                "\t\t\tpadding:3%;\n" +
                "\t\t}\n" +
                "\t\tembed {\n" +
                "\t\t\tdisplay:none;\n" +
                "\t\t}\n" +
                "\t\timg {\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\theight: auto\n" +
                "\t\t}\n" +
                "\t\tem {\n" +
                "\t\t\tfont-size:0.8em;\n" +
                "\t\t\tcolor: #b3b4b7; \n" +
                "\t\t}\n" +
                "\t\t.postinfo {\n" +
                "\t\t\tfont-size:0.8em;\n" +
                "\t\t\tcolor: #b3b4b7; \n" +
                "\t\t}\n" +
                "\t\ta {  \n" +
                "\t\t\tfont-size:1.1em;\n" +
                "\t\t\tcolor: #42454c;  \n" +
                "\t\t\ttext-decoration: none;  \n" +
                "\t\t}  \n" +
                "\t</style>\n" +
                "</head>";

        try {
            document = Jsoup.connect(link)
                    .timeout(2500)
                    .userAgent("Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19")
                    .get();
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
            return "";
        }

        data = document.getElementsByClass("postinfo").toString()+
                document.getElementsByClass("entry").get(0).toString();

        return CSS + data;
    }

    public String commFormater(String link){
        String data;
        String CSS = "<head>\n" +
                "\t<meta http-equiv=\"Content-Type\" content=\"text/html;\" />\n" +
                "\t<title>CSS</title>\n" +
                "\t<style type=\"text/css\">\n" +
                "\t\tbody {\n" +
                "\t\t\tbackground:#fefefe;\n" +
                "\t\t\tfont-family:Arial, Helvetica, sans-serif;\n" +
                "\t\t\tfont-size:1em;\n" +
                "\t\t\tcolor: #42454c; \n" +
                "\t\t}\n" +
                "\t\tembed {\n" +
                "\t\t\tdisplay:none;\n" +
                "\t\t}\n" +
                "\t\timg {\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\theight: auto\n" +
                "\t\t}\n" +
                "\t\tem {\n" +
                "\t\t\tfont-size:0.8em;\n" +
                "\t\t\tcolor: #b3b4b7; \n" +
                "\t\t}\n" +
                "\t\t.postinfo {\n" +
                "\t\t\tfont-size:0.8em;\n" +
                "\t\t\tcolor: #b3b4b7; \n" +
                "\t\t}\n" +
                "\t\ta {  \n" +
                "\t\t\tdisplay:none;\n" +
                "\t\t}  \n" +
                "\t\t.vote{\n" +
                "\t\t\tdisplay:none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t</style>\n" +
                "</head>";

        try {
            document = Jsoup.connect(link).timeout(2500).get();
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
            return "";
        }

        data = document.getElementsByClass("commentlist").toString();

        return CSS + data;
    }
}
