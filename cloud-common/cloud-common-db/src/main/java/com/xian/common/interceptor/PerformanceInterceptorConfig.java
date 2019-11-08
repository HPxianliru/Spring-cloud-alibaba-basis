package com.xian.common.interceptor;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: xlr
 * @Date: 2019/1/21 13:58
 * @Description:
 */
@SuppressWarnings("ALL")
@Slf4j
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "query",
        args = {Statement.class, ResultHandler.class}
), @Signature(
        type = StatementHandler.class,
        method = "update",
        args = {Statement.class}
), @Signature(
        type = StatementHandler.class,
        method = "batch",
        args = {Statement.class}
)})
public class PerformanceInterceptorConfig extends PerformanceInterceptor {

    private static Logger log = LoggerFactory.getLogger(PerformanceInterceptorConfig.class);

    private static Map<String, Class> classCache =new ConcurrentHashMap<>();

    private static final String DruidPooledPreparedStatement = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4CPreparedStatement = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String OraclePreparedStatementWrapper = "oracle.jdbc.driver.OraclePreparedStatementWrapper";
    private static final String HikariPreparedStatementWrapper = "com.zaxxer.hikari.pool.HikariProxyPreparedStatement";
    private boolean format = false;
    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSQLMethod;

    private Long timeWran = 50L;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object firstArg = invocation.getArgs()[0];
        Statement statement;
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }

        try {
            statement = (Statement) SystemMetaObject.forObject(statement).getValue("stmt.statement");
        } catch (Exception var19) {
            ;
        }

        String originalSql = null;
        String stmtClassName = statement.getClass().getName();
        Class clazz;
        Object stmtSql;
        if (DruidPooledPreparedStatement.equals(stmtClassName)) {
            try {
                if (this.druidGetSQLMethod == null) {
                    clazz = classCache.get(DruidPooledPreparedStatement);
                    if(clazz == null){
                        clazz = Class.forName(DruidPooledPreparedStatement);
                        classCache.put(DruidPooledPreparedStatement,clazz);
                    }
                    this.druidGetSQLMethod = clazz.getMethod("getSql");
                }

                stmtSql = this.druidGetSQLMethod.invoke(statement);
                if (stmtSql != null && stmtSql instanceof String) {
                    originalSql = (String) stmtSql;
                }
            } catch (Exception var18) {
                ;
            }
        } else if (!T4CPreparedStatement.equals(stmtClassName) && !OraclePreparedStatementWrapper.equals(stmtClassName)) {
            if (HikariPreparedStatementWrapper.equals(stmtClassName)) {
                try {
                    stmtSql = SystemMetaObject.forObject(statement).getValue("delegate.sqlStatement");
                    if (stmtSql != null) {
                        originalSql = stmtSql.toString();
                    }
                } catch (Exception var16) {
                    ;
                }
            }
        } else {
            try {
                if (oracleGetOriginalSqlMethod != null) {
                    stmtSql = this.oracleGetOriginalSqlMethod.invoke(statement);
                    if (stmtSql != null && stmtSql instanceof String) {
                        originalSql = (String) stmtSql;
                    }
                } else {
                    clazz = classCache.get(stmtClassName);
                    if(clazz == null ){
                        clazz = Class.forName(stmtClassName);
                        classCache.put(stmtClassName,clazz);
                    }
                    this.oracleGetOriginalSqlMethod = this.getMethodRegular(clazz, "getOriginalSql");
                    if (this.oracleGetOriginalSqlMethod != null) {
                        this.oracleGetOriginalSqlMethod.setAccessible(true);
                        if (this.oracleGetOriginalSqlMethod != null) {
                            stmtSql = this.oracleGetOriginalSqlMethod.invoke(statement);
                            if (stmtSql != null && stmtSql instanceof String) {
                                originalSql = (String) stmtSql;
                            }
                        }
                    }
                }
            } catch (Exception var17) {
                ;
            }
        }

        if (originalSql == null) {
            originalSql = statement.toString();
        }

        int index = originalSql.indexOf(58);
        if (index > 0) {
            originalSql = originalSql.substring(index + 1, originalSql.length());
        }
        //记录开始时间
        long start = SystemClock.now();
        //反射调用开始
        Object result = invocation.proceed();
        //结束时间
        long timing = SystemClock.now() - start;

        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        StringBuilder formatSql = new StringBuilder();
        formatSql.append(" Time：").append(timing);
        formatSql.append(" ms - ID：").append(ms.getId());
        formatSql.append("\n Execute SQL：").append(SqlUtils.sqlFormat(originalSql, this.format)).append("\n");
        if (this.isWriteInLog()) {
            if (this.getMaxTime() >= 1L && timing > this.getMaxTime()) {
                log.error(formatSql.toString());
            } else {
                log.info(formatSql.toString());
            }
        } else {
            log.info(formatSql.toString());
            if (this.getMaxTime() >= 1L && timing > this.getMaxTime()) {
                throw new MybatisPlusException(" The SQL execution time is too large, please optimize ! ");
            }
        }
        log.info(formatSql.toString());
        if(timeWran < timing){
            log.warn(" sql 超时  "+"\n"+"阈值 "+timeWran+"毫秒\n"+formatSql.toString());
        }
        return result;
    }

    @PreDestroy
    public void destroy(){
        log.info("mybatis-plus 性能监控插件，缓存clazz对象清空");
        classCache.clear();
    }
}
