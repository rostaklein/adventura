/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logika.IHra;
import main.Main;
import utils.Observer;

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
     * @param novaHra
     */
    public void newGame(IHra novaHra){
        hra.getHerniPlan().removeObservers(this);
        hra = novaHra;
        hra.getHerniPlan().registerObservers(this);
        update();
    }
    
    @Override
    public void update() {
        this.setTopAnchor(postava, hra.getHerniPlan().getAktualniProstor().getPosTop());
        this.setLeftAnchor(postava, hra.getHerniPlan().getAktualniProstor().getPosLeft());
    }
    
}
