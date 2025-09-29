public class MessageProcessor {
    public MessageProcessor(MessageUnpacker unpacker,
                            AuditTrail auditor,
                            MessageDispatcher dispatcher
                            )
    {
        messageProcessor = new MessageProcessor(new XmlMessageUnpacker(counterpartyFinder), auditor,
                        new MessageDispatcher(locationFinder, domesticNotifier, importedNotifier));
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
