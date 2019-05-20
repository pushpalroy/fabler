package fabler.fablededitor.utilities;

import android.view.View;

import java.util.ArrayList;

import fabler.fablededitor.FabledEditor;
import fabler.fablededitor.components.divider.HorizontalDividerComponentItem;
import fabler.fablededitor.components.image.ImageComponentItem;
import fabler.fablededitor.components.text.TextComponentItem;
import fabler.fablededitor.datatype.DraftDataItemModel;
import fabler.fablededitor.models.ComponentTag;
import fabler.fablededitor.models.DraftModel;
import fabler.fablededitor.models.TextComponentModel;

import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;
import static fabler.fablededitor.components.text.TextComponentItem.MODE_NUMBERING;
import static fabler.fablededitor.components.text.TextComponentItem.MODE_PLAIN;
import static fabler.fablededitor.components.text.TextComponentItem.MODE_BULLET;

public class DraftManager {
    public DraftManager() {

    }

    /**
     * Traverse through each item and prepares the draft item list.
     *
     * @param fabledEditor editor object.
     * @return a list of Draft Item types.
     */
    public DraftModel processDraftContent(FabledEditor fabledEditor) {
        ArrayList<DraftDataItemModel> drafts = new ArrayList<>();
        int childCount = fabledEditor.getChildCount();
        View view;
        int textStyle;
        ComponentTag componentTag;
        for (int i = 0; i < childCount; i++) {
            view = fabledEditor.getChildAt(i);
            if (view instanceof TextComponentItem) {
                //check mode
                int mode = ((TextComponentItem) view).getMode();
                if (mode == MODE_PLAIN) {
                    //check for styles {H1-BOLD Blockquote Normal}
                    componentTag = (ComponentTag) view.getTag();
                    textStyle = ((TextComponentModel) componentTag.getComponent()).getHeadingStyle();
                    drafts.add(getPlainModel(textStyle, ((TextComponentItem) view).getContent()));
                } else if (mode == MODE_BULLET) {
                    drafts.add(getUlModel(((TextComponentItem) view).getContent()));
                } else if (mode == MODE_NUMBERING) {
                    drafts.add(getOlModel(((TextComponentItem) view).getContent()));
                }
            } else if (view instanceof HorizontalDividerComponentItem) {
                drafts.add(getHRModel());
            } else if (view instanceof ImageComponentItem) {
                drafts.add(getImageModel(
                        ((ImageComponentItem) view).getDownloadUrl(),
                        ((ImageComponentItem) view).getCaption()
                ));
            }
        }
        return new DraftModel(drafts);
    }

    /**
     * Models Text information.
     *
     * @param textStyle style associated with the text (NORMAL,H1-BOLD,BLOCK_QUOTE)
     * @param content   text content
     * @return a Generic TextType Object containing information.
     */
    private DraftDataItemModel getPlainModel(int textStyle, String content) {
        DraftDataItemModel textType = new DraftDataItemModel();
        textType.setItemType(DraftModel.ITEM_TYPE_TEXT);
        textType.setContent(content);
        textType.setMode(MODE_PLAIN);
        textType.setStyle(textStyle);
        return textType;
    }

    /**
     * Models UnOrdered list text information.
     *
     * @param content text content.
     * @return a UL type model object.
     */
    private DraftDataItemModel getUlModel(String content) {
        DraftDataItemModel textType = new DraftDataItemModel();
        textType.setItemType(DraftModel.ITEM_TYPE_TEXT);
        textType.setContent(content);
        textType.setMode(MODE_BULLET);
        textType.setStyle(NORMAL);
        return textType;
    }

    /**
     * Models Ordered list text information.
     *
     * @param content text content.
     * @return a OL type model object.
     */
    private DraftDataItemModel getOlModel(String content) {
        DraftDataItemModel textType = new DraftDataItemModel();
        textType.setItemType(DraftModel.ITEM_TYPE_TEXT);
        textType.setContent(content);
        textType.setMode(MODE_NUMBERING);
        textType.setStyle(NORMAL);
        return textType;
    }

    /**
     * Models Horizontal rule object.
     *
     * @return a HR type model object.
     */
    private DraftDataItemModel getHRModel() {
        DraftDataItemModel hrType = new DraftDataItemModel();
        hrType.setItemType(DraftModel.ITEM_TYPE_HR);
        return hrType;
    }

    /**
     * Models Image type object item.
     *
     * @param downloadUrl url of the image.
     * @param caption     caption of the image(if any)
     * @return a Image Model object type.
     */
    private DraftDataItemModel getImageModel(String downloadUrl, String caption) {
        DraftDataItemModel imageType = new DraftDataItemModel();
        imageType.setItemType(DraftModel.ITEM_TYPE_IMAGE);
        imageType.setCaption(caption);
        imageType.setDownloadUrl(downloadUrl);
        return imageType;
    }

}
