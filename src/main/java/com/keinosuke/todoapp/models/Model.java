package com.keinosuke.todoapp.models;

import com.keinosuke.todoapp.views.ViewFactory;

public class Model {
    private static Model model;

    private final ViewFactory viewFactory;

    private Model() {
        this.viewFactory = new ViewFactory();
    }

    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory(){
        return viewFactory;
    }
}
