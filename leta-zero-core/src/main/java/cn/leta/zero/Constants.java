package cn.leta.zero;

/**
 * Created by xiegengcai on 17-10-16.
 * @author Xie Gengcai
 */
public interface Constants {
    public enum ErrorCode {
        UNKNOW_ERROR(10000, "未知错误")
        , SUCCESS(0,"SUCCESS")
        , SERVICE_ERROR(10001, "方法或版本错误")
        , OBSOLETED_METHOD(10002, "方法已过期")
        , BODY_FORMAT_ERROR(10003, "包体格式错误")
        ;

        private int code;
        private String message;

        ErrorCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
