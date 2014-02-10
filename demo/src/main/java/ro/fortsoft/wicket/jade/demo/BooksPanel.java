/*
 * Copyright 2013 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package ro.fortsoft.wicket.jade.demo;

import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.panel.Panel;

import ro.fortsoft.wicket.jade.JadePanel;

/**
 * @author Decebal Suiu
 */
public class BooksPanel extends JadePanel {

	private static final long serialVersionUID = 1L;

	public BooksPanel(String id, Map<String, Object> values) {
		super(id, values);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Panel embeddedPanel = new EmbeddedPanel("embedded") {
			
			private static final long serialVersionUID = 1L;

			@Override
		    public MarkupType getMarkupType() {
		        return MarkupType.HTML_MARKUP_TYPE;
		    }
			
		};		
		embeddedPanel.add(AttributeModifier.append("style", "background-color: lightgray;"));
		add(embeddedPanel);
	}
	
}
