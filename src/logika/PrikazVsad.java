package logika;
import java.util.*;
/**
 * Třída PrikazVsad implementuje pro hru příkaz vsaď.
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Rostislav Klein
 * @version   1.00.001
 */
class PrikazVsad implements IPrikaz {
    private static final String NAZEV = "vsaď";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
    */    
    public PrikazVsad(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "vsaď". Jedinou postavou ve hře, se kterou zatím lze hrát je "pobuda" v hospodě.
     *  Pokud by hráč zkusil vsadit v jiném prostoru, než je hospoda, nepovede se mu to.
     *  Pravidla sázky jsou uměle vytvořená v této třídě. Jedná se o "házení kostkou".
     *  
     *  Nejprve je nutno uhradit poplatek a vsazená částka se vynásobí náhodně generovaným "kurzem".
     *  Pokud hráč nemá na uhrazení poplatku a sázky, nemůže si zahrát.
     *  Nelze vsadit více jak předem stanovenou částku zlatých.
     *  Vsazená částka musí být celé nezáporné nenulové číslo.
     *
     *@param parametry - parametrem je celočíselná hodnota sázky
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        Postava pobuda = plan.getAktualniProstor().getPostava("pobuda");
        final int poplatek=10;
        final int maxSazka=200;
        
        if(pobuda==null){
            return "Nemáš si zde s kým zahrát, zkus to v hospodě.";
        }
        
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Pravidla jsou:\nHozená hodnota na třech kostkách děleno šesti krát vsazená částka zaokrouhleno na celá čísla nahoru..\nPoplatek za jednu hru je 10 zlatých.\nKolik chceš vsadit? (např. hraj 150)";
        }
        int sazka = 0;
        
        try {
            sazka = Integer.parseInt(parametry[0]);
            if(sazka<0){
                return "Nemůžeš vsadit zápornou částku.";
            }else if(sazka==0){
                return "Proč by si vsázel nulu? Vsaď něco znova";
            }
        } catch (NumberFormatException e) {
            return "Zkus to znova a zadej částku jako celé číslo.";
        }
        
        if(plan.getAktualniPocetZlatych()<=poplatek){
            return "Nemáš ani na uhrazení poplatku, odpal pryč.";
        }
        else if(sazka+poplatek>plan.getAktualniPocetZlatych()){
            return "Na tohle nemáš dost zlatých. můžeš vsadit maximálně "+(plan.getAktualniPocetZlatych()-poplatek)+".";
        }else if(sazka>maxSazka){
            return "Sázka je moc vysoká, bojím se, že bych tě neměl jak vyplatit. Vsaď prosím měně než 200 zlatých.";
        }
        
        plan.zmenaZlatych(-poplatek);
        plan.zmenaZlatych(-sazka);
        int max = 6;
        int min = 1;
        int soucet =0;
        String vypisText = "Vsadil jsi "+sazka+" zlatých a uhradil poplatek 10 zlatých\nKostky jsou vrženy: ";
        for(int i=0;i<3;i++){
            Random random = new Random();
            int cislo=random.nextInt(max - min + 1) + min;
            soucet+=cislo;
            vypisText+="\nkostka č."+i+" - " + cislo;
        }

        int kurz=soucet/6;
        int vyhra=sazka*kurz;
        plan.zmenaZlatych(vyhra);
        vypisText+="\nsoučet: "+soucet+" | kurz: "+kurz+" | výhra: "+vyhra+" zlatých";
        vypisText+="\nMomentální stav zlatých: "+plan.getAktualniPocetZlatych();
        return vypisText;
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
