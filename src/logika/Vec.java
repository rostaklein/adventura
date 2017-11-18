/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;


import javafx.scene.image.Image;
import main.Main;

import java.awt.*;

/**
 * Třída Vec představuje jednotlivou věc(předmět) ve hře.
 *
 * Tato třída je součástí jednoduché textové hry.
 * 
 * Tato třída sestavuje v konstruktoru jednotlivé věci pomocí daných elementárních vlastností. Poté za použití getterů
 * na tyto vlastnosti odkazuje.
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class Vec
{
    //== Datové atributy (statické i instancí)======================================
    private String nazev;
    private String popis;
    private int vaha;
    private boolean prenositelna;
    private Image obrazek;
    
    //== Konstruktory a tovární metody =============================================

   /**
     * Vytvoření věci se zadanými vlastnostmi.
     *
     * @param nazev název věci, jednoznačný identifikátor daně věci
     * @param popis stručný popis použitelnosti věci
     * @param prenositelna boolean, pokud je věc přenostielná, pak prenositelna=true, jinak false
     * @param vaha celočíselná hodnota výhy dané věci, důležitá vlastnost pro přidávání věcí do batohu
    *  @param obrazekName jméno souboru k obrázku
     */
    public Vec(String nazev, String popis, boolean prenositelna, int vaha, String obrazekName)
    {
        this.nazev = nazev;
        this.popis = popis;
        this.prenositelna = prenositelna;
        this.vaha = vaha;
        this.obrazek = new Image(Main.class.getResourceAsStream("/zdroje/predmety/"+obrazekName));
    }

    /**
     * Vrací název věci (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název věci
     */
    public String getNazev() {
        return nazev;
    }
    /**
     * Vrací popis věci (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return popis věci
     */
    public String getPopis() {
        return popis;
    }
    /**
     * Vrací logickou hodnotu přenositelnosti věci (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return true = je přenositelná, false = není přenositelná
     */
    public boolean isPrenositelna() {
        return prenositelna;
    }
    /**
     * Vrací číselnou hodnotu váhy dané věci (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return váha věci
     */
    public int getVaha() {
        return vaha;
    }

    public Image getObrazek() {
        return obrazek;
    }

    public void setObrazek(Image obrazek) {
        this.obrazek = obrazek;
    }

    //== Soukromé metody (instancí i třídy) ========================================

}
