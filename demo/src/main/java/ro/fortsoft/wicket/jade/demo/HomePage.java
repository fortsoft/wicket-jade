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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.collections.MicroMap;

import ro.fortsoft.wicket.jade.JadePanel;

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
		
		Map<String, Object> booksModel = new HashMap<String, Object>();
		booksModel.put("books", books);
		
		JadePanel booksPanel = new BooksPanel("booksPanel", booksModel);
		booksPanel.setRenderBodyOnly(true);
		add(booksPanel);
		
		booksPanel.add(new Label("wicketLabel", "Pure wicket label !!!"));
		
		Author author = new Author("Douglas", "Adams", "England");
		
		Map<String, Object> authorModel = new MicroMap<String, Object>();
		authorModel.put("author", author);
		AuthorPanel authorPanel = new AuthorPanel("authorPanel", authorModel);
		authorPanel.setRenderBodyOnly(true);
		
		booksPanel.add(authorPanel);		
	}
		
}
