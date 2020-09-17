package kr.hs.emirim.s2019w28.dongji;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class MyFontRecipeKorea extends androidx.appcompat.widget.AppCompatTextView {


    public MyFontRecipeKorea(Context context) {
        super(context);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/RecipekoreaFONT.ttf");
        setTypeface(typeface);
    }

    public MyFontRecipeKorea(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/RecipekoreaFONT.ttf");
        setTypeface(typeface);
    }
}