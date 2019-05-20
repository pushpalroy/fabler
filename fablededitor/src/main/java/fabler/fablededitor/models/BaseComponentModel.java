package fabler.fablededitor.models;

import androidx.annotation.NonNull;

public class BaseComponentModel {
    private String componentType;
    private int componentIndex;

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    @NonNull
    @Override
    public String toString() {
        return "BaseComponentModel{" +
                "componentType='" + componentType + '\'' +
                ", componentIndex=" + componentIndex +
                '}';
    }
}
