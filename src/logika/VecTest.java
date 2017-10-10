/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PostavaTest slouží ke komplexnímu otestování třídy Vec
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class VecTest
{
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
        zidle = new Vec("židle", "Stará ztrouchnivělá židle z masivu. Nemá využití.", true, 25);
        tajnaVec = new Vec("tajnost", "neřeknu", false, 2000);
    }

    //== VLASTNÍ TESTY =========================================================
    /**
     * Testuje elementární vlastní předmětů.
     */
    @Test
    public void testVec()
    { 
        assertEquals("židle", zidle.getNazev());
        assertFalse(tajnaVec.isPrenositelna());
        assertEquals(zidle.getVaha(), 25);
    }

}