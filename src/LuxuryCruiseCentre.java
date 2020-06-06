import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.*;

public class LuxuryCruiseCentre {
    LinkedHashMap<String, List<CruiseShip>> portMap = new LinkedHashMap<String, List<CruiseShip>>() {
        @Override
        public List<CruiseShip> get(Object key) {
            List<CruiseShip> set = super.get(key);
            if (set == null) {
                set = new ArrayList<>();
                put(key.toString(), set);
            }
            return set;
        }
    };

    LuxuryCruiseCentre() {

    }

    boolean add(CruiseShip ship) {
        if(portMap.get(ship.getDepartPort()) == null) {
            List<CruiseShip> set = new ArrayList<>();
            portMap.put(ship.getDepartPort(), set);
        }
        else {
            portMap.get(ship.getDepartPort()).add(ship);
        }
        return true;
    }

    void printMap() {
        for (Map.Entry<String, List<CruiseShip>> entry : portMap.entrySet())
            System.out.println("Key = " + entry.getKey() +
                    "===>>> Value = " + entry.getValue());

        //System.out.println(this.portMap);
    }

    List<CruiseJourney> getPossibleJourneys(String startPort, Calendar startDate, String endPort) {
        List<CruiseJourney> journeyList = new ArrayList<>();

        CruiseJourney cruiseJourney = new CruiseJourney();
        cruiseJourney.setInitialStartDate(startDate);
        try {
            findPaths(startPort, startDate, endPort, cruiseJourney, journeyList);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return journeyList;
    }

    private void findPaths(String departPort, Calendar departDate, String endPort, CruiseJourney current, List<CruiseJourney> journeyList) throws CloneNotSupportedException {
        List<CruiseShip> departShip = this.portMap.get(departPort);

        if(current.getNumberOfTrips() > 0 && current.getEndPort().equals(endPort)) {
            CruiseJourney copyJourney = current.cloneJourney(current);

            journeyList.add(copyJourney);
        }

        for(CruiseShip cruiseShip : departShip) {
            String depPort = cruiseShip.getDepartPort();
            Calendar depDate = cruiseShip.getDepartDate();
            String arrivePort = cruiseShip.getArrivalPort();
            Calendar arriveDate = cruiseShip.getArrivalDate();

            if(checkCondition(current, depPort, depDate, arrivePort)) {
                current.addCruise(cruiseShip);
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
                fmt.setCalendar(cruiseShip.getDepartDate());
                String departFormatted = fmt.format(cruiseShip.getDepartDate().getTime());

                fmt.setCalendar(cruiseShip.getArrivalDate());
                String arrivalFormatted = fmt.format(cruiseShip.getArrivalDate().getTime());

                findPaths(cruiseShip.getArrivalPort(), departDate, endPort, current, journeyList);
            }
            else {
                SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
                fmt.setCalendar(current.getEndDate());
            }
        }
        current.removeLastTrip();

    }

    public boolean checkCondition(CruiseJourney current, String departPort, Calendar departDate, String arrivePort) {
        if(current.getNumberOfTrips() == 0 ) {
            if (current.getEndDate().compareTo(departDate) <= 0) {

                SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
                fmt.setCalendar(current.getEndDate());
                String departFormatted = fmt.format(current.getEndDate().getTime());

                return true;
            }
            else {
                return false;
            }
        }
        else {
            return current.getEndPort().equals(departPort) && current.getEndDate().compareTo(departDate) <= 0 && !current.containsPort(arrivePort);
        }
    }
}
