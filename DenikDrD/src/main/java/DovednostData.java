import lombok.Data;

@Data
public class DovednostData {
    private int index;
    private String nazevDovednosti;
    private String vlastnostDovednosti;
    private String popisDovednosti;
    private String povolani;

    public DovednostData() {
    }

    @Override
    public String toString() {
        return index + ": " + nazevDovednosti + " (" + vlastnostDovednosti + ")";
    }
}
