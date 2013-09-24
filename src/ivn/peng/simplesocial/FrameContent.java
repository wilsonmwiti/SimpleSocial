/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import grandroid.action.GoAction;

/**
 * 內頁首頁 顯是所有朋友的大頭像
 *
 * @author Peng
 */
public class FrameContent extends FaceSocial {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll_content;

        addTopBanner();

        maker.addColLayout(true, maker.layFF());
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("常用聯絡人", 1, Color.BLACK));
                maker.addRowLayout(false, maker.layFW());
                {
                    ll_content = addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    maker.escape();
                }
                maker.escape();
            }

            addLine(0, 0, 880, 2, Color.GRAY);

            maker.addColLayout(false, maker.layFW());
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.add(createStyliseTextView("朋友列表", 1, Color.BLACK));
                maker.addRowLayout(false, maker.layFW());
                {
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    maker.escape();
                }
                maker.addRowLayout(false, maker.layFW());
                {
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    maker.escape();
                }
                maker.escape();
            }
            maker.escape();
        }

        ll_content.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new GoAction(FrameContent.this, FrameContentInfo.class).execute();
            }
        });
    }

    @Override
    public boolean hasMenu() {
        return true;
    }
}
