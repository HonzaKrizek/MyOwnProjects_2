import lombok.Data;

@Data
public class PovolaniData {
    private int index = 0;
    private String nazevPovolani;
    private String popisPovolani;

    public PovolaniData() {
    }

    @Override
    public String toString() {
        return index + ": " + nazevPovolani;
    }

}
