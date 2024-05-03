import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuProprioBuilder implements IListingBuilder {

    private Listing listing;
    public DuProprioBuilder(String rawListing) {
        this.listing = new Listing();
        this.setRawListing(rawListing);
    }

    @Override
    public void setId() {
        this.listing.setId(IdGenerator.generateID());
    }

    @Override
    public void setType() {
        this.listing.setType("N/A");
    }

    @Override
    public void setSite() {
        this.listing.setSite(Site.DuProprio);
    }

    @Override
    public void setSqft() {
        Document document = Jsoup.parse(this.listing.getRawListing());
        Element sqFootageElement = document.select(".search-results-listings-list__item-description__characteristics-popover:contains(Living space area)").first();

        if (sqFootageElement != null) {
            String sqFootageText = sqFootageElement.nextElementSibling().text();
            try {
                double sqft = Double.parseDouble(sqFootageText.replace(",", "").replace(" ft²", ""));
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
        Element addressElement = document.select(".search-results-listings-list__item-description__address").first();
        this.listing.setAddress(addressElement.text().trim());
    }

    @Override
    public void setUrl() {
        Document doc = Jsoup.parse(this.listing.getRawListing());
        Element link = doc.selectFirst("a[href]");
        this.listing.setUrl(link.attr("href"));
    }

    @Override
    public void setNumBedrooms() {
        Document doc = Jsoup.parse(this.listing.getRawListing());
        Element bedroomsElement = doc.select(".search-results-listings-list__item-description__characteristics-popover:contains(Bedrooms)").first();
        String bedroomsText = bedroomsElement.nextSibling().toString().trim();
        this.listing.setNumBedrooms(Integer.parseInt(bedroomsText));
    }

    @Override
    public void setNumBathrooms() {
        Pattern pattern = Pattern.compile("<div class=\"search-results-listings-list__item-description__characteristics-popover\">\\s*Bathrooms \\+ Half baths\\s*<\\/div>\\s*(\\d+|\\d+\\s*\\+\\s*\\d+)");
        Matcher matcher = pattern.matcher(this.listing.getRawListing());

        if (matcher.find()) {
            this.listing.setNumBathrooms(evaluateBathroomValue(matcher.group(1)));
        } else {
            this.listing.setNumBathrooms(-1);
        }
    }

    private static int evaluateBathroomValue(String value) {
        String[] parts = value.split("\\+");
        if (parts.length == 1) {
            return Integer.parseInt(parts[0].trim());
        } else {
            int firstValue = Integer.parseInt(parts[0].trim());
            int secondValue = Integer.parseInt(parts[1].trim());
            return firstValue + secondValue;
        }
    }

    @Override
    public void setPrice() {
        Document doc = Jsoup.parse(this.listing.getRawListing());
        Element priceElement = doc.select(".search-results-listings-list__item-description__price h2").first();
        String priceString = priceElement.text().replace("$", "").replace(",", "");
        this.listing.setPrice(Double.parseDouble(priceString));
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
