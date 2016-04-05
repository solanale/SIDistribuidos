import org.apache.jena.query.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import twitter4j.*;

import java.util.ArrayList;

public class P3
{
    public static void main(String[] args) throws TwitterException {

        Logger.getRootLogger().setLevel(Level.INFO);    //Elimina los warnings
        ArrayList<String> tendencias  = new ArrayList<String>();
        tendencias.add("Neymar"); tendencias.add("Iago_Aspas"); tendencias.add("Ilegales");

        //Error twitter4j.properties
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            ResponseList<Location> locations;
            locations = twitter.getAvailableTrends();
            int woeid = 0;
            for (Location location : locations) {
                if (location.getCountryName().equals("Spain")) woeid = location.getWoeid();
            }
            Trends trends = twitter.getPlaceTrends(woeid);
            for (int i = tendencias.size(); i < trends.getTrends().length; i++) {
                tendencias.add(trends.getTrends()[i].getName().trim().replace("#", "").replace(" ", "_"));
            }

            //String tendencias[] = {"Neymar", "Iago_Aspas", "Ilegales", "Beatles"};
            for (String tendencia : tendencias) {
                String sparqlEndpoint = "http://dbpedia.org/sparql";

                String sparqlQuery = "" +
                        "PREFIX res: <http://dbpedia.org/resource/>" +
                        "PREFIX p: <http://dbpedia.org/property/>" +
                        "PREFIX o: <http://dbpedia.org/ontology/>" +
                        "SELECT * {" +
                        "res:" + tendencia + " p:name ?Nombre ." +
                        "OPTIONAL {res:" + tendencia + " p:currentclub ?Club } ." +
                        "OPTIONAL {res:" + tendencia + " o:genre ?Genero} ." +
                        "}";

                org.apache.jena.query.Query query = QueryFactory.create(sparqlQuery);
                QueryExecution exec = QueryExecutionFactory.sparqlService(
                        sparqlEndpoint, query);
                try {
                    ResultSet results = exec.execSelect();
                    if (results.hasNext()) {
                        ResultSetFormatter.out(System.out, results, query);
                    }

                } finally {
                    exec.close();
                }
            }
        }catch (java.lang.IllegalStateException e){
            System.out.println("El fichero twitter4j.properties es necesario para funcionar, debe incluirse en la raiz del .jar");
        }
    }
}