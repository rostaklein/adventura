package logika;

/**
 * Třída PrikazBatoh implementuje pro hru příkaz batoh.
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Rostislav Klein
 * @version   1.00.001
 */
class PrikazBatoh implements IPrikaz {
    private static final String NAZEV = "batoh";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public PrikazBatoh(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Tento příkaz je bez parametrů. Jeho jedinou funkcí je výpis současného stavu batohu
     *  včetně současné a maximální kapacity.
     *  
     *  Za výpis věcí z batohu připojí současný stav zlatých.
     *
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        return plan.getBatoh().vypisObsah() + "\nV kapse máš: "+plan.getAktualniPocetZlatych()+" zlatých.";
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
