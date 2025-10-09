public class MessageProcessor {
    private final MessageUnpacker unpacker;
    private final AuditTrail auditor;
    private final MessageDispatcher dispatcher;
    private final LocationFinder locationFinder;
    private final DomesticNotifier domesticNotifier;
    private final ImportedNotifier importedNotifier;
    public MessageProcessor(MessageUnpacker unpacker,
                            AuditTrail auditor,
                            MessageDispatcher dispatcher,
                            LocationFinder locationFinder,
                            DomesticNotifier domesticNotifier,
                            ImportedNotifier importedNotifier)
    {
        this.unpacker = unpacker;
        this.auditor = auditor;
        this.dispatcher = dispatcher;
        this.locationFinder = locationFinder;
        this.domesticNotifier = domesticNotifier;
        this.importedNotifier = importedNotifier;
    }
    public void onMessage(Message rawMessage) {
        UnpackedMessage unpacked = unpacker.unpack(rawMessage);
        auditor.recordReceiptOf(unpacked);
        // some other activity here
        if (locationFinder.isDomestic(unpacked)) {
            domesticNotifier.notify(unpacked.asDomesticMessage());
        } else {
            importedNotifier.notify(unpacked.asImportedMessage());
        }
        dispatcher.dispatch(unpacked);
    }
}
