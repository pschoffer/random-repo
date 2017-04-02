package airporter.controller;

import airporter.form.QueryForm;
import airporter.model.entity.Country;
import airporter.service.QueryService;
import airporter.service.exception.CountryNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by pavel on 1.4.17.
 */
public class QueryControllerTest {

    public static final String FORM = "form";
    private static final String ERROR_KEY = "error";
    private static final String AIRPORTS_KEY = "airports";
    private static final String COUNTRY_KEY = "country";
    private static final String MULTIPLE_OPTIONS_KEY = "options";
    private static final String COUNTRY = "CZ";
    private static final String OTHER_COUNTRY = "US";
    private static final String OBJECT_NAME = "form";
    private static final String ACTION_FIND = "find";
    private static final String ACTION_SHOW = "show";

    @Mock
    private QueryService queryService;
    @InjectMocks
    private QueryController controller = new QueryController();
    private Model model;
    private QueryForm inputForm;
    private String action;
    @Mock
    private BindingResult bindResult;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new ExtendedModelMap();
        inputForm = new QueryForm();
        action = ACTION_FIND;
    }

    @Test
    public void whenQueryForm_thenAddFormAttribute() throws Exception {
        // execute
        controller.queryForm(model);

        // assert
        final Object form = model.asMap().get(FORM);
        Assert.assertTrue(form instanceof QueryForm);
    }

    @Test
    public void wQuerySubmit_thenPassTheForm() throws Exception {
        // prepare
        inputForm.setCountry(COUNTRY);
        when(queryService.findCountries(COUNTRY)).thenReturn(Collections.singletonList(new Country()));

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        final QueryForm form = (QueryForm) model.asMap().get(FORM);
        Assert.assertEquals(form.getCountry(), inputForm.getCountry());
    }


    @Test
    public void whenCountryNotFound_thenErrorInModel() throws Exception {
        // prepare
        inputForm.setCountry(COUNTRY);
        final String expectedError = "Not Found";
        final CountryNotFoundException exception = new CountryNotFoundException(expectedError);
        when(queryService.findCountries(COUNTRY)).thenThrow(exception);

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        final String errorMsg = (String) model.asMap().get(ERROR_KEY);
        Assert.assertEquals(errorMsg, expectedError);
    }

    @Test
    public void whenNameTooLong_thenErrorInModel() throws Exception {
        // prepare
        final String error = "too long";
        final ObjectError errorObject = new ObjectError(OBJECT_NAME,  error);
        when(bindResult.hasErrors()).thenReturn(true);
        when(bindResult.getAllErrors()).thenReturn(Arrays.asList(errorObject));

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        final String errorMsg = (String) model.asMap().get(ERROR_KEY);
        Assert.assertEquals(errorMsg, "Country: " + error);
    }

    @Test
    public void whenDataFound_thenPassToModel() throws Exception {
        // prepare
        inputForm.setCountry(COUNTRY);
        final Country expectedCountry = new Country();
        expectedCountry.setCode(COUNTRY);
        when(queryService.findCountries(anyString())).thenReturn(Collections.singletonList(expectedCountry));
        when(queryService.getCountryAirports(anyString())).thenReturn(new ArrayList<>());

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        final Country country = (Country) model.asMap().get(COUNTRY_KEY);
        final String errorMsg = (String) model.asMap().get(ERROR_KEY);
        final Object airports = model.asMap().get(AIRPORTS_KEY);
        Assert.assertNull(errorMsg);
        Assert.assertEquals(country, expectedCountry);
        Assert.assertNotNull(airports);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenMoreCountries_thenPassToModel() throws Exception {
        // prepare
        when(queryService.findCountries(anyString())).thenReturn(Arrays.asList(new Country(), new Country()));
        inputForm.setCountry(COUNTRY);

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        final List countries = (List<Country>) model.asMap().get(MULTIPLE_OPTIONS_KEY);
        Assert.assertEquals(countries.size(), 2);
    }

    @Test
    public void whenActionFind_thenSearchByCountry() throws Exception {
        // prepare
        inputForm.setCountry(COUNTRY);
        inputForm.setOption(OTHER_COUNTRY);
        when(queryService.findCountries(anyString())).thenReturn(Collections.singletonList(new Country()));

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        verify(queryService).findCountries(COUNTRY);
    }

    @Test
    public void whenActionShow_thenSearchByOption() throws Exception {
        // prepare
        action = ACTION_SHOW;
        inputForm.setCountry(COUNTRY);
        inputForm.setOption(OTHER_COUNTRY);
        when(queryService.findCountries(anyString())).thenReturn(Collections.singletonList(new Country()));

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        verify(queryService).findCountries(OTHER_COUNTRY);
    }

    @Test
    public void whenActionShowButNoOption_thenSearchByCountry() throws Exception {
        // prepare
        action = ACTION_SHOW;
        inputForm.setCountry(COUNTRY);
        inputForm.setOption(null);
        when(queryService.findCountries(anyString())).thenReturn(Collections.singletonList(new Country()));

        // execute
        controller.querySubmit(model, inputForm, bindResult, action);

        // assert
        verify(queryService).findCountries(COUNTRY);
    }
}