package com.medyatakip.app.model;

import java.util.ArrayList;

public class VerticalModel {

    private ArrayList<String> fullImages;
    private ArrayList<String> clipImages;

    public VerticalModel(ArrayList<String> fullImages, ArrayList<String> clipImages) {
        this.fullImages = fullImages;
        this.clipImages = clipImages;
    }

    public ArrayList<String> getFullImages() {
        return fullImages;
    }

    public ArrayList<String> getClipImages() {
        return clipImages;
    }

    //    public VerticalModel(ArrayList<String> horizontalImages, ArrayList<VerticalImages> verticalImages) {
//        this.horizontalImages = horizontalImages;
//        this.verticalImages = verticalImages;
//    }
//
//    public class VerticalImages {
//        ArrayList<Integer> verticalImages;
//
//        public VerticalImages(ArrayList<Integer> verticalImages) {
//            this.verticalImages = verticalImages;
//        }
//
//        public ArrayList<Integer> getVerticalImages() {
//            return verticalImages;
//        }
//    }
}
