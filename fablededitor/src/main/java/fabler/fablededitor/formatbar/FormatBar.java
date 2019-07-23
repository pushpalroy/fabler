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
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import fabler.fablededitor.FabledEditor;
import fabler.fablededitor.R;
import fabler.fablededitor.databinding.FormatBarBinding;

import static fabler.fablededitor.formatbar.animation.Anim.inFromRightAnimation;
import static fabler.fablededitor.formatbar.animation.Anim.outToRightAnimation;
import static fabler.fablededitor.styles.TextComponentAlignment.CENTER_ALIGNMENT;
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
    private Button btnLargeText, btnHugeText, btnTextPlain;
    private PopupWindow popupWindowTextSize, popupWindowAlignment;
    private int mTextAlignment, mTextStyle;
    FormatBarBinding mBinding;
    FormatViewModel mViewModel;
    LifecycleOwner mLifecycleOwner;

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
        mLifecycleOwner = (LifecycleOwner) mContext;

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.format_bar, this, true);
        mBinding.setLifecycleOwner(mLifecycleOwner);

        mViewModel = new FormatViewModel();
        mBinding.setFormatViewModel(mViewModel);

        mTextAlignment = LEFT_ALIGNMENT;
        mTextStyle = NORMAL;

        setupPopupTextSize();
        setupPopupAlignment();
        attachObservers();
    }

    public void setEditor(FabledEditor editor) {
        this.mEditor = editor;
        mBinding.setMEditor(mEditor);
        subscribeForStyles();
    }

    private void attachObservers() {
        mViewModel.isBoldEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextStyle = BOLD;
                mEditor.setStyle(mTextStyle);
            } else
                mEditor.setStyle(NORMAL);
        });

        mViewModel.isLargeEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextStyle = H3;
                mEditor.setStyle(mTextStyle);
                btnLargeText.setPressed(true);
            } else {
                mEditor.setStyle(NORMAL);
                btnLargeText.setPressed(false);
            }
            mEditor.setStyle(mTextStyle);
            popupWindowTextSize.dismiss();
        });

        mViewModel.isHugeEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextStyle = H1;
                mEditor.setStyle(mTextStyle);
                btnHugeText.setPressed(true);
            } else {
                mEditor.setStyle(NORMAL);
                btnHugeText.setPressed(false);
            }
            popupWindowTextSize.dismiss();
        });

        mViewModel.isPlainEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextStyle = NORMAL;
                mEditor.setStyle(mTextStyle);
                btnTextPlain.setPressed(true);
            } else
                btnTextPlain.setPressed(false);

            popupWindowTextSize.dismiss();
        });

        mViewModel.isQuoteEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextStyle = QUOTE;
                mEditor.setStyle(mTextStyle);
            } else
                mEditor.setStyle(NORMAL);
        });

        mViewModel.isLeftEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextAlignment = LEFT_ALIGNMENT;
                mEditor.setAlignment(mTextAlignment);
                mEditor.setStyle(mTextStyle);
                mBinding.btnFormatAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_left, null));
            }
            popupWindowAlignment.dismiss();
        });

        mViewModel.isCenterEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextAlignment = CENTER_ALIGNMENT;
                mEditor.setAlignment(mTextAlignment);
                mEditor.setStyle(mTextStyle);
                mBinding.btnFormatAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_center, null));
            }
            popupWindowAlignment.dismiss();
        });

        mViewModel.isRightEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextAlignment = RIGHT_ALIGNMENT;
                mEditor.setAlignment(mTextAlignment);
                mEditor.setStyle(mTextStyle);
                mBinding.btnFormatAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_right, null));
            }
            popupWindowAlignment.dismiss();
        });

        mViewModel.isListEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                if (mBinding.llListMenu.getVisibility() == GONE)
                    mBinding.llListMenu.startAnimation(inFromRightAnimation());
            } else {
                if (mBinding.llListMenu.getVisibility() == VISIBLE)
                    mBinding.llListMenu.startAnimation(outToRightAnimation());
            }
        });

        mViewModel.isBulletEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextAlignment = LEFT_ALIGNMENT;
                mTextStyle = NORMAL;
                mBinding.btnFormatAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_left, null));
                mEditor.changeToBulletMode();
            } else
                mEditor.setStyle(NORMAL);
        });

        mViewModel.isNumberingEnabled().observe(mLifecycleOwner, isEnabled -> {
            if (isEnabled) {
                mTextAlignment = LEFT_ALIGNMENT;
                mTextStyle = NORMAL;
                mBinding.btnFormatAlignment.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_format_align_left, null));
                mEditor.changeToNumMode();
            } else
                mEditor.setStyle(NORMAL);
        });
    }

    private void setupPopupTextSize() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View popupView = layoutInflater.inflate(R.layout.popup_text_size, null, false);

        popupWindowTextSize = new PopupWindow(popupView,
                800, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        popupWindowTextSize.setOutsideTouchable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            popupWindowTextSize.setElevation(10.0f);


        mBinding.setPopupWindowTextSize(popupWindowTextSize);
        btnLargeText = popupView.findViewById(R.id.btn_large_text);
        btnHugeText = popupView.findViewById(R.id.btn_huge_text);
        btnTextPlain = popupView.findViewById(R.id.btn_plain_text);

        btnLargeText.setOnClickListener(v -> mViewModel.onLargeClicked());
        btnHugeText.setOnClickListener(v -> mViewModel.onHugeClicked());
        btnTextPlain.setOnClickListener(v -> mViewModel.onPlainClicked());
    }

    private void setupPopupAlignment() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View popupView = layoutInflater.inflate(R.layout.popup_alignment, null, false);

        popupWindowAlignment = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        popupWindowAlignment.setOutsideTouchable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            popupWindowAlignment.setElevation(10.0f);

        mBinding.setPopupWindowAlignment(popupWindowAlignment);
        ImageButton btnLeftAlign = popupView.findViewById(R.id.btn_alignment_left);
        ImageButton btnRightAlign = popupView.findViewById(R.id.btn_alignment_right);
        ImageButton btnCenterAlign = popupView.findViewById(R.id.btn_alignment_center);

        btnLeftAlign.setOnClickListener(v -> mViewModel.onLeftClicked());
        btnRightAlign.setOnClickListener(v -> mViewModel.onRightClicked());
        btnCenterAlign.setOnClickListener(v -> mViewModel.onCenterClicked());
    }

    @Override
    public void onFocusedViewHas(int mode, int textComponentStyle, int textAlignment) {
        mViewModel.onFocusHas(mode, textComponentStyle, textAlignment);
    }

    private void subscribeForStyles() {
        if (mEditor != null) {
            mEditor.setEditorFocusReporter(this);
        }
    }

    public void setEditorControlListener(FormatBar.EditorControlListener editorControlListener) {
        mBinding.setEditorControlListener(editorControlListener);
    }

    public interface EditorControlListener {
        void onInsertImageClicked();

        void onInsertLinkClicked();
    }
}
