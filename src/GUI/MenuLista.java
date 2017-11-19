/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import main.Main;
/**
 * GUI prvek - menu v záhlaví. Základní položky jako nová hra, konec a nápověda.
 * @author     Rostislav Klein
 * @version    ZS 2017/2018
 */
public class MenuLista extends MenuBar{
    
    private IHra hra;
    private Main main;
    
    public MenuLista(IHra hra, Main main){
        this.hra=hra;
        this.main=main;
        init();
    }

    /**
     * Inicializace GUI prvku. Nastavení itemů a jejich akce.
     */
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
        
        konecHry.setOnAction(event -> System.exit(0));

        /**
         * Po kliku začne nová hra.
         */
        novaHra.setOnAction(event -> {
            hra = new Hra();
            main.newGame(hra);
        });

        oProgramu.setOnAction(event -> {

            Alert oProgramuAlert = new Alert(Alert.AlertType.INFORMATION);
            oProgramuAlert.setTitle("O programu");
            oProgramuAlert.setHeaderText("Nejlepší adventura na světě, asi");
            oProgramuAlert.setContentText("Vytvořeno v rámci kurzu Softwarového inženýrství na VŠE v Praze v roce 2017.");


            oProgramuAlert.showAndWait();

        });

        napoveda.setOnAction(event -> {
            Stage stage = new Stage();
            stage.setTitle("Nápověda");
            WebView webView = new WebView();

            webView.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());

            stage.setScene(new Scene(webView, 1200, 500));
            stage.show();
        });
    }
}
