package fabler.fablededitor.models;

import androidx.annotation.NonNull;

import static fabler.fablededitor.styles.TextComponentAlignment.LEFT_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;

public class TextComponentModel extends BaseComponentModel {
    private int style = NORMAL;
    private int alignment = LEFT_ALIGNMENT;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    @NonNull
    @Override
    public String toString() {
        return "TextComponentModel{" +
                "style=" + style +
                '}';
    }
}
