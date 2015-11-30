package org.bluemix.challenge;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class MainView extends MainViewDesign {
	
	public MainView() {
		Navigator navigator = new Navigator(UI.getCurrent(), scroll_panel);
		navigator.addView(DashboardView.VIEW_NAME, DashboardView.class);
//		navigator.addView(OrderView.VIEW_NAME, OrderView.class);
//		navigator.addView(AboutView.VIEW_NAME, AboutView.class);
		if (navigator.getState().isEmpty()) {
		        navigator.navigateTo(DashboardView.VIEW_NAME);
		    }
		
//		menuButton1.addClickListener(event -> scroll_panel.setContent(new DashboardView()));
//        menuButton2.addClickListener(event -> scroll_panel.setContent(new OrderView()));
//        menuButton3.addClickListener(event -> scroll_panel.setContent(new AboutView()));
		
		menuButton1.addClickListener(event -> doNavigate(DashboardView.VIEW_NAME));
//	    menuButton2.addClickListener(event -> doNavigate(OrderView.VIEW_NAME));
//	    menuButton3.addClickListener(event -> doNavigate(AboutView.VIEW_NAME));
	}

	private void doNavigate(String viewName) {
		// TODO Auto-generated method stub
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}

}
