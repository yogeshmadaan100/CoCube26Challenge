package com.cube26.cube26.models;

import java.io.Serializable;

/**
 * Created by yogeshmadaan on 13/03/16.
 */
public enum SortCriteria implements Serializable {
    NAME("name"),RATINGS("ratings"),SETUPFEE("setupFee"),FAVOURITES("favourites");
    public final String str;
    SortCriteria(String str) {
        this.str = str;
    }
    public int getId() {
        return this.str.hashCode();
    }
    public String toString() {
        return this.str;
    }
}
