package fabler.fablededitor.utilities;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fabler.fablededitor.FabledEditor;
import fabler.fablededitor.formatbar.components.divider.HorizontalDividerComponentItem;
import fabler.fablededitor.formatbar.components.image.ImageComponentItem;
import fabler.fablededitor.formatbar.components.text.TextComponentItem;
import fabler.fablededitor.models.ComponentTag;
import fabler.fablededitor.models.TextComponentModel;

import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_NUMBERING;
import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_PLAIN;
import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_BULLET;

public class MarkDownConverter {
    private StringBuilder stringBuilder;
    private List<String> images;
    private boolean dataProcessed;

    public MarkDownConverter() {
        stringBuilder = new StringBuilder();
        images = new ArrayList<>();
        dataProcessed = false;
    }

    public MarkDownConverter processData(FabledEditor markDEditor) {
        int childCount = markDEditor.getChildCount();
        View view;
        int textStyle;
        ComponentTag componentTag;
        for (int i = 0; i < childCount; i++) {
            view = markDEditor.getChildAt(i);
            if (view instanceof TextComponentItem) {
                //check mode
                int mode = ((TextComponentItem) view).getMode();
                if (mode == MODE_PLAIN) {
                    //check for styles {H1-BOLD Blockquote Normal}
                    componentTag = (ComponentTag) view.getTag();
                    textStyle = ((TextComponentModel) componentTag.getComponent()).getStyle();
                    stringBuilder.append(MarkDownFormat.getTextFormat(textStyle, ((TextComponentItem) view).getContent()));
                } else if (mode == MODE_BULLET) {
                    stringBuilder.append(MarkDownFormat.getULFormat(((TextComponentItem) view).getContent()));
                } else if (mode == MODE_NUMBERING) {
                    stringBuilder.append(MarkDownFormat.getOLFormat(
                            ((TextComponentItem) view).getIndicatorText(),
                            ((TextComponentItem) view).getContent()
                    ));
                }
            } else if (view instanceof HorizontalDividerComponentItem) {
                stringBuilder.append(MarkDownFormat.getLineFormat());
            } else if (view instanceof ImageComponentItem) {
                stringBuilder.append(MarkDownFormat.getImageFormat(((ImageComponentItem) view).getDownloadUrl()));
                images.add(((ImageComponentItem) view).getDownloadUrl());
                stringBuilder.append(MarkDownFormat.getCaptionFormat(((ImageComponentItem) view).getCaption()));
            }
        }
        dataProcessed = true;
        return this;
    }

    /**
     * @return flag whether views are processed or not.
     */
    public boolean isDataProcessed() {
        return dataProcessed;
    }

    /**
     * @return markdown format of data.
     */
    public String getMarkDown() {
        return stringBuilder.toString();
    }

    /**
     * @return list of inserted images.
     */
    public List<String> getImages() {
        return images;
    }
}
