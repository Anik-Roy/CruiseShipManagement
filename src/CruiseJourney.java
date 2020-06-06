import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CruiseJourney {
    private List<CruiseShip> shipList;
    private float totalCost = 0.0f;
    private Calendar initialStartDate;

    public List<CruiseShip> getShipList() {
        return shipList;
    }

    public void setShipList(List<CruiseShip> shipList) {
        this.shipList = shipList;
    }

    public CruiseJourney() {
        shipList = new ArrayList<>();
    }

    public CruiseJourney(List<CruiseShip> shipList) {
        this.shipList = shipList;
    }

    boolean addCruise(CruiseShip ship) {
        shipList.add(ship);
        totalCost += ship.getCost();
        return true;
    }

    boolean removeLastTrip() {
        if(shipList.size() > 0) {
            shipList.remove(shipList.size() - 1);
            return true;
        }
        return false;
    }

    boolean containsPort(String port) {
        for(CruiseShip cruiseShip : shipList) {
            if(cruiseShip.getDepartPort().equals(port) || cruiseShip.getArrivalPort().equals(port)) {
                return true;
            }
        }
        return false;
    }

    String getStartPort() {
        return shipList.get(0).getDepartPort();
    }

    String getEndPort() {
        return shipList.get(shipList.size()-1).getArrivalPort();
    }

    Calendar getStartDate() {
        return shipList.get(0).getDepartDate();
    }

    Calendar getEndDate() {
        if(shipList.isEmpty()) {
            return initialStartDate;
        }
        return shipList.get(shipList.size()-1).getArrivalDate();
    }

    CruiseJourney cloneJourney(CruiseJourney current) {
        List<CruiseShip> ll = current.getShipList();
        CruiseJourney cloneJourney = new CruiseJourney();

        cloneJourney.shipList.addAll(ll);
        return cloneJourney;
    }

    int getNumberOfTrips() {
        return shipList.size();
    }

    double getTotalCost() {
        return this.totalCost;
    }

    public void setInitialStartDate(Calendar date) {
        this.initialStartDate = date;
    }

    @Override
    public String toString() {
        String data = "";

        for(CruiseShip ship : shipList) {
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
            fmt.setCalendar(ship.getDepartDate());
            String departFormatted = fmt.format(ship.getDepartDate().getTime());
            fmt.setCalendar(ship.getArrivalDate());
            String arrivalFormatted = fmt.format(ship.getArrivalDate().getTime());
            String s = ">>>>> BOAT :"+ship.getBoatName()+" COST : $"+ship.getCost()+" <<<<<< "+
                    "\nLEAVING "+ship.getDepartPort()+" AT "+departFormatted+
                    "\nARRIVING :"+ship.getArrivalPort()+" AT "+arrivalFormatted+"\n";

            data += s;
        }
        return data;
    }
}
