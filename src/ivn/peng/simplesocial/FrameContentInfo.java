/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * 
 *
 * @author Peng
 */
public class FrameContentInfo extends FaceSocial {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addTopBanner();

        maker.addColLayout(false, maker.layFF());
        maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
        {
            maker.addRowLayout(false, maker.layFW());
            {
                maker.addRowLayout(false, maker.layAbsolute(0, 0, 660, LinearLayout.LayoutParams.WRAP_CONTENT));
                {
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    addBrock(0, 0, 220, 220, Color.GRAY);
                    maker.escape();
                }
                maker.addRowLayout(false, maker.layAbsolute(100, 0, 220, 220));
                {
                    addBrock(0, 0, 220, 220, Color.RED);
                    maker.escape();
                }
                maker.escape();
            }

            addLine(0, 0, LinearLayout.LayoutParams.MATCH_PARENT, 2, Color.GRAY);

            maker.addColLayout(true, maker.layFW(1));
            maker.setScalablePadding(maker.getLastLayout(), 50, 50, 50, 50);
            {
                maker.addColLayout(false, maker.layFW());
                {
                    addBrock(0, 0, 110, 110, Color.GRAY);
                    maker.add(createStyliseTextView("Hello~", 1, Color.BLACK, Gravity.LEFT), maker.layFW());
                    maker.escape();
                }
                maker.addColLayout(false, maker.layFW()).setGravity(Gravity.RIGHT);
                {
                    addBrock(0, 0, 110, 110, Color.RED);
                    maker.add(createStyliseTextView("Hi!!", 1, Color.BLACK, Gravity.RIGHT), maker.layFW());
                    maker.escape();
                }
                maker.escape();
            }
            maker.escape();
        }

    }
      @Override
    public boolean hasMenu() {
        return true;
    }
}
