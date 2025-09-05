package ua.hudyma.enums;

public enum Country {
    UNITED_STATES("United States", "US"),
    UNITED_KINGDOM("United Kingdom", "GB"),
    UKRAINE("Ukraine", "UA"),
    CANADA("Canada", "CA"),
    GERMANY("Germany", "DE"),
    FRANCE("France", "FR"),
    JAPAN("Japan", "JP"),
    CHINA("China", "CN"),
    INDIA("India", "IN"),
    AUSTRALIA("Australia", "AU");

    private final String fullName;
    private final String isoCode;

    Country(String fullName, String isoCode) {
        this.fullName = fullName;
        this.isoCode = isoCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public static Country fromIsoCode(String code) {
        for (Country country : values()) {
            if (country.getIsoCode().equalsIgnoreCase(code)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid ISO code: " + code);
    }
}

