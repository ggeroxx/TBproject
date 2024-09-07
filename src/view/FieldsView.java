package view;

public interface FieldsView {
	String getUsername();
    String getPassword();
    void setMessageErrorUsername(String message);
    void setMessageErrorPassword(String message);
    void setTextUsername(String message);
    void setPasswordField(String message);

}
