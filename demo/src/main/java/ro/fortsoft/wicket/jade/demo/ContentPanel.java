package ro.fortsoft.wicket.jade.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.collections.MicroMap;

import ro.fortsoft.wicket.jade.JadePanel;

/**
 * @author Decebal Suiu
 */
public class ContentPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ContentPanel(String id) {
		super(id);
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
		
		JadePanel booksPanel = new BooksPanel("books", booksModel);
		booksPanel.add(AttributeModifier.append("style", "border: 1px solid red;"));
		add(booksPanel);
		
		Label messageLabel = new Label("message", "Pure wicket label in Jade panel!!!");
		messageLabel.add(AttributeModifier.append("style", "border: 1px solid blue;"));
		booksPanel.add(messageLabel);
		
		Author author = new Author("Douglas", "Adams", "England");
		
		Map<String, Object> authorModel = new MicroMap<String, Object>();
		authorModel.put("author", author);
		AuthorPanel authorPanel = new AuthorPanel("author", authorModel);
		
		booksPanel.add(authorPanel);		
	}
	
}
