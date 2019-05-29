package fabler.fablededitor;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static fabler.fablededitor.components.text.TextComponentItem.MODE_BULLET;
import static fabler.fablededitor.components.text.TextComponentItem.MODE_NUMBERING;
import static fabler.fablededitor.components.text.TextComponentItem.MODE_PLAIN;
import static fabler.fablededitor.styles.TextComponentStyle.BLOCK_QUOTE;
import static fabler.fablededitor.styles.TextComponentStyle.BOLD;
import static fabler.fablededitor.styles.TextComponentStyle.H1;
import static fabler.fablededitor.styles.TextComponentStyle.H2;
import static fabler.fablededitor.styles.TextComponentStyle.H3;
import static fabler.fablededitor.styles.TextComponentStyle.H4;
import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;

public class FormattingBar extends FrameLayout implements FabledEditor.EditorFocusReporter {
    private Context mContext;
    private FabledEditor mEditor;
    private ImageView normalTextBtn;
    private TextView addHeadingBtn, headingNumberBtn;
    private ImageView addBulletBtn;
    private ImageView addBlockQuoteBtn;
    private ImageView addLinkBtn;
    private ImageView addHorizontalLineBtn;
    private ImageView addImageBtn;
    private ImageView nextLineBtn;
    private int enabledColor;
    private int disabledColor;
    private boolean numberingEnabled;
    private boolean bulletsEnabled;
    private boolean blockQuoteEnabled;

    //    6 - BLOCK_QUOTE
    //    5 - BOLD
    //    4 - H4
    //    3 - H3
    //    2 - H2
    //    1 - H1
    //    0 - NORMAL

    public static final int MIN_HEADING = 1;
    public static final int MAX_HEADING = 5;

    // currentHeading  = 6

    private int currentHeading;

    private EditorControlListener editorControlListener;

    public FormattingBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.formatting_bar, this);
        normalTextBtn = view.findViewById(R.id.normalTextBtn);
        addHeadingBtn = view.findViewById(R.id.headingBtn);
        headingNumberBtn = view.findViewById(R.id.headingNumberBtn);
        addBulletBtn = view.findViewById(R.id.bulletBtn);
        addBlockQuoteBtn = view.findViewById(R.id.blockQuoteBtn);
        addLinkBtn = view.findViewById(R.id.insertLinkBtn);
        addHorizontalLineBtn = view.findViewById(R.id.insertHrBtn);
        addImageBtn = view.findViewById(R.id.insertImageBtn);
        nextLineBtn = view.findViewById(R.id.nextLineBtn);
        enabledColor = Color.parseColor("#0994cf");
        disabledColor = Color.parseColor("#3e3e3e");

        normalTextBtn.setColorFilter(enabledColor);
        addHeadingBtn.setTextColor(disabledColor);
        headingNumberBtn.setTextColor(disabledColor);
        addBulletBtn.setColorFilter(disabledColor);
        addBlockQuoteBtn.setColorFilter(disabledColor);
        addLinkBtn.setColorFilter(disabledColor);
        addHorizontalLineBtn.setColorFilter(disabledColor);
        addImageBtn.setColorFilter(disabledColor);

        currentHeading = MAX_HEADING + 1;
        attachListeners();
    }

    public FormattingBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setEditor(FabledEditor editor) {
        this.mEditor = editor;
        subscribeForStyles();
    }

    private void subscribeForStyles() {
        if (mEditor != null) {
            mEditor.setEditorFocusReporter(this);
        }
    }

    private void attachListeners() {
        normalTextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor.setHeading(NORMAL);
                invalidateStates(MODE_PLAIN, NORMAL);
            }
        });

        addHeadingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentHeading == MIN_HEADING) {
                    currentHeading = MAX_HEADING;
                    mEditor.setHeading(currentHeading);
                } else {
                    mEditor.setHeading(--currentHeading);
                }
                invalidateStates(MODE_PLAIN, currentHeading);
            }
        });

        addBulletBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberingEnabled) {
                    //switch to normal
                    mEditor.setHeading(NORMAL);
                    invalidateStates(MODE_PLAIN, NORMAL);
                    numberingEnabled = false;
                    bulletsEnabled = false;
                } else if (bulletsEnabled) {
                    // switch to ol mode
                    mEditor.changeToNumMode();
                    invalidateStates(MODE_NUMBERING, NORMAL);
                    numberingEnabled = true;
                    bulletsEnabled = false;
                } else {
                    // switch to ul mode
                    mEditor.changeToBulletMode();
                    invalidateStates(MODE_BULLET, NORMAL);
                    bulletsEnabled = true;
                    numberingEnabled = false;
                }
            }
        });

        addBlockQuoteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blockQuoteEnabled) {
                    //switch to normal
                    mEditor.setHeading(NORMAL);
                    invalidateStates(MODE_PLAIN, NORMAL);
                } else {
                    //blockQuote
                    mEditor.changeToBlockQuote();
                    invalidateStates(MODE_PLAIN, BLOCK_QUOTE);
                }
            }
        });

        addHorizontalLineBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditor.insertHorizontalDivider();
            }
        });

        addLinkBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editorControlListener != null) {
                    editorControlListener.onInsertLinkClicked();
                }
            }
        });

        addImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editorControlListener != null) {
                    editorControlListener.onInsertImageClicked();
                }
            }
        });

        nextLineBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.addTextComponent(mEditor.getNextIndex(), "");
            }
        });
    }

    private void enableNormalText(boolean enabled) {
        if (enabled) {
            normalTextBtn.setColorFilter(enabledColor);
        } else {
            normalTextBtn.setColorFilter(disabledColor);
        }
    }

    private void enableHeading(boolean enabled, int headingNumber) {
        if (enabled) {
            currentHeading = headingNumber;
            if (currentHeading == 5) {
                addHeadingBtn.setText("B");
                headingNumberBtn.setText("");
            } else {
                addHeadingBtn.setText("H");
                headingNumberBtn.setText(String.valueOf(currentHeading));
            }

            addHeadingBtn.setTextColor(enabledColor);
            headingNumberBtn.setTextColor(enabledColor);
        } else {
            currentHeading = 6;
            addHeadingBtn.setText("B");
            headingNumberBtn.setText("");

            addHeadingBtn.setTextColor(disabledColor);
            headingNumberBtn.setTextColor(disabledColor);
        }
    }

    private void enableBullet(boolean enable, boolean isOrdered) {
        if (enable) {
            if (isOrdered) {
                numberingEnabled = true;
                bulletsEnabled = false;
                addBulletBtn.setImageResource(R.drawable.ic_bullets);
            } else {
                bulletsEnabled = true;
                numberingEnabled = false;
                addBulletBtn.setImageResource(R.drawable.ic_numbering);
            }
            addBulletBtn.setColorFilter(enabledColor);
        } else {
            bulletsEnabled = false;
            numberingEnabled = false;
            addBulletBtn.setImageResource(R.drawable.ic_numbering);
            addBulletBtn.setColorFilter(disabledColor);
        }
    }

    private void enableBlockQuote(boolean enable) {
        blockQuoteEnabled = enable;
        if (enable) {
            addBlockQuoteBtn.setColorFilter(enabledColor);
        } else {
            addBlockQuoteBtn.setColorFilter(disabledColor);
        }
    }

    private void invalidateStates(int mode, int textComponentStyle) {
        if (mode == MODE_NUMBERING) {
            enableBlockQuote(false);
            enableHeading(false, 1);
            enableNormalText(false);
            enableBullet(true, true);
        } else if (mode == MODE_BULLET) {
            enableBlockQuote(false);
            enableHeading(false, 1);
            enableNormalText(false);
            enableBullet(true, false);
        } else if (mode == MODE_PLAIN) {
            if (textComponentStyle == BOLD) {
                enableBlockQuote(false);
                enableHeading(true, 5);
                enableNormalText(false);
                enableBullet(false, false);
            } else if (textComponentStyle == H4) {
                enableBlockQuote(false);
                enableHeading(true, 4);
                enableNormalText(false);
                enableBullet(false, false);
            } else if (textComponentStyle == H3) {
                enableBlockQuote(false);
                enableHeading(true, 3);
                enableNormalText(false);
                enableBullet(false, false);
            } else if (textComponentStyle == H2) {
                enableBlockQuote(false);
                enableHeading(true, 2);
                enableNormalText(false);
                enableBullet(false, false);
            } else if (textComponentStyle == H1) {
                enableBlockQuote(false);
                enableHeading(true, 1);
                enableNormalText(false);
                enableBullet(false, false);
            } else if (textComponentStyle == BLOCK_QUOTE) {
                enableBlockQuote(true);
                enableHeading(false, 1);
                enableNormalText(false);
                enableBullet(false, false);
            } else if (textComponentStyle == NORMAL) {
                enableBlockQuote(false);
                enableHeading(false, 1);
                enableNormalText(true);
                enableBullet(false, false);
            }
        }
    }

    @Override
    public void onFocusedViewHas(int mode, int textComponentStyle) {
        invalidateStates(mode, textComponentStyle);
    }

    public void setEditorControlListener(EditorControlListener editorControlListener) {
        this.editorControlListener = editorControlListener;
    }

    public interface EditorControlListener {
        void onInsertImageClicked();

        void onInsertLinkClicked();
    }
}
