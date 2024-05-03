import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class ListingFactory {
    static ListingEngineer eng;

    public static ArrayList<Listing> get(Site site){
        ArrayList<Listing> listings = null;
        ArrayList<String> html = null;

        switch(site){
            case RoyalLePage:
                html = new ArrayList<String>();
                listings = new ArrayList<Listing>();

                try {
                    Document document = Jsoup.connect("https://www.royallepage.ca/en/qc/montreal/properties/").get();
                    Elements cardWrapperDivs = document.select("div.card.card--listing-card.js-listing.js-property-details");

                    for (Element cardWrapper : cardWrapperDivs) {
                        html.add(cardWrapper.outerHtml());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (String s : html) {
                    eng = new ListingEngineer(new RoyalLePageBuilder(s));
                    eng.build();
                    listings.add(eng.getListing());
                }
                break;
            case Centris:
                html = new ArrayList<String>();
                listings = new ArrayList<Listing>();

                try {
                    Document document = Jsoup.connect("https://www.centris.ca/en/properties~for-sale").get();
                    Elements listingCards = document.select("div.description");

                    for (Element card : listingCards) {
                        html.add(card.outerHtml());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (String s : html) {
                    eng = new ListingEngineer(new CentrisBuilder(s));
                    eng.build();
                    listings.add(eng.getListing());
                }

                break;

            case DuProprio:
                html = new ArrayList<String>();
                listings = new ArrayList<Listing>();

                try {
                    Document document = Jsoup.connect("https://duproprio.com/en/montreal").get();
                    Elements cardWrapperDivs = document.select("div.search-results-listings-list__container");

                    for (Element cardWrapper : cardWrapperDivs) {
                        html.add(cardWrapper.outerHtml());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (String s : html) {
                    eng = new ListingEngineer(new DuProprioBuilder(s));
                    eng.build();
                    listings.add(eng.getListing());
                }
                break;
        }
        return listings;
    }
}
