/**
*
* @author Mehmetali Demirtas mehmetali.demirtas@ogr.sakarya.edu.tr
* @since 17.03.2022
* <p>
* 	Konsol sat�r�ndan verilen dosyan�n yorum sat�rlar�n� ve string ifadeleri
* 	silen ve olu�an yeni metini Lexical s�n�f�ndan �a��r�lan fonksiyon ile i�leme tabi tutup
* 	sonucu ekrana yazan s�n�ft�r.
* </p>
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {

		final int yorumDegil=0;
		final int tekliYorumSatiri=1;
		final int cokluYorumSatiri=2;
		final int cokluYorumIlkSatir=3; 
		int simdikiDurum=yorumDegil;
		String metin="";
		    	
		File file= new File(args[0]); //Okutulacak dosya komut sat�r� parametresi olarak veriliyor.
	
		try {
			Scanner scanner = new Scanner(file);
		    scanner.useDelimiter("");
			while(scanner.hasNextLine()) { 
				String kelime=scanner.next();
		        switch(simdikiDurum){ //Switch case ile yorum sat�rlar� bulunuyor
		            case yorumDegil: //Yorum de�il durumunda isek..
		                if(kelime.equals("/") && scanner.hasNext()){ //Okunan karakter e�er / ise
		                    String sonrakiKelime=scanner.next(); 
		                    if(sonrakiKelime.equals("/")) //Sonraki karakter de / ise bu bir tekli yorum sat�r�d�r.
		                        simdikiDurum=tekliYorumSatiri;
		                    else if(sonrakiKelime.equals("*")){ // Sonraki karakter * ise �oklu yorum sat�r�n�n ilk satiridir.
		                        simdikiDurum=cokluYorumIlkSatir;
		                    }
		                    else 
		                        metin+=kelime+sonrakiKelime; //Sonraki karakter / veya * de�ilse, yorum sat�r�d� de�il, metin de�i�kenine ekleniyor.
		                }
		                else
		                    metin+=kelime; //Okunan karakter e�er / de�il ise, metin de�i�kenine ekleniyor.
		                break;
		            case tekliYorumSatiri: //Tekli yorum sat�r� durumunda isek..
		                if(kelime.equals("\n")){ //Okunan kelime yeni sat�r ise durum yorum de�il olarak de�i�tiriliyor ve metin de�i�kenine ekleniyor.
		                    simdikiDurum=yorumDegil;
		                    metin+="\n";
		                }
		            break;
		            case cokluYorumIlkSatir: //�oklu yorum sat�r�n�n ilk sat�r�nda isek..
		                if(kelime.equals("\n")){ //Okunan kelime yeni sat�r ise metin de�i�kenine ekleniyor ve durum �oklu yorum sat�r� yap�l�yor.
		                    metin+="\n";
		                    simdikiDurum=cokluYorumSatiri;
		                }
		            case cokluYorumSatiri: //�oklu yorum sat�r�nda isek..
		                while(kelime.equals("*") && scanner.hasNext()){ //Yorum sat�r� kapanana kadar okur, kapand�ktan sonra durum yorumDegil'e e�itlenir.
		                    String sonrakiKelime=scanner.next();
		                    if(sonrakiKelime.equals("/")){
		                        simdikiDurum=yorumDegil;
		                        break;
		                    }
		                    
		                }
		        	}
		        }
		    scanner.close(); //Scanner kapat�ld�.
			String tirnakIsareti = "\""; // "
			metin=metin.replaceAll(tirnakIsareti + ".*" + tirnakIsareti, ""); //String ifadeler silindi.		
			metin = metin.replaceAll("[\r\n]+", " "); //Verilen dosya string ifadeler ve yorum sat�rlar� silindikten sonra tek sat�r haline getirildi.

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
			Lexical lexical = new Lexical(); //Lexical s�n�f�ndan nesne olu�turuldu.
			lexical.operatorVeOperandBul(metin);
			
			//Ekrana yazd�r�l�yor
			System.out.println("Operat�r Bilgisi:");
		    System.out.println("	Tekli Operat�r Say�s�: " + lexical.getTekliSayac());
		    System.out.println("	�kili Operat�r Say�s�: " + lexical.getIkiliSayac());
		    System.out.println("	Say�sal Operat�r Say�s�: " + lexical.getSayisalOperatorSayisi());
		    System.out.println("	�li�kisel Operat�r Say�s�: " + lexical.getIliskiselSayac());
		    System.out.println("	Mant�ksal Operat�r Say�s�: " + lexical.getMantiksalSayac());
	    	System.out.println("Operand Bilgisi:");
	    	System.out.println("	Toplam Operand Say�s�:"+ lexical.getOperandSayac());

	}
}