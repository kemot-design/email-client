package pl.kemot.view;

public enum FontSize {
    SMALL,
    MEDIUM,
    BIG;

    public static String getCSSPath(FontSize fontSize){
        switch (fontSize) {
            case SMALL:
                return "css/fontSmall.css";
            case MEDIUM:
                return "css/fontMedium.css";
            case BIG:
                return "css/fontBig.css";
            default:
                return null;
        }
    }
}
