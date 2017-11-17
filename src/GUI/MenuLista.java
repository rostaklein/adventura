/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import main.Main;
/**
 *
 * @author rostaklein
 */
public class MenuLista extends MenuBar{
    
    private IHra hra;
    private Main main;
    
    public MenuLista(IHra hra, Main main){
        this.hra=hra;
        this.main=main;
        init();
    }
    
    private void init(){
        Menu novySoubor = new Menu("Adventura");
        Menu help = new Menu("Nápověda");

        MenuItem novaHra = new MenuItem("Nová hra");
        //, new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/newgame.png")
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));
        
        MenuItem konecHry = new MenuItem("Konec hry");
        
        novySoubor.getItems().addAll(novaHra, konecHry);

        MenuItem oProgramu = new MenuItem("O programu");
        MenuItem napoveda = new MenuItem("Zobrazit nápovědu");

        help.getItems().addAll(oProgramu, napoveda);
        
        this.getMenus().addAll(novySoubor, help);
        
        konecHry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        novaHra.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hra = new Hra();
                main.getMapa().newGame(hra);
                main.setHra(hra);
                main.getCentralText().setText(hra.vratUvitani());
                //main.getVychody().update();
            }
        });

        oProgramu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert oProgramuAlert = new Alert(Alert.AlertType.INFORMATION);
                oProgramuAlert.setTitle("O programu");
                oProgramuAlert.setHeaderText("Nejlepší adventura na světě, asi");
                oProgramuAlert.setContentText("Vytvořeno v rámci kurzu Softwarového inženýrství na VŠE v Praze v roce 2017.");


                oProgramuAlert.showAndWait();

            }
        });

        napoveda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.setTitle("Nápověda");
                WebView webView = new WebView();

                webView.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());

                stage.setScene(new Scene(webView, 500, 500));
                stage.show();
            }
        });
    }
}
