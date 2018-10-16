package word;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Create {
    ListView<String> listView;
    ObservableList<String> oListStavaka = FXCollections.observableArrayList();
    ObservableList<String> oListStavaka1 = FXCollections.observableArrayList();

    public Create() {
        listView = new ListView<>();
    }
}
