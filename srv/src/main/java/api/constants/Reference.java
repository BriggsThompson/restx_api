package api.constants;


import java.util.HashMap;
import java.util.Map;

public final class Reference {
    public static final String FACEBOOK_SESSION = "facebookSession";
    public static final Map<String, String> validContentTypes;
    static {
        validContentTypes = new HashMap<>();
        validContentTypes.put("image/jpeg", ".jpg");
        validContentTypes.put("image/png", ".png");
    }

}
