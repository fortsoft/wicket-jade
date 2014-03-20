package ro.fortsoft.wicket.jade.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import ro.fortsoft.wicket.jade.JadeWebPage;

public class DemoJadePage extends JadeWebPage {

	private static final long serialVersionUID = 1L;

	public DemoJadePage(PageParameters pageParams) {
		super(pageParams);
	}
	
	protected IModel<Map<String,Object>> initializeModel(PageParameters pageParams) {

		// Create the map
		HashMap<String,Object> modelMap = new HashMap<String,Object>();
		
		// Add mappings
		modelMap.put("books", getBooks());
		
		// Return an appropriate model (either static or dynamic)
		return Model.ofMap(modelMap);
	}

	private List<Book> getBooks() {
		ArrayList<Book> list = new ArrayList<Book>();
		list.add(new Book("Wicket in Action", 50, true));
		list.add(new Book("Jade in Action", 30, true));
		list.add(new Book("Java hidden secrets", 20, false));
		return list;
	}
	
}
