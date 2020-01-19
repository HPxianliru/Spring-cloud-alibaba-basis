package com.xian.cloud.utils;

import com.xian.common.zk.service.ZkApi;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
* SpringBeanContextUtil Tester.
*
* @author <Authors xianliru>
* @since <pre>01/17/2020</pre>
* @version 1.0
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBeanContextUtilTest {

    @Autowired
    private ZkApi zkApi;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

        /**
    *
    * Method: setApplicationContext(ApplicationContext applicationContext)
    *
    */
    @Test
    public void testSetApplicationContext() throws Exception {
        boolean flag = zkApi.createNode("/node_test", "test");
        System.out.printf(flag+"");

    }

        /**
    *
    * Method: getBean(String bean)
    *
    */
    @Test
    public void testGetBeanBean() throws Exception {
    //TODO: Test goes here...
    }

        /**
    *
    * Method: getBean(Class clazz)
    *
    */
    @Test
    public void testGetBeanClazz() throws Exception {
    //TODO: Test goes here...
    }


        }
