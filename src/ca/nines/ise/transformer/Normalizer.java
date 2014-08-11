/*
 * Copyright (C) 2014 Michael Joyce <ubermichael@gmail.com>
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
package ca.nines.ise.transformer;

import ca.nines.ise.node.NodeType;
import ca.nines.ise.node.TextNode;

/**
 *
 * @author Michael Joyce <ubermichael@gmail.com>
 */
public class Normalizer extends IdentityTransform {

  private StringBuilder sb = new StringBuilder();

  @Override
  public void text(TextNode n) {
    sb.append(n.getText());

    if (peek().type() != NodeType.TEXT) {
      TextNode txt = new TextNode(n);
      String s = sb.toString();
      sb = new StringBuilder();
      s = s.replaceAll("[ \\t\\x0B\\f\\r]+", " ");
      s = s.replaceAll("\\n\\s+", "\n");
      s = s.replaceAll("\\n\\n+", "\n");
      txt.setText(s);
      dom.add(txt);
    }
  }

}