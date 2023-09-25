package com.example.libraryapp.Librarian.ModelClass;

public class Librarian {
    private int lib_id;
    private String lib_name,lib_username;

    public Librarian(int lib_id, String lib_name) {
        this.lib_id = lib_id;
        this.lib_name = lib_name;
    }

    public Librarian() {
    }

    public int getLib_id() {
        return lib_id;
    }

    public void setLib_id(int lib_id) {
        this.lib_id = lib_id;
    }

    public String getLib_name() {
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
    }

    public String getLib_username() {
        return lib_username;
    }

    public void setLib_username(String lib_username) {
        this.lib_username = lib_username;
    }
}
