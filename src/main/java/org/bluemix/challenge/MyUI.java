package org.bluemix.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import org.bluemix.challenge.backend.Customer;
import org.bluemix.challenge.backend.DummyDataService;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
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

	final DummyDataService instance = DummyDataService.createDemoService();
	Grid grid = new Grid();
	CustomerForm customerForm = new CustomerForm();
	LoginScreen ls = new LoginScreen();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        showMainUI();
    	//setContent(ls);
    	//setContent(new MainView());
    }

	public void showMainUI() {
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
        
        BeanContainer<Long, Customer> customerBeans = new BeanContainer<Long, Customer> (Customer.class);
        customerBeans.setBeanIdProperty("firstName");
        customerBeans.addAll(instance.findAll());
        
        ComboBox customerCmbBx = new ComboBox();
        customerCmbBx.setContainerDataSource(customerBeans);
        customerCmbBx.setFilteringMode(FilteringMode.CONTAINS);
        customerCmbBx.addValueChangeListener(e -> { 
        	System.out.println(customerCmbBx.getValue());
        	refreshContacts((String)customerCmbBx.getValue());
        });
        customerCmbBx.setCaption("Use Drop Down to Filter Grid Below");
        layout.addComponent(customerCmbBx);
        
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class));
        grid.setSizeFull();
        grid.setColumnOrder("firstName","lastName","email","phone");
        grid.removeColumn("address");
        grid.removeColumn("city");
        grid.removeColumn("zipCode");
        grid.removeColumn("id");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(e -> customerForm.edit((Customer) grid.getSelectedRow()));
        refreshContacts();
        
        final HorizontalLayout hlayout = new HorizontalLayout(grid, customerForm);
        hlayout.setSizeFull();
        hlayout.setExpandRatio(grid, 1);
        layout.addComponent(hlayout);
		
	}

    void refreshContacts() {
        grid.setContainerDataSource(new BeanItemContainer<>(
                Customer.class, instance.findAll()));
        customerForm.setVisible(false);
    }
    
    void refreshContacts(String Filter) {
        grid.setContainerDataSource(new BeanItemContainer<>(
                Customer.class, instance.findAll(Filter)));
        customerForm.setVisible(false);
    }
    
     Map<String, Integer> getGenderCounts(){
    	Map<String, Integer> genderCountMap = new HashMap<String, Integer>();
    	ArrayList<Customer> customerList = (ArrayList<Customer>) instance.findAll();
    	int maleCount =0, femaleCount =0, otherCount = 0;
    	
    	for (Customer cus: customerList	){
    		switch (cus.getGender()){
    		case Male:	maleCount++;
    		break;
    		case Female :	femaleCount++;
    		break;
    		default: otherCount++;
    		}
    	}
    	genderCountMap.put("maleCount", maleCount);
    	genderCountMap.put("femaleCount", femaleCount);
    	genderCountMap.put("otherCount", otherCount);
    	
    	return genderCountMap;
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
