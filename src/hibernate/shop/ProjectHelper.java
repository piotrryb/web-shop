package hibernate.shop;

import java.math.BigDecimal;

public class ProjectHelper {

    public static Long parseStringToLong(String productId) {
        try {
            return Long.valueOf(productId);
        } catch (NumberFormatException nbf) {
            return 0L;
        }
    }

    public static BigDecimal parseStringToBigDecimal(String productAmount) {
        try {
            return new BigDecimal(productAmount);
        } catch (NumberFormatException nbf) {
            return BigDecimal.ZERO;
        }
    }
}
