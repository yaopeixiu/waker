package com.spring.cloud.exption;

public class ParamException extends IllegalArgumentException {

    private static final long serialVersionUID = 7814085077687522073L;

    private String code;
    private String retMsg;
    private static final String identify = "{}";

    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public static ParamException error(String message,Object... va2){
        int index,start = 0;
        StringBuilder builder = new StringBuilder();
        for (Object o : va2){
            index = message.indexOf(identify);
            builder.append(message, start, index);
            builder.append(o);
            message = message.substring(index+identify.length());
        }
        builder.append(message);
        return new ParamException(builder.toString());
    }

    public ParamException(String code, String retMsg) {
        this.code = code;
        this.retMsg = retMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
