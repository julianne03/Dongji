package kr.hs.emirim.s2019w28.dongji;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class BmhannaPro extends androidx.appcompat.widget.AppCompatTextView {


    public BmhannaPro(Context context) {
        super(context);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/BMHANNAPro.ttf");
        setTypeface(typeface);
    }

    public BmhannaPro(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/BMHANNAPro.ttf");
        setTypeface(typeface);
    }
}