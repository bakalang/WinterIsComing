/**
 * Copyright 2015 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2013-2015 Xeiam LLC (http://xeiam.com) and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.xdropwizard.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xdropwizard.business.Book;
import org.knowm.xdropwizard.business.BooksDAO;

/**
 * @author timmolter
 */
@Path("book")
@Produces(MediaType.APPLICATION_JSON)
public class YankBookResource {

  @GET
  @Path("random")
  public Book getRandomBook() {

    return BooksDAO.selectRandomBook();
  }

  @GET
  @Path("all")
  public List<Book> getAllBooks() {

    return BooksDAO.selectAllBooks();
  }
}
