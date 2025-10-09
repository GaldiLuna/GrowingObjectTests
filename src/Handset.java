public class Handset {
    private final Network network;
    private final Camera camera;
    private final Display display;
    private final DataNetwork dataNetwork;
    private final AddressBook addressBook;
    private final Storage storage;
    private final Tuner tuner;

    public Handset(Network network, Camera camera, Display display,
                   DataNetwork dataNetwork, AddressBook addressBook,
                   Storage storage, Tuner tuner)
    {
        this.network = network;
        this.camera = camera;
        this.display = display;
        this.dataNetwork = dataNetwork;
        this.addressBook = addressBook;
        this.storage = storage;
        this.tuner = tuner;
    }
    public void placeCallTo(DirectoryNumber number) {
        network.openVoiceCallTo(number);
    }
    public void takePicture() {
        Frame frame = storage.allocateNewFrame();
        camera.takePictureInto(frame);
        display.showPicture(frame);
    }
    public void showWebPage(URL url) {
        display.renderHtml(dataNetwork.retrievePage(url));
    }
    public void showAddress(SearchTerm searchTerm) {
        display.showAddress(addressBook.findAddress(searchTerm));
    }
    public void playRadio(Frequency frequency) {
        tuner.tuneTo(frequency);
        tuner.play();
    }
    // and so on
}
