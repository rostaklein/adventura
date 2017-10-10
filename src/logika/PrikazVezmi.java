/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 * Třída PrikazVezmi implementuje pro hru příkaz "vezmi".
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */

public class PrikazVezmi implements IPrikaz
{
    private static final String NAZEV = "vezmi";
    private HerniPlan hPlan;

    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */
    public PrikazVezmi(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }

    /**
    *  Provádí příkaz "vezmi". Parametrem je název věci, která se musí nacházet ve stejném prostoru,
    *  jako se aktuálně nachází samotný hráč. Věc musí být přenositelná. Pokud se nevejde do batohu,
    *  zůstane ležet na zemi.
    *
    *@param parametry - název věci, kterou chce hráč sebrat
    *@return zpráva, kterou vypíše hra hráči
    */
    public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím, co mám sebrat.";
        }

        String nazev = parametry[0];
        Vec vec = hPlan.getAktualniProstor().odeberVec(nazev);
        
        if (vec == null) {
            return "Věc '" + nazev + "' tady není.";
        }
        
        if (!vec.isPrenositelna()) {
            hPlan.getAktualniProstor().vlozVec(vec);
            return "Věc " + nazev + " se nedá přenést.";
        }
        
        //vložíme věc do batohu
         
         if (hPlan.getBatoh().pridejVec(vec)) {
             return "Vec " + nazev + " byla vložena do batohu";
         } else {
             hPlan.getAktualniProstor().vlozVec(vec);
             return nazev +" se do batohu už bohužel nevejde.";
         }
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
