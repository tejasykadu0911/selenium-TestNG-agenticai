package framework.driver;

public enum BrowserType {
    CHROME, FIREFOX, EDGE, SAFARI;

    public static BrowserType fromString(String name) {
        return switch (name.trim().toLowerCase()) {
            case "firefox"          -> FIREFOX;
            case "edge"             -> EDGE;
            case "safari"           -> SAFARI;
            default                 -> CHROME;
        };
    }
}