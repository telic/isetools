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

package ca.nines.ise.node.chr;

import ca.nines.ise.dom.Fragment;
import ca.nines.ise.node.CharNode;
import ca.nines.ise.node.Node;
import ca.nines.ise.node.StartNode;
import ca.nines.ise.validator.node.TestBase;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michael
 */
public class CodePointCharNodeTest extends TestBase {

  /**
   * Test of expanded method, of class CodePointChar.
   */
  @Test
  public void testExpanded() throws IOException {
    // leading zeros
    testExpansion("{\\u007A}", "z"); // extended latin
    testExpansion("{\\u0106}", "\u0106"); // extended latin
    testExpansion("{\\u03A6}", "\u03A6"); // greek PHI
    testExpansion("{\\u042F}", "\u042F"); // cyrillic YA.
    
    // no leading zeros
    testExpansion("{\\u7A}", "z"); // extended latin
    testExpansion("{\\u106}", "\u0106"); // extended latin
    testExpansion("{\\u3A6}", "\u03A6"); // greek PHI
    testExpansion("{\\u42F}", "\u042F"); // cyrillic YA.

    // lower case
    testExpansion("{\\u7a}", "z"); // extended latin
    testExpansion("{\\u3a6}", "\u03A6"); // greek PHI
    testExpansion("{\\u42f}", "\u042F"); // cyrillic YA.

    // higher characters
    testExpansion("{\\u6c34}", "\u6C34"); // CJK ideograph water
    
    // now this is just silly. MUSICAL SYMBOL G CLEF.
    testExpansion("{\\u1D11E}", new String(Character.toChars(0x1d11e))); 
  }

  private void testExpansion(String text, String unicode) throws IOException {
    testExpansion(text, unicode, new String[]{});
  }

  private void testExpansion(String text, String unicode, String[] errors) throws IOException {
    CharNode charNode = new CodePointCharNode();
    charNode.setText(text);
    charNode.setAsl("3.2.1");
    charNode.setColumn(42);
    charNode.setLine(420);
    charNode.setTLN("11.3");
    Fragment dom = charNode.expanded();
    Iterator<Node> iterator = dom.iterator();
    Node node;

    assertEquals(3, dom.size());
    node = iterator.next();
    assertEquals("START", node.type().name());
    assertEquals("CODEPOINT", node.getName());
    assertEquals("3.2.1", node.getAsl());
    assertEquals(42, node.getColumn());
    assertEquals(420, node.getLine());
    assertEquals("11.3", node.getTLN());
    assertArrayEquals(new String[]{"setting"}, ((StartNode) node).getAttributeNames());
    assertEquals(text, ((StartNode) node).getAttribute("setting"));

    node = iterator.next();
    assertEquals("TEXT", node.type().name());
    assertEquals("3.2.1", node.getAsl());
    assertEquals(42, node.getColumn());
    assertEquals(420, node.getLine());
    assertEquals("11.3", node.getTLN());
    assertEquals(Normalizer.normalize(unicode, Normalizer.Form.NFC), node.getText());

    node = iterator.next();
    assertEquals("END", node.type().name());
    assertEquals("3.2.1", node.getAsl());
    assertEquals(42, node.getColumn());
    assertEquals(420, node.getLine());
    assertEquals("11.3", node.getTLN());
    assertEquals("CODEPOINT", node.getName());
    checkLog(errors);
  }

}
