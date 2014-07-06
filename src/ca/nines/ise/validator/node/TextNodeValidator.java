/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.nines.ise.validator.node;

import ca.nines.ise.log.Message;
import ca.nines.ise.annotation.ErrorCode;
import ca.nines.ise.log.Log;
import ca.nines.ise.node.TextNode;
import ca.nines.ise.schema.Schema;

/**
 * TextNodeValidator performs validations on pure text nodes. In particular,
 * text nodes cannot contain the octothorpe. This validator also creates an
 * error for bad unicode characters (\uFFFD).
 * <p>
 * @author Michael Joyce <michael@negativespace.net>
 */
public class TextNodeValidator extends NodeValidator<TextNode> {

  /**
   * Construct a TextNodeValidator. The schema parameter is only necessary
   * because of the parent class.
   * <p>
   * @param schema The schema for validation.
   */
  public TextNodeValidator(Schema schema) {
    super(schema);
  }

  @ErrorCode(code = {
    "validator.text.depreciatedhash",
    "validator.text.badunicode"
  })
  @Override
  public void validate(TextNode n) {
    if (n.getText().contains("#")) {
      Message m = Message.builder("validator.text.depreciatedhash")
              .fromNode(n)
              .build();
      Log.addMessage(m);
    }
    if (n.getText().contains("\uFFFD")) {
      Message m = Message.builder("validator.text.badunicode")
              .fromNode(n)
              .build();
      Log.addMessage(m);
    }
  }

}
