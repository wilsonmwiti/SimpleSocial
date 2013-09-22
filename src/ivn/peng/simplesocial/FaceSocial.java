/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import grandroid.phone.DisplayAgent;
import grandroid.view.LayoutMaker;
import grandroid.view.StyledText;

/**
 *
 * @author Peng
 */
public class FaceSocial extends Activity {

    protected ViewGroup layout_backgroundDrawable;
    protected LayoutMaker maker;
    protected DisplayAgent da;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        maker = new LayoutMaker(this);
        maker.setDrawableDesignWidth(this, Config.DRAWABLE_DESIGN_WIDTH);
        maker.setAutoScaleResource(true);
        maker.getLastLayout().setBackgroundColor(Color.WHITE);
        da = maker.getDisplayAgent();
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
                    maker.add(createStyliseTextView(" menu ", 0, Color.WHITE, Gravity.CENTER));
                    maker.escape();
                }
            }
            maker.add(createStyliseTextView(title, 3, Color.WHITE, Gravity.CENTER), maker.layFrameFF());
            maker.escape();
        }

        if (ll_menu != null) {
//            ll_menu;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
