package ro.fortsoft.wicket.jade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.string.Strings;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.parser.Parser;
import de.neuland.jade4j.parser.node.Node;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.ReaderTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;

/**
 * @author Decebal Suiu
 * @author Nigel Sheridan-Smith (github.com/wtfiwtz)
 */
public class JadeWebPage extends GenericWebPage<Map<String, Object>> 
						implements IMarkupResourceStreamProvider { //, IMarkupCacheKeyProvider {

	private static final long serialVersionUID = 1L;

	private boolean throwJadeExceptions;
	private transient String stackTraceAsString;
	
	public JadeWebPage() {
		super(initializeModel());
	}

	public JadeWebPage(PageParameters pageParams) {
		super(pageParams);
		setModel(initializeModel(pageParams));
	}
	
	/** 
	 * 
	 * Initialize the Jade model - to be overridden by subclasses
	 * 
	 * @return the map of model properties (string-to-object mapping pairs)
	 */
	protected static IModel<Map<String,Object>> initializeModel() {
		return Model.ofMap(new HashMap<String,Object>());
	}

	/** 
	 * 
	 * Initialize the Jade model - to be overridden by subclasses
	 * 
	 * @param pageParams the page parameters
	 * @return the map of model properties (string-to-object mapping pairs)
	 */
	protected IModel<Map<String,Object>> initializeModel(PageParameters pageParams) {
		return Model.ofMap(new HashMap<String,Object>());
	}

	
//	@Override
//	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
//		// don't cache the evaluated template
//		return null;
//	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		// load the jade template
		JadeTemplate template = null;
		try {
			template = getTemplate(containerClass);
		} catch (IOException e) {
			onException(e);
		}

		// evaluate the jade template
		String htmlMarkup;
		//if (htmlMarkup == null) {
			htmlMarkup = Jade4J.render(template, getModelObject());
		//}

		return new StringResourceStream(htmlMarkup);
	}

	protected JadeTemplate getTemplate(Class<?> containerClass) throws IOException {
		
		String templateName = containerClass.getName();
		String resourceName = containerClass.getSimpleName() + ".jade";
		
		InputStream inputStream = containerClass.getResourceAsStream(resourceName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		JadeTemplate template = new JadeTemplate();
		TemplateLoader templateLoader = new ReaderTemplateLoader(reader, templateName);
		Parser parser = new Parser(templateName, templateLoader);
		Node root = parser.parse();
		template.setTemplateLoader(templateLoader);
		template.setRootNode(root);
		template.setPrettyPrint(true);
		
		return template;
	}

	protected void onException(Exception e) {
		if (!throwJadeExceptions) {
			// print the exception on the panel
			stackTraceAsString = Strings.toString(e);
		} else {
			// rethrow the exception
			throw new WicketRuntimeException(e);
		}
	}
	
}
