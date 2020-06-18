package constant;

public enum ChatPropertyValues {

    HOST("HOST"),
    PORT("PORT");

    private String propertyName;

    ChatPropertyValues(String propertyName){
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
