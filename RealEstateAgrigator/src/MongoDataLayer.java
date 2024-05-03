import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.ConnectionString;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MongoDataLayer {

    static void check(){
        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            System.out.println("=> Connection successful: " + preFlightChecks(mongoClient));
            System.out.println("=> Print list of databases:");
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
        }
    }
    static boolean preFlightChecks(MongoClient mongoClient) {
        Document pingCommand = new Document("ping", 1);
        Document response = mongoClient.getDatabase("admin").runCommand(pingCommand);
        System.out.println("=> Print result of the '{ping: 1}' command.");
        System.out.println(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.getInteger("ok") == 1;
    }

    static void addListingsToDatabase(List<Listing> listings) {
        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("RealEstateAgrigator");
            MongoCollection<Document> collection = database.getCollection("Listings");

            List<Document> listingDocuments = new ArrayList<>();
            for (Listing listing : listings) {
                Document listingDocument = new Document("type", listing.getType())
                        .append("site", listing.getSite())
                        .append("sqft", listing.getSqft())
                        .append("address", listing.getAddress())
                        .append("url", listing.getUrl())
                        .append("numBedrooms", listing.getNumBedrooms())
                        .append("numBathrooms", listing.getNumBathrooms())
                        .append("price", listing.getPrice())
                        .append("rawListing", listing.getRawListing());
                listingDocuments.add(listingDocument);
            }

            collection.insertMany(listingDocuments);
            System.out.println("Listings added to the RealEstateAgrigator database in the Listings collection.");
        }
    }

    static void deleteAllListings() {
        String connectionString = System.getProperty("mongodb.uri");
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("RealEstateAgrigator");
            MongoCollection<Document> collection = database.getCollection("Listings");

            collection.deleteMany(new Document());

            System.out.println("All items deleted from the Listings collection.");
        }
    }
}
