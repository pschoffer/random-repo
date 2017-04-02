package airporter.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pavel on 1.4.17.
 */
public class QueryForm {
    @NotNull
    @Size(min=2, max=50)
    private String country;

    private String option;

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getOption() {
        return option;
    }

    public void setOption(final String option) {
        this.option = option;
    }
}
