package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logika.IHra;
import logika.Prostor;
import main.Main;
import utils.Observer;

import java.util.Collection;


public class Vychody extends AnchorPane implements Observer {
    private IHra hra;
    private ObservableList vychody;
    private ListView listVychodu;
    public Vychody(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObservers(this);
        init();
    }

    private void init(){
        vychody = FXCollections.observableArrayList();
        listVychodu = new ListView(vychody);
        listVychodu.setOrientation(Orientation.HORIZONTAL);
        listVychodu.setPrefHeight(40);
        this.getChildren().addAll(listVychodu);
        update();
    }



    @Override
    public void update() {
        vychody.setAll();
        for(Prostor vychod : hra.getHerniPlan().getAktualniProstor().getVychody()){
            vychody.add(vychod.getNazev());
        }
    }
}
