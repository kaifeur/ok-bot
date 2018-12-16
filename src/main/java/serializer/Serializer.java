package serializer;

import com.google.gson.Gson;

public class Serializer {
    private static Gson ourInstance = new Gson();

    public static Gson getInstance() {
        return ourInstance;
    }

    private Serializer() {
    }
}
