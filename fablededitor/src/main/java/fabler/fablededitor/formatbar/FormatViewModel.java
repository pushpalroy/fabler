package fabler.fablededitor.formatbar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FormatViewModel extends ViewModel {

    private MutableLiveData<Boolean> boldEnabled, quoteEnabled;
    private MutableLiveData<Boolean> plainEnabled, largeEnabled, hugeEnabled;
    private MutableLiveData<Boolean> leftEnabled, rightEnabled, centerEnabled;
    private MutableLiveData<Boolean> listEnabled, bulletsEnabled, numberingEnabled;

    FormatViewModel() {
    }

    public MutableLiveData<Boolean> isBoldEnabled() {
        if (boldEnabled == null) {
            boldEnabled = new MutableLiveData<>();
            boldEnabled.setValue(false);
        }
        return boldEnabled;
    }

    /**
     * Text Style
     */

    public void onBoldClicked() {
        if (boldEnabled == null) {
            boldEnabled = new MutableLiveData<>();
            boldEnabled.setValue(false);
        }

        if (boldEnabled.getValue() != null && boldEnabled.getValue())
            boldEnabled.setValue(false);
        else {
            resetStyle();
            resetList();
            boldEnabled.setValue(true);
        }
    }

    MutableLiveData<Boolean> isPlainEnabled() {
        if (plainEnabled == null) {
            plainEnabled = new MutableLiveData<>();
            plainEnabled.setValue(true);
        }
        return plainEnabled;
    }

    void onPlainClicked() {
        if (plainEnabled == null) {
            plainEnabled = new MutableLiveData<>();
            plainEnabled.setValue(false);
        }

        if (plainEnabled.getValue() != null && plainEnabled.getValue())
            plainEnabled.setValue(false);
        else {
            resetStyle();
            resetList();
            plainEnabled.setValue(true);
        }
    }

    public MutableLiveData<Boolean> isLargeEnabled() {
        if (largeEnabled == null) {
            largeEnabled = new MutableLiveData<>();
            largeEnabled.setValue(false);
        }
        return largeEnabled;
    }

    void onLargeClicked() {
        if (largeEnabled == null) {
            largeEnabled = new MutableLiveData<>();
            largeEnabled.setValue(false);
        }

        if (largeEnabled.getValue() != null && largeEnabled.getValue()) {
            largeEnabled.setValue(false);
        } else {
            resetStyle();
            resetList();
            largeEnabled.setValue(true);
        }
    }

    public MutableLiveData<Boolean> isHugeEnabled() {
        if (hugeEnabled == null) {
            hugeEnabled = new MutableLiveData<>();
            hugeEnabled.setValue(false);
        }
        return hugeEnabled;
    }

    void onHugeClicked() {
        if (hugeEnabled == null) {
            hugeEnabled = new MutableLiveData<>();
            hugeEnabled.setValue(false);
        }

        if (hugeEnabled.getValue() != null && hugeEnabled.getValue()) {
            hugeEnabled.setValue(false);
        } else {
            resetStyle();
            resetList();
            hugeEnabled.setValue(true);
        }
    }

    public MutableLiveData<Boolean> isQuoteEnabled() {
        if (quoteEnabled == null) {
            quoteEnabled = new MutableLiveData<>();
            quoteEnabled.setValue(false);
        }
        return quoteEnabled;
    }

    public void onQuoteClicked() {
        if (quoteEnabled == null) {
            quoteEnabled = new MutableLiveData<>();
            quoteEnabled.setValue(false);
        }

        if (quoteEnabled.getValue() != null && quoteEnabled.getValue())
            quoteEnabled.setValue(false);
        else {
            resetStyle();
            resetList();
            quoteEnabled.setValue(true);
        }
    }

    /**
     * List Text
     */

    public MutableLiveData<Boolean> isListEnabled() {
        if (listEnabled == null) {
            listEnabled = new MutableLiveData<>();
            listEnabled.setValue(false);
        }
        return listEnabled;
    }

    public MutableLiveData<Boolean> isNumberingEnabled() {
        if (numberingEnabled == null) {
            numberingEnabled = new MutableLiveData<>();
            numberingEnabled.setValue(false);
        }
        return numberingEnabled;
    }

    public void onNumberingClicked() {
        if (numberingEnabled == null) {
            numberingEnabled = new MutableLiveData<>();
            numberingEnabled.setValue(false);
        }

        if (numberingEnabled.getValue() != null && numberingEnabled.getValue()) {
            numberingEnabled.setValue(false);

            if (bulletsEnabled.getValue() == null || !bulletsEnabled.getValue())
                listEnabled.setValue(false);
        } else {
            resetAllExceptListOption();
            numberingEnabled.setValue(true);
            listEnabled.setValue(true);
        }
    }

    public MutableLiveData<Boolean> isBulletEnabled() {
        if (bulletsEnabled == null) {
            bulletsEnabled = new MutableLiveData<>();
            bulletsEnabled.setValue(false);
        }
        return bulletsEnabled;
    }

    public void onBulletClicked() {
        if (bulletsEnabled == null) {
            bulletsEnabled = new MutableLiveData<>();
            bulletsEnabled.setValue(false);
        }

        if (bulletsEnabled.getValue() != null && bulletsEnabled.getValue()) {
            bulletsEnabled.setValue(false);

            if (numberingEnabled.getValue() == null || !numberingEnabled.getValue())
                listEnabled.setValue(false);
        } else {
            resetAllExceptListOption();
            bulletsEnabled.setValue(true);
            listEnabled.setValue(true);
        }
    }

    /**
     * Text Alignment
     */

    MutableLiveData<Boolean> isLeftEnabled() {
        if (leftEnabled == null) {
            leftEnabled = new MutableLiveData<>();
            leftEnabled.setValue(true);
        }
        return leftEnabled;
    }

    void onLeftClicked() {
        if (leftEnabled == null) {
            leftEnabled = new MutableLiveData<>();
            leftEnabled.setValue(false);
        }

        if (leftEnabled.getValue() != null && leftEnabled.getValue())
            leftEnabled.setValue(false);
        else {
            resetAlignment();
            resetList();
            leftEnabled.setValue(true);
        }
    }

    MutableLiveData<Boolean> isRightEnabled() {
        if (rightEnabled == null) {
            rightEnabled = new MutableLiveData<>();
            rightEnabled.setValue(false);
        }
        return rightEnabled;
    }

    void onRightClicked() {
        if (rightEnabled == null) {
            rightEnabled = new MutableLiveData<>();
            rightEnabled.setValue(false);
        }

        if (rightEnabled.getValue() != null && rightEnabled.getValue())
            rightEnabled.setValue(false);
        else {
            resetAlignment();
            resetList();
            rightEnabled.setValue(true);
        }
    }

    MutableLiveData<Boolean> isCenterEnabled() {
        if (centerEnabled == null) {
            centerEnabled = new MutableLiveData<>();
            centerEnabled.setValue(false);
        }
        return centerEnabled;
    }

    void onCenterClicked() {
        if (centerEnabled == null) {
            centerEnabled = new MutableLiveData<>();
            centerEnabled.setValue(false);
        }

        if (centerEnabled.getValue() != null && centerEnabled.getValue())
            centerEnabled.setValue(false);
        else {
            resetAlignment();
            resetList();
            centerEnabled.setValue(true);
        }
    }

    void resetAll() {
        resetStyle();
        resetAlignment();
        resetList();
    }

    private void resetAllExceptListOption() {
        resetStyle();
        resetAlignment();
        resetListSubOptionsOnly();
    }

    private void resetStyle() {
        plainEnabled.setValue(false);
        largeEnabled.setValue(false);
        hugeEnabled.setValue(false);
        boldEnabled.setValue(false);
        quoteEnabled.setValue(false);
    }

    private void resetAlignment() {
        leftEnabled.setValue(false);
        rightEnabled.setValue(false);
        centerEnabled.setValue(false);
    }

    private void resetList() {
        listEnabled.setValue(false);
        bulletsEnabled.setValue(false);
        numberingEnabled.setValue(false);
    }

    private void resetListSubOptionsOnly() {
        bulletsEnabled.setValue(false);
        numberingEnabled.setValue(false);
    }
}
