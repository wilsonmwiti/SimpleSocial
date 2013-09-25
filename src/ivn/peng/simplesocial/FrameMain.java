package ivn.peng.simplesocial;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import grandroid.action.GoAction;

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
            et_password.setText(getData().getPreference(Config.PASSWORD));
        }

        bn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getData().putPreference(Config.ACCOUNT, et_account.getText().toString());
                getData().putPreference(Config.PASSWORD, et_password.getText().toString());
                
                // 帳號密碼檢查
                ;
                // 載入舊有資料
                ;

                new GoAction(FrameMain.this, FrameContent.class).execute();
                finish();
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
