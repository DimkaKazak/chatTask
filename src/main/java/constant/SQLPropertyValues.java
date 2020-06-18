package constant;

public enum SQLPropertyValues {

    USERNAME("username"),
    PASSWORD("password"),
    URL("url"),
    DRIVER("driver");

    private String propertyName;

    SQLPropertyValues(String propertyName){
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
