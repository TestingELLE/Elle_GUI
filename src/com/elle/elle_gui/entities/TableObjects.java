/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elle.elle_gui.entities;

/**
 *
 * @author ren
 */
public class TableObjects {
    private int id;
    private String name;
    private String type;
    private String usage;
    private String description;
    private String author;
    
    public TableObjects(){
        
    }
    
    public TableObjects(int id, String name, String type, String usage, String description, String author) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.usage = usage;
        this.description = description;
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    
    
}
