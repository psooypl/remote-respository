import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class LiczbaZmiennoprzecinkowaTest {

	@Test
	void konstruktorTest() {
		LiczbaZmiennoprzecinkowa test = new LiczbaZmiennoprzecinkowa("0101011001");
		assertEquals("0101011001", test.getLiczbaBin());
	}

	@Test 
	void getteryTest() {
		LiczbaZmiennoprzecinkowa test = new LiczbaZmiennoprzecinkowa("0101011001");
		assertEquals(5, test.getIloscBitowMantysy());
		assertEquals(4, test.getIloscBitowWykladnika());
		assertEquals(7, test.getWartoscBiasu());
		assertEquals(10, test.getIloscBitowLiczby());
	}

	@Test
	void dajWartoscTest() {
		LiczbaZmiennoprzecinkowa test = new LiczbaZmiennoprzecinkowa("0101011001");
		assertEquals(14.25, test.dajWartosc());		
		LiczbaZmiennoprzecinkowa test1 = new LiczbaZmiennoprzecinkowa("0100000000");
		assertEquals(2.0, test1.dajWartosc());	
		LiczbaZmiennoprzecinkowa test2 = new LiczbaZmiennoprzecinkowa("1100000000");
		assertEquals(-2.0, test2.dajWartosc());		
	}

	@Test 
	void ustawIloscBitowTest(){
		LiczbaZmiennoprzecinkowa test = new LiczbaZmiennoprzecinkowa("0101011001");
		test.ustawIloscBitow(1, 1);
		assertEquals("010", test.getLiczbaBin());
		test.ustawIloscBitow(1, 8);
		assertEquals("0101011001", test.getLiczbaBin());
		test.ustawIloscBitow(23, 8);
		assertEquals("01010110010000000000000000000000", test.getLiczbaBin());
	}


	@Test
	void toStringTest() {
		LiczbaZmiennoprzecinkowa test = new LiczbaZmiennoprzecinkowa("0100000000");
		test.ustawIloscBitow(5, 4);
		assertEquals("LiczbaZmiennoprzecinkowa(znak=+, wyk³adnik=2.0 (1000), mantysa=1.0 (00000), wartoœæ=2.0)", test.toString());
	}

	@Test 
	void dodajTest () {
		LiczbaZmiennoprzecinkowa test = new LiczbaZmiennoprzecinkowa("0100000000");
		LiczbaZmiennoprzecinkowa test1 = new LiczbaZmiennoprzecinkowa("0100000000");
		assertEquals(4.0, test.dodaj(test1).dajWartosc());
		test.setLiczbaBin("01001001", 3,4);
		test1.setLiczbaBin("01011101", 3, 4);
		assertEquals(30.0, test.dodaj(test1).dajWartosc());
		test.setLiczbaBin("01001001", 3,4);
		test1.setLiczbaBin("01011111", 3, 4);
		assertEquals(36.0, test.dodaj(test1).dajWartosc());
	}
	
	@Test
	void pomnozTest() {
		LiczbaZmiennoprzecinkowa test = new LiczbaZmiennoprzecinkowa("0000000000");
		LiczbaZmiennoprzecinkowa test1 = new LiczbaZmiennoprzecinkowa("0000000000");
		
		test.setLiczbaBin( "01001001", 3, 4);
		test1.setLiczbaBin("01011101", 3, 4);
		assertEquals(120.0,test.pomnozLiczby(test1).dajWartosc());
		
		test.setLiczbaBin( "01001011", 3, 4);
		test1.setLiczbaBin("01011111", 3, 4);
		assertEquals(160.0,test.pomnozLiczby(test1).dajWartosc());
		
		test.setLiczbaBin( "0100010000", 5, 4);
		test1.setLiczbaBin("0100010000", 5, 4);
		assertEquals(9.0,test.pomnozLiczby(test1).dajWartosc());
		
		test.setLiczbaBin( "1100010000", 5, 4);
		test1.setLiczbaBin("0100010000", 5, 4);
		assertEquals(-9.0,test.pomnozLiczby(test1).dajWartosc());
	}

}
