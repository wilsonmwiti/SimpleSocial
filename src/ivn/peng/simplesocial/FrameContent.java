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
import grandroid.image.PhotoAgent;
import grandroid.image.PhotoHandler;
import java.util.List;
import ivn.peng.simplesocial.model.Friend;

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

//        Log.d(Config.TAG, "get best " + list_best.size());
//        Log.d(Config.TAG, "get " + list.size());
        helperFriend.close();

        addTopBanner();

        maker.addColLayout(true, maker.layFW(1));
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("常用聯絡人", 1, Color.BLACK));
                maker.add(cretaeGridView(list_best), maker.layFW());
                maker.escape();
            }

            addLine(0, 0, LinearLayout.LayoutParams.MATCH_PARENT, 2, Color.GRAY);

            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("朋友列表", 1, Color.BLACK));
                maker.add(cretaeGridView(list), maker.layFW());
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

    private GridView cretaeGridView(List<Friend> list) {
        GridView gv_best = new GridView(this);
        gv_best.setNumColumns(4);
        gv_best.setHorizontalSpacing(2);
        gv_best.setVerticalSpacing(2);
        gv_best.setAdapter(createAdapter(list));
//        gv_best.setOnLongClickListener(null);
        return gv_best;
    }

    private ObjectAdapter<Friend> createAdapter(List<Friend> list) {
        ObjectAdapter<Friend> adapter = new ObjectAdapter<Friend>(this, list) {
            Friend friend;

            @Override
            public View createRowView(int index, Friend item) {
                LinearLayout view = new LinearLayout(context);
                ImageView iv = maker.createImage(ImageView.class, 0);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setTag("image");
                iv.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        new GoAction(context, FrameContentInfo.class).addBundleObject("friend", friend.getAccount()).execute();
                    }
                });
                iv.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        // 功能選單
                        //   加為摰有
                        //   個人資訊
                        return true;
                    }
                });
                view.addView(iv, maker.layAbsolute(0, 0, 220, 220));
                return view;
            }

            @Override
            public void fillRowView(int index, View cellRenderer, Friend item) {
                friend = item;
                loader.displayImage(item.getUrl(), (ImageView) cellRenderer.findViewWithTag("image"));
//                loader.displayImage(item.getUrl(), (ImageView) cellRenderer.findViewWithTag("image"), new PhotoHandler() {
//                    public void execute(PhotoAgent pa) {
//                        int size = 220;
//
//                        int w = pa.getBitmap().getWidth();
//                        int h = pa.getBitmap().getHeight();
//                        Log.d(Config.TAG, "w: " + w + ", h:  " + h);
//                        float xriat = 0;
//                        if (w > 220 && h > 220) {
//                            xriat = w < h ? w / size : h / size;
//                        }
//                        pa.fixSize(Math.round(w / xriat), Math.round(h / xriat));
////                        pa.square(size);
////                       
//                    }
//                });
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
