/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/**
 * Třída Postava představuje postavy, které se nachází na herním plánu a s hráčem komunikují,
 * obchodují a vyměňují předměty.
 *
 * Tato třída je součástí jednoduché textové hry.
 * 
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class Postava
{

    private String jmeno;
    private Vec coChce;
    private Vec coMa;
    private int prodejniCena;
    private String mluvPred;
    private String mluvPo;
    private String recNechce = "Nic od tebe nechci.";
    private String recChce;
    boolean probehlaVymena=false;
    boolean obchoduje=false;
    /**
     * Vytvoření základní postavy, která má pouze jméno a základní text. Neumí obchodovat ani vyměňovat věci.
     *
     * @param jmeno jméno postavy, jednoznačný idetifikátor
     * @param mluvPred text, který vypíše příkaz mluv jako základní
     */
    public Postava(String jmeno, String mluvPred)
    {
        this.jmeno=jmeno;
        this.mluvPred=mluvPred;
    }
    /**
     * Tuto metodu je nutno zavolat a nastavit pro každou postavu, kterou chci nechat vyměňovat věci.
     * Důležité pro příkaz "dej".
     *
     * @param coChce - nastavení věci, kterou postava požaduje
     * @param coMa - nastavení věci, kterou má postava vracet
     * @param mluvPo - po výměně věcí postava říká tuto frázi
     * @param recNechce - pokud postava při výměne nabízenou věc odmítne, řekne tohle
     * @param recChce - pokud postava při výměně nabízenou věc přijme, řekne tohle
     */
    public void nastavVymenu(Vec coChce, Vec coMa, String mluvPo, String recNechce, String recChce){
        this.coChce=coChce;
        this.coMa=coMa;
        this.mluvPred=mluvPred;
        this.mluvPo=mluvPo;
        this.recNechce=recNechce;
        this.recChce=recChce;
    }
    /**
     * Tuto metodu je nutno zavolat a nastavit pro každou postavu, kterou chci nechat obchodovat.
     * Důležité pro příkaz "obchod".
     *
     * @param coMa - nastavení věci, kterou postava prodává
     * @param prodejniCena - kolik nabízená věc stojí
     * @param mluvPo - po prodeji věci postava říká tuto frázi
     * @param recNechce - pokud hráč nenabídne dostatek peněz, postava vypíše tohle
     * @param recChce - pokud hráč nabízenou věc koupí, postava vypíše tohle
     */
    public void nastavProdej(Vec coMa, int prodejniCena, String mluvPo, String recNechce, String recChce){
        this.obchoduje=true;
        this.coMa=coMa;
        this.prodejniCena=prodejniCena;
        this.mluvPo=mluvPo;
        this.recNechce=recNechce;
        this.recChce=recChce;
    }
    /**
     * Vrací jméno postavy.
     *
     * @return jméno postavy
     */
    public String getJmeno(){
        return jmeno;
    }
     /**
     * Pokud postava obchoduje, vrátí text, který by vrátila ikdyž neobchoduje plus informace o nabízeném zboží k tomu.
     * Pokud s postavou již proběhla výměna, vrátí nastavený text, který vrací po výměně.
     *
     * @return text postavy směřovaný na hráče v dané situaci
     */
    public String getMluv(){
        if(obchoduje && !probehlaVymena){
            return mluvPred+getProdejniText();
        }else if(probehlaVymena){
            return mluvPo;
        }else{
            return mluvPred;
        }
    }
    /**
     * Při výměně věcí s hráčem postava říká speciálně nastavené fráze. Ty závisí na tom, zda už provedly svou výměnu,
     * nebo ne.
     *
     * @return text postavy směřovaný na hráče
     */
    public String getRecVymena(){
        if(probehlaVymena){
            return recChce;
        }else{
            return recNechce;
        }
    }
    /**
     * Tato metoda zjišťuje, jestli se nabízená věc rovná věci, kterou postava chce. Pokud je to pravda, vrátí
     * věc, kterou za to nabízí a nastaví proměnnou probehlaVymena na true. V opačném případě, pokud je nabízená věc
     * jiná, vrátí null.
     *
     * @param nabidka věc, kterou hráč nabízí k výměně
     * @return věc, kterou postava vrací z výměny
     */
    public Vec vymena(Vec nabidka){
        if(nabidka.equals(coChce) && !probehlaVymena){
            probehlaVymena=true;
            return coMa;
        }else{
            return null;
        }
    }
    /**
     * Tato metoda slouží k obchodování mezi postavami a hráčem. Parametrem je nabízená částka. Pokud je nabízená částka
     * větší, než cena nabízeného zboží, vrátí metoda nabízenou věc, v opačném případě null.
     * 
     * Obchodovat lze pouze jednou (probehlaVymena = false) a pouze s postavami, které jsou nastaveny jako obchodující
     * (obchoduje = true).
     *
     * @param nabizi počet zlatých, který chce hráč zaplatit
     * @return věc, kterou postava prodává
     */
    public Vec zaplatit(int nabizi){
        if(obchoduje && nabizi>=prodejniCena && !probehlaVymena){
            probehlaVymena=true;
            return coMa;
        }else{
            return null;
        }
    }
    /**
     * Každá postava může prodávat jen jedno zboží, má tedy u sebe i atribut prodejní ceny. Tímto getterem atribut
     * získáme.
     *
     * @return prodejní cena nabízeného zboží
     */
    public int getCena(){
        return prodejniCena;
    }
    /**
     * Aby byl hráč schopen poznat, že daná postava něco nabízí, je potřeba do textového režimu dostat text, který
     * nám tohle řekne. Touto metodou daný text získáme.
     *
     * @return zformátovaný prodejní text obsahující název, cenu a popis zboží
     */
    public String getProdejniText(){
        return "\nNabízím k prodeji:\n" + coMa.getNazev() +" za "+prodejniCena+" zlatých - "+coMa.getPopis();
    }
    /**
     * Pro potřeby některých příkazů je nutno vědět, jakou věc daná postava nabízí.
     *
     * @return věc, kterou postava nabízí
     */
    public Vec getCoMa(){
        return coMa;
    }
    /**
     * Pro potřeby některých příkazů je nutno vědět, jakou věc daná postava chce.
     *
     * @return věc, kterou postava požaduje
     */
    public Vec getCoChce(){
        return coChce;
    }
}
