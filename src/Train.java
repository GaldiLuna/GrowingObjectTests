public class Train {
    private final List<Carriage> carriages;
    private int percentReservedBarrier = 70;

    public void reserveSeats0(ReservationRequest request) {
        for (Carriage carriage : carriages) {
            if (carriage.getSeats().getPercentReserved() < percentReservedBarrier) {
                request.reserveSeatsIn(carriage);
                return;
            }
        }
        request.cannotFindSeats();
    }

    public void reserveSeats1(ReservationRequest request) {
        for (Carriage carriage : carriages) {
            if (carriage.hasSeatsAvailableWithin(percentReservedBarrier)) {
                request.reserveSeatsIn(carriage);
                return;
            }
        }
        request.cannotFindSeats();
    }
}
