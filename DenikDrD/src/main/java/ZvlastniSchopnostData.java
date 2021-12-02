import lombok.Data;

@Data
public class ZvlastniSchopnostData {
    private int index;
    private String nazevZvlastniSchopnosti;
    private String vlastnostZvlastniSchopnosti;
    private String prikladZvlastniSchopnosti;
    private String popisZvlastniSchopnosti;
    private String prirazeni;

    public ZvlastniSchopnostData() {
    }

    @Override
    public String toString() {
        return index + ": " + nazevZvlastniSchopnosti + " (" + vlastnostZvlastniSchopnosti + ")";
    }
}
