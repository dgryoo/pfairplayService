package project.pfairplay.batch;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

public class BatchServerApplication {

    public static void main(String[] args) {

        // Redis environment
        String host = "127.0.0.1";
        int port = 6379;
        int timeout = 3000;
        String redisPassword = null;
        int db = 0;

        // Mysql environment
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
        String sql = "select * from team_review_counter";
        String user = "root";
        String mysqlPassword = "dg9585";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // KeyType
        String upKeyType = "Up";
        String downKeyType = "Down";

        // Redis connect
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        JedisPool pool = new JedisPool(jedisPoolConfig,
                host,
                port,
                timeout,
                redisPassword,
                db);

        Jedis jedis = pool.getResource();

        // To Mysql
//
        try {

            conn = DriverManager.getConnection(url,
                    user,
                    mysqlPassword);

            stmt = conn.createStatement();

            work(jedis, stmt, rs, upKeyType);
            work(jedis, stmt, rs, downKeyType);


            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (jedis != null) {
            jedis.close();
        }


    }

    public static void work(Jedis jedis, Statement stmt, ResultSet rs, String keyType) throws SQLException {


        // Get TeamReviewUp keys
        Set<String> teamReviewUpCounterKeySet = jedis.keys("*" + keyType);

        Iterator<String> teamReviewCounterIter = teamReviewUpCounterKeySet.iterator();

        // work 수행
        while (teamReviewCounterIter.hasNext()) {
            String recentUpKey = teamReviewCounterIter.next();

            String reviewId = recentUpKey.substring(0, 36);

            int thumbsCount = Integer.parseInt(jedis.get(recentUpKey));

            // reviewId에 해당하는 레코드가 있는지 확인

            String recentSelectSql = "select * from team_review_counter where review_id = " + "'" + reviewId + "'";

            rs = stmt.executeQuery(recentSelectSql);

            // 레코드가 있을때 update 없을때 insert

            if (rs.next()) {
                stmt.executeUpdate(getUpdateQuery(reviewId, thumbsCount, keyType));
            } else {
                stmt.executeUpdate(getInsertQuery(reviewId,thumbsCount,keyType));
            }

        }

    }

    public static String getUpdateQuery(String reviewId, int thumbsCount, String keyType) {

        String colName = null;

        if(keyType.equals("Up")) {
            colName = "thumbs_up_count";
        } else if (keyType.equals("Down")) {
            colName = "thumbs_down_count";
        }
        return "update team_review_counter set " + colName + " = " + thumbsCount + " where review_id = " + "'" + reviewId + "'";
    }

    public static String getInsertQuery(String reviewId, int thumbsCount, String keyType) {

        String insertQuery = null;

        if(keyType.equals("Up")) {
            insertQuery =  "insert into team_review_counter values (" + "'" + reviewId + "', " + thumbsCount + ", 0)";
        } else if(keyType.equals("Down")) {
            insertQuery = "insert into team_review_counter values (" + "'" + reviewId + "', 0, " + thumbsCount + ")";
        }
        return insertQuery;
    }


}


