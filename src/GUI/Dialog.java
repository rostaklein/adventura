package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logika.IHra;
import logika.Postava;
import logika.Vec;
import main.Main;
import utils.Observer;

import javax.print.attribute.IntegerSyntax;


/**
 * GUI prvek zobrazující aktuální obsah batohu. Při kliku na item jej zahodí.
 * @author     Rostislav Klein
 * @version    ZS 2017/2018
 */
public class Dialog extends AnchorPane implements Observer {
    private IHra hra;
    private Main main;
    private Label jmenoPostavy;
    private VBox layout;

    public Dialog(IHra hra, Main main){
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
        layout = new VBox();
        jmenoPostavy = new Label();
        jmenoPostavy.setAlignment(Pos.CENTER);
        //jmenoPostavy.setFont(Font.font("Arial", FontWeight.LIGHT, 14));

        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(jmenoPostavy);
        this.getChildren().addAll(layout);
        update();
    }


    public ListView<Vec> obchodPostavy(Postava postava){
        ObservableList<Vec> predmety;
        Vec predmet = postava.getCoMa();
        predmety = FXCollections.observableArrayList();
        predmety.addAll(predmet);
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
                    setText(item.getNazev() +" ("+postava.getCena()+" zlatých)");
                    imageView.setImage(item.getObrazek());
                    imageView.setFitHeight(40);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
                this.setOnMousePressed(event -> {
                    //hra.getHerniPlan().setAktualniProstor(item);
                    main.zpracujPrikaz("obchod "+postava.getCena()+" "+postava.getJmeno());
                    hra.getHerniPlan().notifyObservers();
                    //hra.zpracujPrikaz();
                    update();
                });
            }

        });

        return listPredmetu;
    }


    public VBox hraniKostek(Integer aktualPocet){
        VBox vbox = new VBox();
        Label label = new Label("Potáhnutím zvolíš částku");
        Label labelvsad = new Label();
        label.setAlignment(Pos.CENTER);
        labelvsad.setAlignment(Pos.CENTER);

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(aktualPocet);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        Button vsadit = new Button("Zvolte částku");
        vsadit.setDisable(true);
        slider.valueChangingProperty().addListener((observable, oldValue, newValue) -> {
            Double val = slider.getValue();
            vsadit.setText("Vsadit "+val.intValue()+" zlatých");
            vsadit.setDisable(false);
            //main.zpracujPrikaz("vsaď "+val.intValue()+" pobuda");
        });
        slider.setPadding(new Insets(5));

        vsadit.setOnAction(event -> {
            Double val = slider.getValue();
            main.zpracujPrikaz("vsaď "+val.intValue()+" pobuda");
        });

        vbox.getChildren().setAll(label, slider, vsadit);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    @Override
    public void update() {
        Postava dialogPostava = hra.getHerniPlan().getPostavaDialog();
        layout.getChildren().setAll();
        if(dialogPostava != null){
            if(dialogPostava.getCoMa()!=null && !dialogPostava.isProbehlaVymena()){
                jmenoPostavy.setText("Mluvíš s " + dialogPostava.getJmeno()+" a má u sebe:");
                layout.getChildren().addAll(jmenoPostavy, obchodPostavy(dialogPostava));
            }else if(dialogPostava.getJmeno().equals("pobuda")){
                jmenoPostavy.setText("Pobuda si s tebou chce zahrát kostky!");
                layout.getChildren().addAll(jmenoPostavy, hraniKostek(hra.getHerniPlan().getAktualniPocetZlatych()));
            }else{
                jmenoPostavy.setText("Mluvíš s " + dialogPostava.getJmeno());
                layout.getChildren().add(jmenoPostavy);
            }
        }

    }
}
