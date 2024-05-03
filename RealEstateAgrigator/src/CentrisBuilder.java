import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CentrisBuilder implements IListingBuilder {

    private Listing listing;

    public CentrisBuilder(String rawListing) {
        this.listing = new Listing();
        this.setRawListing(rawListing);
    }

    @Override
    public void setId() {
        this.listing.setId(IdGenerator.generateID());
    }

    @Override
    public void setType() {
        Document document = Jsoup.parse(this.listing.getRawListing());
        Element listingTypeElement = document.select("span.category > div").first();

        if (listingTypeElement != null) {
            String listingType = listingTypeElement.text();
            this.listing.setType(listingType);
        } else {
            this.listing.setType("N/A");
        }
    }

    @Override
    public void setSite() {
        this.listing.setSite(Site.Centris);
    }

    @Override
    public void setSqft() {

        Document document = Jsoup.parse(this.listing.getRawListing());
        Element sqFootageElement = document.select("div.land-area > span").first();

        if (sqFootageElement != null) {
            String sqFootageText = sqFootageElement.text();
            try {
                double sqft = Double.parseDouble(sqFootageText.replace(",", "").replace(" sqft", ""));
                this.listing.setSqft(sqft);
            } catch (NumberFormatException e) {
                this.listing.setSqft(0.0);
            }
        } else {
            this.listing.setSqft(0.0);
        }
    }

    @Override
    public void setAddress() {
        Document document = Jsoup.parse(this.listing.getRawListing());
        Element addressElement = document.select("span.address > div").first();

        if (addressElement != null) {
            String address = addressElement.text();
            this.listing.setAddress(address);
        } else {
            this.listing.setAddress("N/A");
        }
    }
    @Override
    public void setUrl() {

        String prefix = "https://www.centris.ca";

        Document document = Jsoup.parse(this.listing.getRawListing());
        Element urlElement = document.select("a.a-more-detail").first();

        if (urlElement != null) {
            String url = urlElement.attr("href");
            url = prefix + url;
            this.listing.setUrl(url);
        } else {
            this.listing.setUrl("N/A");
        }
    }

    @Override
    public void setNumBedrooms() {
        Document document = Jsoup.parse(this.listing.getRawListing());
        Element bathroomElement = document.select("div.cac").first();
        Element bedroomElement = document.select("div.sdb").first();

        if (bathroomElement != null && bedroomElement != null) {
            String bathroomText = bathroomElement.text();
            String bedroomText = bedroomElement.text();
            try {
                int bathrooms = Integer.parseInt(bathroomText);
                int bedrooms = Integer.parseInt(bedroomText);
                this.listing.setNumBathrooms(bathrooms);
                this.listing.setNumBedrooms(bedrooms);
            } catch (NumberFormatException e) {
                // Handle parsing error if needed
                this.listing.setNumBathrooms(0);
                this.listing.setNumBedrooms(0);
            }
        } else {
            this.listing.setNumBathrooms(0);
            this.listing.setNumBedrooms(0);
        }
    }

    @Override
    public void setNumBathrooms() {
        // not needed for this site
    } // on this site this is set inside of the setNumBedrooms method

    @Override
    public void setPrice() {
        Document document = Jsoup.parse(this.listing.getRawListing());
        Element priceElement = document.select("div.price > meta[itemprop=price]").first();

        if (priceElement != null) {
            String priceText = priceElement.attr("content");
            try {
                double price = Double.parseDouble(priceText);
                this.listing.setPrice(price);
            } catch (NumberFormatException e) {
                this.listing.setPrice(0.0);
            }
        } else {
            this.listing.setPrice(0.0);
        }

    }

    @Override
    public void setRawListing(String rawListing) {
        this.listing.setRawListing(rawListing);
    }

    @Override
    public Listing getListing() {
        return this.listing;
    }
}
