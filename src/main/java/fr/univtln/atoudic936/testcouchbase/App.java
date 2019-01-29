package fr.univtln.atoudic936.testcouchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;


public class App
{

    public static void main(String... args) throws Exception {

        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().connectTimeout(10000).build();

        // Initialize the Connection
        Cluster cluster = CouchbaseCluster.create(env, "localhost");
        cluster.authenticate("AdmnCDB", "mdpCDB");
        Bucket bucket = cluster.openBucket("Chaises");

        Chaise chaise = new Chaise();
        chaise.setID(18);
        chaise.setBonEtat(true);
        chaise.setMaterial("BOIS");


        // Store the Document
        bucket.upsert(JsonDocument.create(Long.toString(chaise.getID()), chaise.toJson()));

        // Create a N1QL Primary Index (but ignore if it exists)
        bucket.bucketManager().createN1qlPrimaryIndex(true, false);

        // Perform a N1QL Query
        N1qlQueryResult result = bucket.query(
                N1qlQuery.simple("SELECT * FROM Chaises")
        );

        for (N1qlQueryRow row : result.allRows()) {
            System.out.println(row);
            if (row.value().getObject("Chaises").getLong("ID") == 4){
                chaise.setID(4);
                chaise.setMaterial("ACIER");
                chaise.setBonEtat(row.value().getObject("Chaises").get("BonEtat").equals(true));
                bucket.upsert(JsonDocument.create(Long.toString(chaise.getID()), chaise.toJson()));
            }
        }

        env.connectTimeout();
//        cluster.disconnect();

    }
}
