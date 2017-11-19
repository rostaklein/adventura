package logika;

/*******************************************************************************
 * Třída PrikazMluv implementuje pro hru příkaz "mluv".
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
class PrikazMluv implements IPrikaz {
    private static final String NAZEV = "mluv";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public PrikazMluv(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "mluv". Vrací text postavy dané v parametru, pokud se nachází v aktuálním
     *  prostoru. Jinak napíše, že zde postava není.
     *
     *@param parametry - jako  parametr obsahuje jméno postavy, kterou chce hráč slyšet
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "S kým mám promluvit? Zadej prosím příkaz znovu.";
        }

        String jmeno = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Postava postava = plan.getAktualniProstor().getPostava(jmeno);
        
        if (postava == null) {
            return "Tato postava zde není.";
        }
        else {
            plan.setPostavaDialog(postava);
            plan.notifyObservers();
            return postava.getJmeno()+" říká: '"+postava.getMluv()+"'";
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
