/**
*
* @author Mehmetali Demirtas mehmetali.demirtas@ogr.sakarya.edu.tr
* @since 21.03.2022
* <p>
* 	Konsol sat�r�ndan verilen dosyan�n d�zenlenmi� son halindeki 
*   operat�r ve operand say�s�n� bulan s�n�ft�r.
* </p>
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Lexical {
	
	    // Saya�lar olu�turuldu.
	    private int tekliSayac=0;
	    private int mantiksalSayac=0;
	    private int iliskiselSayac=0;
	    private int ikiliSayac=0;
        private int ikiliYardimciSayac=0;       
        private int sayisalOperatorSayisi=0;
        private int operandSayac=0;
        

        //Saya�lar i�in getter metodlar� olu�turuldu.
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
		       
		//Operat�r ve operand say�s�n� bulacak fonksiyon
	    public void operatorVeOperandBul(String metin) { 
	    	
	    	//TEKL� OPERAT�R BULMA
	    	String tekliRegex1 = "(?<=[A-Z|a-z])?[\\+\\-][\\+\\-](?<=[a-z|A-Z])?";//x++ ++x ifadelerini bulan regex.
	  	  	Pattern pattern1 = Pattern.compile(tekliRegex1);
	        Matcher matcher1 = pattern1.matcher(metin);
	        while (matcher1.find()) { //�kili operat�rleri bulan regex di�er operat�rlerden baz�lar�n� da buldu�u i�in 
	        	ikiliYardimciSayac++;//yard�mc� bir saya� olu�turup ikili operat�r sayac�ndan ��kart�l�yor.
	            tekliSayac = tekliSayac + 1;
	        }
	        
	    	String tekliRegex2 = "(?<!\\+|\\-)[\\+\\-][a-zA-Z0-9]";//-b +b (pozitif/negatif) ifadelerini bulan regex.
	    	Pattern pattern3 = Pattern.compile(tekliRegex2);
	        Matcher matcher3 = pattern3.matcher(metin);
	        while (matcher3.find()) {
	        	ikiliYardimciSayac++;
	            tekliSayac = tekliSayac + 1;
	        }	    
	        
	        operandSayac = operandSayac + tekliSayac; // Tekli operat�rlerde 1 adet operand var.
	        
	        
	        //MANTIKSAL OPERAT�R BULMA
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
	        operandSayac= operandSayac +mantiksalSayac*2; //Mant�ksal operat�rlerde iki adet operand var.
	        
	        
	        //�L��K�SEL OPERAT�R BULMA
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
	        operandSayac= operandSayac + iliskiselSayac*2; // �li�kisel operat�rlerde iki adet operand var.
	        
	        
	        //�K�L� OPERAT�R BULMA
	        String ikiliRegex1 = "[(a-zA-Z\\s0-9)][*\\/%\\=\\|\\^\\&\\+\\-]"; // �kili operat�rleri bulan regex.
	    	Pattern pattern10 = Pattern.compile(ikiliRegex1);
	        Matcher matcher10 = pattern10.matcher(metin);
	        while (matcher10.find()) {
	        	ikiliSayac = ikiliSayac + 1;
	        }
	        ikiliSayac= ikiliSayac - ikiliYardimciSayac; //�kili operat�r bulan regex tekli ve ili�kisel operat�rleri de buldugu i�in saya�tan d���r�yoruz.
	        sayisalOperatorSayisi = tekliSayac + ikiliSayac;
	        operandSayac = operandSayac + ikiliSayac*2; //�kili operat�rlerde iki adet operand var.
	    }    	 
}