/**
 * Created by naxsel on 4/04/16.
 */
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
public class P3
{
    public static void main(String[] args)
    {

        String tendencias[] = {"Neymar"}; // "Iago_Aspas", "Ilegales"};
        for (String tendencia : tendencias)
        {
            String sparqlEndpoint = "http://dbpedia.org/sparql";

            String sparqlQuery = "SELECT ?name ?currentclub WHERE { ?x <http://live.dbpedia.org/page/"+tendencia + "> ?x }";
            System.out.println(sparqlQuery);
            org.apache.jena.query.Query query = QueryFactory.create(sparqlQuery);
            QueryExecution exec = QueryExecutionFactory.sparqlService(
                    sparqlEndpoint, query );
            try {
                ResultSet results = exec.execSelect();
                while ( results.hasNext() )
                    System.out.println(results.nextSolution());
            } finally {
                exec.close();
            }
        }
    }
}