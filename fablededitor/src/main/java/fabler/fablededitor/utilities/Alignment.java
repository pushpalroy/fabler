package fabler.fablededitor.utilities;

import android.view.Gravity;

import static fabler.fablededitor.styles.TextComponentAlignment.CENTER_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentAlignment.LEFT_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentAlignment.RIGHT_ALIGNMENT;

public class Alignment {
    public static int getGravity(int alignment) {
        switch (alignment) {
            case LEFT_ALIGNMENT:
                return Gravity.START;
            case RIGHT_ALIGNMENT:
                return Gravity.END;
            case CENTER_ALIGNMENT:
                return Gravity.CENTER_HORIZONTAL;
        }
        return Gravity.START;
    }
}