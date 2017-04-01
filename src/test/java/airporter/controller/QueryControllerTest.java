package airporter.controller;

import airporter.form.QueryForm;
import airporter.service.CountryService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by pavel on 1.4.17.
 */
public class QueryControllerTest {

    public static final String FORM = "form";
    private static final String COUNTRY = "CZ";

    @Mock
    private CountryService countryService;
    @InjectMocks
    private QueryController controller = new QueryController();
    private Model model;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new ExtendedModelMap();
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
        final QueryForm inputForm = new QueryForm();
        inputForm.setCountry(COUNTRY);

        // execute
        controller.querySubmit(model, inputForm);

        // assert
        final QueryForm form = (QueryForm) model.asMap().get(FORM);
        Assert.assertEquals(form.getCountry(), inputForm.getCountry());
    }

}