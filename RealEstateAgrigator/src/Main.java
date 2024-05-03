import com.mongodb.client.MongoClient;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Get all available listings
        ArrayList<Listing> RoyalLePageListings = ListingFactory.get(Site.RoyalLePage);
        ArrayList<Listing> duproprioListings = ListingFactory.get(Site.DuProprio);
        ArrayList<Listing> centrisListings = ListingFactory.get(Site.Centris);

        // Verify Mongo connection
        MongoDataLayer.check();

        // Delete all current listings in Collection
        MongoDataLayer.deleteAllListings();

        // Add all newly obtained listings
        MongoDataLayer.addListingsToDatabase(RoyalLePageListings);
        MongoDataLayer.addListingsToDatabase(duproprioListings);
        MongoDataLayer.addListingsToDatabase(centrisListings);

        // Check amount of listing received to make sure i got the same amount in MongoDB
        System.out.println(RoyalLePageListings.size() + duproprioListings.size() + centrisListings.size());
    }
}