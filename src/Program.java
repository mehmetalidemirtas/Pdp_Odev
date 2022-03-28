/**
*
* @author Mehmetali Demirtas mehmetali.demirtas@ogr.sakarya.edu.tr
* @since 17.03.2022
* <p>
* 	Konsol satýrýndan verilen dosyanýn yorum satýrlarýný ve string ifadeleri
* 	silen ve oluþan yeni metini Lexical sýnýfýndan çaðýrýlan fonksiyon ile iþleme tabi tutup
* 	sonucu ekrana yazan sýnýftýr.
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
		    	
		File file= new File(args[0]); //Okutulacak dosya komut satýrý parametresi olarak veriliyor.
	
		try {
			Scanner scanner = new Scanner(file);
		    scanner.useDelimiter("");
			while(scanner.hasNextLine()) { 
				String kelime=scanner.next();
		        switch(simdikiDurum){ //Switch case ile yorum satýrlarý bulunuyor
		            case yorumDegil: //Yorum deðil durumunda isek..
		                if(kelime.equals("/") && scanner.hasNext()){ //Okunan karakter eðer / ise
		                    String sonrakiKelime=scanner.next(); 
		                    if(sonrakiKelime.equals("/")) //Sonraki karakter de / ise bu bir tekli yorum satýrýdýr.
		                        simdikiDurum=tekliYorumSatiri;
		                    else if(sonrakiKelime.equals("*")){ // Sonraki karakter * ise çoklu yorum satýrýnýn ilk satiridir.
		                        simdikiDurum=cokluYorumIlkSatir;
		                    }
		                    else 
		                        metin+=kelime+sonrakiKelime; //Sonraki karakter / veya * deðilse, yorum satýrýdý deðil, metin deðiþkenine ekleniyor.
		                }
		                else
		                    metin+=kelime; //Okunan karakter eðer / deðil ise, metin deðiþkenine ekleniyor.
		                break;
		            case tekliYorumSatiri: //Tekli yorum satýrý durumunda isek..
		                if(kelime.equals("\n")){ //Okunan kelime yeni satýr ise durum yorum deðil olarak deðiþtiriliyor ve metin deðiþkenine ekleniyor.
		                    simdikiDurum=yorumDegil;
		                    metin+="\n";
		                }
		            break;
		            case cokluYorumIlkSatir: //Çoklu yorum satýrýnýn ilk satýrýnda isek..
		                if(kelime.equals("\n")){ //Okunan kelime yeni satýr ise metin deðiþkenine ekleniyor ve durum çoklu yorum satýrý yapýlýyor.
		                    metin+="\n";
		                    simdikiDurum=cokluYorumSatiri;
		                }
		            case cokluYorumSatiri: //Çoklu yorum satýrýnda isek..
		                while(kelime.equals("*") && scanner.hasNext()){ //Yorum satýrý kapanana kadar okur, kapandýktan sonra durum yorumDegil'e eþitlenir.
		                    String sonrakiKelime=scanner.next();
		                    if(sonrakiKelime.equals("/")){
		                        simdikiDurum=yorumDegil;
		                        break;
		                    }
		                    
		                }
		        	}
		        }
		    scanner.close(); //Scanner kapatýldý.
			String tirnakIsareti = "\""; // "
			metin=metin.replaceAll(tirnakIsareti + ".*" + tirnakIsareti, ""); //String ifadeler silindi.		
			metin = metin.replaceAll("[\r\n]+", " "); //Verilen dosya string ifadeler ve yorum satýrlarý silindikten sonra tek satýr haline getirildi.

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
			Lexical lexical = new Lexical(); //Lexical sýnýfýndan nesne oluþturuldu.
			lexical.operatorVeOperandBul(metin);
			
			//Ekrana yazdýrýlýyor
			System.out.println("Operatör Bilgisi:");
		    System.out.println("	Tekli Operatör Sayýsý: " + lexical.getTekliSayac());
		    System.out.println("	Ýkili Operatör Sayýsý: " + lexical.getIkiliSayac());
		    System.out.println("	Sayýsal Operatör Sayýsý: " + lexical.getSayisalOperatorSayisi());
		    System.out.println("	Ýliþkisel Operatör Sayýsý: " + lexical.getIliskiselSayac());
		    System.out.println("	Mantýksal Operatör Sayýsý: " + lexical.getMantiksalSayac());
	    	System.out.println("Operand Bilgisi:");
	    	System.out.println("	Toplam Operand Sayýsý:"+ lexical.getOperandSayac());

	}
}