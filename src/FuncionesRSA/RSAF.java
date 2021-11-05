
package FuncionesRSA;

import java.math.BigInteger;

import java.util.*;

public class RSAF {

    int tamprimo; //2, 3, 4 etc
    public BigInteger p, q, n;
    public BigInteger fi;
    public BigInteger e, d;
    public String textoC;

    //constructor de RSA
    public RSAF(int tamprimo) {
        this.tamprimo = tamprimo+1;
        System.out.println(tamprimo);
    }

    //metodo para generar los numeros primos
    public void generarPrimos() {

        p = new BigInteger(tamprimo, 10, new Random());

        do {
            q = new BigInteger(tamprimo, 10, new Random());
        } while (q.compareTo(p) == 0);
        System.out.println(q+"    "+p);
    }

    //generar la claves
    public void generarClaves() {
        /*
        Recordar que n = p*q
        fi = (p-1)*(q-1)
         */

        n = p.multiply(q);

        //(p-1)
        fi = p.subtract(BigInteger.valueOf(1));

        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));

        /*
        como calculabamos a e
        
        e debe de ser un coprimo de n de tal que
        1<e<fi(n)
         */
        do {
            e = new BigInteger(2 * tamprimo, new Random());
        } while ((e.compareTo(fi) != -1) || (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));

        //calcular a d = e ^ 1 mod fi   inverso multiplicativo de e
        d = e.modInverse(fi);
        System.out.println(n);
    }

    //criframos con la clave publica
    // e n
    public String cifrar(String mensaje) {

        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();

        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        for (i = 0; i < bigdigitos.length; i++) {
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }

        BigInteger[] cifrado = new BigInteger[bigdigitos.length];

        for (i = 0; i < bigdigitos.length; i++) {
            //formula
            // c = M ^ e mod n
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        textoC = "";
        
        for(int j=0; j<cifrado.length; j++){
            textoC += String.valueOf(cifrado[j])+",";
        }
        
        return textoC;
    }

    //desciframos con clave privada
    // d n
    public String descifrar(String mensaje1) {
        String [] numeros = mensaje1.split(",");
        BigInteger[] cifrado = new BigInteger[numeros.length];
        for(int i=0;i<numeros.length;i++){
            System.out.println(numeros[i]);
            cifrado[i] = new BigInteger(numeros[i]);
        }

        BigInteger[] descifrado = new BigInteger[cifrado.length];

        //vamos a descifrar con la formula
        // Md = C ^d mod n
        for (int j = 0; j < descifrado.length; j++) {
            descifrado[j] = cifrado[j].modPow(d, n);
        }

        char[] charArray = new char[descifrado.length];

        for (int k = 0; k < charArray.length; k++) {
            charArray[k] = (char) (descifrado[k].intValue());
        }

        return (new String(charArray));
    }
}
