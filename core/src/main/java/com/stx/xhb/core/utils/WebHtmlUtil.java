package com.stx.xhb.core.utils;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * desc :html工具
 * </pre>
 */
public class WebHtmlUtil {
    /**
     * 图片点击
     *
     * @param webView
     */
    public static void clickImage(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    public static void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.width = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    /**
     * 获取html中的所有图片
     *
     * @param htmlContnet
     * @return
     */
    public static ArrayList<String> returnImageUrlsFromHtml(String htmlContnet) {
        ArrayList<String> imageSrcList = new ArrayList<String>();
        if (TextUtils.isEmpty(htmlContnet)) {
            return imageSrcList;
        }
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlContnet);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList", "资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList;
    }

    /**
     * 对服务端给的文本进行加入完整的html格式和css样式
     **/
    public static String htmlText(String htmlContnet, String htmlCss) {
        if (TextUtils.isEmpty(htmlContnet)) {
            return "";
        }
        String html = "<html>\n" +
                "<head>\n " +
                "<meta charset=\"utf-8\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "<link href=\"" + htmlCss + "\" rel=\"stylesheet\" type=\"text/css\"/>" +
                "<style type=\"text/css\">\n " +
                "html {\n" +
                "line-height: 1.15;\n" +
                "-ms-text-size-adjust: 100%;\n" +
                "-webkit-text-size-adjust: 100%;\n" +
                "width:100%;\n" +
                "height:100%;\n" +
                "margin:0 auto;\n" +
                "padding:0;\n" +
                "}\n" +
                "body {\n" +
                "font-size:16px;\n" +
                "line-height:1.8;\n" +
                "background-color: #fff !important;\n" +
                "color:#000\n" +
                "margin: 0;\n" +
                "padding: 0 10px;\n" +
                "word-wrap: break-word;\n" +
                "outline: 0;\n" +
                "-webkit-tap-highlight-color: rgba(0,0,0,0);\n" +
                "-webkit-tap-highlight-color: transparent;\n" +
                "-webkit-touch-callout: none;\n" +
                "-webkit-user-select: none;\n" +
                "overflow-y: scroll;\n" +
                "}\n" +
                "img.image {\n" +
                "width: 100%;\n" +
                "height: auto;\n" +
                "border: 0;\n" +
                "margin: 5px 0!important;\n" +
                "}" +
                "</style>\n" +
                "<head>\n" +
                "<body>\n" +
                "" + htmlContnet + "\n" +
                "</body>\n" +
                "</html>";
        return html;
    }
}
