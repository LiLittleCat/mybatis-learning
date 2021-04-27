package com.lilittlecat.mybatis.learning;

import org.apache.ibatis.executor.*;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Executor 测试类.
 *
 * @author LiLittleCat
 * @since 2021/4/27
 */
public class ExecutorTest {
    private Configuration configuration;
    private Connection connection;
    private JdbcTransaction jdbcTransaction;
    private MappedStatement ms;
    private SqlSessionFactory factory;

    @BeforeEach
    public void init() throws SQLException {
        // 获取构建器
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        // 解析XML 并构造会话工厂
        factory = factoryBuilder.build(ExecutorTest.class.getResourceAsStream("/mybatis-config.xml"));
        configuration = factory.getConfiguration();
        jdbcTransaction = new JdbcTransaction(factory.openSession().getConnection());
        // 获取SQL映射
        ms = configuration.getMappedStatement("com.lilittlecat.mybatis.learning.dao.UserMapper.selectById");
    }

    // 简单执行器测试
    @Test
    public void simpleTest() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        List<Object> list = executor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));

        executor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
        System.out.println(list.get(0));
    }

    // 重用执行器
    @Test
    public void ReuseTest() throws SQLException {
        ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);
        List<Object> list = executor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
        //  相同的SQL 会缓存对应的 PrepareStatement-->缓存周期：会话
        executor.doQuery(ms, 10, RowBounds.DEFAULT,
                SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
        System.out.println(list.get(0));
    }

    // 批处理执行器
    @Test
    public void BatchTest() throws SQLException {
        BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement setName = configuration
                .getMappedStatement("com.lilittlecat.mybatis.learning.dao.UserMapper.setName");
        Map param = new HashMap<>();
        param.put("arg0", 10);
        param.put("arg1", "he is good man");
        executor.doUpdate(setName, param); //设置
        executor.doUpdate(setName, param);// 设置
        executor.doFlushStatements(false);
    }


    @Test
    public void cacheExecutorTest() throws SQLException {
        // BaseExecutor
        Executor executor = new SimpleExecutor(configuration, jdbcTransaction);
        // 装饰器模式
        Executor cachingExecutor = new CachingExecutor(executor);// 二级缓存相关逻辑 执行数据操作逻辑

        cachingExecutor.query(ms, 10, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
        cachingExecutor.commit(true);
        //  提交之后才会更新
        cachingExecutor.query(ms, 10, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);

    }

    @Test
    public void sessionTest() {
        SqlSession sqlSession = factory.openSession(ExecutorType.REUSE, true);
        // 降低调用复杂性
        List<Object> list = sqlSession.selectList("com.lilittlecat.mybatis.learning.dao.UserMapper.selectById", 10);
        System.out.println(list.get(0));
    }
}
