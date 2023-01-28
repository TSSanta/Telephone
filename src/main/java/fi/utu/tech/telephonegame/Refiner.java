package fi.utu.tech.telephonegame;

import java.util.Random;

public class Refiner {

	private static final String[] text = new String(
			"Voit käyttää kierrätyskynttilöiden valmistukseen ylimääräisiä "
			+ "kynttilänpätkiä katkenneita kynttilöitä ja kynttilän palasia "
			+ "Lisäksi tarvitset kynttilän sydänlankaa Sitä voi ostaa isoista "
			+ "marketeista tai askartelukaupoista Tarvitset myös puutikkuja, "
			+ "kuten grillaustikkuja Ne pitävät sydänlangan paikoillaan valmistuksen aikana "
			+ "Sulata kynttilänpätkät puhtaissa peltipurkeiss jotka ovat "
			+ "kiehuvassa vedessä Vettä varten tarvitset vanhan kattilan "
			+ "Kynttilänpätkiä ei saa sulattaa suoraan kattilassa koska "
			+ "silloin voi syttyä tulipalo "
			+ "Kaada sulanut kynttilämassa muotteihin, joissa sydänlangat "
			+ "ovat valmiina Kuuma kynttilämassa kovettuu jäähtyessään "
			+ "Kierrätyskynttilöiden muotteja ei oteta pois vaan ne jäävät "
			+ "kynttilöiden kuoriksi "
			+ "Muotteina voit käyttää esimerkiksi hyvin pestyjä peltipurkkeja "
			+ "joista olet poistanut etiketit "
			+ "Vanhat kahvikupit ovat kauniita kynttilämuotteja Lasipurkkeja "
			+ "ei saa käyttää muotteina koska ne saattavat särkyä kuumetessaan").split(" ");
	private static Random rnd = new Random();

	
	/*
	 * The refineText method is used to change the message
	 * Now it is time invent something fun! 
	 * 
	 * In the example implementation a random work from a word list is added to the end of the message.
	 * 
	 * Please keep the message readable. No ROT13 etc, please
	 * 
	 */
	public static String refineText(String inText) {
		String outText = inText;
		
		// Vaihdetaan satunainen sana viestissä toiseksi
		String[] textSpaces = inText.split(" ");
		textSpaces[rnd.nextInt(textSpaces.length)] = text[rnd.nextInt(text.length)];
		outText = String.join(" ", textSpaces);
		
		// Esimerkin sananlisäys viestin perään
		outText = outText + " " + text[rnd.nextInt(text.length)];

		return outText;
	}

	
	/*
	 * This method changes the color. No editing needed.
	 * 
	 * The color hue value is an integer between 0 and 360
	 */
	public static Integer refineColor(Integer inColor) {
		return (inColor + 20) < 360 ? (inColor + 20) : 0;
	}

}
