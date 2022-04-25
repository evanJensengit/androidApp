package com.example.fragmentstest2;

import com.google.gson.annotations.SerializedName;

public class GsonParser {

    @SerializedName("classPath")
    private String classPath;

    @SerializedName("layoutResource")
    private String layoutResource;

    @SerializedName("enterAnim")
    private String enterAnim;

    @SerializedName("exitAnim")
    private String exitAnim;

    public String getExitAnim() {
        return exitAnim;
    }

    public String getEnterAnim() {
        return enterAnim;
    }

    public String getLayoutResource() {
        return layoutResource;
    }

    public String getClassPath() {
        return classPath;
    }

}
