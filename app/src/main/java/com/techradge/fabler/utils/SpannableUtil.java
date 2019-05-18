package com.techradge.fabler.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

public class SpannableUtil {
    public static SpannableStringBuilder makeBold(String text,
                                                  SpannableStringBuilder spannableStringBuilder,
                                                  int startIndex, int endIndex) {

        int startPos = 0, endPos = 0;
        // If there is no text selection
        if (startIndex == endIndex) {
            // Make entire paragraph bold
            endPos = text.indexOf('\n', startIndex);
            if (endPos < 0)
                endPos = text.length() - 1;

            startPos = text.lastIndexOf('\n', startIndex);
            if (startPos < 0)
                startPos = 0;
        }


        final StyleSpan boldStyleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        spannableStringBuilder.setSpan(
                boldStyleSpan,
                startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    public SpannableStringBuilder makeItalic(String text, int startIndex, int endIndex) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        final StyleSpan italicStyleSpan = new StyleSpan(Typeface.ITALIC);
        spannableStringBuilder.setSpan(
                italicStyleSpan,
                startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }
}
