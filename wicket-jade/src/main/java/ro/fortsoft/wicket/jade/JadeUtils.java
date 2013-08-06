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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.neuland.jade4j.parser.Parser;
import de.neuland.jade4j.parser.node.Node;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.ReaderTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;

/**
 * @author Decebal Suiu
 */
public class JadeUtils {

	public static JadeTemplate getTemplateFromPackage(Class<?> scope, String name) throws IOException {
		// TODO some performance optimization
		InputStream inputStream = scope.getResourceAsStream(name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		JadeTemplate template = new JadeTemplate();
		TemplateLoader templateLoader = new ReaderTemplateLoader(reader, name);
		Parser parser = new Parser(name, templateLoader);
		Node root = parser.parse();
		template.setTemplateLoader(templateLoader);
		template.setRootNode(root);
		template.setPrettyPrint(true);
		
		return template;
	}

}
