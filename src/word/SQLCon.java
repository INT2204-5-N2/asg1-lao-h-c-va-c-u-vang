package word;

import java.sql.*;

class SQLCon {
    private final String urlCon = "jdbc:sqlite:src/word/dictionaries.db";
    private  final Connection conn = DriverManager.getConnection(urlCon);

    SQLCon() throws SQLException {
    }

    void addToSQL(String word, String wordMean) throws SQLException {
        String url = "INSERT INTO tbl_edict (idx, word, detail) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(url);
        statement.setInt(1, DictionaryFeatures.lines+1);
        statement.setString(2,word );
        statement.setString(3, wordMean);
        statement.executeUpdate();
    }

    void removeFromSQL(String deleteWord) throws SQLException {
        String url = "DELETE FROM tbl_edict WHERE word=?";
        PreparedStatement statement = conn.prepareStatement(url);
        statement.setString(1,deleteWord);
        statement.executeUpdate();
    }

    String getMeanFromSQL(String getWord) throws SQLException {
        String url = "SELECT word,detail from tbl_edict";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(url);
        while(rs.next()){
            String word = rs.getString("word");
            if(getWord.equals(word)){
                return rs.getString("detail");
            }
        }
        return null;
    }

    void fixFromSQL(String word, String fixMean) throws SQLException {
        String url = "UPDATE tbl_edict SET detail=? WHERE word=?";
        PreparedStatement statement = conn.prepareStatement(url);
        statement.setString(1, fixMean);
        statement.setString(2,word);
        statement.executeUpdate();
    }

}
