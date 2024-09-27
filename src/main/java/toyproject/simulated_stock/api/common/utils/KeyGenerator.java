package toyproject.simulated_stock.api.common.utils;

import java.util.UUID;

public final class KeyGenerator {
    public static String generateKey(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
