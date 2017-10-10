package logika;


/**
 * Třída Hra - třída představující logiku adventury.
 * 
 * Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan,
 * která inicializuje místnosti hry a vytváří seznam platných příkazů a instance tříd
 * provádějící jednotlivé příkazy. Vypisuje uvítací a ukončovací text hry. Také
 * vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Rostislav Klein
 * @version    ZS 2016/2017
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;
    private boolean vyhra = false;

    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazVezmi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazBatoh(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazZahod(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazMluv(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazDej(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazObchod(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazVsad(herniPlan));
    }

    /**
     *  Vrátí úvodní zprávu pro hráče včetně popisu prvního prostoru, kde se hráč nachází.
     */
    public String vratUvitani() {
        return "START HRY----------------------------Goddard.ver.1.0\n" +
               "-------------------Vítej jinochu,-------------------\n" +
               "nacházíš se v městě Goddard, uvnitř města i za hradbami\n" +
               "se dějí podivné věci. Z královské rodiny se nedávno\n" +
               "ztratila nejmladší dcera. Tvým úkolem je ji dovést\n" +
               "zpátky k rodině.\n" +
               "Napište 'napoveda', pokud si nevíte rady, jak hrát dál.\n" +
               "-------------------Hodně štěstí!-------------------\n" +
               "\n" +
               herniPlan.getAktualniProstor().dlouhyPopis();
    }
    
    /**
     *  Vrátí závěrečnou zprávu pro hráče. Orientuje se podle stavu hry, zda hráč prohrál, nebo vyhrál.
     */
    @Override
    public String vratEpilog() {
        String textVrat="";
        String radekCara="--------------------------------------------------------\n";
        if(vyhra){
            textVrat+=radekCara;
            textVrat+="----------------GRATULUJI, VYHRÁL/A JSI!----------------\n";
            textVrat+=radekCara;
        }else{
            textVrat+=radekCara;
            textVrat+="----BOHUŽEL, TENTOKRÁT TO NEVYŠLO... PROHRÁL/A JSI------\n";
            textVrat+=radekCara;
        }
        textVrat+="KONEC HRY----------------------------Goddard.ver.1.0\n" +
                  "--------------------Díky za hru.--------------------\n";
        return textVrat;
    }
    
    /** 
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry() {
        return konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
            parametry[i]= slova[i+1];   
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.proved(parametry);
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
    
     /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní IPrikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
    /**
     *  Abychom byli schopni poznat, zda hráč prohrál nebo vyhrál, slouží nám k tomu tato metoda setKonecnyStav,
     *  ta hru ukončí a uloží nám do stavu hry zda hráč tuto seanci prohrál, nebo vyhrál.
     *  
     *  @param  vyhra  hodnota false= prohrál, true = vyhrál
     */
    public void setKonecnyStav(boolean vyhra){
        this.vyhra=vyhra;
        setKonecHry(true);
    }
     /**
     *  Pouze pro účely testů. Vrací konečný stav - false = prohra, true = výhra
     *  
     *  @return boolean - false = prohra, true = výhra
     */
    public boolean getKonecnyStav(){
        return vyhra;
    }
     /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     *  @return     odkaz na herní plán
     */
     public HerniPlan getHerniPlan(){
        return herniPlan;
     }
    
}
