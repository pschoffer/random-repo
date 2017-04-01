package airporter.controller;

import airporter.form.QueryForm;
import airporter.model.entity.Country;
import airporter.service.QueryService;
import airporter.service.dto.CountryAirports;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by pavel on 1.4.17.
 */
public class QueryControllerTest {

    public static final String FORM = "form";
    private static final String ERROR_KEY = "error";
    private static final String COUNTRY_KEY = "country_info";
    private static final String COUNTRY = "CZ";
    private static final String OBJECT_NAME = "form";

    @Mock
    private QueryService queryService;
    @InjectMocks
    private QueryController controller = new QueryController();
    private Model model;
    private QueryForm inputForm;
    @Mock
    private BindingResult bindResult;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new ExtendedModelMap();
        inputForm = new QueryForm();
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

        // execute
        controller.querySubmit(model, inputForm, bindResult);

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
        when(queryService.getCountryAirports(COUNTRY)).thenThrow(exception);

        // execute
        controller.querySubmit(model, inputForm, bindResult);

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
        controller.querySubmit(model, inputForm, bindResult);

        // assert
        final String errorMsg = (String) model.asMap().get(ERROR_KEY);
        Assert.assertEquals(errorMsg, "Country: " + error);
    }

    @Test
    public void whenCountryFound_thenPassToModel() throws Exception {
        // prepare
        inputForm.setCountry(COUNTRY);
        final Country expectedCountry = new Country();
        final CountryAirports expectedCountryAirports = new CountryAirports(expectedCountry, new ArrayList<>());
        expectedCountry.setCode(COUNTRY);
        when(queryService.getCountryAirports(anyString())).thenReturn(expectedCountryAirports);

        // execute
        controller.querySubmit(model, inputForm, bindResult);

        // assert
        final CountryAirports countryAirports = (CountryAirports) model.asMap().get(COUNTRY_KEY);
        final String errorMsg = (String) model.asMap().get(ERROR_KEY);
        Assert.assertNull(errorMsg);
        Assert.assertEquals(countryAirports.getCountry(), expectedCountry);
    }
}