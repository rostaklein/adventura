package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logika.IHra;
import logika.Vec;
import main.Main;
import utils.Observer;


/**
 * GUI prvek zobrazující aktuální obsah batohu. Při kliku na item jej zahodí.
 * @author     Rostislav Klein
 * @version    ZS 2017/2018
 */
public class Batoh extends AnchorPane implements Observer {
    private IHra hra;
    private ObservableList<Vec> predmety;
    private Main main;

    public Batoh(IHra hra, Main main){
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

    /**
     * Inicializace GUI prvku.
     */
    private void init(){
        predmety = FXCollections.observableArrayList();
        ListView<Vec> listPredmetu = new ListView<>(predmety);
        listPredmetu.setPrefHeight(150);
        listPredmetu.setCellFactory(param -> new ListCell<Vec>() {
            private ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Vec item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getNazev());
                    imageView.setImage(item.getObrazek());
                    imageView.setFitHeight(40);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
                this.setOnMousePressed(event -> {
                    //hra.getHerniPlan().setAktualniProstor(item);
                    main.zpracujPrikaz("zahoď "+item.getNazev());
                    //hra.zpracujPrikaz();
                    update();
                });
            }

        });
        Label labelBatoh = new Label("Obsah batohu:");
        labelBatoh.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox batohLayout = new VBox();
        batohLayout.setAlignment(Pos.CENTER);
        batohLayout.getChildren().addAll(labelBatoh, listPredmetu);
        this.getChildren().addAll(batohLayout);
        update();
    }



    @Override
    public void update() {
        predmety.setAll();
        predmety.addAll(hra.getHerniPlan().getBatoh().getObsahBatohu().values());
    }
}
