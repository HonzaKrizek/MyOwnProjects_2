import lombok.Data;

@Data
public class RasaData {
    private int index = 0;
    private String nazevRasy;
    private String popisRasy;


    public RasaData() {
    }

    @Override
    public String toString() {
        return index + ": " + nazevRasy;
    }
}
