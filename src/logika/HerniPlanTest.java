/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída PostavaTest slouží ke komplexnímu otestování třídy HerniPlan
 *
 * @author    Rostislav Klein
 * @version   1.00.001
 */
public class HerniPlanTest
{
    private Hra hra1;
    private HerniPlan plan;
    
    private Prostor namesti;
    private Prostor aktualniProstor;
    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        plan=new HerniPlan();
        namesti = new Prostor("náměstí","Nacházíte se na prostranství plném prapodivných lidí. Všichni na sebe nahlas křičí a ty bys měl odsud rychle vypadnout.",12,14);
        aktualniProstor = namesti;
    }

    //== VLASTNÍ TESTY =========================================================
    /**
     * Testuje správnost aktuálního prostoru.
     */
    @Test
    public void testAktProstor()
    { 
        assertEquals(namesti, plan.getAktualniProstor());
    }
    
     /**
     * Testuje správnou aktuální hodnotu a manipulaci se zlatými.
     */
    @Test
    public void testZlate()
    { 
        assertEquals(plan.getAktualniPocetZlatych(), 150);
        plan.zmenaZlatych(-50);
        assertEquals(plan.getAktualniPocetZlatych(), 100);
    }
}