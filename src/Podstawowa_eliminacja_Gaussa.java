import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
//Adam Józef Bogusz
public class Podstawowa_eliminacja_Gaussa
{
	public static final double ZERO=Math.exp(-25);
	public static ArrayList<ArrayList<Double>> przykladowa_macierz;
	protected ArrayList<ArrayList<Double>> macierz;
	protected double rozwiazania_macierzy[];
	
	public static void main(String[] args)
	{
		Podstawowa_eliminacja_Gaussa podstawowa=new Podstawowa_eliminacja_Gaussa();
		
		System.out.println("Czy chcesz użyć przykładowej macierzy, czy wolisz zadeklarować własną?"
				+ "\n1)Użyj przykładowej macierzy"
				+ "\n2)Zadeklaruj własną macierz\n");
		
		Scanner wybor=new Scanner(System.in);
		byte wybrana_opcja;		
		while(true)
		{
			try
			{
				wybrana_opcja=wybor.nextByte();
				if (wybrana_opcja<(byte)1 || wybrana_opcja>(byte)2) throw new InputMismatchException();
				break;
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Podaj liczbę 1 lub 2");
				//wybor.reset();
				wybor.nextLine();
			}
		}
		
		switch(wybrana_opcja) {
		case (1):		
			pokaz_macierz(przykladowa_macierz);
			podst_el_Gaussa(przykladowa_macierz);
			pokaz_macierz(przykladowa_macierz);
			podstawowe_rozwiazanie_macierzy(przykladowa_macierz, null);
			break;
		case (2):
			ustaw_macierz(podstawowa);
			pokaz_macierz(podstawowa.macierz);
			podst_el_Gaussa(podstawowa.macierz);
			pokaz_macierz(podstawowa.macierz);
			podstawowe_rozwiazanie_macierzy(podstawowa.macierz, podstawowa.rozwiazania_macierzy);
			break;
		}
	}

	public Podstawowa_eliminacja_Gaussa()
	{
		ustaw_przykladowa_macierz_do_podst_el_Gaussa();
	}
	
	public static void pokaz_macierz(ArrayList<ArrayList<Double>> macierz)
	{
		try {
			System.out.println("Macierz");
			for (int i=0; i<macierz.size(); i++)
			{
				for(int j=0; j<macierz.get(i).size(); j++)
				{
					System.out.print(macierz.get(i).get(j)+"\t");
				}
			System.out.println();
			}
			System.out.println();
		}
		catch(NullPointerException ex)
		{
			System.out.println("Macierz nie istnieje");
		}
	}
		
	public static ArrayList<ArrayList<Double>> podst_el_Gaussa(ArrayList<ArrayList<Double>> macierz)
	{
		if (macierz==null) return null;
		
		ArrayList<ArrayList<Double>> macierz_kopia=macierz;
				
		for(int krok=1; krok<(macierz.size()); krok++)
		{
			if (Math.abs(macierz_kopia.get(krok-1).get(krok-1))<=ZERO)
			{
				//System.out.println("Zero na przekątnej - podstawowa eliminacja Gaussa zawiodła");
				return null;
			}
					
			for(int podkrok=krok; podkrok<(macierz.size()); podkrok++)
			{				
				double wspolczynnik=(macierz.get(podkrok).get(krok-1))/(macierz.get(krok-1).get(krok-1));
				
				for(int wiersz=0; wiersz<=(macierz.size()); wiersz++)					
				{
					macierz_kopia.get(podkrok).set(wiersz, (double)Math.round((1000000)*(macierz.get(podkrok).get(wiersz) - (wspolczynnik * macierz.get(krok-1).get(wiersz))))/1000000);
				}
			}				
			macierz=macierz_kopia;			
		}
		
		if (Math.abs(macierz_kopia.get(macierz.size()-1).get(macierz.size()-1))<=ZERO)
		{
			//System.out.println("Zero na przekątnej - podstawowa eliminacja Gaussa zawiodła");
			return null;
		}
		return macierz_kopia;
	}

	public static void podstawowe_rozwiazanie_macierzy(ArrayList<ArrayList<Double>> macierz, double[] rozwiazania)
	{
		if (podst_el_Gaussa(macierz)!=null) 
		{
			rozwiazania=new double[macierz.size()];
			for (int krok=macierz.size()-1; krok>=0; krok--)
			{
				double suma=0;
				for(int j=krok+1; j<macierz.size(); j++)
				{
					suma+=rozwiazania[j]*macierz.get(krok).get(j);
				}
				rozwiazania[krok]=(double)Math.round(1000000*( (macierz.get(krok).get(macierz.size())-suma) / macierz.get(krok).get(krok) ))/1000000;
				System.out.println("x_"+(krok+1)+" = "+rozwiazania[krok]);
			}
		}
		else System.out.println("Zero na przekątnej - podstawowa eliminacja Gaussa zawiodła");		
	}

	public static void ustaw_macierz(Podstawowa_eliminacja_Gaussa obiekt)
	{
		obiekt.macierz=new ArrayList<>();
		
		System.out.print("Podaj rozmiar macierzy: ");
		//@SuppressWarnings("resource")
		Scanner skaner=new Scanner(System.in);
		int rozmiar;
		
		while(true)
		{
			try
			{
				rozmiar=skaner.nextInt();
				break;
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Podaj liczbę całkowitą");
				skaner.nextLine();
			}
		}
		System.out.println("Podawaj kolejne wartości w wierszach macierzy "+rozmiar+" na "+(rozmiar+1)+" (ostatnia wartość każdego wiersza to wyraz wolny)");		
		for(int i=0; i<rozmiar; i++)
		{
			obiekt.macierz.add(new ArrayList<Double>());
			
			for(int j=0; j<(rozmiar+1); j++)
			{
				System.out.print("\t["+i+"]["+j+"] = ");
				while (true)
				{
					try
					{
						double wartosc=skaner.nextDouble();
						Double.toString(wartosc).replace(".", ",");
						obiekt.macierz.get(i).add(j, (double)(wartosc));
						break;
					}
					catch(InputMismatchException ex)
					{
						System.out.println("Podaj liczbę zmiennoprzecinkową");
						System.out.print("\t["+i+"]["+j+"] = ");
						skaner.nextLine();
					}
				}
			}
		}
	}
	
	public void ustaw_przykladowa_macierz_do_podst_el_Gaussa()
	{
		try	{ przykladowa_macierz.clear(); }
		catch (Exception ex) { System.out.println("Nie można wyczyścić macierzy"); }
		
		przykladowa_macierz=new ArrayList<>();
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(0).add(0, (double)1);
		przykladowa_macierz.get(0).add(1, (double)1);
		przykladowa_macierz.get(0).add(2, (double)0);
		przykladowa_macierz.get(0).add(3, (double)(-3));
		przykladowa_macierz.get(0).add(4, (double)1);
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(1).add(0, (double)1);
		przykladowa_macierz.get(1).add(1, (double)4);
		przykladowa_macierz.get(1).add(2, (double)-1);
		przykladowa_macierz.get(1).add(3, (double)(-4));
		przykladowa_macierz.get(1).add(4, (double)(-2));
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(2).add(0, (double)(0.5));
		przykladowa_macierz.get(2).add(1, (double)(0.5));
		przykladowa_macierz.get(2).add(2, (double)(-3));
		przykladowa_macierz.get(2).add(3, (double)(-5.5));
		przykladowa_macierz.get(2).add(4, (double)(1.5));
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(3).add(0, (double)(1.5));
		przykladowa_macierz.get(3).add(1, (double)(3));
		przykladowa_macierz.get(3).add(2, (double)(-5));
		przykladowa_macierz.get(3).add(3, (double)(-9));
		przykladowa_macierz.get(3).add(4, (double)(-0.5));
	}
}
