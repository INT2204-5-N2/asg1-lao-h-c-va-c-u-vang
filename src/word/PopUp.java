package word;

import javafx.scene.control.Alert;

public class PopUp  {
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void popMessage(){
        alert.setTitle("Da co san");
        alert.setHeaderText(null);
        alert.setContentText("Tu nay da co san");

        alert.showAndWait();
    }

    public void popReplaceMessage(){
        alert.setTitle("Chua chon tu");
        alert.setHeaderText(null);
        alert.setContentText("Vui long chon tu can sua");

        alert.showAndWait();
    }

    public void popDeleteMessage(){

        alert.setTitle("Chua chon tu");
        alert.setHeaderText(null);
        alert.setContentText("Vui long chon tu can xoa");

        alert.showAndWait();
    }

    public void popAddMessage(){

        alert.setTitle("Chua chon tu");
        alert.setHeaderText(null);
        alert.setContentText("Vui long dien day du thong tin");

        alert.showAndWait();
    }

}