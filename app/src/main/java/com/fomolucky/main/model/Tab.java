package com.fomolucky.main.model;


public class Tab {
    private String name;
    private String type;
    private int icon_selected;
    private int icon_unselected;
    private boolean isSelected;

    public Tab(String name, String type, int icon_selected, int icon_unselected, boolean isSelected) {
        this.name = name;
        this.type = type;
        this.icon_selected = icon_selected;
        this.icon_unselected = icon_unselected;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getIcon_selected() {
        return icon_selected;
    }

    public int getIcon_unselected() {
        return icon_unselected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
