/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Třída PrikazProzkoumej implementuje pro hru příkaz "prozkoumej".
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */

public class PrikazProzkoumej implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "prozkoumej";
    
    private HerniPlan hPlan;
    //== Konstruktory a tovární metody =============================================

    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */
    public PrikazProzkoumej(HerniPlan plan)
    {
        this.hPlan = plan;
    }

   /**
    *  Provádí příkaz "prozkoumej". Bez parametrů vypíše znova informace o prostoru, které se normálně
    *  vypisují při každém přechodu do prostoru. S parametrem vypíše informace o věci v prostoru, nebo
    *  v batohu hráče. Pokud daný předmět není v prostoru či v batohu, vyhodí chybovou hlášku.
    *
    *@param parametry - bez parametru vypíše info o aktuálním prostoru, s parametrem názvu věci vypíše informaci o daném předmětu
    *@return zpráva, kterou vypíše hra hráči
    */
   public String proved(String... parametry) {
        if (parametry.length < 1) {
            return hPlan.getAktualniProstor().dlouhyPopis();
        }

        String nazev = parametry[0];
        
        if(hPlan.getAktualniProstor().getVec(nazev)==null && hPlan.getBatoh().getVec(nazev)==null){
            return "věc '" + nazev + "' tu není";
        }else if(hPlan.getAktualniProstor().getVec(nazev)==null){
            Vec vec = hPlan.getBatoh().getVec(nazev);
            return nazev + ": " + vec.getPopis();
        }else{
            Vec vec = hPlan.getAktualniProstor().getVec(nazev);
            return nazev + ": " + vec.getPopis();
        }
    }
    /**
    *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
    *  
    *  @return nazev prikazu
    */
    public String getNazev() {
        return NAZEV;
    }

    //== Soukromé metody (instancí i třídy) ========================================

}
