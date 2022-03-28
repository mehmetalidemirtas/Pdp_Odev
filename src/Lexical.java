/**
*
* @author Mehmetali Demirtas mehmetali.demirtas@ogr.sakarya.edu.tr
* @since 21.03.2022
* <p>
* 	Konsol satýrýndan verilen dosyanýn düzenlenmiþ son halindeki 
*   operatör ve operand sayýsýný bulan sýnýftýr.
* </p>
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Lexical {
	
	    // Sayaçlar oluþturuldu.
	    private int tekliSayac=0;
	    private int mantiksalSayac=0;
	    private int iliskiselSayac=0;
	    private int ikiliSayac=0;
        private int ikiliYardimciSayac=0;       
        private int sayisalOperatorSayisi=0;
        private int operandSayac=0;
        

        //Sayaçlar için getter metodlarý oluþturuldu.
		public int getTekliSayac() {
			return tekliSayac;
		}

		public int getIkiliSayac() {
			return ikiliSayac;
		}

		public int getSayisalOperatorSayisi() {
			return sayisalOperatorSayisi;
		}

		public int getIliskiselSayac() {
			return iliskiselSayac;
		}

		public int getMantiksalSayac() {
			return mantiksalSayac;
		}

		public int getOperandSayac() {
			return operandSayac;
		}	
		       
		//Operatör ve operand sayýsýný bulacak fonksiyon
	    public void operatorVeOperandBul(String metin) { 
	    	
	    	//TEKLÝ OPERATÖR BULMA
	    	String tekliRegex1 = "(?<=[A-Z|a-z])?[\\+\\-][\\+\\-](?<=[a-z|A-Z])?";//x++ ++x ifadelerini bulan regex.
	  	  	Pattern pattern1 = Pattern.compile(tekliRegex1);
	        Matcher matcher1 = pattern1.matcher(metin);
	        while (matcher1.find()) { //Ýkili operatörleri bulan regex diðer operatörlerden bazýlarýný da bulduðu için 
	        	ikiliYardimciSayac++;//yardýmcý bir sayaç oluþturup ikili operatör sayacýndan çýkartýlýyor.
	            tekliSayac = tekliSayac + 1;
	        }
	        
	    	String tekliRegex2 = "(?<!\\+|\\-)[\\+\\-][a-zA-Z0-9]";//-b +b (pozitif/negatif) ifadelerini bulan regex.
	    	Pattern pattern3 = Pattern.compile(tekliRegex2);
	        Matcher matcher3 = pattern3.matcher(metin);
	        while (matcher3.find()) {
	        	ikiliYardimciSayac++;
	            tekliSayac = tekliSayac + 1;
	        }	    
	        
	        operandSayac = operandSayac + tekliSayac; // Tekli operatörlerde 1 adet operand var.
	        
	        
	        //MANTIKSAL OPERATÖR BULMA
	        String mantiksalRegex1 = "[&\\|]+[&\\|)]";// && ve || ifadelerini bulan regex.
	    	Pattern pattern4 = Pattern.compile(mantiksalRegex1);
	        Matcher matcher4 = pattern4.matcher(metin);
	        while (matcher4.find()) {
	        	mantiksalSayac = mantiksalSayac + 1;
	        	ikiliYardimciSayac++;
	        }
	        
	    	String mantiksalRegex2 = "[\\!][a-zA-Z0-9\\(]"; // ! ifadesini bulan regex.
	    	Pattern pattern5 = Pattern.compile(mantiksalRegex2);
	        Matcher matcher5 = pattern5.matcher(metin);
	        while (matcher5.find()) {
	        	mantiksalSayac = mantiksalSayac + 1;
	        	operandSayac = operandSayac - 1;
	        }	        
	        operandSayac= operandSayac +mantiksalSayac*2; //Mantýksal operatörlerde iki adet operand var.
	        
	        
	        //ÝLÝÞKÝSEL OPERATÖR BULMA
	        String iliskiselRegex1 = "[=][=]";// == ifadesini bulan regex.
	    	Pattern pattern6 = Pattern.compile(iliskiselRegex1);
	        Matcher matcher6 = pattern6.matcher(metin);
	        while (matcher6.find()) {
	        	ikiliYardimciSayac++;
	        	iliskiselSayac = iliskiselSayac + 1;
	        }
	        
	    	String iliskiselRegex2 = "[><][=]";// <= ve >= ifadelerini bulan regex.
	    	Pattern pattern7 = Pattern.compile(iliskiselRegex2);
	        Matcher matcher7 = pattern7.matcher(metin);
	        while (matcher7.find()) {
	        	iliskiselSayac = iliskiselSayac + 1;
	        }
	        
	    	String iliskiselRegex3 = "[!][=]";// != ifadesini bulan regex.
	    	Pattern pattern8 = Pattern.compile(iliskiselRegex3);
	        Matcher matcher8 = pattern8.matcher(metin);
	        while (matcher8.find()) {
	        	iliskiselSayac = iliskiselSayac + 1;
	        }
	        
	        
	    	String iliskiselRegex4 = "[><][a-zA-Z\\s0-9]";// < ve > ifadelerini bulan regex.
	    	Pattern pattern9 = Pattern.compile(iliskiselRegex4);
	        Matcher matcher9 = pattern9.matcher(metin);
	        while (matcher9.find()) {
	        	iliskiselSayac = iliskiselSayac + 1;
	        }
	        operandSayac= operandSayac + iliskiselSayac*2; // Ýliþkisel operatörlerde iki adet operand var.
	        
	        
	        //ÝKÝLÝ OPERATÖR BULMA
	        String ikiliRegex1 = "[(a-zA-Z\\s0-9)][*\\/%\\=\\|\\^\\&\\+\\-]"; // Ýkili operatörleri bulan regex.
	    	Pattern pattern10 = Pattern.compile(ikiliRegex1);
	        Matcher matcher10 = pattern10.matcher(metin);
	        while (matcher10.find()) {
	        	ikiliSayac = ikiliSayac + 1;
	        }
	        ikiliSayac= ikiliSayac - ikiliYardimciSayac; //Ýkili operatör bulan regex tekli ve iliþkisel operatörleri de buldugu için sayaçtan düþürüyoruz.
	        sayisalOperatorSayisi = tekliSayac + ikiliSayac;
	        operandSayac = operandSayac + ikiliSayac*2; //Ýkili operatörlerde iki adet operand var.
	    }    	 
}