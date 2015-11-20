package org.bluemix.challenge;

import javax.servlet.annotation.WebServlet;

import org.bluemix.challenge.backend.Customer;
import org.bluemix.challenge.backend.DummyDataService;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.client.widgets.Grid.SelectionMode;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("mytheme")
@Widgetset("org.bluemix.challenge.MyAppWidgetset")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        // Create a text field
        final TextField tf = new TextField("Enter your Name below");
        layout.addComponent(tf);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking " + tf.getValue()));
            }
        });
        
        layout.addComponent(button);
        
        final DummyDataService instance = DummyDataService.createDemoService();
        
        BeanContainer<Long, Customer> customerBeans = new BeanContainer<Long, Customer> (Customer.class);
        customerBeans.setBeanIdProperty("firstName");
        customerBeans.addAll(instance.findAll());
        
        ComboBox customerCmbBx = new ComboBox();
        customerCmbBx.setContainerDataSource(customerBeans);
        customerCmbBx.setFilteringMode(FilteringMode.CONTAINS);
        
        layout.addComponent(customerCmbBx);
        
        Grid grid = new Grid(customerBeans);
        grid.setSizeFull();
        grid.setColumnOrder("firstName","lastName","email","phone","address","city","zipCode");
        layout.addComponent(grid);
        
     // Use single-selection mode (default)
        grid.addSelectionListener(selectionEvent -> { // Java 8
            // Get selection from the selection model
            Object selected = ((SingleSelectionModel)
                grid.getSelectionModel()).getSelectedRow();

            if (selected != null)
                Notification.show("Selected " +
                    grid.getContainerDataSource().getItem(selected)
                        .getItemProperty("firstName").getValue());
            else
                Notification.show("Nothing selected");
        });
        

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
