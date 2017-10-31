package logika;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída ProstorTest slouží ke komplexnímu otestování
 * třídy Prostor
 *
 * @author    Jarmila Pavlíčková, Rostislav Klein
 * @version   ZS 2016/2017
 */
public class ProstorTest
{
    //== Datové atributy (statické i instancí)======================================
    private Prostor namesti;
    private Prostor hospoda;
    private Vec fontana;
    private Postava ork;
    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @Before
    public void setUp() {
        namesti = new Prostor("náměstí","Nacházíte se na prostranství plném prapodivných lidí. Všichni na sebe nahlas křičí a ty bys měl odsud rychle vypadnout.",20,40);
        hospoda = new Prostor("hospoda", "Hospodský nalévá jednu whisky za druhou. Tato špeluňka je mezi lidmi známá spíše jako herna.",20,40);
        fontana = new Vec("fontána", "Plná špinavé vody, na dně je pár drobáků, ale nikdo se neodvažuje sáhnout dovnitř.", false, 2000);
        ork = new Postava("orčík", "JÁ VELKÁ ORK! ZABÍT TĚ SNADNO A RYCHLE!!!");
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeProjit() {      
        
        hospoda.setVychod(namesti);
        namesti.setVychod(hospoda);
        assertEquals(namesti, hospoda.vratSousedniProstor("náměstí"));
        assertEquals(null, hospoda.vratSousedniProstor("les"));
    }
    
    /**
     * Testuje, zda jdou přidávat, dostat a odebírat jednotlivé předměty.
     */
    @Test
    public void testVeci() {
        namesti.vlozVec(fontana);
        assertEquals(namesti.getVec("fontána"),fontana);
        namesti.odeberVec("fontána");
        assertEquals(null,namesti.odeberVec("fontána"));
    }
    
    /**
     * Testuje, zda jdou přidávat, dostat a odebírat jednotlivé postavy.
     */
    @Test
    public void testPostavy() {
        namesti.vlozPostavu(ork);
        assertEquals(namesti.getPostava("orčík"),ork);
        namesti.smazPostavu("orčík");
        assertEquals(null, namesti.getPostava("orčík"));
    }

}
