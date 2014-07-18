/*
 * Copyright (C) 2014 Michael Joyce <michael@negativespace.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ca.nines.ise.document;

import ca.nines.ise.node.lemma.Coll;
import ca.nines.ise.util.BuilderInterface;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author michael
 */
public class Collations extends Apparatus<Coll> {

  public static class CollationsBuilder extends ApparatusBuilder<Coll> implements BuilderInterface<Collations> {

    private CollationsBuilder() {
    }
    
    @Override
    public Collations build() {
      return new Collations(source, lemmas);
    }

    public CollationsBuilder from(Node in) throws ParserConfigurationException, XPathExpressionException {
      return this;
    }

    public CollationsBuilder from(String in) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
      return this;
    }

    public CollationsBuilder from(File in) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
      return this;
    }

  }

  public static CollationsBuilder builder() {
    return new CollationsBuilder();
  }
  
  private Collations(String source, List<Coll> lemmas) {
    super(source, lemmas);
  }

}
