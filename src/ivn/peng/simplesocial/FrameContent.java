/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import grandroid.action.GoAction;
import grandroid.adapter.ObjectAdapter;
import grandroid.database.GenericHelper;
import java.util.List;
import model.Friend;

/**
 * 內頁首頁 顯示所有朋友的大頭像
 *
 * @author Peng
 */
public class FrameContent extends FaceSocial {

    List list_best, list;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll_content;

        GenericHelper<Friend> helperFriend = new GenericHelper<Friend>(fd, Friend.class);
        list_best = helperFriend.select("WHERE is_best = 'Y'");
        list = helperFriend.select();

        Log.d(Config.TAG, "get best " + list_best.size());
        Log.d(Config.TAG, "get " + list.size());
        helperFriend.close();

        addTopBanner();

        maker.addColLayout(true, maker.layFW(1));
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("常用聯絡人", 1, Color.BLACK));
                GridView gv_best = new GridView(this);
                gv_best.setNumColumns(4);
                gv_best.setAdapter(createAdapter(list_best));
                maker.add(gv_best, maker.layFW());
//                maker.addRowLayout(false, maker.layFW());
//                {
//                    ll_content = addBrock(0, 0, 220, 220, Color.GRAY);
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    maker.escape();
//                }
                maker.escape();
            }

            addLine(0, 0, LinearLayout.LayoutParams.MATCH_PARENT, 2, Color.GRAY);

            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("朋友列表", 1, Color.BLACK));
                GridView gv = new GridView(this);
                gv.setNumColumns(4);
                gv.setAdapter(createAdapter(list));
                maker.add(gv, maker.layFW());
//                maker.addRowLayout(false, maker.layFW());
//                {
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    maker.escape();
//                }
//                maker.addRowLayout(false, maker.layFW());
//                {
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    addBrock(0, 0, 220, 220, Color.GRAY);
//                    maker.escape();
//                }
                maker.escape();
            }
            maker.escape();
        }
        addButtomBanner(1);

//        ll_content.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                new GoAction(FrameContent.this, FrameContentInfo.class).addBundleObject("target", "id").setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).execute();
//            }
//        });
    }

    private ObjectAdapter<Friend> createAdapter(List<Friend> list) {
        ObjectAdapter<Friend> adapter = new ObjectAdapter<Friend>(this, list) {
            Friend friend;

            @Override
            public View createRowView(int index, Friend item) {
                LinearLayout view = new LinearLayout(context);
                ImageView iv = maker.createImage(ImageView.class, 0);
                iv.setScaleType(ImageView.ScaleType.CENTER);
                iv.setTag("image");
                iv.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        new GoAction(context, FrameContentInfo.class).addBundleObject("friend", friend.getAccount()).execute();
                    }
                });
                view.addView(iv, maker.layAbsolute(0, 0, 220, 220));
                return view;
            }

            @Override
            public void fillRowView(int index, View cellRenderer, Friend item) {
                friend = item;
                loader.displayImage(item.getUrl(), (ImageView) cellRenderer.findViewWithTag("image"));
            }
        };
        return adapter;
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
