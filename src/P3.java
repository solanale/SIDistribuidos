/**
 * Created by naxsel on 4/04/16.
 */

import org.apache.jena.query.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import twitter4j.*;

public class P3
{
    public static void main(String[] args) throws TwitterException {
        Logger.getRootLogger().setLevel(Level.INFO);    //Elimina los warnings

        Twitter twitter = new TwitterFactory().getInstance();
        ResponseList<Location> locations;
        locations = twitter.getAvailableTrends();
        int woeid = 0;
        for (Location location: locations){
            if(location.getCountryName().equals("Spain"))  woeid = location.getWoeid();
        }
        Trends trends = twitter.getPlaceTrends(woeid);
        for (int i = 0; i < trends.getTrends().length;i++){

        }

        String tendencias[] = {"Neymar", "Iago_Aspas", "Ilegales"};
        for (String tendencia : tendencias)
        {
            String sparqlEndpoint = "http://dbpedia.org/sparql";

            String sparqlQuery = "" +
                    "PREFIX res: <http://dbpedia.org/resource/>" +
                    "PREFIX p: <http://dbpedia.org/property/>" +
                    "PREFIX o: <http://dbpedia.org/ontology/>" +
                    "SELECT * {" +
                    "res:" +tendencia+ " p:name ?Nombre ." +
                    "OPTIONAL {res:" +tendencia+ " p:currentclub ?Club } ." +
                    "OPTIONAL {res:" +tendencia+ " o:genre ?Genero} ."+
                    "}";

            org.apache.jena.query.Query query = QueryFactory.create(sparqlQuery);
            QueryExecution exec = QueryExecutionFactory.sparqlService(
                    sparqlEndpoint, query );
            try {
                ResultSet results = exec.execSelect();
                ResultSetFormatter.out(System.out, results, query);

            } finally {
                exec.close();
            }
        }
    }
}