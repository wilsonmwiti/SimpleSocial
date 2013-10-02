package ivn.peng.simplesocial;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import grandroid.action.ContextAction;
import grandroid.action.GoAction;
import grandroid.action.ThreadAction;
import grandroid.database.GenericHelper;
import grandroid.database.RawFaceData;
import grandroid.json.JSONConverter;
import grandroid.net.Mon;
import java.util.ArrayList;
import ivn.peng.simplesocial.model.Friend;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 首頁。 登入 或選擇註冊新帳號
 *
 * @author Peng
 */
public class FrameMain extends FaceSocial {

    EditText et_account, et_password;
    Button bn_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll_register;

        maker.addColLayout(false, maker.layFF()).setGravity(Gravity.CENTER);
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("Simple", 200, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                maker.add(createStyliseTextView("Social", 200, Color.BLACK, Gravity.RIGHT | Gravity.TOP), maker.layFW(1));
                maker.escape();
            }
            maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.addColLayout(false, maker.layFW(1));
                {
                    maker.add(createStyliseTextView("帳號", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_account = maker.add(createStyliseEditView("", 1, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                    maker.escape();
                }
                maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
                {
                    maker.add(createStyliseTextView("密碼", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_password = maker.add(createStyliseEditView("", 1, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                    maker.escape();
                }
                maker.escape();
            }
            maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                bn_login = maker.add(maker.createButton("登入"), maker.layFW(1));
                bn_login.setGravity(Gravity.CENTER);
                ll_register = maker.addRowLayout();
                {
                    maker.add(createStyliseTextView("註冊新帳號", 1, Color.BLACK, Gravity.RIGHT | Gravity.BOTTOM), maker.layFW(0));
                    maker.escape();
                }
                maker.escape();
            }
            maker.escape();
        }

        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        if (getData().getPreference(Config.ACCOUNT) != null && !getData().getPreference(Config.ACCOUNT).equals("")) {
            et_account.setText(getData().getPreference(Config.ACCOUNT));
        }
        et_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        bn_login.requestFocus();
                        bn_login.callOnClick();
                        return true;
                    }
                }
                return false;
            }
        });

        if (getData().getPreference(Config.IS_LOGIN) != null && getData().getPreference(Config.IS_LOGIN).equals("true")) {
            reloadAll();
            Toast.makeText(this, "已登入", Toast.LENGTH_SHORT).show();
            new GoAction(FrameMain.this, FrameContent.class).execute();
            finish();
        }

        bn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (et_account.getText().toString().equals("") && et_password.getText().toString().equals("")) {
                    Toast.makeText(FrameMain.this, "請輸入帳號密碼", Toast.LENGTH_LONG).show();
                } else {
                    // 帳號密碼檢查
                    ;
                    // 假資料
                    //  admin
                    //  admin123
                    if (et_account.getText().toString().equals("admin") || et_password.getText().toString().equals("admin123")) {
                        // 載入舊有資料
                        ;

                        getData().putPreference(Config.ACCOUNT, et_account.getText().toString());
                        getData().putPreference(Config.PASSWORD, et_password.getText().toString());

                        reloadAll();

                        getData().putPreference(Config.IS_LOGIN, "true");
                        new GoAction(FrameMain.this, FrameContent.class).execute();
                        finish();
                    } else {
                        Toast.makeText(FrameMain.this, "帳號密碼錯誤", Toast.LENGTH_LONG).show();
                        et_password.setText("");
                    }
                }
            }
        });
        ll_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GoAction(FrameMain.this, FrameRegister.class).execute();
            }
        });

    }

    // 載入舊有資料
    protected void reloadAll() {
        // 假資料
        // 基本資料
        getData().putPreference(Config.NAME, "小小兵");
        getData().putPreference(Config.PHOTO_FILE, "http://4.bp.blogspot.com/-O6vpzzEkhCM/UdT75Pjrt9I/AAAAAAAAkoE/7PCnALll5DA/s1600/20001.jpg");
        // 朋友資料
        rfd = new RawFaceData(FrameMain.this, Config.DB_NAME, true);
        rfd.startEdit();
        GenericHelper<Friend> helperFriend = new GenericHelper<Friend>(rfd, Friend.class, true);
        helperFriend.truncate();

        ArrayList<Friend> flist = new ArrayList<Friend>();
        flist.add(new Friend("0000", "小小兵2號", "http://stars.udn.com/starimages/slide/213834/M_213842-DM2_Minion_Bob_005.jpg", "Y"));
        flist.add(new Friend("0001", "海棉寶寶", "http://7.blog.xuite.net/7/9/0/9/17804467/blog_855626/txt/24861275/9.jpg", "N"));
        flist.add(new Friend("0002", "派大星", "http://f.blog.xuite.net/f/d/0/1/23861562/blog_2024565/txt/32906143/0.jpg", "N"));

        helperFriend.insert(flist);

        helperFriend.close();
        rfd.endEdit();
    }

//       protected void reloadMessage() {
//        new ThreadAction(this, "", findStringResourceByName("alert_wait"), new ContextAction(this) {
//            @Override
//            public boolean execute(Context cntxt) {
//                rfd = new RawFaceData(FaceAdasia.this, Config.DB_NAME, true);
//                rfd.startEdit();
//                try {
//                    JSONObject jSONObject;
//                    jSONObject = new JSONObject(result_message);
//                    if (jSONObject.getString("message").equals("OK")) {
//                        JSONArray data = jSONObject.getJSONArray("data");
//
//                        helperUsermsng = new GenericHelper<UserMessage>(rfd, UserMessage.class, true);
//
//                        // 拿掉一次性資料
//                        List msngList = helperUsermsng.select("WHERE type <> '3'");
//                        helperUsermsng.truncate();
//                        helperUsermsng.insert(msngList);
//
//                        if (data.length() >= 1) {
//                            getData().putPreference(Config.USERMESSAGE_LAST_ID, data.getJSONObject(0).getString("message_id"));
//                            Log.d(Config.TAG, "get last id " + data.getJSONObject(0).getString("message_id"));
//                        }
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject cell = data.getJSONObject(i);
//                            UserMessage message = JSONConverter.toObject(cell, UserMessage.class);
//                            helperUsermsng.insert(message);
//                        }
//
//                        Log.d(Config.TAG, "helperUsermsng size " + helperUsermsng.select().size());
//
//                        Log.d(Config.TAG, "update message done.");
//                        helperUsermsng.close();
//                    } else {
//                        Log.e(Config.TAG, jSONObject.getString("detail"));
//                    }
//                } catch (JSONException ex) {
//                    Log.e(Config.TAG, "", ex);
//                }
//                rfd.endEdit();
//                return true;
//            }
//        }) {
//            public boolean execute(Context context) {
//                try {
//                    result_message = new Mon(Config.URL).put("action", "usermessage.get").put("uid", getData().getPreference(Config.QRCODE)).put("last_id", getData().getPreference(Config.USERMESSAGE_LAST_ID, "")).sendWithError();
//                    Log.d(Config.TAG, "result_message = " + result_message);
//                } catch (Exception ex) {
//                    Log.e(Config.TAG, null, ex);
//                }
//                return true;
//            }
//        };
//    }
    @Override
    public boolean hasMenu() {
        return false;
    }
}
