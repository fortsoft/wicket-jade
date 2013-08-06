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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;

import ro.fortsoft.wicket.jade.JadePanel;
import de.neuland.jade4j.parser.Parser;
import de.neuland.jade4j.parser.node.Node;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.ReaderTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;

/**
 * @author Decebal Suiu
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public HomePage() {
		super();		
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("The Hitchhiker's Guide to the Galaxy", 5.70, true));
		books.add(new Book("Life, the Universe and Everything", 5.60, false));
		books.add(new Book("The Restaurant at the End of the Universe", 5.40, true));
		
		Map<String, Object> jadeModel = new HashMap<String, Object>();
		jadeModel.put("books", books);
		
		add(new JadePanel("jadePanel", jadeModel) {

			private static final long serialVersionUID = 1L;

			@Override
			public JadeTemplate getTemplate() {
				try {
					return HomePage.this.getTemplate("books.jade");
				} catch (IOException e) {
					onException(e);
				}
				
				return null;
			}
			
//		}.setThrowFreemarkerExceptions(true));
		});
	}
	
	private JadeTemplate getTemplate(String name) throws IOException {
		// TODO some performance optimization
		InputStream inputStream = HomePage.class.getResourceAsStream(name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		TemplateLoader templateLoader = new ReaderTemplateLoader(reader, name);
		Parser parser = new Parser(name, templateLoader);
		Node root = parser.parse();
		JadeTemplate template = new JadeTemplate();
		template.setTemplateLoader(templateLoader);
		template.setRootNode(root);
//		template.setPrettyPrint(true);
		
		return template;
	}
	
}
