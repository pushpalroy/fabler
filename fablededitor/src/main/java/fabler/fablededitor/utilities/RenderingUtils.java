package fabler.fablededitor.utilities;

import java.util.ArrayList;

import fabler.fablededitor.FabledEditor;
import fabler.fablededitor.datatype.DraftDataItemModel;
import fabler.fablededitor.models.DraftModel;

import static fabler.fablededitor.components.text.TextComponentItem.MODE_BULLET;
import static fabler.fablededitor.components.text.TextComponentItem.MODE_NUMBERING;
import static fabler.fablededitor.components.text.TextComponentItem.MODE_PLAIN;
import static fabler.fablededitor.styles.TextComponentStyle.BLOCK_QUOTE;
import static fabler.fablededitor.styles.TextComponentStyle.H1;
import static fabler.fablededitor.styles.TextComponentStyle.H2;
import static fabler.fablededitor.styles.TextComponentStyle.H3;
import static fabler.fablededitor.styles.TextComponentStyle.H4;
import static fabler.fablededitor.styles.TextComponentStyle.BOLD;
import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;

public class RenderingUtils {
    private FabledEditor markDEditor;

    public void setEditor(FabledEditor markDEditor) {
        this.markDEditor = markDEditor;
    }

    public void render(ArrayList<DraftDataItemModel> contents) {
        //visit each item type
        for (int i = 0; i < contents.size(); i++) {
            DraftDataItemModel item = contents.get(i);
            //identify item of data
            if (item.getItemType() == DraftModel.ITEM_TYPE_TEXT) {
                //identify mode of text item
                switch (item.getMode()) {
                    case MODE_PLAIN:
                        //includes NORMAL, H1-BOLD, BlockQuote
                        renderPlainData(item);
                        break;
                    case MODE_NUMBERING:
                        //renders orderedList
                        renderOrderedList(item);
                        break;
                    case MODE_BULLET:
                        //renders unorderedList
                        renderUnOrderedList(item);
                        break;
                    default:
                        //default goes to normal text
                        renderPlainData(item);
                }
            } else if (item.getItemType() == DraftModel.ITEM_TYPE_HR) {
                renderHR();
            } else if (item.getItemType() == DraftModel.ITEM_TYPE_IMAGE) {
                renderImage(item);
            }
        }
    }

    /**
     * Sets mode to plain and insert a a text component.
     *
     * @param item model of text data item
     */
    private void renderPlainData(DraftDataItemModel item) {
        markDEditor.setCurrentInputMode(MODE_PLAIN);
        switch (item.getStyle()) {
            case NORMAL:
            case H1:
            case H2:
            case H3:
            case H4:
            case BOLD:
            case BLOCK_QUOTE:
                markDEditor.addTextComponent(getInsertIndex(), item.getContent());
                markDEditor.setHeading(item.getStyle());
                break;
            default:
                markDEditor.addTextComponent(getInsertIndex(), item.getContent());
                markDEditor.setHeading(NORMAL);
        }
    }

    /**
     * Sets mode to ordered-list and insert a a text component.
     *
     * @param item model of text data item.
     */
    private void renderOrderedList(DraftDataItemModel item) {
        markDEditor.setCurrentInputMode(MODE_NUMBERING);
        markDEditor.addTextComponent(getInsertIndex(), item.getContent());
    }

    /**
     * Sets mode to unordered-list and insert a a text component.
     *
     * @param item model of text data item.
     */
    private void renderUnOrderedList(DraftDataItemModel item) {
        markDEditor.setCurrentInputMode(MODE_BULLET);
        markDEditor.addTextComponent(getInsertIndex(), item.getContent());
    }

    /**
     * Adds Horizontal line.
     */
    private void renderHR() {
        markDEditor.insertHorizontalDivider(false);
    }

    /**
     * @param item model of image item.
     *             Inserts image.
     *             Sets caption
     */
    private void renderImage(DraftDataItemModel item) {
        markDEditor.insertImage(getInsertIndex(), item.getDownloadUrl(), true, item.getCaption());
    }

    /**
     * Since childs are going to be arranged in linear fashion, child count can act as insert index.
     *
     * @return insert index.
     */
    private int getInsertIndex() {
        int index = markDEditor.getChildCount();
        return index;
    }
}
