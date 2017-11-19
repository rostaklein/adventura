package logika;

/**
 * Třída PrikazDej implementuje pro hru příkaz dej.
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Rostislav Klein
 * @version   1.00.001
 */
class PrikazDej implements IPrikaz {
    private static final String NAZEV = "dej";
    private HerniPlan plan;
    private Hra hra;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    *  @param hra samotná hra, aby ji bylo možné tímto příkazem vyhrát
    */    
    public PrikazDej(HerniPlan plan, Hra hra) {
        this.plan = plan;
        this.hra = hra;
    }

    /**
     *  Provádí příkaz "dej". Tento příkaz předává určitou věc určité postavě, ta podle
     *  nastaveného chování vrací zadanou věc, nebo nabízenou věc odmítne. Samozřejmě jde
     *  předávat pouze věc, kterou mám u sebe a pouze postavě, která je v mém prostoru.
     *  
     *  Tato třída má v sobě implementovanou i jedninou možnou cestu výhry a to je předání
     *  šatů princezně.
     *
     *@param parametry prvním parametrem je "co" a druhým "komu" takže například "dej šaty princezna"
     *znamená předání šatů princezně
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Co mám komu dát?";
        }else if(parametry.length == 1){
            return "Komu mám dát "+parametry[0]+"?";
        }

        String jakaVec = parametry[0];
        String komu = parametry[1];

        Vec vec = plan.getBatoh().getVec(jakaVec);
        Postava postava = plan.getAktualniProstor().getPostava(komu);
        
        if(vec==null && postava==null){
            return "Věc u sebe ani nemáš a postava tady není.";  
        }else if(vec==null && postava!=null){
            return "Požadovanou věc u sebe nemáš.";
        }else if(vec!=null && postava==null){
            return "Postava není poblíž tebe.";
        }else if(postava.getJmeno().equals("princezna") && vec.equals(postava.getCoChce())){
            hra.setKonecnyStav(true);
            return "Splnil si svůj úkol! Princezna tě sice nechce, ale ani po tobě se více nechce.";
        }else{
             Vec coDal = postava.vymena(vec);
             if(coDal==null){
                 return postava.getRecVymena();
             }else{
                 plan.getBatoh().zahodVec(vec.getNazev());
                 plan.getBatoh().pridejVec(coDal);
                 plan.notifyObservers();
                 return postava.getRecVymena();
             }
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
