package kku.sunanyuenyong.apinya.easykku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Apinya on 12/11/2559.
 */

public class MyAlert {

    //Explicit
    private Context context;
    private int anInt;
    private String titleString, messageString;

    public MyAlert(Context context, int anInt, String titleString, String messageString) {
        this.context = context;
        this.anInt = anInt;
        this.titleString = titleString;
        this.messageString = messageString;
    }

    public void myDialog(){ //ไม่รีเทิร์นค่ากลับ void

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // ถ้า pop-up ขึ้นมาแล้วกด undo ไม่ได้ มันไม่หาย ต้องกด OK ถึงจะยอมให้ข้อความหายไป เป็น false
        builder.setIcon(anInt);
        builder.setTitle(titleString);
        builder.setMessage(messageString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    } //myDialog

}// Main Class
