import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/challenge_java",
                    "root",
                    "Odami-38629+");
            System.out.println("データベース接続成功：" + con);

            // SQLクエリを準備
            stmt = con.createStatement();

            // 'name'カラムが'武者小路勇気'のレコードを更新
            int updated = stmt
                    .executeUpdate("UPDATE scores SET score_math = 95, score_english = 80 WHERE name = '武者小路勇気'");
            System.out.println("レコード更新を実行します");
            System.out.println(updated + "件のレコードが更新されました");
            System.out.println("数学・英語の点数が高い順に並べ替えました");

            // 点数順に並べる
            rs = stmt.executeQuery("SELECT * FROM scores ORDER BY score_math DESC, score_english DESC");

            // 並べ替え結果を表示
            int count = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int scoreMath = rs.getInt("score_math");
                int scoreEnglish = rs.getInt("score_english");
                System.out.println(
                        ++count + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + scoreMath + "／英語=" + scoreEnglish);
            }

        } catch (SQLException e) {
            System.out.println("データベース接続失敗：" + e.getMessage());
        } finally {
            // リソースの解放
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}