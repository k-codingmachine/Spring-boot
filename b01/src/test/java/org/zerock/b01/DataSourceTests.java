package org.zerock.b01;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Log4j2
public class DataSourceTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws SQLException{

        @Cleanup // 테스트가 끝나면 db연결을 끊어버림
        Connection con = dataSource.getConnection();

        log.info(con);

        Assertions.assertNotNull(con);
    }
}
