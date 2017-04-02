package airporter.model.dao.dto;

/**
 * Created by pavel on 2.4.17.
 */
public class RunwaySurfaceCount {
    private final String surface;
    private final int count;
    private final String countryCode;

    public RunwaySurfaceCount(final String countryCode, final String surface, final int count) {
        this.surface = surface;
        this.count = count;
        this.countryCode = countryCode;
    }

    public String getSurface() {
        return surface;
    }

    public int getCount() {
        return count;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
