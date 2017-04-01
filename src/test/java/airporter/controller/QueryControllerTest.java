package airporter.controller;

import airporter.form.QueryForm;
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

    private QueryController controller = new QueryController();
    private Model model;

    @BeforeMethod
    public void setUp() throws Exception {
        model = new ExtendedModelMap();
    }

    @Test
    public void testQueryForm() throws Exception {
        // execute
        controller.queryForm(model);

        // assert
        final Object form = model.asMap().get(FORM);
        Assert.assertTrue(form instanceof QueryForm);
    }

    @Test
    public void testQuerySubmit() throws Exception {

    }

}