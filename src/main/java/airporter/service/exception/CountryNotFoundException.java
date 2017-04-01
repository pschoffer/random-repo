package airporter.service.exception;

import airporter.exception.AirporterException;

/**
 * Created by pavel on 1.4.17.
 */
public class CountryNotFoundException extends AirporterException {
    public CountryNotFoundException(final String msg) {
        super(msg);
    }
}
