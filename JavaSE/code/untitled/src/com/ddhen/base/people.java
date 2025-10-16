package com.ddhen.base;

public class people {
    private int height;

    public people() {
    }

    public people(int height) {
        this.height = height;
    }

    /**
     * 获取
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 设置
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public String toString() {
        return "people{height = " + height + "}";
    }
}
