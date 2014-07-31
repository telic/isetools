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

package ca.nines.ise.node;

import ca.nines.ise.annotation.ErrorCode;
import ca.nines.ise.dom.Fragment;
import ca.nines.ise.log.Log;
import ca.nines.ise.log.Message;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Michael Joyce <michael@negativespace.net>
 */
public class AbbrNode extends Node {

  private static final Pattern longAbbrPattern = Pattern.compile("^([a-zA-Z])\\^([a-zA-Z])$");
  private static final Pattern shortAbbrPattern = Pattern.compile("^([a-zA-Z]+)$");

  @ErrorCode(code = {
    "abbr.expand.syntax"
  })
  @Override
  public Fragment expanded() {

    String content = text.replaceAll("\\|", "");
    Fragment fragment = new Fragment();
    TagNode node = new StartNode(this);
    node.setName("ABBR");

    node.setAttribute("expan", "xxxxx");
    fragment.add(node);
    Matcher m = longAbbrPattern.matcher(content);
    if (m.find()) {
      TextNode textNode = new TextNode(this);
      textNode.setText(m.group(1));
      fragment.add(textNode);
      node = new StartNode(this);
      node.setName("SUP");
      fragment.add(node);
      textNode = new TextNode(this);
      textNode.setText(m.group(2));
      fragment.add(textNode);
      node = new EndNode(this);
      node.setName("SUP");
      fragment.add(node);
    } else {
      if (!content.matches(shortAbbrPattern.pattern())) {
        Message msg = Message.builder("abbr.expand.syntax")
                .fromNode(this)
                .addNote("The syntax error occurs in " + text)
                .build();
        Log.getInstance().add(msg);
      }
      TextNode tn = new TextNode(this);
      tn.setText(content);
      fragment.add(tn);
    }
    node = new EndNode(this);
    node.setName("ABBR");
    fragment.add(node);

    return fragment;
  }

  @Override
  public String plain() {
    return getText().replaceAll("[^a-zA-Z]*", "");
  }

  @Override
  public String sgml() {
    return text;
  }

  @Override
  public NodeType type() {
    return NodeType.ABBR;
  }

  @Override
  public String unicode() {
    return getText().replaceAll("[^a-zA-Z]*", "");
  }
  
  
}
