/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logika.IHra;
import main.Main;
import utils.Observer;

import java.awt.*;

/**
 * GUI prvek mapy obsahuje obrázek reprezentující mapu adventury. Po mapě se pohybuje (nastavováním posLeft a posTop obrázek hrdiny.
 * @author     Rostislav Klein
 * @version    ZS 2017/2018
 */
public class Mapa extends AnchorPane implements Observer{

    private IHra hra;
    private ImageView postava;
    
    public Mapa(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObservers(this);
        init();
    }
    
    private void init(){
        ImageView obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/adventura_mapa.png"), 649, 395, true, true));
        postava = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/krtek.png"), 71, 85, true, true));
        this.getChildren().addAll(obrazekImageView, postava);
        
        update();
    }

    /**
     * Nastaví sledování nové hry.
     * @param novaHra instance nové hry
     */
    public void newGame(IHra novaHra){
        hra.getHerniPlan().removeObservers(this);
        hra = novaHra;
        hra.getHerniPlan().registerObservers(this);
        init();
        update();
    }

    public void mapaText(String text){
        this.getChildren().setAll();
        HBox prohralBox = new HBox();
        Label prohral = new Label(text);
        prohral.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        prohralBox.setAlignment(Pos.CENTER);
        prohralBox.setPrefSize(649, 395);
        prohralBox.getChildren().addAll(prohral);


        this.getChildren().addAll(prohralBox);
    }
    
    @Override
    public void update() {
        this.setTopAnchor(postava, hra.getHerniPlan().getAktualniProstor().getPosTop());
        this.setLeftAnchor(postava, hra.getHerniPlan().getAktualniProstor().getPosLeft());
    }
    
}
