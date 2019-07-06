package fabler.fablededitor;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import fabler.fablededitor.formatbar.components.text.TextComponentItem;
import fabler.fablededitor.models.BulletGroupModel;

import static fabler.fablededitor.formatbar.components.text.TextComponentItem.MODE_NUMBERING;

public class FabledCore extends LinearLayout {
    ArrayList<BulletGroupModel> bulletGroupModels;

    public FabledCore(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        bulletGroupModels = new ArrayList<>();
    }

    /**
     * Creates bullet groups and invalidate the view.
     */
    protected void refreshViewOrder() {
        makeBulletGroups();
        invalidateComponentMode(bulletGroupModels);
    }


    /**
     * This method find the group of ic_bullets.
     * There can be 2 type of group.
     * {UL,StartIndex=0,EndIndex=3}
     * and
     * {OL,StartIndex=5,EndIndex=9}
     * <p>
     * These group are useful for maintaining correct order
     * even when view are inserted and deleted in any way.
     */
    private void makeBulletGroups() {
        bulletGroupModels.clear();
        int startIndex;
        int endIndex = -1;
        View child;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            //skip non-text component items
            if ((child instanceof TextComponentItem)) {
                if (((TextComponentItem) child).getMode() == MODE_NUMBERING) {
                    startIndex = i;
                    //search end of this group
                    for (int j = i; j < childCount; j++) {
                        i = j;
                        child = getChildAt(j);
                        if (child instanceof TextComponentItem) {
                            if (((TextComponentItem) child).getMode() == MODE_NUMBERING) {
                                endIndex = i;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    //prepare model and add
                    BulletGroupModel groupModel = new BulletGroupModel();
                    groupModel.setOrderType(MODE_NUMBERING);
                    groupModel.setStartIndex(startIndex);
                    groupModel.setEndIndex(endIndex);
                    bulletGroupModels.add(groupModel);
                }
            }
        }
    }

    /**
     * Helper method to update the ic_bullets.
     * If view are inserted/removed, ic_bullets are reassigned to view,
     * so we need to update the view.
     *
     * @param bulletGroupModels list of groups of ic_bullets.
     */
    private void invalidateComponentMode(ArrayList<BulletGroupModel> bulletGroupModels) {
        int ot;
        int si;
        int ei;
        TextComponentItem _tempChild;
        //loop through each group
        for (int i = 0; i < bulletGroupModels.size(); i++) {
            ot = bulletGroupModels.get(i).getOrderType();
            si = bulletGroupModels.get(i).getStartIndex();
            ei = bulletGroupModels.get(i).getEndIndex();
            if (ot == MODE_NUMBERING) {
                //set ol mode
                int ci = 1;
                for (int j = si; j <= ei; j++) {
                    try {
                        _tempChild = (TextComponentItem) getChildAt(j);
                        _tempChild.setMode(MODE_NUMBERING);
                        _tempChild.setIndicator(ci + ".");
                        ci++;
                    } catch (Exception e) {
                        Log.d("EditorCore", "pos " + j);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
