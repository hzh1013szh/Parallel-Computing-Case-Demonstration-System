package com.hwk.springboot.common;
// 前后端通用的返回数据格式
public class Result {
    private static final String SUCCESS = "0";
    private static final String ERROR = "-1";

    private String code;// 状态码
    private String msg; //报错信息
    private Object data;// 数据

    public static Result success(){
        Result result = new Result();
        result.setCode(SUCCESS);
        return result;
    }
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setData(data);
        return result;
    }
    public static Result error(String msg){
        Result result = new Result();
        result.setCode(ERROR);
        result.setMsg(msg);
        return result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
