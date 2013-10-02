/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import grandroid.adapter.JSONAdapter;
import grandroid.database.GenericHelper;
import ivn.peng.simplesocial.model.Conversation;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * @author Peng
 */
public class FrameContentInfo extends FaceSocial {

    EditText et_msng;
    ImageView iv_me, iv_friend;
    Conversation conversation;
    String friend;
    JSONAdapter adapter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        ListView listView;

        Bundle bundle = this.getIntent().getExtras();
        friend = bundle.getString("friend");

        GenericHelper<Conversation> helperConversation = new GenericHelper<Conversation>(fd, Conversation.class);
        conversation = helperConversation.selectSingle("WHERE account = '" + friend + "'");

        adapter = new JSONAdapter(this, null) {
            @Override
            public View createRowView(int index, JSONObject item) {
                LinearLayout ll = new LinearLayout(context);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setLayoutParams(maker.layAbsolute(0, 0, 660, LinearLayout.LayoutParams.WRAP_CONTENT));
                ImageView iv = maker.createImage(ImageView.class, 0);
                iv.setTag("image");
                ll.addView(iv, maker.layAbsolute(0, 0, 110, 110));
                TextView tv_name = createStyliseTextView("", 0, Color.BLACK);
                tv_name.setTag("name");
                ll.addView(tv_name, maker.layWW(0));
                TextView tv_time = createStyliseTextView("", 0, Color.GRAY);
                tv_time.setTag("time");
                ll.addView(tv_time, maker.layWW(0));
                TextView tv_mesg = createStyliseTextView("", 1, Color.BLACK);
                tv_name.setTag("name");
                ll.addView(tv_name, maker.layWW(0));

                try {
                    if (item.getString("message_acount").equals(getData().getPreference(Config.ACCOUNT))) {
                        ll.setGravity(Gravity.RIGHT);
                        tv_mesg.setGravity(Gravity.RIGHT);
                    } else {
                        ll.setGravity(Gravity.LEFT);
                        tv_mesg.setGravity(Gravity.LEFT);
                    }
                } catch (JSONException ex) {
                    Log.e(Config.TAG, null, ex);
                }
                return ll;
            }

            @Override
            public void fillRowView(int index, View view, JSONObject item) throws JSONException {
                ((TextView) view.findViewWithTag("mesg")).setText(item.getString("content"));
                 ((TextView) view.findViewWithTag("time")).setText(item.getString("time"));
                if (item.getString("who").equals(getData().getPreference(Config.ACCOUNT))) {
                    ((TextView) view.findViewWithTag("name")).setText(getData().getPreference(Config.ACCOUNT));
                    loader.displayImage(getData().getPreference(Config.PHOTO_FILE), ((ImageView) view.findViewWithTag("image")));
                } else {
                    ((TextView) view.findViewWithTag("name")).setText(item.getString("who"));
                    loader.displayImage(conversation.getUrl(), ((ImageView) view.findViewWithTag("image")));
                }
            }

            @Override
            public void onLongPressItem(int index, View view, JSONObject item) {
                super.onLongPressItem(index, view, item);
                // 功能選單
                //   加為摰有
                //   個人資訊
            }
        };

        addTopBanner();

        LinearLayout lv = maker.addColLayout(false, maker.layFW(1));
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addRowLayout(false, maker.layFW());
            {
                maker.addRowLayout(false, maker.layAbsolute(0, 0, 660, LinearLayout.LayoutParams.WRAP_CONTENT));
                {
                    iv_friend = maker.addImage(0, maker.layAbsolute(0, 0, 220, 220));
                    maker.escape();
                }
                maker.addRowLayout(false, maker.layAbsolute(100, 0, 220, 220));
                {
                    iv_me = maker.addImage(0, maker.layAbsolute(0, 0, 220, 220));
                    maker.escape();
                }
                maker.escape();
            }

            addLine(0, 0, LinearLayout.LayoutParams.MATCH_PARENT, 2, Color.GRAY);

            maker.addColLayout(false, maker.layFW(1));
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                listView = maker.addListView(adapter);
                maker.escape();
            }

            maker.addRowLayout(false, maker.layFW(0));
            {
                et_msng = maker.add(createStyliseEditView("在此輸入訊息", 1, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW());
                maker.escape();
            }
            maker.escape();
        }
        addButtomBanner(2);

        lv.setFocusable(true);
        lv.setFocusableInTouchMode(true);
        lv.requestFocus();

        loader.displayImage(conversation.getUrl(), iv_friend);
        loader.displayImage(getData().getPreference(Config.PHOTO_FILE), iv_me);

        et_msng.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        et_msng.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean hasMenu() {
        return true;
    }
}
