package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private Label kapacita;
    private Label stavZlatych;
    private Label kliknutim;

    public Batoh(IHra hra, Main main){
        this.hra = hra;
        this.main = main;
        hra.getHerniPlan().registerObservers(this);
        init();
    }

    /**
     * Nastaví sledování nové hry.
     * @param novaHra nová instance hry
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
        listPredmetu.setPrefHeight(120);
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
                    if(hra.getHerniPlan().getPostavaDialog()!=null){
                        main.zpracujPrikaz("dej "+item.getNazev()+" "+hra.getHerniPlan().getPostavaDialog().getJmeno());
                    }else{
                        main.zpracujPrikaz("zahoď "+item.getNazev());
                    }
                    hra.getHerniPlan().notifyObservers();
                    //hra.zpracujPrikaz();
                    update();
                });
            }

        });
        Label labelBatoh = new Label("Obsah batohu:");
        kapacita = new Label();
        stavZlatych = new Label();
        kliknutim = new Label();
        labelBatoh.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        stavZlatych.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox batohLayout = new VBox();
        batohLayout.setAlignment(Pos.CENTER);
        batohLayout.setSpacing(10);
        batohLayout.setPadding(new Insets(10));

        HBox zlate = new HBox();

        ImageView zlateIcon = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/gold-mini.png"), 17, 17, true, true));
        zlate.getChildren().addAll(stavZlatych, zlateIcon);
        zlate.setSpacing(10);
        zlate.setAlignment(Pos.CENTER);

        batohLayout.getChildren().addAll(labelBatoh, kapacita, listPredmetu, zlate, kliknutim);
        this.getChildren().addAll(batohLayout);
        update();
    }



    @Override
    public void update() {
        if(!hra.getHerniPlan().getBatoh().getObsahBatohu().isEmpty()){
            if(hra.getHerniPlan().getPostavaDialog()==null){
                kliknutim.setText("Kliknutím věc zahodíš.");
            }else{
                kliknutim.setText("Kliknutím věc předáš postavě - "+hra.getHerniPlan().getPostavaDialog().getJmeno());
            }
        }
        kapacita.setText("váha: "+hra.getHerniPlan().getBatoh().getAktualniVaha()+"/"+hra.getHerniPlan().getBatoh().getMaxVaha());
        stavZlatych.setText(hra.getHerniPlan().getAktualniPocetZlatych()+" zlatých");
        predmety.setAll();
        predmety.addAll(hra.getHerniPlan().getBatoh().getObsahBatohu().values());

    }
}
