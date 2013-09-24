/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import grandroid.action.GoAction;

/**
 * 修改個人資訊
 *
 * @author Peng
 */
public class FrameEdit extends FaceSocial {

    EditText et_id, et_psold, et_ps1, et_ps2, et_email, et_name;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Button btn_base, btn_password, btn_info;

        addTopBanner("註冊", false);

        maker.addColLayout(true, maker.layFF()).setGravity(Gravity.CENTER);
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            addBrock(0, 0, 660, 660, Color.RED);

            maker.addColLayout(false, maker.layFW(1));
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("帳號", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                et_id = maker.add(createStyliseEditView("請輸入6-12位元的字母及數字", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                maker.add(createStyliseTextView("暱稱", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                et_name = maker.add(createStyliseEditView("請輸入公開的暱稱", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                maker.add(createStyliseTextView("信箱", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                et_email = maker.add(createStyliseEditView("請輸入電子信箱", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                maker.add(createStyliseTextView("", 0, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                btn_base = maker.add(maker.createButton("修改基本資料"), maker.layFW(1));
                btn_base.setGravity(Gravity.CENTER);
                maker.escape();
            }
            maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("密碼", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                et_psold = maker.add(createStyliseEditView("請輸入原本的密碼", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                maker.add(createStyliseTextView("", 0, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                et_ps1 = maker.add(createStyliseEditView("請輸入6-12位元的字母及數字", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                maker.add(createStyliseTextView("", 0, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                et_ps2 = maker.add(createStyliseEditView("請再輸入一次密碼", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                maker.add(createStyliseTextView("", 0, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                btn_password = maker.add(maker.createButton("修改密碼"), maker.layFW(1));
                btn_password.setGravity(Gravity.CENTER);
                maker.escape();
            }
            maker.escape();
        }


        et_id.setText(getData().getPreference(Config.ID));
        et_id.setClickable(false);
        et_id.setFocusable(false);
        et_id.setEnabled(false);
        et_name.setText(getData().getPreference(Config.NAME));
        et_email.setText(getData().getPreference(Config.EMAIL));

        btn_base.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 修改基本資料
                ;
            }
        });

        btn_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 修改密碼
                ;

            }
        });
    }

    @Override
    public boolean hasMenu() {
        return true;
    }
}
