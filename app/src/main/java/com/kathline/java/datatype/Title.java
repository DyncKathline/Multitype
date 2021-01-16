package com.kathline.java.datatype;

import java.io.Serializable;

public class Title implements Serializable {
    String mainTitle = "";
    String subTitle = "";

    public Title(String mainTitle, String subTitle) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
