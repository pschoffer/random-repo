package airporter.model.dao.dto;

/**
 * Created by pavel on 2.4.17.
 */
public class RunwayIdentCount {
    private final String ident;
    private final int count;

    public RunwayIdentCount(final String ident, final int count) {
        this.ident = ident;
        this.count = count;
    }

    public String getIdent() {
        return ident;
    }

    public int getCount() {
        return count;
    }
}
