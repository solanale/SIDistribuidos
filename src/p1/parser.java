package p1;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.Scanner;

class Parser {

    static final int empresa = 35;

    // Aplica un parser al html y devuelve la estructura Nombre\tUltimo\n de las 35 empresas
    public static void main (String[] args) throws ParseException{
        URL url;
        BufferedReader br;
        int i,input,ultimo;
        String text;
        String aux [] = new String[empresa];
        String lines [][] = new String [empresa][2];
        try {
//            url = new URL("http://www.infobolsa.es/acciones/ibex35");
//            URLConnection connection = url.openConnection();
//            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            System.out.println(br.readLine());
//            System.out.println(br.readLine().toString());

            String html = new Scanner(new URL("http://www.infobolsa.es/acciones/ibex35").openStream(), "UTF-8").useDelimiter("\\A").next();
            System.out.println(html);

            text =parser.Start(html);
            aux = text.split("\n");
            System.out.println("Introduce Numero de Empresa a consultar, 1-35");
            for (i=0; i< empresa; i++ ){
                lines[i] = aux[i].split("\t");
                System.out.printf("%d. %s\n", i+1,lines[i][0]);
            }
            Scanner in = new Scanner (System.in);
            input = in.nextInt();
            //br.close();
            br = new BufferedReader(new FileReader("p1/last.txt"));
            for (i=0; i< input; i++){
                text = br.readLine();
            }
            aux = text.split("\t");
            ultimo = Integer.parseInt(aux[2]);

            System.out.printf("Empresa %s, ultimo: %d anterior %d",lines[input][0], lines[input][1], ultimo );

        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (FileNotFoundException fnf){
            fnf.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
//        } finally {
//            try {
//                if (is != null) is.close();
//            } catch (IOException ioe) {
//                // nothing to see here
//            }
        }
    }
}