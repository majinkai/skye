package com.handu.skye.mysql;

import com.handu.skye.Span;
import com.handu.skye.Tracer;
import org.junit.Test;

import java.sql.*;

/**
 * @author Jinkai.Ma
 */
public class SkyeMySQLInterceptorTest {

    @Test
    public void test() throws SQLException, InterruptedException {
        Span root = Tracer.begin("Thinking for skye");
        root.addEvent("Start");
        Span cSpan = Tracer.begin("DBConnection");
        cSpan.addEvent("Start Connection");
        Connection connection = DriverManager.getConnection("jdbc:mysql://172.16.1.75/mdm?statementInterceptors=com.handu.skye.mysql.SkyeMySQLInterceptor", "root", "password");
        cSpan.addEvent("Connection Success");
        Tracer.commit(cSpan);
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            PreparedStatement ps = connection.prepareStatement("select * from sys_users");
            ResultSet rs = ps.executeQuery();
            rs.close();
            ps.close();
            System.out.println(System.currentTimeMillis() - start);
        }
        connection.close();
        root.addEvent("End");
        Tracer.commit(root);
        Thread.sleep(11000L);
    }

}