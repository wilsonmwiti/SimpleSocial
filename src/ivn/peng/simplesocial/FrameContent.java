/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import grandroid.action.GoAction;

/**
 * 內頁首頁 顯示所有朋友的大頭像
 *
 * @author Peng
 */
public class FrameContent extends FaceSocial {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll_content;

        addTopBanner();

        maker.addColLayout(true, maker.layFW(1));
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("常用聯絡人", 1, Color.BLACK));
                maker.addRowLayout(false, maker.layFW());
                {
                    ll_content = addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    maker.escape();
                }
                maker.escape();
            }

            addLine(0, 0, 880, 2, Color.GRAY);

            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("朋友列表", 1, Color.BLACK));
                maker.addRowLayout(false, maker.layFW());
                {
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    maker.escape();
                }
                maker.addRowLayout(false, maker.layFW());
                {
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    maker.escape();
                }
                maker.escape();
            }
            maker.escape();
        }
        addButtomBanner(1);

        ll_content.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new GoAction(FrameContent.this, FrameContentInfo.class).addBundleObject("target", "id").setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (state == 1) {
            state = 0;
            smenu.showContent();
        } else {
            AlertDialog.Builder dialog = alertDialog("確定要離開嗎?");
            dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.create().show();
        }
    }

    @Override
    public boolean hasMenu() {
        return true;
    }
}
