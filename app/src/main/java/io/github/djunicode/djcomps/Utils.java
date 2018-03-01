package io.github.djunicode.djcomps;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    public static int dpToPx(int dp) {
        Resources r = Resources.getSystem();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
