package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logika.IHra;
import logika.Prostor;
import logika.Vec;
import main.Main;
import utils.Observer;



public class Predmety extends AnchorPane implements Observer {
    private IHra hra;
    private ObservableList<Vec> predmety;
    private Main main;

    public Predmety(IHra hra, Main main){
        this.hra = hra;
        this.main = main;
        hra.getHerniPlan().registerObservers(this);
        init();
    }

    public void newGame(IHra novaHra){
        hra.getHerniPlan().removeObservers(this);
        hra = novaHra;
        hra.getHerniPlan().registerObservers(this);
        update();
    }

    private void init(){
        predmety = FXCollections.observableArrayList();
        ListView<Vec> listPredmetu = new ListView<>(predmety);
        listPredmetu.setOrientation(Orientation.HORIZONTAL);
        listPredmetu.setPrefHeight(50);
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
                    main.zpracujPrikaz("vezmi "+item.getNazev());
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
        predmety.setAll();
        predmety.addAll(hra.getHerniPlan().getAktualniProstor().getVeci().values());
    }
}
