/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PostavaTest slouží ke komplexnímu otestování třídy Postava
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class PostavaTest
{
    private Postava pobuda;
    private Vec zidle;
    private Vec tajnaVec;
    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        pobuda = new Postava("pobuda", "první řeč pobudy");
        zidle = new Vec("židle", "Stará ztrouchnivělá židle z masivu. Nemá využití.", true, 25);
        tajnaVec = new Vec("tajnost", "neřeknu", true, 15);
    }

    //== VLASTNÍ TESTY =========================================================
    /**
     * Testuje, zda postavy umí správně vyměňovat věci a podle toho se chovat.
     */
    @Test
    public void testVymena()
    { 
      pobuda.nastavVymenu(zidle, tajnaVec, "po", "nechci", "chci");
      assertEquals(pobuda.getMluv(),"první řeč pobudy");
      Vec vymenenaVec = pobuda.vymena(zidle);
      assertEquals(tajnaVec, vymenenaVec);
      assertEquals(pobuda.getMluv(),"po");
    }
    
    /**
     * Testuje, zda postavy umí správně obchodovat.
     */
    @Test
    public void testObchod()
    { 
      pobuda.nastavProdej(tajnaVec, 20, "po", "nechci", "chci");
      assertEquals(pobuda.getCena(),20);
      assertEquals(pobuda.getCoMa(), tajnaVec);
      assertEquals(pobuda.getMluv(),"první řeč pobudy"+pobuda.getProdejniText());
      Vec vymenenaVec = pobuda.zaplatit(20);
      assertEquals(tajnaVec, vymenenaVec);
      assertEquals(pobuda.getMluv(),"po");
    }

}