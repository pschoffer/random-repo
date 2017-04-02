package airporter.controller;

import airporter.service.QueryService;
import airporter.service.dto.CountryRunway;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Created by pavel on 2.4.17.
 */
public class ReportControllerTest {
    private static final int LIMIT = 1;
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private static final String TOTAL = "total";

    @Mock
    private QueryService queryService;
    @InjectMocks
    private ReportController controller = new ReportController();
    private Model model;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        model = new ExtendedModelMap();
    }

    @Test
    public void whenQueryForm() throws Exception {
        // prepare
        final List<CountryRunway> top = new ArrayList<>();
        final List<CountryRunway> bottom = new ArrayList<>();
        final long total = 1;
        when(queryService.findCountriesHighestAirportCount(LIMIT)).thenReturn(top);
        when(queryService.findCountriesLowestAirportCount(LIMIT)).thenReturn(bottom);
        when(queryService.getTotalCountCountries()).thenReturn(total);

        // execute
        controller.queryForm(model);

        // assert
        final Map<String, Object> modelAttributes = model.asMap();
        Assert.assertEquals(modelAttributes.get(TOP), top);
        Assert.assertEquals(modelAttributes.get(BOTTOM), bottom);
        Assert.assertEquals(modelAttributes.get(TOTAL), total);
    }

}