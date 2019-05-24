import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Main {
	
	private static final String ODBUDDY_API_URL = "https://api.rsbuddy.com/grandExchange?a=guidePrice&i=";

	
	public static void main(String[] args) throws Exception {
		calcHerbPotsPrices();
	}
	
	private static void calcHerbPotsPrices() throws NumberFormatException, Exception {
		PotItem[] potitems = new PotItem[]{
				new PotItem("Snapdragon", 3004, 3004),
				new PotItem("Toadlfax", 3002, 2998),
				new PotItem("Cadantine", 107, 265),
				new PotItem("Ranarr", 99, 257),
				new PotItem("Lantadyme", 2483, 2481),
				new PotItem("Kwuarm", 105, 263),
				new PotItem("Avantoe", 103, 261),
				new PotItem("Dwarf weed", 109, 267),
				new PotItem("Irit", 101, 259),
				new PotItem("Harralander", 97, 255),
				new PotItem("Tarromin", 95, 253),
				new PotItem("Guam", 91, 249),
				new PotItem("Marentill", 93, 251),
				new PotItem("Torsol", 111, 269),
				
		};
		
		
		for(PotItem potItem : potitems){
			System.out.println(potItem.pot_name + " " + getPotProfit(potItem));
		}
	}
	
	private static int getPotProfit(PotItem potItem) throws NumberFormatException, Exception {
		int potPrice = Integer.parseInt(getFromDoc("buying", ODBUDDY_API_URL + potItem.pot_id));
		int herbPrice = Integer.parseInt(getFromDoc("selling", ODBUDDY_API_URL + potItem.herb_id));
		
		return potPrice - herbPrice;
	}

	private static String getFromDoc(String key, String url) throws Exception {
		String response = readUrl(url);
		response = response.substring(1, response.length()-2);
		
		List<String> splits = Arrays.asList(response.split(","));
		
		return splits
				.stream()
				.filter(
						s -> s.contains(key)
						)
				.findAny().get().split(":")[1];
	}
	
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
}
