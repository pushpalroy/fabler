package fabler.fablededitor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fabler.fablededitor.datatype.DraftDataItemModel;
import fabler.fablededitor.formatbar.components.divider.HorizontalDividerComponent;
import fabler.fablededitor.formatbar.components.divider.HorizontalDividerComponentItem;
import fabler.fablededitor.formatbar.components.image.ImageComponent;
import fabler.fablededitor.formatbar.components.image.ImageComponentItem;
import fabler.fablededitor.formatbar.components.text.TextComponent;
import fabler.fablededitor.formatbar.components.text.TextComponentItem;
import fabler.fablededitor.models.ComponentTag;
import fabler.fablededitor.models.DraftModel;
import fabler.fablededitor.models.ImageComponentModel;
import fabler.fablededitor.models.TextComponentModel;
import fabler.fablededitor.utilities.ComponentMetadataHelper;
import fabler.fablededitor.utilities.DraftManager;
import fabler.fablededitor.utilities.MarkDownConverter;
import fabler.fablededitor.utilities.RenderingUtils;

import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_BULLET;
import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_NUMBERING;
import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_PLAIN;
import static fabler.fablededitor.styles.TextComponentAlignment.LEFT_ALIGNMENT;
import static fabler.fablededitor.styles.TextComponentStyle.NORMAL;
import static fabler.fablededitor.styles.TextComponentStyle.QUOTE;

public class FabledEditor extends FabledCore implements
        TextComponent.TextComponentCallback,
        ImageComponentItem.ImageComponentListener {
    private static String serverUrl;
    private View _activeView;
    private Context mContext;
    private DraftManager draftManager;
    private TextComponent __textComponent;
    private ImageComponent __imageComponent;
    private HorizontalDividerComponent __horizontalComponent;
    private int currentInputMode;
    private MarkDownConverter markDownConverter;
    private String serverToken;
    private RenderingUtils renderingUtils;
    private EditorFocusReporter editorFocusReporter;
    private String startHintText;
    private int defaultHeadingType = NORMAL;
    private int defaultAlignment = LEFT_ALIGNMENT;
    private boolean isFreshEditor, isImageAdded;
    private DraftModel oldDraft;
    private EditorCallback editorCallback;

    public FabledEditor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        draftManager = new DraftManager();
        bulletGroupModels = new ArrayList<>();
        markDownConverter = new MarkDownConverter();
        currentInputMode = MODE_PLAIN;
        isImageAdded = false;
        __textComponent = new TextComponent(context, this);
        __imageComponent = new ImageComponent(context);
        __horizontalComponent = new HorizontalDividerComponent(context);
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    /**
     * Helper method to configure editor
     *
     * @param serverUrl
     * @param serverToken
     * @param isDraft
     * @param startHint
     * @param defaultHeadingType
     */
    public void configureEditor(
            String serverUrl,
            String serverToken,
            boolean isDraft,
            String startHint,
            int defaultHeadingType,
            EditorCallback editorCallback) {
        this.serverToken = serverToken;
        this.serverUrl = serverUrl;
        this.startHintText = startHint;
        this.defaultHeadingType = defaultHeadingType;
        this.editorCallback = editorCallback;
        if (!isDraft) {
            startFreshEditor();
        }
    }

    /**
     * Inserts single text component
     */
    public void startFreshEditor() {
        //starts basic editor with single text component.
        this.isFreshEditor = true;
        addTextComponent(0);
        setStyle(defaultHeadingType);
        setAlignment(defaultAlignment);
    }

    /**
     * adds new TextComponent.
     *
     * @param insertIndex at which addition of new textcomponent take place.
     */
    private void addTextComponent(int insertIndex) {
        TextComponentItem textComponentItem = __textComponent.newTextComponent(currentInputMode);
        //prepare tag
        TextComponentModel textComponentModel = new TextComponentModel();
        if (insertIndex == 0) {
            if (startHintText != null && isFreshEditor) {
                textComponentItem.setHintText(startHintText);
            }
        }
        ComponentTag componentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        componentTag.setComponent(textComponentModel);
        textComponentItem.setTag(componentTag);
        addView(textComponentItem, insertIndex);
        __textComponent.updateComponent(textComponentItem, 0, 0);
        setFocus(textComponentItem);
        reComputeTagsAfter(insertIndex);
        refreshViewOrder();
    }

    /**
     * sets style to text component
     *
     * @param style number to be set
     */
    public void setStyle(int style) {
        currentInputMode = MODE_PLAIN;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setStyle(style);

            int startSelection = ((TextComponentItem) _activeView).getInputBox().getSelectionStart();
            int endSelection = ((TextComponentItem) _activeView).getInputBox().getSelectionEnd();

            __textComponent.updateComponent(_activeView, startSelection, endSelection);
        }
        refreshViewOrder();
    }

    /**
     * sets alignment to text component
     *
     * @param alignment number to be set
     */
    public void setAlignment(int alignment) {
        currentInputMode = MODE_PLAIN;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setAlignment(alignment);

            __textComponent.updateComponent(_activeView, 0, 0);
        }
        refreshViewOrder();
    }

    /**
     * @param view to be focused on.
     */
    private void setFocus(View view) {
        _activeView = view;
        if (_activeView instanceof TextComponentItem) {
            currentInputMode = ((TextComponentItem) _activeView).getMode();
            ((TextComponentItem) view).getInputBox().requestFocus();
            reportStylesOfFocusedView((TextComponentItem) view);
        }
    }

    /**
     * re-compute the indexes of view after a view is inserted/deleted.
     *
     * @param startIndex index after which re-computation will be done.
     */
    private void reComputeTagsAfter(int startIndex) {
        View _child;
        for (int i = startIndex; i < getChildCount(); i++) {
            _child = getChildAt(i);
            ComponentTag componentTag = (ComponentTag) _child.getTag();
            componentTag.setComponentIndex(i);
            _child.setTag(componentTag);
        }
    }

    /**
     * method to send callback for focused view back to subscriber(if any).
     *
     * @param view newly focus view.
     */
    private void reportStylesOfFocusedView(TextComponentItem view) {
        if (editorFocusReporter != null) {
            editorFocusReporter.onFocusedViewHas(view.getMode(), view.getTextHeadingStyle(), view.getTextAlignment());
        }
    }

    public void loadDraft(DraftModel draft) {
        oldDraft = draft;
        ArrayList<DraftDataItemModel> contents = draft.getItems();
        if (contents != null) {
            if (contents.size() > 0) {
                renderingUtils = new RenderingUtils();
                renderingUtils.setEditor(this);
                renderingUtils.render(contents);
            } else {
                startFreshEditor();
            }
        } else {
            startFreshEditor();
        }
    }

    /**
     * Sets current mode for insert.
     *
     * @param currentInputMode mode of insert.
     */
    public void setCurrentInputMode(int currentInputMode) {
        this.currentInputMode = currentInputMode;
    }

    /**
     * adds new TextComponent with pre-filled text.
     *
     * @param insertIndex at which addition of new text component take place.
     */
    public void addTextComponent(int insertIndex, String content) {
        TextComponentItem textComponentItem = __textComponent.newTextComponent(currentInputMode);
        //prepare tag
        TextComponentModel textComponentModel = new TextComponentModel();
        ComponentTag componentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        componentTag.setComponent(textComponentModel);
        textComponentItem.setTag(componentTag);
        textComponentItem.setText(content);
        addView(textComponentItem, insertIndex);
        __textComponent.updateComponent(textComponentItem, 0, 0);
        setFocus(textComponentItem);
        reComputeTagsAfter(insertIndex);
        refreshViewOrder();
    }

    @Override
    public void onInsertTextComponent(int selfIndex) {
        addTextComponent(selfIndex + 1);
    }

    @Override
    public void onFocusGained(View view) {
        setFocus(view);
    }

    /**
     * This callback method removes view at given index.
     * It checks if there is a horizontal line just before it, it removes the line too.
     * Else it removes the current view only.
     *
     * @param selfIndex index of view to remove.
     */
    @Override
    public void onRemoveTextComponent(int selfIndex) {
        if (selfIndex == 0)
            return;
        View viewToBeRemoved = getChildAt(selfIndex);
        View previousView = getChildAt(selfIndex - 1);
        String content = ((TextComponentItem) viewToBeRemoved).getInputBox().getText().toString();
        if (previousView instanceof HorizontalDividerComponentItem) {
            //remove previous view.
            removeViewAt(selfIndex - 1);
            reComputeTagsAfter(selfIndex - 1);
            //focus on latest text component
            int lastTextComponent = getLatestTextComponentIndexBefore(selfIndex - 1);
            setFocus(getChildAt(lastTextComponent));
        } else if (previousView instanceof TextComponentItem) {
            removeViewAt(selfIndex);
            int contentLen = ((TextComponentItem) previousView).getInputBox().getText().toString().length();
            ((TextComponentItem) previousView).getInputBox().append(String.format("%s", content));
            setFocus(previousView, contentLen);
        } else if (previousView instanceof ImageComponentItem) {
            setActiveView(previousView);
            ((ImageComponentItem) previousView).setFocus();
        }
        reComputeTagsAfter(selfIndex);
        refreshViewOrder();
    }

    @Override
    public void onBackPressed() {
        editorCallback.onBackPressed();
    }

    /**
     * This method searches within view group for a TextComponent which was
     * inserted prior to startIndex.
     *
     * @param starIndex index from which search starts.
     * @return index of LatestTextComponent before startIndex.
     */
    private int getLatestTextComponentIndexBefore(int starIndex) {
        View view = null;
        for (int i = starIndex; i >= 0; i--) {
            view = getChildAt(i);
            if (view instanceof TextComponentItem)
                return i;
        }
        return 0;
    }

    /**
     * overloaded method for focusing view, it puts the cursor at specified position.
     *
     * @param view to be focused on.
     */
    private void setFocus(View view, int cursorPos) {
        _activeView = view;
        view.requestFocus();
        if (view instanceof TextComponentItem) {
            InputMethodManager mgr = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            //move cursor
            ((TextComponentItem) view).getInputBox().setSelection(cursorPos);
            reportStylesOfFocusedView((TextComponentItem) view);
        }
    }

    private void setActiveView(View view) {
        _activeView = view;
    }

    /**
     * adds link.
     *
     * @param text link text
     * @param url  linking url.
     */
    public void addLink(String text, String url) {
        if (_activeView instanceof TextComponentItem) {
            String link = "<a href=\"" +
                    url +
                    "\">" +
                    text +
                    "</a>";
            ((TextComponentItem) _activeView).getInputBox().append(link);
        }
    }

    /**
     * changes the current text into blockQuote.
     */
    public void changeToQuote() {
        currentInputMode = MODE_PLAIN;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setStyle(QUOTE);
            __textComponent.updateComponent(_activeView, 0, 0);
        }
        refreshViewOrder();
    }

    /**
     * change the current insert mode to Ordered List Mode.
     * Increasing numbers are used to denote each item.
     */
    public void changeToNumMode() {
        currentInputMode = MODE_NUMBERING;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setStyle(NORMAL);
            __textComponent.updateComponent(_activeView, 0, 0);
        }
        refreshViewOrder();
    }

    /**
     * change the current insert mode to UnOrdered List Mode.
     * Circular filled ic_bullets are used to denote each item.
     */
    public void changeToBulletMode() {
        currentInputMode = MODE_BULLET;
        if (_activeView instanceof TextComponentItem) {
            ((TextComponentItem) _activeView).setMode(currentInputMode);
            ComponentTag componentTag = (ComponentTag) _activeView.getTag();
            ((TextComponentModel) componentTag.getComponent()).setStyle(NORMAL);
            ((TextComponentModel) componentTag.getComponent()).setAlignment(LEFT_ALIGNMENT);
            __textComponent.updateComponent(_activeView, 0, 0);
        }
        refreshViewOrder();
    }

    /**
     * This method gets the suitable insert index using
     * `checkInvalidateAndCalculateInsertIndex()` method.
     * Prepares the ImageComponent and inserts it.
     * Since the user might need to type further, it inserts new TextComponent below
     * it.
     *
     * @param filePath uri of image to be inserted.
     */
    public void insertImage(String filePath) {
        // int insertIndex = checkInvalidateAndCalculateInsertIndex();
        if (isImageAdded)
            removeViewAt(0);

        // Image will always be added on the top
        int insertIndex = 0;
        ImageComponentItem imageComponentItem = __imageComponent.getImageComponentItem(this);

        // Prepare tag
        ImageComponentModel imageComponentModel = new ImageComponentModel();
        ComponentTag imageComponentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        imageComponentTag.setComponent(imageComponentModel);
        imageComponentItem.setTag(imageComponentTag);
        imageComponentItem.setImageInformation(filePath, serverToken, false, "");

        addView(imageComponentItem, insertIndex);
        reComputeTagsAfter(insertIndex);
        refreshViewOrder();

        isImageAdded = true;
        // Add another text component below image
        // insertIndex++;
        // currentInputMode = MODE_PLAIN;
        // addTextComponent(insertIndex);
    }

    /**
     * This method checks the current active/focused view.
     * If there is some text in it, then next insertion will take place below this
     * view.
     * Else the current focussed view will be removed and new view will inserted
     * at its position.
     *
     * @return index of next insert.
     */
    private int checkInvalidateAndCalculateInsertIndex() {
        if (_activeView == null)
            return 0;
        ComponentTag tag = (ComponentTag) _activeView.getTag();
        int activeIndex = tag.getComponentIndex();
        View view = getChildAt(activeIndex);
        //check for TextComponentItem
        if (view instanceof TextComponentItem) {
            //if active text component has some texts.
            if (((TextComponentItem) view).getInputBox().getText().length() > 0) {
                //insert below it
                return activeIndex + 1;
            } else {
                //remove current view
                removeViewAt(activeIndex);
                reComputeTagsAfter(activeIndex);
                refreshViewOrder();
                //insert at the current position.
                return activeIndex;
            }
        }
        return activeIndex + 1;
    }

    /**
     * This method gets the suitable insert index using
     * `checkInvalidateAndCalculateInsertIndex()` method.
     * Prepares the ImageComponent and inserts it.
     * loads already uploaded image and sets caption
     *
     * @param filePath uri of image to be inserted.
     */
    public void insertImage(int insertIndex, String filePath, boolean uploaded, String caption) {
        ImageComponentItem imageComponentItem = __imageComponent.getImageComponentItem(this);
        //prepare tag
        ImageComponentModel imageComponentModel = new ImageComponentModel();
        ComponentTag imageComponentTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        imageComponentTag.setComponent(imageComponentModel);
        imageComponentItem.setTag(imageComponentTag);
        imageComponentItem.setImageInformation(filePath, "", uploaded, caption);
        addView(imageComponentItem, insertIndex);
        reComputeTagsAfter(insertIndex);
    }

    /**
     * Inserts new horizontal ruler.
     */
    public void insertHorizontalDivider() {
        int insertIndex = getNextIndex();
        HorizontalDividerComponentItem horizontalDividerComponentItem = __horizontalComponent.getNewHorizontalComponentItem();
        ComponentTag _hrTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        horizontalDividerComponentItem.setTag(_hrTag);
        addView(horizontalDividerComponentItem, insertIndex);
        reComputeTagsAfter(insertIndex);
        //add another text component below image
        insertIndex++;
        currentInputMode = MODE_PLAIN;
        addTextComponent(insertIndex);
        refreshViewOrder();
    }

    /**
     * @return index next to focused view.
     */
    public int getNextIndex() {
        ComponentTag tag = (ComponentTag) _activeView.getTag();
        return tag.getComponentIndex() + 1;
    }

    /**
     * Inserts new horizontal ruler.
     * Adds new text components based on passed parameter.
     */
    public void insertHorizontalDivider(boolean insertNewTextComponentAfterThis) {
        int insertIndex = getNextIndex();
        HorizontalDividerComponentItem horizontalDividerComponentItem = __horizontalComponent.getNewHorizontalComponentItem();
        ComponentTag _hrTag = ComponentMetadataHelper.getNewComponentTag(insertIndex);
        horizontalDividerComponentItem.setTag(_hrTag);
        addView(horizontalDividerComponentItem, insertIndex);
        reComputeTagsAfter(insertIndex);
        //add another text component below image
        if (insertNewTextComponentAfterThis) {
            insertIndex++;
            currentInputMode = MODE_PLAIN;
            addTextComponent(insertIndex);
        } else {
            setFocus(horizontalDividerComponentItem);
        }
        refreshViewOrder();
    }

    @Override
    public void onImageRemove(int removeIndex) {
        if (removeIndex == 0) {
            //insert 1 text component
            removeViewAt(0);
            //addTextComponent(0);
            isImageAdded = false;
        } else {
            removeViewAt(removeIndex);
        }
        reComputeTagsAfter(removeIndex);
        refreshViewOrder();
    }

    @Override
    public void onExitFromCaptionAndInsertNewTextComponent(int currentIndex) {
        addTextComponent(currentIndex);
    }

    /**
     * @return markdown format of editor content.
     */
    public String getMarkdownContent() {
        if (markDownConverter.isDataProcessed()) {
            return markDownConverter.getMarkDown();
        } else {
            return markDownConverter.processData(this).getMarkDown();
        }
    }

    /**
     * @return List of Draft Content.
     */
    public DraftModel getDraft() {
        DraftModel newDraft = draftManager.processDraftContent(this);
        if (oldDraft != null) {
            newDraft.setDraftId(oldDraft.getDraftId());
        } else {
            newDraft.setDraftId(System.currentTimeMillis());
        }
        return newDraft;
    }

    /**
     * @return list of images inserted.
     */
    public List<String> getImageList() {
        if (markDownConverter.isDataProcessed()) {
            return markDownConverter.getImages();
        } else {
            return markDownConverter.processData(this).getImages();
        }
    }

    /**
     * setter method to subscribe for listening to focus change.
     *
     * @param editorFocusReporter callback for editor focus.
     */
    public void setEditorFocusReporter(EditorFocusReporter editorFocusReporter) {
        this.editorFocusReporter = editorFocusReporter;
    }

    public interface EditorFocusReporter {
        void onFocusedViewHas(int mode, int textComponentStyle, int textAlignment);
    }

    public interface EditorCallback {
        void onBackPressed();
    }
}
