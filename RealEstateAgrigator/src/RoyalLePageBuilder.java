import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Objects;

public class RoyalLePageBuilder implements IListingBuilder {

    private Listing listing;

    public RoyalLePageBuilder(String rawListing) {
        this.listing = new Listing();
        this.listing.setRawListing(rawListing);
    }

    @Override
    public void setId() {
        this.listing.setId(IdGenerator.generateID());
    }

    @Override
    public void setType() {
        Document doc = Jsoup.parse(this.listing.getRawListing());
        Element listingMeta = doc.selectFirst("div.listing-meta span");

        this.listing.setType((listingMeta != null) ? listingMeta.text() : "Type not found");
    }

    @Override
    public void setSite() {
        this.listing.setSite(Site.RoyalLePage);
    }


    @Override
    public void setAddress() {
        this.listing.setAddress("RoyalLePage Address");
        Document document = Jsoup.parse(this.listing.getRawListing());
        Element addressElement = document.selectFirst("address[class=address-1] a");
        this.listing.setAddress(addressElement != null ? addressElement.text() : "Address not found");
    }

    @Override
    public void setUrl() {
        Document doc = Jsoup.parse(this.listing.getRawListing());
        Element linkElement = doc.select("div.card__body address.address-1 a").first();
        this.listing.setUrl(linkElement.attr("href"));
    }

    @Override
    public void setNumBedrooms() {
        Document doc = Jsoup.parse(this.listing.getRawListing());
        Elements listingMeta = doc.select("div.listing-meta span");
        String result = (listingMeta.size() > 1) ? listingMeta.get(1).text() : "Type not found";

        if (Objects.equals(result, "Type not found")) {
            this.listing.setNumBedrooms(0);
            this.listing.setNumBathrooms(0);
        } else {
            String[] parts = result.split("\\s*,\\s*");
            for (String part : parts) {
                if (part.contains("bds")) {
                    this.listing.setNumBedrooms(Integer.parseInt(part.replaceAll("\\D+", "")));
                } else if (part.contains("bath") || part.contains("bed")) {
                    this.listing.setNumBathrooms(Integer.parseInt(part.replaceAll("\\D+", "")));
                }
            }
        }
    }

    @Override
    public void setNumBathrooms() { } // on this site it is done in the setNumBedrooms method

    @Override
    public void setSqft() {
        this.listing.setSqft(0.0);
    }

    @Override
    public void setPrice() {
        Document doc = Jsoup.parse(this.listing.getRawListing());
        Element priceElement = doc.selectFirst("span.title--h3.price");

        if (priceElement != null) {
            String priceText = priceElement.text().replaceAll("[^\\d,]", "").replace(",", "");
            this.listing.setPrice(Double.parseDouble(priceText));
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
