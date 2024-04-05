import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
    public static void main(String[] args) {
        Connection con = null;
        try {
            // データベースに接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/challenge_java?useUnicode=true&characterEncoding=utf8",
                    "root",
                    "Odami-38629+");
            System.out.println("データベース接続成功：" + con);
            System.out.println("レコード追加を実行します");

            // SQLクエリを実行するためのStatementを作成
            Statement st = con.createStatement();

            // レコードを追加するSQLクエリ
            String sqlInsert = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES "
                    + "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13),"
                    + "(1002, '2023-02-08', 'お疲れ様です！', 12),"
                    + "(1003, '2023-02-09', '今日も頑張ります！', 18),"
                    + "(1001, '2023-02-09', '無理は禁物ですよ！', 17),"
                    + "(1002, '2023-02-10', '明日から連休ですね！', 20);";
            // レコード追加を実行
            int rowsInserted = st.executeUpdate(sqlInsert);
            System.out.println(rowsInserted + "件のレコードが追加されました");

            System.out.println("ユーザーIDが1002のレコードを検索しました");
            // user_idが1002の投稿を検索するSQLクエリ
            String sqlSelect = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
            ResultSet rs = st.executeQuery(sqlSelect);

            // 結果を出力
            int count = 0;
            while (rs.next()) {
                count++;
                Date postedAt = rs.getDate("posted_at");
                String postContent = rs.getString("post_content");
                int likes = rs.getInt("likes");
                System.out.println(count + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likes);
            }

            // リソースを解放
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("データベース接続失敗：" + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("データベース接続解除失敗：" + e.getMessage());
            }
        }
    }
}
