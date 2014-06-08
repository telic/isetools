/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.nines.ise.node;

import ca.nines.ise.dom.Fragment;

/**
 *
 * @author Michael Joyce <michael@negativespace.net>
 */
public class AbbrNode extends Node {

  public String type() {
    return "#ABBR";
  }

  @Override
  String getName() {
    return "#ABBR";
  }

  @Override
  Fragment expanded() {
    TextNode n = new TextNode();
    Fragment f = new Fragment();
    f.add(n);
    return f;
  }

  @Override
  String plain() {
    return getText();
  }

  @Override
  String unicode() {
    return getText();
  }

}
