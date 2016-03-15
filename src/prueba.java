import java.io.*;
import java.net.*;
//import java.text.ParseException;
import java.util.Scanner;

public class prueba
{
    static final int empresa = 35;

    public static void main(String [] args) throws ParseException, TokenMgrError
    {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String text;
        int i, input;
        double last;
        double value [] = new double [empresa];
        String name [] = new String [empresa];
        String lines [] = new String [empresa];
        String par [] = new String [2];
        //        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        try
        {
            //                String html = new Scanner(new URL("http://www.infobolsa.es/acciones/ibex35").openStream(), "UTF-8").useDelimiter("\\A").next();
            //                System.out.println(html);
            url = new URL("http://www.infobolsa.es/acciones/ibex35");
            URLConnection connection = url.openConnection();
            is = connection.getInputStream();
            //		        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Parser parser = new Parser(is);
            text = parser.Start();
            System.out.println(text);
            lines = text.split("\n");
            br = new BufferedReader(new FileReader("src/last.txt"));
            System.out.println("Introduce Numero de Empresa a consultar, 1-35");
            for (i = 0; i < empresa; i++)
            {
                par = lines [i].split("\t");
                name [i] = par [0];
                value [i] = Double.parseDouble(par [1]);
                System.out.printf("%d. %s\t\t%.2f\n", i + 1, name [i], value [i]);
            }
            Scanner in = new Scanner(System.in);
            input = in.nextInt();
            //br.close();
            for (i = 0; i < input; i++)
            {
                par = br.readLine().split("\t");
            }
            System.out.printf("Empresa %s, valor actual: %.2f anterior %.2f", name [input], value [input], Double.parseDouble(par[1]));
        }
        catch (MalformedURLException mue)
        {
            mue.printStackTrace();
        }
        catch (FileNotFoundException fnf)
        {
            fnf.printStackTrace();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (ParseException poe)
        {
            //      System.out.println(ParserTokenManager.curLexState);
            poe.printStackTrace();
        }
    }
}