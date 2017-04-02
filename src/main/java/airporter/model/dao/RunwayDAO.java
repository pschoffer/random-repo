package airporter.model.dao;

import airporter.model.dao.dto.RunwaySurfaceCount;

import java.util.List;

/**
 * Created by pavel on 1.4.17.
 */
public interface RunwayDAO {
    List<RunwaySurfaceCount> findRunwaySurfaceCounts(final List<String> countryCodes);
}
