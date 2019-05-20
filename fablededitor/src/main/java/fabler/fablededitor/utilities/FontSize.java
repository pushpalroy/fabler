package fabler.fablededitor.utilities;

import fabler.fablededitor.styles.TextComponentStyle;

import static fabler.fablededitor.styles.TextComponentStyle.BOLD;
import static fabler.fablededitor.styles.TextComponentStyle.H1;
import static fabler.fablededitor.styles.TextComponentStyle.H2;
import static fabler.fablededitor.styles.TextComponentStyle.H3;
import static fabler.fablededitor.styles.TextComponentStyle.H4;

public class FontSize {
    private static final int H1_SIZE = 32;
    private static final int H2_SIZE = 28;
    private static final int H3_SIZE = 24;
    private static final int H4_SIZE = 22;
    private static final int BOLD_SIZE = 20;
    private static final int NORMAL = 20;

    public static int getFontSize(int heading) {
        switch (heading) {
            case H1:
                return H1_SIZE;
            case H2:
                return H2_SIZE;
            case H3:
                return H3_SIZE;
            case H4:
                return H4_SIZE;
            case BOLD:
                return BOLD_SIZE;
            case TextComponentStyle.NORMAL:
                return NORMAL;
        }
        return BOLD_SIZE;
    }
}
