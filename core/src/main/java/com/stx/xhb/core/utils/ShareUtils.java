
package com.stx.xhb.core.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.stx.xhb.core.R;

import java.io.File;


/**
 * Created by xiaohaibin on 7/12/16.
 * 分享工具类
 */
public class ShareUtils {

    /**
     * 分享文本
     * @param context
     * @param stringRes
     */
    public static void share(Context context, @StringRes int stringRes) {
        share(context, context.getString(stringRes));
    }

    /**
     * 分享图片
     * @param context
     * @param uri
     * @param title
     */
    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    /**
     * 分享文本
     * @param context
     * @param msgTitle
     * @param imgPath
     */
    public static void shareMsg(Context context, String msgTitle, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        File f = new File(imgPath);
        if (f != null && f.exists() && f.isFile()) {
            intent.setType("image/jpg");
            Uri u = Uri.fromFile(f);
            intent.putExtra(Intent.EXTRA_STREAM, u);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, msgTitle));
    }

    /**
     * 分享文本内容
     * @param context
     * @param extraText
     */
    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.action_share)));
    }

    /**
     * 分享图片至微信朋友圈
     */
    public static void shareImageToWeChat(Context context, String kdescription, Uri uri) {
        if (!isInstallWeChart(context)) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setType("image/*");
        //这个是分享文字描述
        intent.putExtra("Kdescription", kdescription);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(intent);

    }

    /**
     * 检查是否安装微信
     * @param context
     * @return
     */
    public static boolean isInstallWeChart(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mm", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
