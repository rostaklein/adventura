package logika;

import java.util.ArrayList;
import java.util.List;
import utils.Observer;
import utils.Subject;


/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory, propojuje je vzájemně pomocí východů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Rostislav Klein
 * @version    ZS 2016/2017
 */
public class HerniPlan implements Subject{
    
    private Prostor aktualniProstor;
    private int stavZlatych;
    private Batoh batoh;
    
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        defaultSetUp();
        stavZlatych=150;
        batoh = new Batoh(60);
    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů. Vytvoří a vloží věci. Vytvoří, nastaví a vloží postavy.
     *  Jako výchozí aktuální prostor nastaví domeček.
     */
    private void defaultSetUp() {
        // vytvářejí se jednotlivé prostory
        Prostor namesti = new Prostor("náměstí","Nacházíte se na prostranství plném prapodivných lidí. Všichni na sebe nahlas křičí a ty bys měl odsud rychle vypadnout.", 70, 100);
        Prostor hospoda = new Prostor("hospoda", "Hospodský nalévá jednu whisky za druhou. Tato špeluňka je mezi lidmi známá spíše jako herna.", 35, 170);
        Prostor kovar = new Prostor("kovárna","Žár roztaveného železa je cítit hned při otevření vchodových dveří. S údivem na tebe koukají dva trpaslíci.", 211, 36);
        Prostor predmesti = new Prostor("předměstí","Je odsud krásný výhled na les a na opačné se tyčí hradby města.", 235, 293);
        Prostor les = new Prostor("les","Kdo může, raději se tady nezastavuje. Les je proslulý neustálým loupením. Dávejte si pozor, snadno můžete spadnout do nory podivným bytostem.", 370, 233);
        Prostor nora = new Prostor("nora","Prostor jako stvořený pro věznění osob, smrad a špína všude kde se podíváš.", 526, 164);
        
        // přiřazují se průchody mezi prostory (sousedící prostory)
        namesti.setVychod(hospoda);
        hospoda.setVychod(namesti);
        namesti.setVychod(kovar);
        kovar.setVychod(namesti);
        namesti.setVychod(predmesti);
        predmesti.setVychod(les);
        predmesti.setVychod(namesti);
        les.setVychod(predmesti);
        les.setVychod(nora);
        nora.setVychod(les);
                
        aktualniProstor = namesti;  // hra začíná v domečku
        
        // vytvoříme věci
        Vec fontana = new Vec("fontána", "Plná špinavé vody, na dně je pár drobáků, ale nikdo se neodvažuje sáhnout dovnitř.", false, 2000, "fontana.png");
        Vec lopata = new Vec("lopata", "Lopata, kterou už dlouho nikdo nepoužíval, těžko říct, jestli už neztratila svou funkčnost.", true, 10, "lopata.png");
        Vec zidle = new Vec("židle", "Stará ztrouchnivělá židle z masivu. Nemá využití.", true, 25, "zidle.png");
        Vec kovadlina = new Vec("kovadlina", "Rozpálená kovadlina, na které malí trpaslíci vyrábí nejrůznější předměty.", false, 250, "kovadlina.png");
        Vec mec = new Vec("meč", "Tento meč má své nejlepší časy dávno za sebou, ale k obraně by mohl posloužit.", true, 25,"mec.png");
        Vec saty = new Vec("šaty", "Z nejluxusnějšího hedvábí ušité svatební šaty potěší jistě každou něžnou duši.", true, 10, "saty.png");
        Vec helma = new Vec("zlatá_helma", "Opravdu težký kus zlata. Třpytí se tak, že by se určitě hodil do sbírky nějakému trpaslíkovi.", true, 40, "helma.png");
        
        // vložíme věci do prostorů
        namesti.vlozVec(fontana);
        namesti.vlozVec(lopata);
        hospoda.vlozVec(zidle);
        kovar.vlozVec(kovadlina);
        
        //porodíme postavy
        Postava trpaslik = new Postava("trpaslík", "Nazdar, za těch pár drobných si tady nic nekoupíš. Dones mi kus pravého zlata a můžeme se spolu bavit.");
        Postava trpaslice = new Postava("trpaslice", "Vítám tě dobrodruhu. Kromě brnění zde můžeš zakoupit i šaty. Platby přijímá tadyhle otec.");
        Postava ork = new Postava("ork", "JÁ VELKÁ ORK! ZABÍT TĚ SNADNO A RYCHLE!!!");
        Postava princezna = new Postava("princezna", "Co na mě tak civíš? Vůbec nevypadáš jako ten rytíř, kterého jsem si představovala. Takhle jít domů za rodičema nemůžu, přines mi něco na sebe.");
        Postava hospodsky = new Postava("hospodský", "Na dluh nenalíváme! Bez zlatých do hospody nelez.");
        Postava pobuda = new Postava("pobuda", "Kdysi se tady u stolu mariáš, ale po zákazu hazardu v hospodách už tady mám jen kostky.\nTo je taková hra pro děti... Chceš si zahrát?.");
        Postava rytir = new Postava("rytíř", "Povídá se, že za lesem se v noře schovává smradlavý ork, pokud se však neumíš bránit, ani tam nelez.");
        
        //nastavení co kdo chce
        trpaslik.nastavVymenu(helma, saty, "Helmu už ti zpátky nedám. Teď už běž pryč!", "Tohle mi je k ničemu. Zkus něco lesklejšího.", "No perfektní práce! Vypadáš dost chudě... Vezmi si tyhle šaty, jsou mi k ničemu a už mazej pryč.");
        ork.nastavVymenu(null, helma, "Uggh, aagh.", "Huh.", "Hrrrrghah!!");
        princezna.nastavVymenu(saty, null, "Děkuji ti, teď už jsem spokojená.", "Tak to ani nezkoušej, pěkně si mě tím akorát urazil.", "Díky, opilý pobudo. Nepočítej ale s tím, že bys měl dostat moji ruku. Možná půl soudku piva.");
        rytir.nastavProdej(mec, 175, "Vše co jsem ti mohl prodat už máš.", "Tak to je málo můj milej, musíš sehnat více zlatejch.", "No podívejme seee, penízky. Tady máš na oplátku můj starej meč.");
        
        //vložení postav do prostoru
        kovar.vlozPostavu(trpaslik);
        kovar.vlozPostavu(trpaslice);
        nora.vlozPostavu(ork);
        nora.vlozPostavu(princezna);
        namesti.vlozPostavu(rytir);
        hospoda.vlozPostavu(hospodsky);
        hospoda.vlozPostavu(pobuda);
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve kterém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    
    /**
     *  Metoda vrací aktuální číslo (počet) zlatých, se kterými hráč může disponovat.
     *
     *@return     aktuální počet zlatých
     */
    public int getAktualniPocetZlatych() {
        return stavZlatych;
    }
    
    
    /**
     *  Metoda manipuluje s aktuálním počtem zlatých, zlaté jde odčítat i přičítat. (Podle znaménka přičítaného čísla.)
     *
     *@param částka kladná, nebo záporná
     */
    public void zmenaZlatych(int zmena){
        stavZlatych+=zmena;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       notifyObservers();
    }
    
    /**
     *  Metoda vrací třídu Batoh, která patří k aktuálnímu hernímu plánu.
     *
     *@return batoh
     */
     public Batoh getBatoh() {
         return batoh;
     };

    @Override
    public void registerObservers(Observer observer) {
        listObserveru.add(observer);
    }

    @Override
    public void removeObservers(Observer observer) {
        listObserveru.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer listObserveruItem : listObserveru){
            listObserveruItem.update();
        }
    }

}
