package com.xx.chinetek.util.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.widget.EditText;

import com.xx.chinetek.cywms.R;
import com.xx.chinetek.util.function.CommonUtil;


public class MessageBox {
    /**
     * 弹出默认提示框
     *
     * @param context 上下文
     * @param message 需要弹出的消息
     */
    public static void Show(Context context, String message) {
        new AlertDialog.Builder(context).setTitle(context.getString(R.string.hint)).setCancelable(false).setMessage(message).setPositiveButton(context.getString(R.string.config), null).show();
    }

    public static void Show(Context context, int resourceID) {
        String msg = context.getResources().getString(resourceID);
        new AlertDialog.Builder(context).setTitle(context.getString(R.string.hint)).setCancelable(false).setMessage(msg).setPositiveButton(context.getString(R.string.config), null).show();
    }

    public static void Show(Context context, String mString, EditText togText, AlertDialog alertDialog) {
        alertDialog = new AlertDialog.Builder(context).setTitle(context.getString(R.string.hint)).setCancelable(false).setMessage(mString).setPositiveButton(context.getString(R.string.config), null).create();

        final EditText tagEditText = togText;
        alertDialog.setOnShowListener(new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                CommonUtil.setEditFocus(tagEditText);
            }
        });

        alertDialog.show();
    }

    public static void Show(Context context, String mString, EditText togText) {
        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false).setTitle(context.getString(R.string.hint)).setMessage(mString).setPositiveButton(context.getString(R.string.config), null).create();

        final EditText tagEditText = togText;
        dialog.setOnShowListener(new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                CommonUtil.setEditFocus(tagEditText);
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                CommonUtil.setEditFocus(tagEditText);
            }
        });
        dialog.show();
    }

    public static void Show(Context context, String message, EditText recivceTEXT, EditText sendTEXT) {
        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false).setTitle(context.getString(R.string.hint)).setMessage(message).setPositiveButton(context.getString(R.string.switch_yes), null).create();
        final EditText RecivceTEXT = recivceTEXT;
        final EditText SendTEXT = sendTEXT;
        dialog.setOnShowListener(new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                CommonUtil.setEditFocus(RecivceTEXT);
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                CommonUtil.setEditFocus(SendTEXT);
            }
        });
        dialog.show();
    }

}
