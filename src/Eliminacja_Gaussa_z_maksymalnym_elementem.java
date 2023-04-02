import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
//Adam Józef Bogusz
public class Eliminacja_Gaussa_z_maksymalnym_elementem extends Podstawowa_eliminacja_Gaussa
{
	protected int niewiadome[];
	
	public static void main(String[] args)
	{
		Eliminacja_Gaussa_z_maksymalnym_elementem maksymalna=new Eliminacja_Gaussa_z_maksymalnym_elementem();
		
		System.out.println("Czy chcesz użyć przykładowej macierzy, czy wolisz zadeklarować własną?"
				+ "\n1)Użyj przykładowej macierzy do metody kolumnowej"
				+ "\n2)Użyj przykładowej macierzy do metody wierszowej"
				+ "\n3)Zadeklaruj własną macierz do metody kolumnowej"
				+ "\n4)Zadeklaruj własną macierz do metody wierszowej\n");
		
		Scanner wybor=new Scanner(System.in);
		byte wybrana_opcja;		
		while(true)
		{
			try
			{
				wybrana_opcja=wybor.nextByte();
				if (wybrana_opcja<(byte)1 || wybrana_opcja>(byte)4) throw new InputMismatchException();
				break;
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Podaj liczbę 1, 2, 3 lub 4");
				//wybor.reset();
				wybor.nextLine();
			}
		}

		switch(wybrana_opcja) {
		case (1):
			maksymalna.macierz=ustaw_przykladowa_macierz_do_maksymalnej_el_Gaussa();
			maksymalna.niewiadome=new int[maksymalna.macierz.size()];
			for (int i=0; i<maksymalna.niewiadome.length; i++) maksymalna.niewiadome[i]=i+1;
			pokaz_macierz(maksymalna.macierz);
			kolumn_el_Gaussa(maksymalna.macierz, maksymalna.niewiadome);
			pokaz_macierz(maksymalna.macierz);
			kolumnowe_rozwiazanie_macierzy(maksymalna.macierz, maksymalna.rozwiazania_macierzy, maksymalna.niewiadome);
			break;
		case (2):		
			pokaz_macierz(przykladowa_macierz);
			wiersz_el_Gaussa(przykladowa_macierz);
			pokaz_macierz(przykladowa_macierz);
			podstawowe_rozwiazanie_macierzy(przykladowa_macierz, null);
			break;
		case (3):
			ustaw_macierz(maksymalna);
			maksymalna.niewiadome=new int[maksymalna.macierz.size()];
			for (int i=0; i<maksymalna.niewiadome.length; i++) maksymalna.niewiadome[i]=i+1;
			pokaz_macierz(maksymalna.macierz);
			kolumn_el_Gaussa(maksymalna.macierz, maksymalna.niewiadome);
			pokaz_macierz(maksymalna.macierz);
			kolumnowe_rozwiazanie_macierzy(maksymalna.macierz, maksymalna.rozwiazania_macierzy, maksymalna.niewiadome);
			break;
		case (4):
			ustaw_macierz(maksymalna);
			pokaz_macierz(maksymalna.macierz);
			wiersz_el_Gaussa(maksymalna.macierz);
			pokaz_macierz(maksymalna.macierz);
			podstawowe_rozwiazanie_macierzy(maksymalna.macierz, null);
			break;
		}
	}
	
	public Eliminacja_Gaussa_z_maksymalnym_elementem()
	{
		ustaw_przykladowa_macierz_do_maksymalnej_el_Gaussa();
	}
	
	public static int[] zamien_miejscami(int niewiadome[], int indeks_1, int indeks_2)
	{
		int chwilowa=niewiadome[indeks_1];
		niewiadome[indeks_1]=niewiadome[indeks_2];
		niewiadome[indeks_2]=chwilowa;
		return niewiadome;
	}
	
	public static ArrayList<ArrayList<Double>> kolumn_el_Gaussa(ArrayList<ArrayList<Double>> macierz, int niewiadome[])
	{
		if (macierz==null) return null;
		
		ArrayList<ArrayList<Double>> macierz_kopia=macierz;		

		for(int krok=1; krok<(macierz.size()); krok++)
		{
			double maks= maks_el_w_kol(macierz_kopia, krok);
			int indeks_maks=macierz_kopia.get(krok-1).indexOf(maks);
			
			if (macierz_kopia.get(krok-1).get(krok-1) != maks)
			{
				for (int i=0; i<macierz_kopia.size(); i++)
					{
						Collections.swap(macierz_kopia.get(i), krok-1, indeks_maks);
					}
				niewiadome=zamien_miejscami(niewiadome, krok-1, indeks_maks);
			}
			
			if (Math.abs(macierz_kopia.get(krok-1).get(krok-1))<=ZERO)
			{
				//System.out.println("Zero na przekątnej - kolumnowa eliminacja Gaussa zawiodła");
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
			pokaz_macierz(macierz_kopia);
		}
		
		if (Math.abs(macierz_kopia.get(macierz.size()-1).get(macierz.size()-1))<=ZERO)
		{
			//System.out.println("Zero na przekątnej - kolumnowa eliminacja Gaussa zawiodła");
			return null;
		}
		return macierz_kopia;
	}
	
	public static ArrayList<ArrayList<Double>> wiersz_el_Gaussa(ArrayList<ArrayList<Double>> macierz)
	{
		if (macierz==null) return null;
		
		ArrayList<ArrayList<Double>> macierz_kopia=macierz;

		for(int krok=1; krok<(macierz.size()); krok++)
		{
			double maks= maks_el_w_wier(macierz_kopia, krok);
			System.out.println(maks+"<--");
			int indeks_maks=-1;
			for (int i=krok-1; i<macierz.get(krok-1).size()-1; i++)
			{
				if ((macierz_kopia.get(i).get(krok-1)) == maks)
				{
					indeks_maks=i;
					System.out.println(indeks_maks+"<-indeks");
				}
			}
			
			if (macierz_kopia.get(krok-1).get(krok-1) != maks)
			{
				Collections.swap(macierz_kopia, krok-1, indeks_maks);
				
			}
			
			if (Math.abs(macierz_kopia.get(krok-1).get(krok-1))<=ZERO)
			{
				//System.out.println("Zero na przekątnej - kolumnowa eliminacja Gaussa zawiodła");
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
			pokaz_macierz(macierz_kopia);
		}
		
		if (Math.abs(macierz_kopia.get(macierz.size()-1).get(macierz.size()-1))<=ZERO)
		{
			//System.out.println("Zero na przekątnej - kolumnowa eliminacja Gaussa zawiodła");
			return null;
		}
		return macierz_kopia;
	}
	
	public static double maks_el_w_kol(ArrayList<ArrayList<Double>> macierz, int krok)
	{
		double maks = (macierz.get(krok-1).get(macierz.size()-1));
		
		for (int i=krok-1; i<macierz.get(krok-1).size()-1; i++)
		{
			if (Math.abs(macierz.get(krok-1).get(i)) > Math.abs(maks))
				maks=(macierz.get(krok-1).get(i));
		}
		return maks;
	}
	
	public static double maks_el_w_wier(ArrayList<ArrayList<Double>> macierz, int krok)
	{
		double maks = (macierz.get(macierz.size()-1).get(krok-1));
		
		for (int i=krok-1; i<macierz.get(krok-1).size()-1; i++)
		{
			if (Math.abs(macierz.get(i).get(krok-1)) > Math.abs(maks))
				maks=(macierz.get(i).get(krok-1));
		}
		System.out.println(maks+"<-maks\n");
		return maks;
	}
	
	public static void kolumnowe_rozwiazanie_macierzy(ArrayList<ArrayList<Double>> macierz, double[] rozwiazania, int niewiadome[])
	{
		if (kolumn_el_Gaussa(macierz, niewiadome)!=null) 
		{
			rozwiazania=new double[macierz.size()];
			for (int krok=macierz.size()-1; krok>=0; krok--)
			{
				double suma=0;
				for(int j=krok+1; j<macierz.size(); j++)
				{
					suma+=rozwiazania[j]*macierz.get(krok).get(j);
				}
				rozwiazania[krok]=( (macierz.get(krok).get(macierz.size())-suma) / macierz.get(krok).get(krok) );
				System.out.println("x_"+(niewiadome[krok])+" = "+rozwiazania[krok]);
			}
		}
		else System.out.println("Zero na przekątnej - kolumnowa eliminacja Gaussa zawiodła");
	}
	
	public static void wierszowe_rozwiazanie_macierzy(ArrayList<ArrayList<Double>> macierz, double[] rozwiazania)
	{
		if (wiersz_el_Gaussa(macierz)!=null) 
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
		else System.out.println("Zero na przekątnej - wierszowa eliminacja Gaussa zawiodła");		
	}
	
	public static ArrayList<ArrayList<Double>> ustaw_przykladowa_macierz_do_maksymalnej_el_Gaussa()
	{
		try	{ przykladowa_macierz.clear(); }
		catch (Exception ex) { System.out.println("Nie można wyczyścić macierzy"); }
		
		przykladowa_macierz=new ArrayList<>();
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(0).add(0, (double)(2.25));
		przykladowa_macierz.get(0).add(1, (double)(-2.5));
		przykladowa_macierz.get(0).add(2, (double)4);
		przykladowa_macierz.get(0).add(3, (double)(-5.25));
		przykladowa_macierz.get(0).add(4, (double)(-1));
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(1).add(0, (double)(-3));
		przykladowa_macierz.get(1).add(1, (double)(-7.5));
		przykladowa_macierz.get(1).add(2, (double)(6.5));
		przykladowa_macierz.get(1).add(3, (double)(0));
		przykladowa_macierz.get(1).add(4, (double)(17));
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(2).add(0, (double)(-6.25));
		przykladowa_macierz.get(2).add(1, (double)(-12.5));
		przykladowa_macierz.get(2).add(2, (double)(0.25));
		przykladowa_macierz.get(2).add(3, (double)(5.25));
		przykladowa_macierz.get(2).add(4, (double)(24.25));
		przykladowa_macierz.add(new ArrayList<Double>());
		przykladowa_macierz.get(3).add(0, (double)(9));
		przykladowa_macierz.get(3).add(1, (double)(10));
		przykladowa_macierz.get(3).add(2, (double)(7));
		przykladowa_macierz.get(3).add(3, (double)(-21));
		przykladowa_macierz.get(3).add(4, (double)(-33));
		
		return przykladowa_macierz;
	}
}
