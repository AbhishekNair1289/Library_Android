package com.example.libraryapp.Student.Modelclass;

public class CategoryDetail {
    String id,name;

    public CategoryDetail(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
