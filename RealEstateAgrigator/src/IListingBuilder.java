public interface IListingBuilder {
    public void setId();
    public void setType();
    public void setSite();
    public void setSqft();
    public void setAddress();
    public void setUrl();
    public void setNumBedrooms();
    public void setNumBathrooms();
    public void setPrice();
    public void setRawListing(String rawListing);
    public Listing getListing();
}
