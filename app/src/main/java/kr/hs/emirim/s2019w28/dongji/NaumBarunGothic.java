package kr.hs.emirim.s2019w28.dongji;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class NaumBarunGothic extends androidx.appcompat.widget.AppCompatTextView {


    public NaumBarunGothic(Context context) {
        super(context);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothic.ttf");
        setTypeface(typeface);
    }

    public NaumBarunGothic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothic.ttf");
        setTypeface(typeface);
    }
}