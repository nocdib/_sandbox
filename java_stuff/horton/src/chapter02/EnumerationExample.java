package chapter02;

public enum EnumerationExample {
	READ("READ"), 
	WRITE("WRITE"), 
	MIXED("MIXED");
	
	private String value;

	EnumerationExample(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
	
}
