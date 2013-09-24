/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import grandroid.action.GoAction;
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
    protected int state; // 0： 主頁　１：ｓｉｄｅＭｅｎｕ
    LayoutMaker lm_side;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        maker = new LayoutMaker(this);
        maker.setDrawableDesignWidth(this, Config.DRAWABLE_DESIGN_WIDTH);
        maker.setAutoScaleResource(true);
        maker.getLastLayout().setBackgroundColor(Color.WHITE);
        da = maker.getDisplayAgent();

        if (hasMenu()) {
            smenu = new SlidingMenu(this);
            lm_side = smenu.createLeftMenuMaker();
            createSideMenuLayout();
            smenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW, false);
        }
    }

    public abstract boolean hasMenu();

    protected void createSideMenuLayout() {
        TextView tv_name, tv_edit;
        Button btn_list, btn_search, btn_add;
//        LayoutMaker lm_side = new LayoutMaker(this);
//        lm_side.setDrawableDesignWidth(this, 952);
//        lm_side.setAutoScaleResource(true);
        lm_side.getLastLayout().setBackgroundColor(Color.WHITE);

        lm_side.addColLayout(false, lm_side.layFF());
        lm_side.setScalablePadding(lm_side.getLastLayout(), 40, 40, 40, 40);
        {
            lm_side.addColLayout(false, lm_side.layFW(1));
            lm_side.getLastLayout().setBackgroundColor(Color.GRAY);
            {
                tv_name = lm_side.add(createStyliseTextView(getData().getPreference(Config.NAME, "彭小柔"), 2, Color.BLACK), lm_side.layFW());
//                addBrock(0, 0, 440, 440, Color.RED);
                tv_edit = lm_side.add(createStyliseTextView("修改資料", 0, Color.BLACK, Gravity.RIGHT), lm_side.layFW());
                lm_side.escape();
            }
            lm_side.addColLayout(false, lm_side.layFW(3)).setGravity(Gravity.CENTER | Gravity.TOP);
            lm_side.setScalablePadding(lm_side.getLastLayout(), 40, 40, 40, 40);
            {
                btn_list = lm_side.addButton("朋友列表");
                btn_search = lm_side.addButton("搜尋朋友");
                btn_add = lm_side.addButton("新增好友");
                lm_side.escape();
            }
            lm_side.escape();
        }

        tv_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GoAction(FaceSocial.this, FrameEdit.class).execute();
            }
        });
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

    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            Log.d(Config.TAG, "MENU pressed");
            if (state == 0) {
                smenu.showMenu();
                state = 1;
            } else if (state == 1) {
                smenu.showContent();
                state = 0;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
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
