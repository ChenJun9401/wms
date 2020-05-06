package cn.wolfcode.wms.exception;

//自定义的安全检查异常
public class SecurityException extends RuntimeException {
    public SecurityException() {
        super();
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }
}
