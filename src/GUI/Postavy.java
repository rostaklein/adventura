package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import logika.IHra;
import logika.Postava;
import main.Main;
import utils.Observer;

/**
 * GUI prvek zobrazující aktuální postavy v prostoru. Při kliku na jmméno postavy s ní hráč promluví.
 * @author     Rostislav Klein
 * @version    ZS 2017/2018
 */
public class Postavy extends AnchorPane implements Observer {
    private IHra hra;
    private ObservableList<Postava> postavy;
    private Main main;

    public Postavy(IHra hra, Main main){
        this.hra = hra;
        this.main = main;
        hra.getHerniPlan().registerObservers(this);
        init();
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


    private void init(){
        postavy = FXCollections.observableArrayList();
        ListView<Postava> listPredmetu = new ListView<>(postavy);
        listPredmetu.setOrientation(Orientation.HORIZONTAL);
        listPredmetu.setPrefHeight(50);
        listPredmetu.setCellFactory(param -> new ListCell<Postava>() {
            @Override
            protected void updateItem(Postava item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getJmeno() == null) {
                    setText(null);
                } else {
                    setText(item.getJmeno());
                }
                this.setOnMousePressed(event -> {
                    //hra.getHerniPlan().setAktualniProstor(item);
                    main.zpracujPrikaz("mluv "+item.getJmeno());
                    //hra.zpracujPrikaz();
                    update();
                });
            }

        });
        this.getChildren().addAll(listPredmetu);
        update();
    }



    @Override
    public void update() {
        postavy.setAll();
        postavy.addAll(hra.getHerniPlan().getAktualniProstor().getPostavy().values());
    }
}
