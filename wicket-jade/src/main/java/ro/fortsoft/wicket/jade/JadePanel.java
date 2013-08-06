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
package ro.fortsoft.wicket.jade;

import java.io.IOException;
import java.util.Map;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.string.Strings;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.template.JadeTemplate;

/**
 * @author Decebal Suiu
 */
public abstract class JadePanel extends GenericPanel<Map<String, Object>> 
		implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

	private static final long serialVersionUID = 1L;

	private boolean throwFreemarkerExceptions;
	private transient String evaluatedTemplate;
	private transient String stackTraceAsString;
	
	public JadePanel(String id, Map<String, Object> values) {
		this(id, Model.ofMap(values));
	}
	
	public JadePanel(String id, IModel<Map<String, Object>> model) {
		super(id, model);
    }

	public abstract JadeTemplate getTemplate();
	
	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		JadeTemplate template = getTemplate();
		if (template == null) {
			throw new WicketRuntimeException("Could not find Jade template for panel: " + this);
		}

		// evaluate the template and return a new StringResourceStream
		try {
			evaluatedTemplate = evaluateJadeTemplate(template);
		} catch (Exception e) {
			onException(e);
		} 

		StringBuffer sb = new StringBuffer();
		sb.append("<wicket:panel>");
		sb.append(evaluatedTemplate);
		sb.append("</wicket:panel>");
		
		String markup = sb.toString();
//		System.out.println(markup);

		return new StringResourceStream(markup);
	}

	@Override
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		// don't cache the evaluated template
		return null;
	}

	@Override
	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		if (!Strings.isEmpty(stackTraceAsString)) {
			// TODO: only display the Jade error/stacktrace in development mode?
			replaceComponentTagBody(markupStream, openTag,
					Strings.toMultilineMarkup(stackTraceAsString));
        } else {
			if (evaluatedTemplate == null) {
				// initialize evaluatedTemplate
				getMarkupResourceStream(null, null);
			}
			replaceComponentTagBody(markupStream, openTag, evaluatedTemplate);
        }
	}

	/**
     * Whether any Jade exception should be trapped and displayed on the panel (false) or thrown
     * up to be handled by the exception mechanism of Wicket (true). The default is false, which
     * traps and displays any exception without having consequences for the other components on the
     * page.
     * <p>
     * Trapping these exceptions without disturbing the other components is especially useful in CMS
     * like applications, where 'normal' users are allowed to do basic scripting. On errors, you
     * want them to be able to have them correct them while the rest of the application keeps on
     * working.
     * </p>
     * 
     * @return Whether any Jade exceptions should be thrown or trapped. The default is false.
     */
    public JadePanel setThrowFreemarkerExceptions(boolean value) {
    	this.throwFreemarkerExceptions = value;
    	
    	return this;
    }
    
	@Override
    protected void onDetach() {
		super.onDetach();
            
        evaluatedTemplate = null;
        stackTraceAsString = null;
    }
	
	protected void onException(Exception e) {
		if (!throwFreemarkerExceptions) {
			// print the exception on the panel
			stackTraceAsString = Strings.toString(e);
		} else {
			// rethrow the exception
			throw new WicketRuntimeException(e);
		}
	}

	private String evaluateJadeTemplate(JadeTemplate jadeTemplate) throws JadeCompilerException, IOException {
		if (evaluatedTemplate == null) {
			evaluatedTemplate = Jade4J.render(getTemplate(), getModelObject());
//			System.out.println("evaluatedTemplate: " + evaluatedTemplate);
		}
		
		return evaluatedTemplate;
	}
	
}
