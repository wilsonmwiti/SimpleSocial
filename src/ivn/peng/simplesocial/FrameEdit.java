/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import grandroid.action.PhotoAction;
import grandroid.action.PickPhotoAction;
import grandroid.cache.lazyloader.ImageLoader;
import grandroid.dialog.CommandPickModel;
import grandroid.image.ImageUtil;
import grandroid.image.PhotoAgent;
import grandroid.image.PhotoHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 修改個人資訊
 *
 * @author Peng
 */
public class FrameEdit extends FaceSocial {

    EditText et_id, et_psold, et_ps1, et_ps2, et_email, et_name;
    protected ImageView photoView;
    String focus;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Button btn_base, btn_password, btn_info;

        loader = new ImageLoader(this, cacher, 660, 660, Color.GRAY);

        addTopBanner("修改資料", false);
        LinearLayout lv = maker.addColLayout(true, maker.layFW(1));
        lv.setGravity(Gravity.CENTER);
        maker.setScalablePadding(lv, 50, 50, 50, 50);
        {
            photoView = maker.addImage(0, maker.layAbsolute(0, 0, 660, 660));
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
        addButtomBanner(0);

        Bundle bundle = this.getIntent().getExtras();
        focus = bundle.getString("focus");
        if (focus.equals("name")) {
            et_name.requestFocus();
//        } else if (focus.equals("photo")) {
//            photoView.requestFocus();
        } else {
            lv.setFocusable(true);
            lv.setFocusableInTouchMode(true);
            lv.requestFocus();
        }

        Log.d(Config.TAG, "get photo " + getData().getPreference(Config.PHOTO_FILE));
        photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        if (getData().getPreference(Config.PHOTO_FILE).subSequence(0, 4).equals("http")) {
            Log.d(Config.TAG, "load photo from net");
              loader.displayImage(getData().getPreference(Config.PHOTO_FILE), photoView);
//            loader.displayImage(getData().getPreference(Config.PHOTO_FILE), photoView, new PhotoHandler() {
//                public void execute(PhotoAgent pa) {
//                    pa.square(660f);
////                    pa.fixSize(660, 660);
//                }
//            });
        } else {
            Log.d(Config.TAG, "load photo from sdcard");
            try {
                photoView.setImageBitmap(ImageUtil.loadBitmap(getData().getPreference(Config.PHOTO_FILE)));
            } catch (Exception ex) {
                Log.e(Config.TAG, null, ex);
            }
        }

        et_id.setText(getData().getPreference(Config.ACCOUNT));
        et_id.setClickable(
                false);
        et_id.setFocusable(
                false);
        et_id.setEnabled(
                false);
        et_name.setText(getData().getPreference(Config.NAME));
        et_email.setText(getData().getPreference(Config.EMAIL));

        et_ps1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_ps2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_psold.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btn_base.setOnClickListener(
                new View.OnClickListener() {
            public void onClick(View view) {
                getData().putPreference(Config.NAME, et_name.getText().toString());
                getData().putPreference(Config.EMAIL, et_email.getTag().toString());
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

        photoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String[] choseWay = {
                    "重新拍照", "從相簿挑選", "取消"
                };
                pickObject(new CommandPickModel("更換大頭照", choseWay) {
                    @Override
                    public void onCommand(int i) {
                        switch (i) {
                            case 0:
                                takePhoto();
                                break;
                            case 1:
                                pickPhoto();
                                break;
                            case 2:
                                break;
                        }
                    }
                });

            }
        });
    }

    protected void takePhoto() {
        new PhotoAction(this) {
            @Override
            protected void execute(Context cntxt, PhotoAgent pa) {
                pa.fixSize(660, 660).save(pa.sdcard().dir(Config.DB_NAME).file("simpleSocial.png"), Bitmap.CompressFormat.PNG);

                String storedPath = pa.getStoredFile().getAbsolutePath();
                if (storedPath != null) {
                    getData().putPreference(Config.PHOTO_FILE, storedPath);
                }
                photoView.setImageBitmap(pa.getBitmap());
            }
        }.execute();
    }

    protected void pickPhoto() {
        new PickPhotoAction(this) {
            @Override
            public void execute(Context cntxt, PhotoAgent pa) {
                pa.fixSize(660, 660).save(pa.sdcard().dir(Config.DB_NAME).file("simpleSocial.png"), Bitmap.CompressFormat.PNG);

                String storedPath = pa.getStoredFile().getAbsolutePath();
                if (storedPath != null) {
                    getData().putPreference(Config.PHOTO_FILE, storedPath);
                }
                photoView.setImageBitmap(pa.getBitmap());
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        photoView.setImageBitmap(null);
        photoView = null;
        super.onDestroy();
    }

    @Override
    public boolean hasMenu() {
        return true;
    }
}
