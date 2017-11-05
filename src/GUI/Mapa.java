/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.Hra;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 *
 * @author rostaklein
 */
public class Mapa extends AnchorPane implements Observer{

    private IHra hra;
    private Circle tecka;
    private ImageView postava;
    
    public Mapa(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObservers(this);
        init();
    }
    
    private void init(){
        ImageView obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/adventura_mapa.png"), 649, 395, true, true));
        postava = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/krtek.png"), 71, 85, true, true));
        tecka = new Circle(10, Paint.valueOf("red"));
        
        
        
        this.getChildren().addAll(obrazekImageView, postava);
        
        update();
    }
    
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
