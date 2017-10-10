/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída BatohTest slouží ke komplexnímu otestování třídy Batoh
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class BatohTest
{
    private Batoh batoh;
    private Vec saty;
    private Vec zidle;
    private Vec obraz;
    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        batoh = new Batoh(60);  
        saty = new Vec("šaty", "popis", true, 20);
        zidle = new Vec("židle", "popis", true, 30);
        obraz = new Vec("obraz", "popis", true, 20);
    }

    //== VLASTNÍ TESTY =========================================================
    /**
     * Testuje, zda do batohu lze přidávat věci aniž by překročil limit.
     */
    @Test
    public void testVaha()
    { 
      assertTrue(batoh.pridejVec(saty));
      assertTrue(batoh.pridejVec(zidle));
      assertEquals(batoh.getAktualniVaha(),50);
      assertFalse(batoh.pridejVec(obraz));
    }
    
    /**
     * Testuje, zda do a z batohu lze strkat, vybírat a zahazovat věci.
     */
    @Test
    public void testManipulace()
    { 
      batoh.pridejVec(saty);
      batoh.pridejVec(zidle);
      assertNull(batoh.getVec("obraz"));
      assertEquals(batoh.getVec("židle"),zidle);
      assertEquals(batoh.zahodVec("šaty"),saty);
      assertNull(batoh.getVec("šaty"));
    }
}