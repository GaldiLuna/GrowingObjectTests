public class RacingCar {
    private final Track track;
    private DrivingStrategy driver = DriverTypes.borderlineAggressiveDriving();
    private Tyres tyres = TyreTypes.mediumSlicks();
    private Suspension suspension = SuspensionTypes.mediumStiffness();
    private Wing frontWing = WingTypes.mediumDownforce();
    private Wing backWing = WingTypes.mediumDownforce();
    private double fuelLoad = 0.5;
    private CarListener listener = CarListener.NONE;
    public RacingCar(Track track)
    {
        this.track = track;
    }
    public void setSuspension(Suspension suspension) {  }
    public void setTyres(Tyres tyres) {  }
    public void setEngine(Engine engine) {  }
    public void setListener(CarListener listener) {  }
}
