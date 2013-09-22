/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import grandroid.action.GoAction;

/**
 *
 * @author Peng
 */
public class FrameRegister extends FaceSocial {

    EditText et_id, et_ps1, et_ps2, et_email, et_name;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Button btn_ok;

        addTopBanner("註冊", false);

        maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addColLayout(false, maker.layFW(5));
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.addColLayout(false, maker.layFW(1));
                {
                    maker.add(createStyliseTextView("帳號", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_id = maker.add(createStyliseEditView("請輸入6-12位元的字母及數字", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                    maker.escape();
                }
                maker.addColLayout(false, maker.layFW(1));
                {
                    maker.add(createStyliseTextView("暱稱", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_name = maker.add(createStyliseEditView("請輸入公開的暱稱", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                    maker.escape();
                }
                maker.addColLayout(false, maker.layFW(2)).setGravity(Gravity.CENTER);
                {
                    maker.add(createStyliseTextView("密碼", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_ps1 = maker.add(createStyliseEditView("請輸入6-12位元的字母及數字", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
//                    maker.escape();
//                }
//                maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
//                {
                    maker.add(createStyliseTextView("", 0, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_ps2 = maker.add(createStyliseEditView("請再輸入一次密碼", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                    maker.escape();
                }
                maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
                {
                    maker.add(createStyliseTextView("信箱", 1, Color.BLACK, Gravity.LEFT | Gravity.BOTTOM), maker.layFW(1));
                    et_email = maker.add(createStyliseEditView("請輸入電子信箱", 0, Color.BLACK, Gravity.LEFT | Gravity.CENTER), maker.layFW(1));
                    maker.escape();
                }
                maker.escape();
            }
            maker.addColLayout(false, maker.layFW(1)).setGravity(Gravity.CENTER);
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                btn_ok = maker.add(maker.createButton("登入"), maker.layFW(1));
                btn_ok.setGravity(Gravity.CENTER);
                maker.escape();
            }
            maker.escape();
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 輸入判定
                ;
                // 註冊
                ;
                
                new GoAction(FrameRegister.this, FrameContent.class).execute();
            }
        });
    }
}
