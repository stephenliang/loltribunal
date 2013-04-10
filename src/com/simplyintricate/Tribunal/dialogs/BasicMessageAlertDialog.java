package com.simplyintricate.Tribunal.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class BasicMessageAlertDialog {
    public static void createBasicMessageDialog(Context context, String textToDisplay)
    {
        AlertDialog ad = new AlertDialog.Builder(context).create();
        ad.setCancelable(false);
        ad.setMessage(textToDisplay);
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }
}
