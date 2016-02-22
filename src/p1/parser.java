package p1;

import java.io.*;
import java.math.*;
import java.net.*;
import java.text.ParseException;
import java.util.Scanner;

public class Parser {

    final int empresa = 35;

    // Aplica un parser al html y devuelve la estructura Nombre\tUltimo\n de las 35 empresas
    public static void main (String[] args) throws ParseException, TokenMgrError{
        URL url;
        InputStream is = null;
        BufferedReader br;
        int input;
        String text;
        String aux [] = new String [empresa];
        String lines [][] = new String [empresa][2];
        try {
            url = new URL("http://www.infobolsa.es/acciones/ibex35");
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            text = parser.Start(br);
            aux = text.split("\n");
            System.out.println("Introduce Numero de Empresa a consultar, 1-35");
            for (int i=0; i< empresa; i++ ){
                lines[i] = aux[i].split("\t");
                System.out.printf("%d. %s\n", i+1,lines[i][0]);
            }
            Scanner in = new Scanner (System.in);
            input = in.nextInt();

            System.out.printf("Empresa %s, ultimo: %d anterior %d",lines[input][0], lines[input][1],  );


        } catch (MalformedURLException mue) {
        mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
    }
}