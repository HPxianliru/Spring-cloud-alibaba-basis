package com.xian.cloud.user.fegin;

import com.xian.cloud.api.UserServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <Description>
 *
 * @author xianliru@163.com
 * @version 1.0
 * @createDate 2019/11/21 14:50
 */
@FeignClient(value = "cloud-user-center")
public interface UserService extends UserServiceApi {
}
