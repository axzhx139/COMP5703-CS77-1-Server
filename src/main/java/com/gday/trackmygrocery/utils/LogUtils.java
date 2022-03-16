package com.gday.trackmygrocery.utils;

import java.util.List;

public class LogUtils<E> {

    private volatile static LogUtils logUtils;
    private LogUtils(){}
    public static LogUtils getInstance() {
        if (logUtils == null) {
            synchronized (LogUtils.class) {
                if (logUtils == null) {
                    logUtils = new LogUtils();
                }
            }
        }
        return logUtils;
    }

    public String printListAsLog(List<E> list) {

        StringBuilder sb = new StringBuilder();
        for (E o : list) {
            sb.append(o.toString());
        }
        return sb.toString();

    }



}
