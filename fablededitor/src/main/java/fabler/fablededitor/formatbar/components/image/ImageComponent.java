package fabler.fablededitor.formatbar.components.image;

import android.content.Context;

public class ImageComponent {
    private Context context;

    public ImageComponent(Context context) {
        this.context = context;
    }

    public ImageComponentItem getImageComponentItem(ImageComponentItem.ImageComponentListener imageRemoveListener) {
        ImageComponentItem imageComponentItem = new ImageComponentItem(context);
        imageComponentItem.setImageComponentListener(imageRemoveListener);
        return imageComponentItem;
    }
}
