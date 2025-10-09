public class AuctionSiteCredentialsBuilder implements Builder<AuctionSiteCredentials> {
    public AuctionSiteCredentials build() {
        return new AuctionSiteCredentials();
    }
    public AuctionSiteCredentialsBuilder forSite(Builder<AuctionSite> builder) {
        return this;
    }
}
