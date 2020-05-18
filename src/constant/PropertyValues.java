package constant;

public enum PropertyValues {

    HOST("HOST"),
    PORT("PORT");

    private String propertyName;

    PropertyValues(String propertyName){
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
