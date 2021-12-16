package com.legend.androidstudyapp.rxjava.model;

import java.util.ArrayList;

public class ResponseNewsModels {
    private ArrayList<NewsModel> source;
    private boolean hasNext;

    public ArrayList<NewsModel> getNewsModels() {
        return source;
    }

    public void setNewsModels(ArrayList<NewsModel> newsModels) {
        this.source = newsModels;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

}
