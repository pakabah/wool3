package com.overcoretech.troski.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by akabah on 10/25/15.
 */
public class CustomAutoView extends AutoCompleteTextView {

    public CustomAutoView(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }

    /*
     * after a selection we have to capture the new value and append to the existing text
     */
    @Override
    protected void replaceText(final CharSequence text) {
        super.replaceText(text);
    }


    public CustomAutoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomAutoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        //do stuff that was in your original constructor...
    }
}
