/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.nines.ise.node.chr;

import ca.nines.ise.dom.Fragment;
import ca.nines.ise.node.CharNode;
import ca.nines.ise.node.Node;
import ca.nines.ise.node.StartNode;
import ca.nines.ise.validator.node.TestBase;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michael
 */
public class AccentCharNodeTest extends TestBase {

  /**
   * Test of expanded method, of class AccentCharNode.
   * 
   * Check that all the new nodes have the correct line, column, etc. here.
   * It isn't really necessary to repeat those tests elsewhere.
   * @throws java.io.IOException
   */
  @Test
  public void testExpanded() throws IOException {
    testExpansion("{^e}", "e\u0302");
    testExpansion("{\"e}", "e\u0308");
    testExpansion("{'e}", "e\u0301");
    testExpansion("{`e}", "e\u0300");
    testExpansion("{_e}", "e\u0304");
    testExpansion("{~e}", "e\u0303");
    testExpansion("{!e}", "e\uFFFD", new String[]{"char.accent.unknown"});
  }

  private void testExpansion(String text, String unicode) throws IOException {
    testExpansion(text, unicode, new String[]{});
  }
  
  private void testExpansion(String text, String unicode, String[] errors) throws IOException {
    CharNode charNode = new AccentCharNode();
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
    assertEquals("ACCENT", node.getName());
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
    assertEquals(Normalizer.normalize(unicode, Form.NFC), node.getText());

    node = iterator.next();
    assertEquals("END", node.type().name());
    assertEquals("3.2.1", node.getAsl());
    assertEquals(42, node.getColumn());
    assertEquals(420, node.getLine());
    assertEquals("11.3", node.getTLN());
    assertEquals("ACCENT", node.getName());
    checkLog(errors);
  }
  
}