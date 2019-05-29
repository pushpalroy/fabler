package fabler.fablededitor.components.text;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import fabler.fablededitor.R;
import fabler.fablededitor.models.ComponentTag;
import fabler.fablededitor.models.TextComponentModel;
import fabler.fablededitor.utilities.FontSize;

import static fabler.fablededitor.components.text.TextComponentItem.MODE_PLAIN;
import static fabler.fablededitor.styles.TextComponentStyle.BLOCK_QUOTE;
import static fabler.fablededitor.styles.TextComponentStyle.BOLD;
import static fabler.fablededitor.styles.TextComponentStyle.H1;
import static fabler.fablededitor.styles.TextComponentStyle.H4;
import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;

public class TextComponent {
    private final Context mContext;
    private TextComponentCallback _textComponentCallback;
    private Resources r;
    private boolean spaceExist;

    public TextComponent(Context context, TextComponentCallback textComponentCallback) {
        this.mContext = context;
        r = mContext.getResources();
        _textComponentCallback = textComponentCallback;
    }

    /**
     * Method to create new instance according to mode provided.
     * Mode can be [PLAIN, UL, OL]
     *
     * @param mode mode of new TextComponent.
     * @return new instance of TextComponent.
     */
    public TextComponentItem newTextComponent(final int mode) {
        final TextComponentItem customInput = new TextComponentItem(mContext, mode);
        final EditText et = customInput.getInputBox();
        et.setImeActionLabel("Enter", KeyEvent.KEYCODE_ENTER);
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    _textComponentCallback.onBackPressed();
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (_textComponentCallback != null) {
                        _textComponentCallback.onRemoveTextComponent(((ComponentTag) customInput.getTag()).getComponentIndex());
                    }
                }
                return false;
            }
        });

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean inFocus) {
                if (inFocus) {
                    if (_textComponentCallback != null) {
                        _textComponentCallback.onFocusGained(customInput);
                    }
                }
            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                int length = charSequence.length();
                if (length > 0) {
                    char ch = charSequence.charAt(charSequence.length() - 1);
                    if (isSpaceCharacter(ch) && (before < count)) {
                        if (spaceExist) {
                            String newString = charSequence.toString().trim();
                            newString = String.format("%s ", newString);
                            et.setText(newString);
                            et.setSelection(newString.length());
                        }
                        spaceExist = true;
                    } else {
                        spaceExist = false;
                    }

                    String sequenceToCheckNewLineCharacter = (length > 1) ? charSequence.subSequence(length - 2, length).toString()
                            :
                            charSequence.subSequence(length - 1, length).toString();
                    boolean noReadableCharactersAfterCursor = sequenceToCheckNewLineCharacter.trim().length() == 0;
                    //if last characters are [AB\n<space>] or [AB\n] then we insert new TextComponent
                    //else if last characters are [AB\nC] ignore the insert.
                    if (sequenceToCheckNewLineCharacter.contains("\n") && noReadableCharactersAfterCursor) {
                        //If last characters are like [AB\n ] then new sequence will be [AB]
                        // i.e leave 2 characters from end.
                        //else if last characters are like [AB\n] then also new sequence will be [AB]
                        //but we need to leave 1 character from end.
                        CharSequence newSequence = sequenceToCheckNewLineCharacter.length() > 1 ?
                                charSequence.subSequence(0, length - 2)
                                :
                                charSequence.subSequence(0, length - 1);
                        et.setText(newSequence);
                        if (_textComponentCallback != null) {
                            _textComponentCallback.onInsertTextComponent(((ComponentTag) customInput.getTag()).getComponentIndex());
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return customInput;
    }

    private boolean isSpaceCharacter(char ch) {
        return ch == ' ';
    }

    /**
     * updates view with latest style info.
     *
     * @param view to be updated.
     */
    public void updateComponent(View view, int startSelection, int endSelection) {
        ComponentTag componentTag = (ComponentTag) view.getTag();
        //get heading
        int style = ((TextComponentModel) componentTag.getComponent()).getHeadingStyle();
        TextComponentItem textComponentItem = (TextComponentItem) view;
        textComponentItem.getInputBox().setTextSize(FontSize.getFontSize(style));
        //get mode
        int mode = textComponentItem.getMode();

//        if (style == BOLD) {
//            CharSequence text = (((TextComponentItem) view).getInputBox()).getText().toString()
//                    .substring(startSelection, endSelection);
//            SpannableString span = new SpannableString(text);
//            span.setSpan(new StyleSpan(Typeface.BOLD), startSelection, endSelection, 0);
//            (((TextComponentItem) view).getInputBox()).setText(span, TextView.BufferType.SPANNABLE);
//
//            (((TextComponentItem) view).getInputBox()).setBackgroundResource(R.drawable.text_input_bg);
//            ((TextComponentItem) view).getInputBox().setPadding(
//                    dpToPx(16),//left
//                    dpToPx(8),//top
//                    dpToPx(16),//right
//                    dpToPx(8)//bottom
//            );
//            ((TextComponentItem) view).getInputBox().setLineSpacing(2f, 1.1f);
//        }

        if (style >= H1 && style <= BOLD) {
            ((TextComponentItem) view).getInputBox().setTypeface(ResourcesCompat.getFont(mContext, R.font.muli_bold));
            (((TextComponentItem) view).getInputBox()).setBackgroundResource(R.drawable.text_input_bg);
            ((TextComponentItem) view).getInputBox().setPadding(
                    dpToPx(16),//left
                    dpToPx(8),//top
                    dpToPx(16),//right
                    dpToPx(8)//bottom
            );
            ((TextComponentItem) view).getInputBox().setLineSpacing(2f, 1.1f);
        }

        if (style == NORMAL) {
            ((TextComponentItem) view).getInputBox().setTypeface(ResourcesCompat.getFont(mContext, R.font.muli_regular));
            (((TextComponentItem) view).getInputBox()).setBackgroundResource(R.drawable.text_input_bg);
            if (mode == MODE_PLAIN) {
                ((TextComponentItem) view).getInputBox().setPadding(
                        dpToPx(16),//left
                        dpToPx(4),//top
                        dpToPx(16),//right
                        dpToPx(4)//bottom
                );
            } else {
                ((TextComponentItem) view).getInputBox().setPadding(
                        dpToPx(4),//left
                        dpToPx(4),//top
                        dpToPx(16),//right
                        dpToPx(4)//bottom
                );
            }
            ((TextComponentItem) view).getInputBox().setLineSpacing(2f, 1.1f);
        }

        if (style == BLOCK_QUOTE) {
            ((TextComponentItem) view).getInputBox().setTypeface(ResourcesCompat.getFont(mContext, R.font.muli_italic));
            (((TextComponentItem) view).getInputBox()).setBackgroundResource(R.drawable.block_quote_bg);
            ((TextComponentItem) view).getInputBox().setPadding(
                    dpToPx(16),//left
                    dpToPx(2),//top
                    dpToPx(16),//right
                    dpToPx(2)//bottom
            );
            ((TextComponentItem) view).getInputBox().setLineSpacing(2f, 1.2f);
        }
    }

    /**
     * Convert dp to px value.
     *
     * @param dp value
     * @return pixel value of given dp.
     */
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, r.getDisplayMetrics());
    }

    public interface TextComponentCallback {
        void onInsertTextComponent(int selfIndex);

        void onFocusGained(View view);

        void onRemoveTextComponent(int selfIndex);

        void onBackPressed();
    }
}
