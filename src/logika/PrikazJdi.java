package logika;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author     Jarmila Pavlickova, Luboš Pavlíček, Jan Riha, Rostislav Klein
 * @version    ZS 2016/2017
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;
    private Hra hra;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    *  @param hra samotná hra, aby ji bylo možné tímto příkazem prohrát
    */    
    public PrikazJdi(HerniPlan plan, Hra hra) {
        this.plan = plan;
        this.hra = hra;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *  
     *  Tato třída má v sobě zabudovanou možnost prohry při vstoupení do prostoru "nora",
     *  když hráč u sebe nemá věc "meč".
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        }
        else {
            plan.setAktualniProstor(sousedniProstor);
            if(plan.getAktualniProstor().getNazev().equals("nora") && plan.getAktualniProstor().getPostava("ork")!=null){
                if(plan.getBatoh().getVec("meč")==null){
                    hra.setKonecnyStav(false);
                    return "Vrhnul se na tebe šilený ork, neměl ses čím ubránit. Příště to zkus třeba s mečem. Bohužel, ale hra je u konce.";
                }else{
                    plan.getAktualniProstor().vlozVec(plan.getAktualniProstor().getPostava("ork").getCoMa());
                    plan.notifyObservers();
                    plan.getAktualniProstor().smazPostavu("ork");
                    return "Tak tak ses orkovi ubránil mečem co máš u sebe, vypadla z něj zlatá helma";
                }
            }
            return sousedniProstor.dlouhyPopis();
        }
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
