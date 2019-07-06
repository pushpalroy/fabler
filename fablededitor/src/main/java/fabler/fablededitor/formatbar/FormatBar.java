package fabler.fablededitor.formatbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import fabler.fablededitor.FabledEditor;
import fabler.fablededitor.R;

import static fabler.fablededitor.formatbar.animation.Anim.inFromRightAnimation;
import static fabler.fablededitor.formatbar.animation.Anim.outToRightAnimation;
import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_BULLET;
import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_NUMBERING;
import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_PLAIN;
import static fabler.fablededitor.styles.TextComponentAlignment.CENTER_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentAlignment.JUSTIFY_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentAlignment.LEFT_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentAlignment.RIGHT_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentStyle.BOLD;
import static fabler.fablededitor.styles.TextComponentStyle.H1;
import static fabler.fablededitor.styles.TextComponentStyle.H3;
import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;
import static fabler.fablededitor.styles.TextComponentStyle.QUOTE;

public class FormatBar extends FrameLayout implements FabledEditor.EditorFocusReporter {

    private Context mContext;
    private FabledEditor mEditor;
    private ImageButton btnBold, btnTextSize, btnQuote, btnDivider,
            btnInsertLink, btnAlignment, btnList, btnLeftAlign, btnRightAlign, btnCenterAlign,
            btnJustifyAlign, btnBullets, btnNumbering;
    private Button btnLargeText, btnHugeText, btnTextPlain;
    private LinearLayout layoutListMenu;
    private PopupWindow popupWindowTextSize, popupWindowAlignment;

    private int enabledColor, disabledColor, textAlignment;
    private boolean boldEnabled, quoteEnabled, sizeEnabled, plainEnabled, largeEnabled, hugeEnabled,
            bulletsEnabled, numberingEnabled, leftEnabled, rightEnabled, centerEnabled, justifyEnabled;

    private FormatBar.EditorControlListener editorControlListener;

    public FormatBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FormatBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        View formatBarView = LayoutInflater.from(context).inflate(R.layout.formatting_bar, this);
        btnBold = formatBarView.findViewById(R.id.btn_format_bold);
        btnTextSize = formatBarView.findViewById(R.id.btn_format_size);
        btnQuote = formatBarView.findViewById(R.id.btn_format_quote);
        btnDivider = formatBarView.findViewById(R.id.btn_format_divider);
        btnInsertLink = formatBarView.findViewById(R.id.btn_format_insert_link);
        btnAlignment = formatBarView.findViewById(R.id.btn_format_alignment);
        btnList = formatBarView.findViewById(R.id.btn_format_list);
        layoutListMenu = formatBarView.findViewById(R.id.ll_list_menu);
        btnBullets = formatBarView.findViewById(R.id.btn_format_bullets);
        btnNumbering = formatBarView.findViewById(R.id.btn_format_numbering);

        enabledColor = R.color.formatGrey;
        disabledColor = R.color.transparent;
        textAlignment = LEFT_ALIGNMENT;
        leftEnabled = true;

        setupPopupTextSize();
        setupPopupAlignment();
        attachListeners();
    }

    public void setEditor(FabledEditor editor) {
        this.mEditor = editor;
        subscribeForStyles();
    }

    private void attachListeners() {
        btnBold.setOnClickListener(v -> {
            if (!boldEnabled)
                mEditor.setStyle(BOLD);
            else
                mEditor.setStyle(NORMAL);

            // Toggles the BOLD state & other options
            updateUiStatesOfStyle(MODE_PLAIN, BOLD);
        });

        btnTextSize.setOnClickListener(v -> {
            btnTextPlain.setPressed(plainEnabled);
            btnLargeText.setPressed(largeEnabled);
            btnHugeText.setPressed(hugeEnabled);

            popupWindowTextSize.showAsDropDown(v, 0, -270);
        });

        btnQuote.setOnClickListener(v -> {
            if (!quoteEnabled)
                mEditor.changeToQuote();
            else
                mEditor.setStyle(NORMAL);

            // Toggles the QUOTE state only
            updateUiStatesOfStyle(MODE_PLAIN, QUOTE);
        });

        btnDivider.setOnClickListener(v -> mEditor.insertHorizontalDivider());

        btnInsertLink.setOnClickListener(v -> {
            if (editorControlListener != null)
                editorControlListener.onInsertLinkClicked();
        });

        btnAlignment.setOnClickListener(v -> {
            btnLeftAlign.setPressed(leftEnabled);
            btnRightAlign.setPressed(rightEnabled);
            btnCenterAlign.setPressed(centerEnabled);
            btnJustifyAlign.setPressed(justifyEnabled);

            popupWindowAlignment.showAsDropDown(v, -35, -630);
        });

        btnList.setOnClickListener(v -> {
            layoutListMenu.setVisibility(VISIBLE);
            layoutListMenu.startAnimation(inFromRightAnimation());

            mEditor.changeToBulletMode();
            enableLeftAlignment();
            updateUiStatesOfStyle(MODE_BULLET, NORMAL);
        });

        btnBullets.setOnClickListener(v -> {
            mEditor.changeToBulletMode();
            updateUiStatesOfStyle(MODE_BULLET, NORMAL);
        });

        btnNumbering.setOnClickListener(v -> {
            mEditor.changeToNumMode();
            updateUiStatesOfStyle(MODE_NUMBERING, NORMAL);
        });
    }

    private void setupPopupTextSize() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View popupView = layoutInflater.inflate(R.layout.popup_text_size, null, false);

        popupWindowTextSize = new PopupWindow(popupView,
                800,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                false);

        popupWindowTextSize.setOutsideTouchable(true);
        //popupWindowTextSize.setAnimationStyle(R.style.popup_window_animation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindowTextSize.setElevation(10.0f);
        }

        btnLargeText = popupView.findViewById(R.id.btn_large_text);
        btnHugeText = popupView.findViewById(R.id.btn_huge_text);
        btnTextPlain = popupView.findViewById(R.id.btn_plain_text);

        // Default selection should be Plain
        plainEnabled = true;

        btnLargeText.setOnClickListener(v -> {
            mEditor.setStyle(H3);
            updateUiStatesOfStyle(MODE_PLAIN, H3);
            popupWindowTextSize.dismiss();
        });

        btnHugeText.setOnClickListener(v -> {
            mEditor.setStyle(H1);
            updateUiStatesOfStyle(MODE_PLAIN, H1);
            popupWindowTextSize.dismiss();
        });

        btnTextPlain.setOnClickListener(v -> {
            mEditor.setStyle(NORMAL);
            updateUiStatesOfStyle(MODE_PLAIN, NORMAL);
            popupWindowTextSize.dismiss();
        });
    }

    private void setupPopupAlignment() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View popupView = layoutInflater.inflate(R.layout.popup_alignment, null, false);

        popupWindowAlignment = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                false);

        popupWindowAlignment.setOutsideTouchable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindowAlignment.setElevation(10.0f);
        }

        btnLeftAlign = popupView.findViewById(R.id.btn_alignment_left);
        btnRightAlign = popupView.findViewById(R.id.btn_alignment_right);
        btnCenterAlign = popupView.findViewById(R.id.btn_alignment_center);
        btnJustifyAlign = popupView.findViewById(R.id.btn_alignment_justify);

        btnLeftAlign.setOnClickListener(v -> {
            textAlignment = LEFT_ALIGNMENT;
            mEditor.setAlignment(textAlignment);
            updateUiStatesOfAlignment(textAlignment);
            popupWindowAlignment.dismiss();
        });

        btnRightAlign.setOnClickListener(v -> {
            textAlignment = RIGHT_ALIGNMENT;
            mEditor.setAlignment(textAlignment);
            updateUiStatesOfAlignment(textAlignment);
            popupWindowAlignment.dismiss();
        });

        btnCenterAlign.setOnClickListener(v -> {
            textAlignment = CENTER_ALIGNMENT;
            mEditor.setAlignment(textAlignment);
            updateUiStatesOfAlignment(textAlignment);
            popupWindowAlignment.dismiss();
        });

        btnJustifyAlign.setOnClickListener(v -> {
            textAlignment = JUSTIFY_ALIGNMENT;
            mEditor.setAlignment(textAlignment);
            updateUiStatesOfAlignment(textAlignment);
            popupWindowAlignment.dismiss();
        });
    }

    private void updateUiStatesOfStyle(int mode, int textComponentStyle) {
        switch (mode) {
            case MODE_PLAIN:
                switch (textComponentStyle) {
                    case NORMAL:
                        enablePlain();
                        break;
                    case BOLD:
                        enableBold(!boldEnabled);
                        break;
                    case QUOTE:
                        enableQuote(!quoteEnabled);
                        break;
                    case H3:
                        enableSize(H3);
                        break;
                    case H1:
                        enableSize(H1);
                }
                break;
            case MODE_BULLET:
                enableBullets(!bulletsEnabled);
                break;
            case MODE_NUMBERING:
                enableNumbering(!numberingEnabled);
        }
    }

    private void updateUiStatesOfAlignment(int alignment) {
        resetAlignmentValues();
        resetListValues();
        switch (alignment) {
            case LEFT_ALIGNMENT:
                btnAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_left, null));
                leftEnabled = true;
                break;
            case RIGHT_ALIGNMENT:
                btnAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_right, null));
                rightEnabled = true;
                break;
            case CENTER_ALIGNMENT:
                btnAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_center, null));
                centerEnabled = true;
                break;
            default:
                btnAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_left, null));
                justifyEnabled = true;
        }
    }

    private void enablePlain() {
        resetUi();
        plainEnabled = true;
    }

    private void enableBold(boolean enable) {
        resetFormatValues();
        resetListValues();
        boldEnabled = enable;
        if (enable)
            btnBold.setBackgroundResource(enabledColor);
        else
            btnBold.setBackgroundResource(disabledColor);
    }

    private void enableQuote(boolean enable) {
        resetFormatValues();
        resetListValues();
        quoteEnabled = enable;
        if (enable)
            btnQuote.setBackgroundResource(enabledColor);
        else
            btnQuote.setBackgroundResource(disabledColor);
    }

    private void enableSize(int size) {
        resetFormatValues();
        resetListValues();
        sizeEnabled = true;

        if (size == H3) largeEnabled = true;
        else if (size == H1) hugeEnabled = true;
        btnTextSize.setBackgroundResource(enabledColor);
    }

    private void enableBullets(boolean enable) {
        resetFormatValues();
        resetAlignmentValues();

        if (enable) {
            btnNumbering.setBackgroundResource(disabledColor);
            btnBullets.setBackgroundResource(enabledColor);
            bulletsEnabled = true;
            numberingEnabled = false;
        } else {
            btnBullets.setBackgroundResource(disabledColor);
            layoutListMenu.startAnimation(outToRightAnimation());
            layoutListMenu.setVisibility(GONE);
            mEditor.setStyle(NORMAL);
            bulletsEnabled = false;
            numberingEnabled = false;
        }
    }

    private void enableNumbering(boolean enable) {
        resetFormatValues();
        resetAlignmentValues();

        if (enable) {
            btnBullets.setBackgroundResource(disabledColor);
            btnNumbering.setBackgroundResource(enabledColor);
            numberingEnabled = true;
            bulletsEnabled = false;
        } else {
            btnNumbering.setBackgroundResource(disabledColor);
            layoutListMenu.startAnimation(outToRightAnimation());
            layoutListMenu.setVisibility(GONE);
            mEditor.setStyle(NORMAL);
            numberingEnabled = false;
            bulletsEnabled = false;
        }
    }

    private void enableLeftAlignment() {
        textAlignment = LEFT_ALIGNMENT;
        btnAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_format_align_left, null));
        leftEnabled = true;
    }

    @Override
    public void onFocusedViewHas(int mode, int textComponentStyle, int textAlignment) {
        resetUi();
        updateUiStatesOfStyle(mode, textComponentStyle);
        updateUiStatesOfAlignment(textAlignment);
    }

    private void subscribeForStyles() {
        if (mEditor != null) {
            mEditor.setEditorFocusReporter(this);
        }
    }

    public void setEditorControlListener(FormatBar.EditorControlListener editorControlListener) {
        this.editorControlListener = editorControlListener;
    }

    private void resetUi() {
        resetFormatValues();
        resetAlignmentValues();
        resetListValues();
    }

    private void resetFormatValues() {
        boldEnabled = false;
        quoteEnabled = false;
        sizeEnabled = false;

        plainEnabled = false;
        largeEnabled = false;
        hugeEnabled = false;

        btnBold.setBackgroundResource(disabledColor);
        btnQuote.setBackgroundResource(disabledColor);
        btnTextSize.setBackgroundResource(disabledColor);

        btnTextPlain.setPressed(plainEnabled);
        btnLargeText.setPressed(largeEnabled);
        btnHugeText.setPressed(hugeEnabled);
    }

    private void resetAlignmentValues() {
        leftEnabled = false;
        rightEnabled = false;
        centerEnabled = false;
        justifyEnabled = false;

        btnLeftAlign.setPressed(leftEnabled);
        btnRightAlign.setPressed(rightEnabled);
        btnCenterAlign.setPressed(centerEnabled);
        btnJustifyAlign.setPressed(justifyEnabled);
    }

    private void resetListValues() {
        bulletsEnabled = false;
        numberingEnabled = false;

        btnBullets.setPressed(bulletsEnabled);
        btnNumbering.setPressed(numberingEnabled);

        if (layoutListMenu != null && layoutListMenu.getVisibility() == VISIBLE) {
            layoutListMenu.startAnimation(outToRightAnimation());
            layoutListMenu.setVisibility(GONE);
        }
    }

    public PopupWindow getTextSizePopupWindow() {
        return popupWindowTextSize;
    }

    public interface EditorControlListener {
        void onInsertImageClicked();

        void onInsertLinkClicked();
    }
}
