package com.example.linsawako.scau.bean;

import java.io.Serializable;

/**
 * Created by linsawako on 2017/2/9.
 */

public class TimeTableBean implements Serializable {
    /**
     * 课程名
     */
    private String cName;
    /**第几节开始
     *
     */
    private int startNum;
    /**
     * 第几节结束
     */
    private int endNum;
    /**
     * 星期几
     */
    private String day;
    /**
     * 哪间教室
     */
    private String classroom;

    public TimeTableBean() {
    }

    public TimeTableBean(String cName, int startNum, int endNum, String day, String classroom) {
        this.cName = cName;
        this.startNum = startNum;
        this.endNum = endNum;
        this.day = day;
        this.classroom = classroom;
    }


    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
