import java.util.InputMismatchException;
import java.util.Scanner;
//Adam Józef Bogusz
public class Wykonanie extends Pelna_eliminacja_Gaussa
{
	public static void main(String[] args)
	{
		System.out.println("Jaki typ eliminacji Gaussa chcesz zastosować?"
				+ "\n1)Podstawowy"
				+ "\n2)Z szukaniem maksymalnego elementu w kolumnie lub wierszu"
				+ "\n3)Pełny\n");
		
		Scanner typ_eliminacji=new Scanner(System.in);
		byte wybrana_eliminacja;		
		while(true)
		{
			try
			{
				wybrana_eliminacja=typ_eliminacji.nextByte();
				if (wybrana_eliminacja<(byte)1 || wybrana_eliminacja>(byte)3) throw new InputMismatchException();
				break;
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Podaj liczbę 1, 2 lub 3");
				//wybor.reset();
				typ_eliminacji.nextLine();
			}
		}

		switch(wybrana_eliminacja)
		{//
			case (1):
					Podstawowa_eliminacja_Gaussa podstawowa=new Podstawowa_eliminacja_Gaussa();
				
				System.out.println("Czy chcesz użyć przykładowej macierzy, czy wolisz zadeklarować własną?"
						+ "\n1)Użyj przykładowej macierzy"
						+ "\n2)Zadeklaruj własną macierz\n");
				
				Scanner wybor_podstawowej=new Scanner(System.in);
				byte wybrana_opcja_podstawowej;		
				while(true)
				{
					try
					{
						wybrana_opcja_podstawowej=wybor_podstawowej.nextByte();
						if (wybrana_opcja_podstawowej<(byte)1 || wybrana_opcja_podstawowej>(byte)2) throw new InputMismatchException();
						break;
					}
					catch(InputMismatchException ex)
					{
						System.out.println("Podaj liczbę 1 lub 2");
						//wybor.reset();
						wybor_podstawowej.nextLine();
					}
				}
				
				switch(wybrana_opcja_podstawowej) {
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
			break;//
			case (2):
				Eliminacja_Gaussa_z_maksymalnym_elementem maksymalna=new Eliminacja_Gaussa_z_maksymalnym_elementem();
			
			System.out.println("Czy chcesz użyć przykładowej macierzy, czy wolisz zadeklarować własną?"
					+ "\n1)Użyj przykładowej macierzy do metody kolumnowej"
					+ "\n2)Użyj przykładowej macierzy do metody wierszowej"
					+ "\n3)Zadeklaruj własną macierz do metody kolumnowej"
					+ "\n4)Zadeklaruj własną macierz do metody wierszowej\n");
			
			Scanner wybor_maksymalnej=new Scanner(System.in);
			byte wybrana_opcja_maksymalnej;		
			while(true)
			{
				try
				{
					wybrana_opcja_maksymalnej=wybor_maksymalnej.nextByte();
					if (wybrana_opcja_maksymalnej<(byte)1 || wybrana_opcja_maksymalnej>(byte)4) throw new InputMismatchException();
					break;
				}
				catch(InputMismatchException ex)
				{
					System.out.println("Podaj liczbę 1, 2, 3 lub 4");
					//wybor.reset();
					wybor_maksymalnej.nextLine();
				}
			}

			switch(wybrana_opcja_maksymalnej) {
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
			break;//
			case (3):
					Pelna_eliminacja_Gaussa pelna=new Pelna_eliminacja_Gaussa();
				pelna.macierz=Eliminacja_Gaussa_z_maksymalnym_elementem.ustaw_przykladowa_macierz_do_maksymalnej_el_Gaussa();
				
				System.out.println("Czy chcesz użyć przykładowej macierzy, czy wolisz zadeklarować własną?"
						+ "\n1)Użyj przykładowej macierzy do pełnej metody eliminacji Gaussa"
						+ "\n2)Zadeklaruj własną macierz do pełnej metody eliminacji Gaussa\n");
				
				Scanner wybor_pelnej=new Scanner(System.in);
				byte wybrana_opcja_pelnej;		
				while(true)
				{
					try
					{
						wybrana_opcja_pelnej=wybor_pelnej.nextByte();
						if (wybrana_opcja_pelnej<(byte)1 || wybrana_opcja_pelnej>(byte)2) throw new InputMismatchException();
						break;
					}
					catch(InputMismatchException ex)
					{
						System.out.println("Podaj liczbę 1 lub 2");
						//wybor.reset();
						wybor_pelnej.nextLine();
					}
				}
	
				switch(wybrana_opcja_pelnej) {
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
	}
}
