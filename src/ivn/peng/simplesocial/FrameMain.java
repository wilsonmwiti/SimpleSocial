package ivn.peng.simplesocial;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import grandroid.action.GoAction;
import grandroid.database.FaceData;
import grandroid.database.GenericHelper;
import model.Friend;

/**
 * 首頁。 登入 或選擇註冊新帳號
 *
 * @author Peng
 */
public class FrameMain extends FaceSocial {

    EditText et_account, et_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll_register;
        Button bn_login;
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
                    et_account = maker.add(createStyliseEditView("", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                    maker.escape();
                }
                maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
                {
                    maker.add(createStyliseTextView("密碼", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_password = maker.add(createStyliseEditView("", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
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

        if (getData().getPreference(Config.IS_LOGIN) != null && getData().getPreference(Config.IS_LOGIN).equals("true")) {
            // 載入舊有資料
            getData().putPreference(Config.NAME, "小小兵");
            getData().putPreference(Config.PHOTO_FILE, "http://4.bp.blogspot.com/-O6vpzzEkhCM/UdT75Pjrt9I/AAAAAAAAkoE/7PCnALll5DA/s1600/20001.jpg");
            // 朋友資料
            GenericHelper<Friend> helperFriend = new GenericHelper<Friend>(fd, Friend.class);

            helperFriend.insert(new Friend("0000", "小小兵2號", "http://stars.udn.com/starimages/slide/213834/M_213842-DM2_Minion_Bob_005.jpg", "Y"));
            helperFriend.insert(new Friend("0001", "海棉寶寶", "http://7.blog.xuite.net/7/9/0/9/17804467/blog_855626/txt/24861275/9.jpg", "N"));
            helperFriend.insert(new Friend("0002", "派大星", "http://f.blog.xuite.net/f/d/0/1/23861562/blog_2024565/txt/32906143/0.jpg", "N"));
            helperFriend.close();
            ;
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

                        // 假資料
                        // 基本資料
                        getData().putPreference(Config.NAME, "小小兵");
                        getData().putPreference(Config.PHOTO_FILE, "http://4.bp.blogspot.com/-O6vpzzEkhCM/UdT75Pjrt9I/AAAAAAAAkoE/7PCnALll5DA/s1600/20001.jpg");
                        // 朋友資料
                        GenericHelper<Friend> helperFriend = new GenericHelper<Friend>(fd, Friend.class);

                        helperFriend.insert(new Friend("0000", "小小兵2號", "http://stars.udn.com/starimages/slide/213834/M_213842-DM2_Minion_Bob_005.jpg", "Y"));
                        helperFriend.insert(new Friend("0001", "海棉寶寶", "http://7.blog.xuite.net/7/9/0/9/17804467/blog_855626/txt/24861275/9.jpg", "N"));
                        helperFriend.insert(new Friend("0002", "派大星", "http://f.blog.xuite.net/f/d/0/1/23861562/blog_2024565/txt/32906143/0.jpg", "N"));
                        helperFriend.close();

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

    @Override
    public boolean hasMenu() {
        return false;
    }
}
