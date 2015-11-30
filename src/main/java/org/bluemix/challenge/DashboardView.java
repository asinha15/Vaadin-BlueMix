package org.bluemix.challenge;

import java.util.Map;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

public class DashboardView extends DashboardViewDesign implements View {
	public static final String VIEW_NAME = "dashboard";

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public DashboardView(){
		billing_header_label.setValue("Here is the breakdown for Customer Gender");
		Map data = ((MyUI) UI.getCurrent()).getGenderCounts();
		statistics_label.setValue((String) data.get("maleCount").toString());
		statistics_label6.setValue((String) data.get("femaleCount").toString());
		statistics_label18.setValue((String) data.get("otherCount").toString());
	}
	
}
