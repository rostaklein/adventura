package logika;

/**
 * Třída PrikazObchod implementuje pro hru příkaz obchod.
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Rostislav Klein
 * @version   1.00.001
 */
class PrikazObchod implements IPrikaz {
    private static final String NAZEV = "obchod";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public PrikazObchod(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "obchod". Postavy mohou mít u sebe možnost obchodování, hráč
     *  poté pomocí svých zlatých s postavami obchoduje a ty mu za to nabízí svou věc.
     *  
     *  Zjištění nabídky dané postavy se prování příkazem "mluv", kdy za svou frázi
     *  postava připojí i nabídku prodeje.
     *  
     *  Obchod může proběhnout pouze v případě, že postava je ve stejném prostoru a hráč
     *  má dostatek zlatých u sebe. Po úspěšném obchodu se hráči odečte příslušný počet
     *  zlatých a do inventáře (pokud má místo) se mu vloží koupená věc.
     *
     *@param parametry - prvním parametrem je "kolik" a druhým "komu" mám zaplatit, takže například
     *"obchod 150 rytíř" znamená zaplacení 150 zlatých rytíři.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kolik mám komu platit?";
        }else if(parametry.length == 1){
            return "Komu mám zaplatit "+parametry[0]+" zlatých?";
        }

        String komu = parametry[1];
        int kolik = Integer.parseInt(parametry[0]);
        
        if(kolik>plan.getAktualniPocetZlatych()){
            return "Bohužel, ale tolik zlatých u sebe ani nemáš.";
        }
        
        Postava postava = plan.getAktualniProstor().getPostava(komu);
        Vec vec = postava.zaplatit(kolik);
        
        if(vec==null && postava!=null){
            return postava.getRecVymena();  
        }else if(postava==null){
            return "Pokoušíš se zaplatit někomu, kdo tady není.";
        }else{
             if(vec==null){
                 return postava.getRecVymena();
             }else{
                 if(plan.getBatoh().pridejVec(vec)){
                    plan.zmenaZlatych(-postava.getCena());
                 }else{
                     return "Nemáš v batohu dostatek místa, něco vyhoď a zkus to znova.";
                 }
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
