package com.young.http;

public class HttpResult <T> extends BaseDataModel{

    public T data;
    public int code = -1;
    public String msg;
    public String text;

}
