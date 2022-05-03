package com.example.qlkhov2.TemPlate;

public class DialogListItem {

    private String Id;
    private String Name;

    public DialogListItem(String id, String name) {
        Id = id;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
