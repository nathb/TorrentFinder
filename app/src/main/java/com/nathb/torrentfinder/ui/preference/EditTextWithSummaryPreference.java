package com.nathb.torrentfinder.ui.preference;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class EditTextWithSummaryPreference extends EditTextPreference {

    public EditTextWithSummaryPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        setSummary(text);
    }
}
