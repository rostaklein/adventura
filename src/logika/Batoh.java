/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.*;


/**
 * Třída Batoh představuje batoh, který postava nosí během hry u sebe.
 *
 * Tato třída je součástí jednoduché textové hry.
 * 
 * Třída umí batoh vytvořit (s jeho maximální kapacitou), dostat jeho aktuální váhu, kompletní výpis obsahu.
 * Přidat věci, odstranit věci, dostat věci z batohu.
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class Batoh
{
    //== Datové atributy (statické i instancí)======================================
    private Map<String, Vec> obsahBatohu;
    private int maxVaha;
    //== Konstruktory a tovární metody =============================================

     /**
     * Vytvoření batohu s jeho maximální váhou.
     *
     * @param maxVaha maximální možná kapacita batohu
     */
    public Batoh(int maxVaha)
    {
       obsahBatohu = new HashMap<>();
       this.maxVaha = maxVaha;
    }
    /**
     * Vrací aktuální zaplnění kapacity batohu, sčítá váhy všech věcí v batohu.
     *
     * @return aktuální váha batohu
     */
    public int getAktualniVaha(){
        int aktualniVaha=0;
        for(Vec predmet : obsahBatohu.values()){
            aktualniVaha+=predmet.getVaha();
        }
        return aktualniVaha;
    }
    /**
     * Dle aktuální váhy tato metoda buďto přidá zadanou věc v parametru, nebo vyhodí false
     * a věc nepřidá kvůli tomu, že už se do batohu nevleze.
     *
     * @param vec objekt přidávané věci
     * @return true = věc byla přidána, false = věc nebyla přidána
     */
    public boolean pridejVec(Vec vec){
        if((getAktualniVaha()+vec.getVaha())<maxVaha){
            obsahBatohu.put(vec.getNazev(), vec);
            return true;
        }
        return false;
    }
    /**
     * Odstraní věc z batohu pomocí jejího názvu, pokud se podaří ji odstranit, vrátí nám ji
     * jako objekt. Pokud věc v batohu není, vrátí se null.
     *
     * @param nazev název věci, kterou chceme z batohu odstranit
     * @return objekt Vec - odstraněná věc, null = věc v batohu nebyla
     */
    public Vec zahodVec(String nazev){
       return obsahBatohu.remove(nazev);
    }
    
    /**
     * Dostaneme objekt věci z batohu, pokud se v batohu nachází, jinak null. Z batohu ji neodstraní.
     *
     * @param nazev název věci, kterou chceme z batohu ukázat
     * @return objekt Vec - požadovaná věc, null = věc v batohu nebyla
     */
    public Vec getVec(String nazev){
        return obsahBatohu.get(nazev);
    }
    /**
     * Pro potřeby dané hry vypisuje obsah batohu - jednotlivé položky, jejich váhu.
     * Vypíše také aktuální kapacitu batohu s maximální kapacitou batohu.
     * Pokud v batohu nic není, vypíše speciální zprávu, že v batohu nic není.
     *
     * @return textový řetězec výpisu obsahu batohu
     */
    public String vypisObsah(){
        if(obsahBatohu.isEmpty()){
            return "V batohu nic není, zaplněno 0 z celkových "+maxVaha+" kg.";
        }else{
            String vypis ="Obsah batohu (zaplněno "+getAktualniVaha()+" z "+maxVaha+" kg): ";
            for(Vec predmet : obsahBatohu.values()){
                String nazev=predmet.getNazev();
                int vaha=predmet.getVaha();
                vypis+=nazev+"("+vaha+" kg) ";
            }
            return vypis; 
        }
    }

    /**
     * Vrací objekty z obsahu batohu.
     * @return obsah batohu
     */
    public Map<String, Vec> getObsahBatohu() {
        return obsahBatohu;
    }

    public int getMaxVaha() {
        return maxVaha;
    }
}
