package word;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;

import java.sql.*;

class Create {

    ObservableList<String> oListStavaka = FXCollections.observableArrayList();
    ObservableList<String> oListStavaka1 = FXCollections.observableArrayList();
    JFXListView<String> jfxListView;
    private static final String urlCon = "jdbc:sqlite:src/word/dictionaries.db";

    Create() throws SQLException {
        jfxListView = new JFXListView<>();
        jfxListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String url = "SELECT word,detail from tbl_edict ";
        Connection conn = DriverManager.getConnection(urlCon);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(url);
        while(rs.next()){
            oListStavaka.add(rs.getString("word"));
            oListStavaka1.add(rs.getString("detail"));
            DictionaryFeatures.lines++;
        }
        jfxListView.setItems(oListStavaka);
    }
}
