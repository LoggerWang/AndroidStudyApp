package com.legend.androidstudyapp.rxjava.model;

/**
 * date：2021/10/28 11:23
 *
 * @author wanglezhi
 * desc:
 */
public class NewsBeesBaseData<T> {
    private T data;
    private int result_code;
    private String message;
    private long timestamp;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
