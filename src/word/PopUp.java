package word;

import javafx.scene.control.Alert;

class PopUp  {
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    void popMessage(){
        alert.setTitle("Da co san");
        alert.setHeaderText(null);
        alert.setContentText("Tu nay da co san");

        alert.showAndWait();
    }

    void popReplaceMessage(){
        alert.setTitle("Chua chon tu");
        alert.setHeaderText(null);
        alert.setContentText("Vui long chon tu can sua");

        alert.showAndWait();
    }

    void popDeleteMessage(){

        alert.setTitle("Chua chon tu");
        alert.setHeaderText(null);
        alert.setContentText("Vui long chon tu can xoa");

        alert.showAndWait();
    }

    void popAddMessage(){

        alert.setTitle("Chua chon tu");
        alert.setHeaderText(null);
        alert.setContentText("Vui long dien day du thong tin");

        alert.showAndWait();
    }

}