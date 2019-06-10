package com.asion.common.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 基础单元测试类
 * 
 * @author chenboyang
 *
 */
@Deprecated
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-beans.xml" })
public abstract class BaseTest {

}
