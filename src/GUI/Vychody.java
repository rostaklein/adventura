package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import logika.IHra;
import logika.Prostor;
import main.Main;
import utils.Observer;


/**
 * GUI prvek zobrazující východy z aktuálního prostoru. Při kliku na prostor do něj hráč přejde.
 * @author     Rostislav Klein
 * @version    ZS 2017/2018
 */
public class Vychody extends AnchorPane implements Observer {
    private IHra hra;
    private ObservableList<Prostor> vychody;
    private Main main;

    public Vychody(IHra hra, Main main){
        this.hra = hra;
        this.main = main;
        hra.getHerniPlan().registerObservers(this);
        init();
    }

    /**
     * Nastaví sledování nové hry.
     * @param novaHra instance nové hry
     */
    public void newGame(IHra novaHra){
        hra.getHerniPlan().removeObservers(this);
        hra = novaHra;
        hra.getHerniPlan().registerObservers(this);
        update();
    }

    private void init(){
        vychody = FXCollections.observableArrayList();
        ListView<Prostor> listVychodu = new ListView<>(vychody);
        listVychodu.setOrientation(Orientation.HORIZONTAL);
        listVychodu.setPrefHeight(50);
        listVychodu.setCellFactory(param -> new ListCell<Prostor>() {
            @Override
            protected void updateItem(Prostor item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setText(null);
                } else {
                    setText(item.getNazev());
                }
                this.setOnMousePressed(event -> {
                    //hra.getHerniPlan().setAktualniProstor(item);
                    main.zpracujPrikaz("jdi "+item.getNazev());
                    //hra.zpracujPrikaz();
                });
            }

        });
        this.getChildren().addAll(listVychodu);
        update();
    }



    @Override
    public void update() {
        vychody.setAll();
        vychody.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }
}
