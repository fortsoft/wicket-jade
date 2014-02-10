package ro.fortsoft.wicket.jade.demo;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Decebal Suiu
 */
public class EmbeddedPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public EmbeddedPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new Label("message", "Pure wicket panel in Jade panel !!!"));
	}

}
