package org.bluemix.challenge;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class LoginScreen extends LoginScreenDesign implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public LoginScreen(){
		btnSignIn.addClickListener(e -> {
			
			if (username.getValue().equals("vaadin") && password.getValue().equals("bluemix")){
				String msg = String.format("Login Successful '%s %s'.",
						username.getValue(),
						password.getValue());
				Notification.show(msg,Type.TRAY_NOTIFICATION); 
				getUI().showMainUI();
				//getUI().setContent(new MainView());
			}
			else
				Notification.show("Entered Credentials don't match with our records.",
	                  "Please try again. Click this box to close this message.",
	                  Notification.Type.ERROR_MESSAGE);
								
		});
	}
	
	@Override
    public MyUI getUI() {
        return (MyUI) super.getUI();
    }

}
