import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
//Adam Józef Bogusz
public class Pelna_eliminacja_Gaussa extends Eliminacja_Gaussa_z_maksymalnym_elementem  {

	public Pelna_eliminacja_Gaussa()
	{
		Eliminacja_Gaussa_z_maksymalnym_elementem.ustaw_przykladowa_macierz_do_maksymalnej_el_Gaussa();
	}

	public static void main(String[] args)
	{
		Pelna_eliminacja_Gaussa pelna=new Pelna_eliminacja_Gaussa();
		pelna.macierz=Eliminacja_Gaussa_z_maksymalnym_elementem.ustaw_przykladowa_macierz_do_maksymalnej_el_Gaussa();
		
		System.out.println("Czy chcesz użyć przykładowej macierzy, czy wolisz zadeklarować własną?"
				+ "\n1)Użyj przykładowej macierzy do pełnej metody eliminacji Gaussa"
				+ "\n2)Zadeklaruj własną macierz do pełnej metody eliminacji Gaussa\n");
		
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
			pelna.macierz=ustaw_przykladowa_macierz_do_maksymalnej_el_Gaussa();
			pelna.niewiadome=new int[pelna.macierz.size()];
			for (int i=0; i<pelna.niewiadome.length; i++) pelna.niewiadome[i]=i+1;
			pokaz_macierz(pelna.macierz);
			pelna_el_Gaussa(pelna.macierz, pelna.niewiadome);
			pokaz_macierz(pelna.macierz);
			pelne_rozwiazanie_macierzy(pelna.macierz, pelna.rozwiazania_macierzy, pelna.niewiadome);
			break;
		case (2):
			ustaw_macierz(pelna);
			pelna.niewiadome=new int[pelna.macierz.size()];
			for (int i=0; i<pelna.niewiadome.length; i++) pelna.niewiadome[i]=i+1;
			pokaz_macierz(pelna.macierz);
			pelna_el_Gaussa(pelna.macierz, pelna.niewiadome);
			pokaz_macierz(pelna.macierz);
			pelne_rozwiazanie_macierzy(pelna.macierz, pelna.rozwiazania_macierzy, pelna.niewiadome);
			break;
		}
	}
	
	public static ArrayList<ArrayList<Double>> pelna_el_Gaussa(ArrayList<ArrayList<Double>> macierz, int niewiadome[])
	{
		if (macierz==null) return null;
		
		ArrayList<ArrayList<Double>> macierz_kopia=macierz;		
		
		for(int krok=1; krok<(macierz.size()); krok++)
		{
			double maks= maks_el_w_podmacierzy(macierz_kopia, krok);
			int wiersz_indeks_maks = 0; //0 bo trzeba zainicjalizować
			int kolumn_indeks_maks = 0;
			
			for (int i=krok-1; i<macierz.size(); i++)
			{
				for (int j=krok-1; j<macierz.get(krok-1).size()-1; j++)
				{
					if (Math.abs(macierz.get(i).get(j))==Math.abs(maks))
						{
							wiersz_indeks_maks=i;
							kolumn_indeks_maks=j;
						}
				}
			}
			
			if (macierz_kopia.get(krok-1).get(krok-1) != maks)
			{
				if ((krok-1)==wiersz_indeks_maks)
				{
					for (int i=0; i<macierz_kopia.size(); i++)
						{
							Collections.swap(macierz_kopia.get(i), krok-1, macierz_kopia.get(krok-1).indexOf(maks));
						}
					niewiadome=zamien_miejscami(niewiadome, krok-1, macierz_kopia.get(krok-1).indexOf(maks));
				}
				else if ((krok-1)==kolumn_indeks_maks)
				{
					Collections.swap(macierz_kopia, krok-1, wiersz_indeks_maks);
				}
				else
				{
					Collections.swap(macierz_kopia, krok-1, wiersz_indeks_maks);
					
					for (int i=0; i<macierz_kopia.size(); i++)
					{
						Collections.swap(macierz_kopia.get(i), krok-1, kolumn_indeks_maks);
					}
					niewiadome=zamien_miejscami(niewiadome, krok-1,kolumn_indeks_maks);					
				}
			}
			//pokaz_macierz(macierz_kopia);
			
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
		}
		
		if (Math.abs(macierz_kopia.get(macierz.size()-1).get(macierz.size()-1))<=ZERO)
		{
			return null;
		}
		return macierz_kopia;
	}
	
	public static double maks_el_w_podmacierzy(ArrayList<ArrayList<Double>> macierz, int krok)
	{
		double maks = macierz.get(krok-1).get(krok-1);
		
		for (int i=krok-1; i<macierz.size(); i++)
		{
			for (int j=krok-1; j<macierz.get(krok-1).size()-1; j++)
			{
				if (Math.abs(macierz.get(i).get(j)) > Math.abs(maks))
					maks=(macierz.get(i).get(j));
			}
		}
		return maks;
	}
	
	public static void pelne_rozwiazanie_macierzy(ArrayList<ArrayList<Double>> macierz, double[] rozwiazania, int niewiadome[])
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
		else System.out.println("Zero na przekątnej - pełna eliminacja Gaussa zawiodła");
	}

}
