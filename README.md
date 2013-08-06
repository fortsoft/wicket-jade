Description
=====================
Wicket-Jade integration. The current implementation is based on [jade4j](https://github.com/neuland/jade4j).

Current build status: [![Build Status](https://buildhive.cloudbees.com/job/decebals/job/wicket-jade/badge/icon)](https://buildhive.cloudbees.com/job/decebals/job/wicket-jade/)

Components
-------------------
- **JadePanel** is a panel that displays the result of rendering a [Jade](http://jade-lang.com/) template.

Artifacts
-------------------
- Wicket Jade `wicket-jade` (jar)
- Wicket Jade Demo `wicket-jade-demo` (war)

Using Maven
-------------------
In your pom.xml you must define the dependencies to Wicket Jade artifacts with:

```xml
<dependency>
    <groupId>ro.fortsoft.wicket.jade</groupId>
    <artifactId>wicket-jade</artifactId>
    <version>${wicket-jade.version}</version>
</dependency>    
```

where ${wicket-jade.version} is the last wicket-jade version.

You may want to check for the latest released version using [Maven Search](http://search.maven.org/#search%7Cga%7C1%7Cwicket-jade)

How to use
-------------------
It's very simple to add a jade panel in your wicket application.

Define entity (business) object - a simple POJO:

```java
public class Book implements Serializable {
	
	private String name;
	private double price;
	private boolean available;
	
	public Book(String name, double price, boolean available) {
		this.name = name;
		this.price = price;
		this.available = available;
	}

    // getters and setters
}
```

```java
public class Author implements Serializable {

	private String firstName;
	private String lastName;
	private String country;
	
	public Author(String firstName, String lastName, String country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}
	
	// getters and setters
}
```

In your wicket page:

```java
public class HomePage extends WebPage {

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
		
		Author author = new Author("Douglas", "Adams", "England");
		
		Map<String, Object> authorModel = new MicroMap<String, Object>();
		authorModel.put("author", author);
		AuthorPanel authorPanel = new AuthorPanel("authorPanel", authorModel);
		authorPanel.setRenderBodyOnly(true);
		
		booksPanel.add(authorPanel);
    }  

}
```

Your jade template - BooksPanel.jade (in HomePage package or in other location):

```
div(wicket:id="authorPanel")
ol#books
  for book in books
    if book.available
      li #{book.name} for #{book.price} €
```

AuthorPanel.jade

```
.author
  .name #{author.firstName} #{author.lastName} from #{author.country}:
```

Running the above code will result in the following html output for jade file

```html
<div class="author">
	<div class="name">Douglas Adams from England:</div>		
</div>
<ol id="books">
	<li>The Hitchhiker's Guide to the Galaxy for 5.7 €</li>
	<li>The Restaurant at the End of the Universe for 5.4 €</li>
</ol>
```

Demo
-------------------
The demo application is in demo package.
To run the demo application use:  
 
    mvn install
    cd demo
    mvn jetty:run

In the internet browser type http://localhost:8081/.

License
--------------
Copyright 2013 Decebal Suiu
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
the License. You may obtain a copy of the License in the LICENSE file, or at:
 
http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
