

package com.xian.common.utils.validator;


/**
 * 数据校验
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringHelper.isBlank(str)) {
            throw new RuntimeException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }
}
