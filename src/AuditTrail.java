public interface AuditTrail {
    void recordReceiptOf(UnpackedMessage unpacked);
}
