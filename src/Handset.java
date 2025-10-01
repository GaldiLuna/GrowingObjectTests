public class Handset {
    public Handset(Network network, Camera camera, Display display,
                   DataNetwork dataNetwork, AddressBook addressBook,
                   Storage storage, Tuner tuner)
    {
        // set the fields here
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
