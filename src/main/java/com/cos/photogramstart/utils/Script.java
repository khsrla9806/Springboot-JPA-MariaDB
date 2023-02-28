package com.cos.photogramstart.utils;

// 에러메시지를 알림창에 띄워주고, 원래 있던 곳으로 돌아가게 해주는 역할을 하는 Javascript 코드를 작성
public class Script {

    public static String back(String message) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('" + message +"');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}
