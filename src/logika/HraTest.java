package logika;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída HraTest slouží ke komplexnímu otestování
 * třídy Hra.
 *
 * @author   Jarmila Pavlíčková
 * @version  ZS 2016/2017
 */
public class HraTest {
    private Hra hra1;

    //== Datové atributy (statické i instancí)======================================

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
        hra1 = new Hra();
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /***************************************************************************
     * Testuje průběh hry, po zavolání každěho příkazu testuje, zda hra končí
     * a v jaké aktuální místnosti se hráč nachází.
     * Při dalším rozšiřování hry doporučujeme testovat i jaké věci nebo osoby
     * jsou v místnosti a jaké věci jsou v batohu hráče.
     * 
     */
    @Test
    public void testProhra() {
        assertEquals("náměstí", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi předměstí");
        assertEquals(false, hra1.konecHry());
        assertEquals("předměstí", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi les");
        assertEquals(false, hra1.konecHry());
        assertEquals("les", hra1.getHerniPlan().getAktualniProstor().getNazev());
        hra1.zpracujPrikaz("jdi nora");
        assertEquals(true, hra1.konecHry());
        assertFalse(hra1.getKonecnyStav());
    }
    /***************************************************************************
     * Minimálním počtem kroků otestujeme možnost vyhrát. Pro jistotu testu navýšíme počet zlatých 
     * hned ze začátku hry, takže test není vystaven náhodnému generátoru kurzu u vsázení v hospodě.
     * S vsázením by to znamenalo, že test někdy projde a někdy ne.
     * 
     * Průběžně se testuje, že hra ještě neskončila a na konci stav hry jako true = výhra.
     */
    @Test
    public void testRealnyScenarVyhra() {
        //pro 100% jistotu testu navýšíme počet zlatých a nebudeme spoléhat na random faktor vsázení v hospodě
        hra1.getHerniPlan().zmenaZlatych(100);
        hra1.zpracujPrikaz("obchod 175 rytíř");
        assertFalse(hra1.konecHry());
        hra1.zpracujPrikaz("jdi předměstí");
        hra1.zpracujPrikaz("jdi les");
        hra1.zpracujPrikaz("jdi nora");
        hra1.zpracujPrikaz("zahoď meč");
        hra1.zpracujPrikaz("vezmi zlatá_helma");
        assertFalse(hra1.konecHry());
        hra1.zpracujPrikaz("jdi les");
        hra1.zpracujPrikaz("jdi předměstí");
        hra1.zpracujPrikaz("jdi náměstí");
        hra1.zpracujPrikaz("jdi kovárna");
        hra1.zpracujPrikaz("dej zlatá_helma trpaslík");
        assertFalse(hra1.konecHry());
        hra1.zpracujPrikaz("jdi náměstí");
        hra1.zpracujPrikaz("jdi předměstí");
        hra1.zpracujPrikaz("jdi les");
        hra1.zpracujPrikaz("jdi nora");
        assertFalse(hra1.getKonecnyStav());
        hra1.zpracujPrikaz("dej šaty princezna");
        
        assertTrue(hra1.getKonecnyStav());
    }
}
