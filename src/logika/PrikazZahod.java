/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 * Třída PrikazZahod implementuje pro hru příkaz "zahoď".
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class PrikazZahod implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "zahoď";
    
    private HerniPlan hPlan;
    //== Konstruktory a tovární metody =============================================

    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */
    public PrikazZahod(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }
    
    /**
    *  Provádí příkaz "zahoď". Zkouší zahodit z batohu danou věc, pokud ji
    *  uživatel v batohu skutečně má, z batohu ji zahodí a položí do aktuálního prostoru.
    *
    *@param parametry - jako  parametr obsahuje jméno předmětu (věci), kterou má hráč zahodit na zem
    *@return zpráva, kterou vypíše hra hráči
    */
   
    //== Nesoukromé metody (instancí i třídy) ======================================
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím, co mám zahodit.";
        }

        String nazev = parametry[0];
        Vec vec = hPlan.getBatoh().zahodVec(nazev);
        
        if (vec == null) {
            return "Věc '" + nazev + "' ani nemáš v batohu.";
        }
         
         hPlan.getAktualniProstor().vlozVec(vec);
         return "Vec " + nazev + " byla zahozena, leží vedle vás na zemi.";
    }
   /**
    *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
    *  
    *  @ return nazev prikazu
    */ 
    public String getNazev() {
        return NAZEV;
    }

    //== Soukromé metody (instancí i třídy) ========================================

}
