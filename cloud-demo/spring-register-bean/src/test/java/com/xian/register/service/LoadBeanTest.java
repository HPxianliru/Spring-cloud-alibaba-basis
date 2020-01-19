package com.xian.register.service;

import com.xian.cloud.dto.DeptDTO;
import com.xian.register.SpringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* LoadBean Tester.
*
* @author <Authors xianliru>
* @since <pre>01/19/2020</pre>
* @version 1.0
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoadBeanTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testPostProcessBeanFactory() throws Exception {
        DeptDTO deptDTO = SpringUtils.getBean(DeptDTO.class);

        System.out.println(deptDTO);
    }


}
