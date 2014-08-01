/*
 * Copyright 2013 FortSoft
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

import java.io.Serializable;

/**
 * @author Decebal Suiu
 */
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private double price;
	private boolean available;

	public Book(String name, double price, boolean available) {
		this.name = name;
		this.price = price;
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public boolean isAvailable() {
		return available;
	}

	@Override
	public String toString() {
		return "Book [naSme=" + name + ", price=" + price + ", available=" + available + "]";
	}

}
