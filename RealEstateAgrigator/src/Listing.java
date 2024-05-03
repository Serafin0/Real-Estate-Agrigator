import java.net.URL;

public class Listing {
    String id;
    String type;
    Site site;
    double sqft;
    String address;
    String url;
    int numBedrooms;
    int numBathrooms;
    double price;
    String rawListing;

    Listing(){}

    void setId(String id) { this.id = id; }
    void setType(String type) { this.type = type; }
    void setSite(Site site) { this.site = site; }
    void setSqft(double sqft) { this.sqft = sqft; }
    void setAddress(String address) { this.address = address; }
    void setUrl(String url) { this.url = url; }
    void setNumBedrooms(int numBedrooms) { this.numBedrooms = numBedrooms; }
    void setNumBathrooms(int numBathrooms) { this.numBathrooms = numBathrooms; }
    void setPrice(double price) { this.price = price; }
    void setRawListing(String rawListing) { this.rawListing = rawListing; }

    String getId() { return this.id; }
    String getType() { return this.type; }
    Site getSite() { return this.site; }
    double getSqft() { return this.sqft; }
    String getAddress() { return this.address; }
    String getUrl() { return this.url; }
    int getNumBedrooms() { return this.numBedrooms; }
    int getNumBathrooms() { return this.numBathrooms; }
    double getPrice() { return this.price; }
    String getRawListing() { return this.rawListing; }

    @Override
    public String toString() {
        return "Listing: " + getId() + "\n" +
            "Type: " + getType() + "\n" +
            "Site: " + getSite() + "\n" +
            "Sqft: " + getSqft() + "\n" +
            "Address: " + getAddress() + "\n" +
            "URL: " + getUrl() + "\n" +
            "Bedrooms: " + getNumBedrooms() + "\n" +
            "Bathrooms: " + getNumBathrooms() + "\n" +
            "Price: " + getPrice() + "\n";
    }
}
