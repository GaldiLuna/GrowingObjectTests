public interface ReservationRequest {
    void reserveSeatsIn(Carriage carriage);
    void cannotFindSeats();
}
