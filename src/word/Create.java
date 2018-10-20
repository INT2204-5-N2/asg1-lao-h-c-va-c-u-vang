package word;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Create {

    ObservableList<String> oListStavaka = FXCollections.observableArrayList();
    ObservableList<String> oListStavaka1 = FXCollections.observableArrayList();
    JFXListView<String> jfxListView;

    public Create() {
        jfxListView = new JFXListView<>();
    }
}
