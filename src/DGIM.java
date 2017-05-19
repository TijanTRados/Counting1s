import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DGIM {
	
	public static int N;
	public static int vrijeme = 0;
	public static double MAXN = 10000;
	public static Hashtable<Integer, Integer> pretinci = new Hashtable<Integer, Integer>();
	public static int[] brojac;

	//Dodaje novu jedinicu
	public static void dodaj(){
		pretinci.put(vrijeme, 0);
		brojac[0]++;
		prebroji();
	}
	
	//Provjerava ima li nekih vise od 3
	public static void prebroji(){
		for (int i=0; i<brojac.length; i++) if (brojac[i] == 3) azuriraj(i);
	}	
		
	//Spoji one kojih je 3
	public static void azuriraj(int i){
		Set<Integer> keys = pretinci.keySet();
		List<Integer> oni = new ArrayList<Integer>();
        for(Integer key: keys) if (pretinci.get(key) == i) oni.add(key);
        Collections.sort(oni);
        brojac[i+1]++;
        pretinci.put(oni.get(1), i+1);
        brojac[i]-=2;
        pretinci.remove(oni.get(0));

	}
	
	//Provjerava je li ikoji pretinac izasao iz prozora
	public static void provjeri(){
		Iterator<Integer> keys = pretinci.keySet().iterator();
		while( keys.hasNext()) {
			int key = keys.next();
			if ((vrijeme - key) == N) {
				brojac[pretinci.get(key)]--;
				keys.remove();
			};
		}
	}
	
	//Odbacuje pretinac
	public static void odbaci(int i){
		brojac[pretinci.get(i)]--;
		pretinci.remove(i);
	}
	
	//Vraca velicinu pretinca ovisno o potenciji i broja 2
	public static int izracunaj(int i){
		return (int) Math.pow(2, i);
	}
	
	//Vraca log2 od i
	public static int log2(int i){
		return (int) (Math.log(i)/Math.log(2));
	}
	
	//Odgovori na upit k
	public static int odgovori(int k){
		Set<Integer> keys = pretinci.keySet();
		List<Integer> oni = new ArrayList<Integer>();
		for (Integer key: keys) if ((vrijeme - k + 1) <= key) oni.add(key);
		Collections.sort(oni);
		int ukupno = 0;
		for (int i = 1; i<oni.size(); i++) ukupno+= izracunaj(pretinci.get(oni.get(i)));
		if (ukupno == 0) return 0;
		else {
			int zadnji = izracunaj(pretinci.get(oni.get(0)))/2;
			return ukupno + zadnji;
		}
	}
	
	//MAIN
	public static void main(String[] args) {
		
		BufferedReader ulaz = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			N = Integer.parseInt(ulaz.readLine());
		} 
		catch (NumberFormatException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		int MAX = log2(N);
		brojac = new int[MAX];
		try {
		String redak;
		while ((redak = ulaz.readLine()) != null){
			if (redak.split(" ")[0].equals("q")) System.out.println(odgovori(Integer.parseInt(redak.split(" ")[1])));
			else {
				for (int i=0; i < redak.length(); i++){
					vrijeme++;
					provjeri();
					if(redak.charAt(i)=='1') dodaj();
				}
			}
		}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
