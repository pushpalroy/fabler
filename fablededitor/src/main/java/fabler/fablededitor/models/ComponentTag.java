package fabler.fablededitor.models;

import androidx.annotation.NonNull;

public class ComponentTag {
    private int componentIndex;
    private BaseComponentModel baseComponent;

    public BaseComponentModel getComponent() {
        return baseComponent;
    }

    public void setComponent(BaseComponentModel baseComponent) {
        this.baseComponent = baseComponent;
    }

    public int getComponentIndex() {
        return componentIndex;
    }

    public void setComponentIndex(int componentIndex) {
        this.componentIndex = componentIndex;
    }

    @NonNull
    @Override
    public String toString() {
        return "ComponentTag{" +
                "componentIndex=" + componentIndex +
                ", baseComponent=" + baseComponent +
                '}';
    }
}
