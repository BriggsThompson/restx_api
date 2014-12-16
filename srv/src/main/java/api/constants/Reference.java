package api.constants;


import java.util.HashMap;
import java.util.Map;

public final class Reference {
    public static final String FACEBOOK_SESSION = "facebookSession";
    public static final String IMAGE_PATH = "rentsy-images.s3.amazonaws.com/";
    public static final Map<String, String> validContentTypes;
    static {
        validContentTypes = new HashMap<>();
        validContentTypes.put("image/jpeg", ".jpg");
        validContentTypes.put("image/png", ".png");
    }

}
