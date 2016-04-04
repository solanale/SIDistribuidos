/**
 * Created by naxsel on 4/04/16.
 */
import org.apache.jena.query.*;

public class P3
{
    public static void main(String[] args)
    {

        //BasicConfigurator.configure();
        String tendencias[] = {"Neymar", "Iago_Aspas", "Ilegales"};
        for (String tendencia : tendencias)
        {
            String sparqlEndpoint = "http://dbpedia.org/sparql";

            String sparqlQuery = "PREFIX res: <http://dbpedia.org/resource/>" +
                    "PREFIX p: <http://dbpedia.org/property/>" +
                    "PREFIX o: <http://dbpedia.org/ontology/>" +
                    "SELECT * {" +
                    "res:" +tendencia+ " p:name ?Nombre ." +
                    "OPTIONAL {res:" +tendencia+ " p:currentclub ?Club } ." +
                    "OPTIONAL {res:" +tendencia+ " o:genre ?Genero} ."+
                    "}";

            System.out.println(sparqlQuery);
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