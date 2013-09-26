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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import grandroid.action.GoAction;
import grandroid.cache.ImageCacher;
import grandroid.cache.lazyloader.ImageLoader;
import grandroid.database.FaceData;
import grandroid.dialog.CommandPickModel;
import grandroid.image.ImageUtil;
import grandroid.image.PhotoAgent;
import grandroid.image.PhotoHandler;
import grandroid.phone.DisplayAgent;
import grandroid.slidemenu.SlidingMenu;
import grandroid.view.Face;
import grandroid.view.LayoutMaker;
import grandroid.view.StyledText;

/**
 *
 * @author Peng
 */
public abstract class FaceSocial extends Face {

    protected ViewGroup layout_backgroundDrawable;
    protected LayoutMaker maker;
    protected DisplayAgent da;
    protected SlidingMenu smenu;
    protected int state; // 0： 主頁　１：ｓｉｄｅＭｅｎｕ      2: config
    LayoutMaker lm_side;
    ImageLoader loader;
    ImageCacher cacher;
    FaceData fd;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        maker = new LayoutMaker(this);
        maker.setDrawableDesignWidth(this, Config.DRAWABLE_DESIGN_WIDTH);
        maker.setAutoScaleResource(true);
        maker.getLastLayout().setBackgroundColor(Color.WHITE);
        da = maker.getDisplayAgent();
        cacher = new ImageCacher(this, Config.DB_NAME_IMAGE);
        loader = new ImageLoader(this, cacher, 0);
        fd = new FaceData(FaceSocial.this, Config.DB_NAME);
        if (hasMenu()) {
            smenu = new SlidingMenu(this);
            lm_side = smenu.createLeftMenuMaker();
            createSideMenuLayout();
            smenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW, false);
        }
    }

    public abstract boolean hasMenu();
    LinearLayout layout_list, layout_search, layout_add;

    protected void createSideMenuLayout() {
        TextView tv_name, tv_edit;
        Button btn_list, btn_search, btn_add;
        ImageView iv_photo;
        EditText et_search, et_add;
        Button btn_add_ok;

        TextView tv_temp;

        loader = new ImageLoader(this, cacher, 330, 330, Color.GRAY);

//        LayoutMaker lm_side = new LayoutMaker(this);
//        lm_side.setDrawableDesignWidth(this, 952);
//        lm_side.setAutoScaleResource(true);
        lm_side.getLastLayout().setBackgroundColor(Color.WHITE);

        lm_side.addColLayout(false, lm_side.layFF());
        lm_side.setScalablePadding(lm_side.getLastLayout(), 40, 40, 40, 40);
        {
            lm_side.addColLayout(false, lm_side.layFW(0));
//            lm_side.getLastLayout().setBackgroundColor(Color.GRAY);
            lm_side.setScalablePadding(lm_side.getLastLayout(), 100, 20, 100, 40);
            {
                tv_name = lm_side.add(createStyliseTextView(getData().getPreference(Config.NAME), 2, Color.BLACK), lm_side.layFW());
                iv_photo = lm_side.addImage(0, lm_side.layAbsolute(0, 0, 330, 330));
                tv_edit = lm_side.add(createStyliseTextView("修改資料", 0, Color.BLACK, Gravity.RIGHT), lm_side.layFW());
                lm_side.escape();
            }

            lm_side.addColLayout(false, lm_side.layAbsolute(0, 0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
            lm_side.getLastLayout().setBackgroundColor(Color.GRAY);
            lm_side.escape();

            lm_side.addColLayout(false, lm_side.layFW(1)).setGravity(Gravity.CENTER | Gravity.TOP);
            lm_side.setScalablePadding(lm_side.getLastLayout(), 40, 40, 40, 40);
            {
                btn_list = lm_side.add(lm_side.createButton("朋友列表"), lm_side.layFW());
                layout_list = lm_side.addColLayout();
                layout_list.setGravity(Gravity.CENTER_HORIZONTAL);
                {
                    // 朋友列表
                    tv_temp = lm_side.add(createStyliseTextView("好友1號", 1, Color.BLACK, Gravity.CENTER), lm_side.layFW());
                    ;

                    lm_side.escape();
                }
                btn_search = lm_side.add(lm_side.createButton("搜尋朋友"), lm_side.layFW());
                layout_search = lm_side.addColLayout();
//                layout_search.setGravity(Gravity.CENTER_VERTICAL);
                {
                    et_search = lm_side.add(createStyliseEditView("", 1, Color.BLACK), lm_side.layFW());
                    lm_side.add(createStyliseTextView("請輸入好友的姓名或ID來搜尋", 0, Color.GRAY), lm_side.layFW());
                    // 搜尋結果顯示
                    ;

                    lm_side.escape();
                }
                btn_add = lm_side.add(lm_side.createButton("新增好友"), lm_side.layFW());
                layout_add = lm_side.addColLayout();
//                layout_add.setGravity(Gravity.CENTER_VERTICAL);
                {
                    et_add = lm_side.add(createStyliseEditView("", 1, Color.BLACK), lm_side.layFW());
                    lm_side.add(createStyliseTextView("請輸入好友ID來新增好友", 0, Color.GRAY), lm_side.layFW());
                    btn_add_ok = lm_side.add(lm_side.createButton("新增"), lm_side.layWW(0));
                    lm_side.escape();
                }
                lm_side.escape();
            }
            lm_side.escape();
        }
        iv_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (getData().getPreference(Config.PHOTO_FILE).subSequence(0, 4).equals("http")) {
            Log.d(Config.TAG, "load photo from net");
            loader.displayImage(getData().getPreference(Config.PHOTO_FILE), iv_photo, new PhotoHandler() {
                public void execute(PhotoAgent pa) {
                    pa.square(1f);
                    pa.fixSize(330, 330);
                }
            });
        } else {
            Log.d(Config.TAG, "load photo from sdcard");
            try {
                iv_photo.setImageBitmap(ImageUtil.loadBitmap(getData().getPreference(Config.PHOTO_FILE)));
            } catch (Exception ex) {
                Log.e(Config.TAG, null, ex);
            }
        }

        iv_photo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GoAction(FaceSocial.this, FrameEdit.class).addBundleObject("focus", "photo").execute();
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GoAction(FaceSocial.this, FrameEdit.class).addBundleObject("focus", "name").execute();
            }
        });
        tv_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GoAction(FaceSocial.this, FrameEdit.class).addBundleObject("focus", "none").execute();
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layout_list.setVisibility(View.VISIBLE);
                layout_search.setVisibility(View.GONE);
                layout_add.setVisibility(View.GONE);
            }
        });
        tv_temp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new GoAction(FaceSocial.this, FrameContentInfo.class).execute();
            }
        });
        layout_list.setVisibility(View.GONE);
        btn_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layout_list.setVisibility(View.GONE);
                layout_search.setVisibility(View.VISIBLE);
                layout_add.setVisibility(View.GONE);
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
//                Log.d(Config.TAG, "after text change");
//                search(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d(Config.TAG, "before text change");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(Config.TAG, "on text change");
            }
        });
        layout_search.setVisibility(View.GONE);

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                layout_list.setVisibility(View.GONE);
                layout_search.setVisibility(View.GONE);
                layout_add.setVisibility(View.VISIBLE);
            }
        });
        btn_add_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // 新增好友
                ;
            }
        });
        layout_add.setVisibility(View.GONE);

    }

    protected LinearLayout addLine(int x, int y, int w, int h, int color) {
        LinearLayout brock = maker.addColLayout(false, maker.layAbsolute(x, y, w, h));
        brock.setBackgroundColor(color);
        maker.escape();
        return brock;
    }

    protected LinearLayout addBrock(int x, int y, int w, int h, int color) {
        LinearLayout brockOut = maker.addColLayout(false, maker.layAbsolute(x, y, w, h));
        brockOut.setBackgroundColor(Color.WHITE);
        brockOut.setGravity(Gravity.CENTER);
        {
            LinearLayout brock = maker.addColLayout(false, maker.layAbsolute(2, 2, w - 4, h - 4));
            brock.setPadding(1, 1, 1, 1);
            brock.setBackgroundColor(color);
            maker.escape();
        }
        maker.escape();
        return brockOut;
    }

    /**
     *
     * @param text
     * @param textSize 0 = small, 1 = normal, 2 = large
     * @return
     */
    protected TextView createStyliseTextView(String text, int textSize) {
        return this.createStyliseTextView(text, textSize, Color.WHITE);
    }

    protected TextView createStyliseTextView(String text, int textSize, int color) {
        return this.createStyliseTextView(text, textSize, color, Gravity.LEFT);
    }

    protected TextView createStyliseTextView(String text, int textSize, int color, int gravity) {
        return maker.createStyledText(text).size(StyledText.Unit.Auto, getTextSize(textSize)).color(color).gravity(gravity).get();
    }

    protected EditText createStyliseEditView(String text, int textSize) {
        return this.createStyliseEditView(text, textSize, Color.WHITE);
    }

    protected EditText createStyliseEditView(String text, int textSize, int color) {
        return this.createStyliseEditView(text, textSize, color, Gravity.LEFT | Gravity.CENTER);
    }

    protected EditText createStyliseEditView(String text, int textSize, int color, int gravity) {
        EditText editText = maker.createEditText("");
        editText.setGravity(gravity);
        editText.setHint(text);
        editText.setTextColor(color);
//        int size = Math.round(getTextSize(textSize) * xratio);
//        editText.setTextSize(StyledText.Unit.Px.ordinal(), size);
        editText.setTextSize(StyledText.Unit.Px.ordinal(), (int) maker.getMatrix().mapRadius(getTextSize(textSize)));
        editText.setSingleLine();
//        editText.setBackgroundResource(0);
        return editText;
    }

    protected int getTextSize(int textSize) {
        int size;
        switch (textSize) {
            case 0:
                size = 30;
                break;
            case 1:
                size = 42;
                break;
            case 2:
                size = 56;
                break;
            case 3:
                size = 72;
                break;
            default:
                size = textSize;
        }
        return size;
    }

    /**
     *
     * @param title
     * @param hasMenu 左側選單按鈕
     * @return
     */
    public void addTopBanner() {
        this.addTopBanner(Config.TITLE, true);
    }

    public void addTopBanner(boolean hasMenu) {
        this.addTopBanner(Config.TITLE, hasMenu);
    }

    public void addTopBanner(String title) {
        this.addTopBanner(title, true);
    }

    public void addTopBanner(String title, boolean hasMenu) {
        LinearLayout ll_menu = null;

        maker.addFrame(maker.layAbsolute(0, 0, Config.DRAWABLE_DESIGN_WIDTH, Config.BANNER_HEIGHT));
        maker.getLastLayout().setBackgroundColor(Color.GRAY);
        {
            if (hasMenu) {
                ll_menu = maker.addRowLayout(false, maker.layFrameWF(Gravity.LEFT));
                {
                    maker.add(createStyliseTextView(" MENU ", 0, Color.WHITE, Gravity.CENTER), maker.layWF(0));
                    maker.escape();
                }
            }
            maker.add(createStyliseTextView(title, 3, Color.WHITE, Gravity.CENTER), maker.layFrameFF());
            maker.escape();
        }

        if (ll_menu != null) {
            ll_menu.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (smenu.isMenuShowing()) {
                        smenu.showContent();
                        state = 0;
                    } else {
                        smenu.showMenu();
                        state = 1;
                    }
                }
            });
        }

    }

    public AlertDialog.Builder alertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FaceSocial.this);
        builder.setMessage(message);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder;
    }
    Button btn_fl, btn_tolk, btn_config;
    CommandPickModel cpm;

    public void addButtomBanner(int frame) {
        maker.addRowLayout(false, maker.layFW());
        maker.getLastLayout().setBackgroundColor(Color.GRAY);
        {
            btn_fl = maker.add(maker.createButton("好友"), maker.layWF(1));
            btn_fl.setBackgroundColor(Color.GRAY);
            btn_tolk = maker.add(maker.createButton("近聊"), maker.layWF(1));
            btn_tolk.setBackgroundColor(Color.GRAY);
            btn_config = maker.add(maker.createButton("設定"), maker.layWF(1));
            btn_config.setBackgroundColor(Color.GRAY);
            maker.escape();
        }
        if (frame == 1) {
            btn_fl.setBackgroundColor(Color.WHITE);
            btn_fl.setClickable(false);
        } else if (frame == 2) {
            btn_tolk.setBackgroundColor(Color.WHITE);
            btn_tolk.setClickable(false);
        }

        btn_fl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new GoAction(FaceSocial.this, FrameContent.class).setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).execute();
            }
        });

        btn_tolk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new GoAction(FaceSocial.this, FrameContentInfo.class).setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).addBundleObject("target", getData().getPreference(Config.LAST_TARGET)).execute();
            }
        });

        btn_config.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                state = 2;
//                btn_fl.setBackgroundColor(Color.GRAY);
//                btn_fl.setClickable(true);
//                btn_tolk.setBackgroundColor(Color.GRAY);
//                btn_tolk.setClickable(true);
//                btn_config.setBackgroundColor(Color.WHITE);
                String[] choseWay = {
                    "登出", "結束連線", "取消"
                };
                cpm = (new CommandPickModel("", choseWay) {
                    @Override
                    public void onCommand(int i) {
                        switch (i) {
                            case 0:
                                logout();
                                break;
                            case 1:
                                // 結束連線
                                ;
                                FaceSocial.this.finishActivity(0);
                                break;
                            case 2:
                                state = 0;
//                                btn_config.setBackgroundColor(Color.GRAY);
                                break;
                        }
                    }
                });
                pickObject(cpm);
            }
        });
    }

    private void logout() {
        // 清空所有資料
        getData().putPreference(Config.ACCOUNT, "");
        getData().putPreference(Config.PASSWORD, "");
        getData().putPreference(Config.EMAIL, "");
        getData().putPreference(Config.PHOTO_FILE, "");
        getData().putPreference(Config.NAME, "");
        getData().putPreference(Config.PHOTO_URL, "");
        getData().putPreference(Config.LAST_TARGET, "");

        new GoAction(FaceSocial.this, FrameMain.class).setFlag(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK).execute();
    }

    /*
     * (non-Javadoc) @see android.app.Activity#onKeyDown(int,
     * android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            Log.d(Config.TAG, "MENU pressed");
            if (hasMenu()) {
                if (state == 0) {
                    smenu.showMenu();
                    state = 1;
                } else if (state == 1) {
                    smenu.showContent();
                    state = 0;
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (state == 2) {
            cpm.onCommand(2);
//            state = 0;
//            btn_config.setBackgroundColor(Color.GRAY);
        }
        if (state == 1) {
            state = 0;
            smenu.showContent();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
