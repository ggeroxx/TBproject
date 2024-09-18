package controller.ClientServer;

import java.io.Serializable;

public class SomeResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private boolean success;

    public SomeResponse(String message, boolean success) {
        this.message = message;
        this.success = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess()
    {
        return this.success;
    }
   

    @Override
    public String toString() {
        return "SomeResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
