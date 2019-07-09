
public class LiczbaZmiennoprzecinkowa {

	private int iloscBitowWykladnika = 4;
	private int iloscBitowMantysy = 5;
	private int iloscBitowLiczby = iloscBitowMantysy + iloscBitowWykladnika + 1;
	private int wartoscBiasu = (int) (Math.pow(2, iloscBitowWykladnika - 1) - 1);
	private int znak;
	private String liczbaBin;
	private String liczbaBinPonownie;
	private String mantysaBin;
	private String wykladnikBin;

	public LiczbaZmiennoprzecinkowa(String liczbaBin) {
		this.liczbaBin = liczbaBin;
		this.liczbaBinPonownie = liczbaBin;
		this.wykladnikBin = liczbaBin.substring(1, iloscBitowWykladnika + 1);
		this.mantysaBin = liczbaBin.substring(iloscBitowWykladnika + 1, iloscBitowLiczby);
	}

	public LiczbaZmiennoprzecinkowa(String liczbaBin, int liczbaBitowMantysy, int liczbaBitowWykladnika) {
		this.liczbaBin = liczbaBin;
		this.liczbaBinPonownie = liczbaBin;
		this.iloscBitowWykladnika = liczbaBitowWykladnika;
		this.iloscBitowMantysy = liczbaBitowMantysy;
		this.iloscBitowLiczby = iloscBitowMantysy + iloscBitowWykladnika + 1;
		this.wykladnikBin = liczbaBin.substring(1, iloscBitowWykladnika + 1);
		this.mantysaBin = liczbaBin.substring(iloscBitowWykladnika + 1, iloscBitowLiczby);
		this.wartoscBiasu = (int) (Math.pow(2, iloscBitowWykladnika - 1) - 1);
	}

	public LiczbaZmiennoprzecinkowa pomnozLiczby(LiczbaZmiennoprzecinkowa other) {

		String A = "1" + this.liczbaBin.substring(this.iloscBitowWykladnika + 1);
		String B = "1" + other.liczbaBin.substring(this.iloscBitowWykladnika + 1);
		String wynik = null;
		String resztaMantysy = null;
		String znak = null;
		int wykladnikADec = Integer.parseInt(this.wykladnikBin, 2) - wartoscBiasu;
		int wykladnikBDec = Integer.parseInt(other.wykladnikBin, 2) - wartoscBiasu;
		String wykladnikKoncowy = Integer.toBinaryString((wykladnikADec + wykladnikBDec + wartoscBiasu));

		if (((this.liczbaBin.charAt(0)) == '1') || other.liczbaBin.charAt(0) == '1') {
			znak = "1";
		} else {
			znak = "0";
		}
		;
		if (wykladnikKoncowy.length() < iloscBitowWykladnika) {
			for (int i = 0; i <= iloscBitowWykladnika - wykladnikKoncowy.length(); i++) {
				wykladnikKoncowy = "0".concat(wykladnikKoncowy);
			}
		}

		if (A.length() > B.length()) { // wyrownanie liczb A i B poprzez dodanie zer na poczatku
			int diff = A.length() - B.length();
			for (int i = 0; i < diff; i++) {
				B = "0".concat(B);
			}
		}
		if (A.length() < B.length()) {
			int diff = B.length() - A.length();
			for (int i = 0; i < diff; i++) {
				A = "0".concat(A);
			}
		}

		String[] liczby = new String[A.length()]; // tworze tablice stringow o dlugosci ilosci bitow liczb po wyrownaniu

		for (int i = 0; i < A.length(); i++) { // wypelniam je, zeby nie bylo nullpointerexception
			liczby[i] = "";
		}

		for (int i = A.length() - 1; i >= 0; i--) { // mnozenie i zapisywanie kazdej liczby tymczasowej w kolejnych
			// zmiennych tablicy
			for (int j = A.length() - 1; j >= 0; j--) {

				int k = A.length() - i - 1;
				if (k == A.length()) {
					break;
				}
				if (A.charAt(j) == '0' && B.charAt(i) == '0') {
					liczby[k] = "0".concat(liczby[k]);
				} else if ((A.charAt(j) == '1' && B.charAt(i) == '0') || (A.charAt(j) == '0' && B.charAt(i) == '1')) {
					liczby[k] = "0".concat(liczby[k]);
				} else if (A.charAt(j) == '1' && B.charAt(i) == '1') {
					liczby[k] = "1".concat(liczby[k]);
				}
			}
		}

		for (int i = 1; i < A.length(); i++) { // dodanie zer do kazdej kolejnej liczby w celu poprawnego dodania
			for (int j = A.length() - i; j < A.length(); j++) {
				liczby[i] = liczby[i].concat("0");
			}
		}

		wynik = liczby[0]; // przypisanie do wyniku liczby z tablicy o indeksie 0

		for (int i = 1; i < A.length(); i++) { // dodawanie liczby o indeksie od 1 w gore
			wynik = dodajLiczby(wynik, liczby[i]);
		}

		if (wynik.length() > (A.length() + B.length()) - 1) {
			wykladnikKoncowy = dodajLiczby(wykladnikKoncowy, "1");
		};
		resztaMantysy = wynik.substring(iloscBitowMantysy + 1);
		wynik = wynik.substring(0, iloscBitowMantysy + 1);

		if (wynik.charAt(iloscBitowMantysy - 1) == '1' && wynik.charAt(iloscBitowMantysy) == '0') {
			if (resztaMantysy.matches("101.*")) {
				wynik = dodajLiczby(wynik, "1");
			} else if (wynik.charAt(iloscBitowMantysy - 1) == '0' && wynik.charAt(iloscBitowMantysy) == '0') {
				if (resztaMantysy.matches("101.*")) {
					wynik = dodajLiczby(wynik, "1");
				}
			} else if (wynik.charAt(iloscBitowMantysy) == '1') {
				if (resztaMantysy.matches("1.*")) {
					wynik = dodajLiczby(wynik, "1");
				}
			}
		}
		wynik = znak + wykladnikKoncowy + wynik.substring(1);

		return new LiczbaZmiennoprzecinkowa(wynik, wynik.substring(wykladnikKoncowy.length()).length() - 1,
				wykladnikKoncowy.length());
	}

	public LiczbaZmiennoprzecinkowa dodaj(LiczbaZmiennoprzecinkowa other) {

		String mantysaPoDodaniu = null;
		String mantysaA = "1" + this.mantysaBin;
		String mantysaB = "1" + other.mantysaBin;
		String resztaMantysy = null;
		String wykladnikKoncowy = null;
		int wykladnikADec = Integer.parseInt(this.wykladnikBin, 2) - wartoscBiasu;
		int wykladnikBDec = Integer.parseInt(other.wykladnikBin, 2) - wartoscBiasu;
		String wynik = null;

		if (wykladnikADec > wykladnikBDec) {
			for (int i = 0; i < wykladnikADec - wykladnikBDec; i++) {
				mantysaB = "0".concat(mantysaB);
				mantysaA = mantysaA.concat("0");
			}
			wykladnikKoncowy = this.wykladnikBin;
		}

		if (wykladnikADec < wykladnikBDec) {
			for (int i = 0; i < wykladnikBDec - wykladnikADec; i++) {
				mantysaA = "0".concat(mantysaA);
				mantysaB = mantysaB.concat("0");
			}
			wykladnikKoncowy = other.wykladnikBin;
		}

		if (wykladnikADec == wykladnikBDec) {
			wykladnikKoncowy = this.wykladnikBin;
		}

		mantysaPoDodaniu = dodajLiczby(mantysaA, mantysaB);

		if (mantysaA.length() < mantysaPoDodaniu.length()) {
			for (int i = 0; i < (mantysaPoDodaniu.length() - mantysaA.length()); i++) {
				wykladnikKoncowy = dodajLiczby(wykladnikKoncowy, "1");
			}
		}
		
		resztaMantysy = mantysaPoDodaniu.substring(iloscBitowMantysy + 1);
		mantysaPoDodaniu = mantysaPoDodaniu.substring(0, iloscBitowMantysy + 1);

		if (mantysaPoDodaniu.charAt(iloscBitowMantysy - 1) == '0'
				&& mantysaPoDodaniu.charAt(iloscBitowMantysy) == '0') {
			if (resztaMantysy.matches("101.*")) {
				mantysaPoDodaniu = dodajLiczby(mantysaPoDodaniu, "1");
			}
		} else if (mantysaPoDodaniu.charAt(iloscBitowMantysy) == '1') {
			if (resztaMantysy.matches("1.*")) {
				mantysaPoDodaniu = dodajLiczby(mantysaPoDodaniu, "1");
			}
		}
		wynik = "0" + wykladnikKoncowy + mantysaPoDodaniu.substring(1);

		return new LiczbaZmiennoprzecinkowa(wynik, mantysaPoDodaniu.substring(1).length(), wykladnikKoncowy.length());
	}

	public String dodajLiczby(String A, String B) {

		String wynik = new String();
		int reszta = 0; // nadmiar spowodowany dwoma jedynkami w kolumnie

		if (A.length() > B.length()) {
			int roznica = A.length() - B.length();
			for (int i = 0; i < roznica; i++) {
				B = "0".concat(B);
			}
		}
		if (A.length() < B.length()) {
			int roznica = B.length() - A.length();
			for (int i = 0; i < roznica; i++) {
				A = "0".concat(A);
			}
		}
		for (int i = A.length() - 1; i >= 0; i--) {

			if (A.charAt(i) == B.charAt(i) && A.charAt(i) == '1') {

				if (reszta == 0) {
					wynik = "0".concat(wynik);
					reszta = 1;
				} else if (reszta == 1) {
					wynik = "1".concat(wynik);
					reszta = 1;
				}
			} else if (A.charAt(i) == B.charAt(i) && A.charAt(i) == '0') {
				if (reszta == 0) {
					wynik = "0".concat(wynik);
				} else if (reszta == 1) {
					wynik = "1".concat(wynik);
					reszta = 0;
				}
			} else if (A.charAt(i) != B.charAt(i) && (A.charAt(i) == '1' || B.charAt(i) == '1')) {
				if (reszta == 0) {
					wynik = "1".concat(wynik);
				} else if (reszta == 1) {
					wynik = "0".concat(wynik);
					reszta = 1;
				}
			}
		}
		if (reszta == 1)
			wynik = "1".concat(wynik);

		return wynik;
	}

	public String getLiczbaBin() {
		return liczbaBin;
	}

	public void ustawIloscBitow(int iloscBitowMantysy, int iloscBitowWykladnika) {

		this.liczbaBin = liczbaBinPonownie;
		this.iloscBitowMantysy = iloscBitowMantysy;
		this.iloscBitowWykladnika = iloscBitowWykladnika;
		this.iloscBitowLiczby = iloscBitowMantysy + iloscBitowWykladnika + 1;
		this.wartoscBiasu = (int) (Math.pow(2, iloscBitowWykladnika - 1) - 1);

		if (this.liczbaBin.length() > iloscBitowLiczby) {
			this.liczbaBin = liczbaBin.substring(0, iloscBitowLiczby);
		} else if (this.liczbaBin.length() < iloscBitowLiczby) {
			while (this.liczbaBin.length() != iloscBitowLiczby)
				this.liczbaBin = liczbaBin.concat("0");
		}
		this.wykladnikBin = liczbaBin.substring(1, iloscBitowWykladnika + 1);
		this.mantysaBin = liczbaBin.substring(iloscBitowWykladnika + 1, iloscBitowLiczby);
	}

	// @Override
	public String toString() {
		String znaczek = null;

		if (dajZnak() == 0) {
			znaczek = "+";
		} else {
			znaczek = "-";
		}
		return "LiczbaZmiennoprzecinkowa(znak=" + znaczek + ", wyk³adnik=" + obliczenieWykladnika(this.wykladnikBin)
				+ " (" + wykladnikBin + "), mantysa=" + obliczanieMantysy(this.mantysaBin) + " (" + mantysaBin
				+ "), wartoœæ=" + dajWartosc() + ")";
	}

	private int dajZnak() {
		if (liczbaBin.charAt(0) == '0') {
			znak = 0;
		} else if (liczbaBin.charAt(0) == '1') {
			znak = 1;
		}
		return znak;
	}

	private double obliczenieWykladnika(String wykladnikBin) {
		return Math.pow(2, Integer.parseInt(wykladnikBin, 2) - wartoscBiasu);
	}

	public double dajWartosc() {
		return Math.pow(-1, dajZnak()) * obliczanieMantysy(this.mantysaBin)
				* Math.pow(2, Integer.parseInt(this.wykladnikBin, 2) - this.wartoscBiasu);
	}

	private double obliczanieMantysy(String mantysaDoObliczenia) {
		
		double calculatedMantissa = 0;
		for (int i = 0; i < mantysaDoObliczenia.length(); i++) { // gdy pojawi siê 1 w ciagu dodaje dwojke w
			// odpowiedniej potedze
			if (mantysaDoObliczenia.charAt(i) == '1') {
				calculatedMantissa += Math.pow(2, -i - 1);
			}
		}
		return 1.0 + calculatedMantissa;
	}

	public int getIloscBitowMantysy() {
		return iloscBitowMantysy;
	}

	public int getIloscBitowWykladnika() {
		return iloscBitowWykladnika;
	}

	public int getWartoscBiasu() {
		return wartoscBiasu;
	}

	public void setLiczbaBin(String liczbaBin, int iloscMantysy, int iloscWykladnika) {
		this.liczbaBin = liczbaBin;
		this.liczbaBinPonownie = liczbaBin;
		ustawIloscBitow(iloscMantysy, iloscWykladnika);
	}

	public int getIloscBitowLiczby() {
		return iloscBitowLiczby;
	}
}