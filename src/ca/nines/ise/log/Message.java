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

package ca.nines.ise.log;

import ca.nines.ise.node.Node;
import ca.nines.ise.node.lemma.Lemma;
import ca.nines.ise.util.BuilderInterface;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Message captures all the information about a parse, validation, or other
 * error.
 * <p>
 * @author michael
 */
public class Message implements Comparable<Message> {

  private static final ErrorCodes errorCodes;

  static {
    ErrorCodes tmp = null;
    try {
      tmp = ErrorCodes.defaultErrorCodes();
    } catch (Exception ex) {
      Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
    }
    errorCodes = tmp;
  }
  private final String TLN;
  private final String code;
  private final int columnNumber;
  private final String line;
  private final int lineNumber;
  private final List<String> notes;
  private final String source;

  public static MessageBuilder builder(String code) {
    return new MessageBuilder(code);
  }

  public static class MessageBuilder implements BuilderInterface<Message> {

    private String TLN = "unknown";
    private String code = "unknown";
    private int columnNumber = 0;
    private String line = "";
    private int lineNumber = 0;
    private final List<String> notes = new ArrayList<>();
    private String source = "unknown";

    private MessageBuilder(String code) {
      this.code = code;
    }

    public MessageBuilder addNote(String note) {
      notes.add(note);
      return this;
    }

    @Override
    public Message build() {
      Message m = new Message(code, TLN, lineNumber, columnNumber, line, source, notes);
      return m;
    }

    public MessageBuilder fromNode(Node n) {
      setColumnNumber(n.getColumn());
      setLineNumber(n.getLine());
      setSource(n.getSource());
      setTLN(n.getTLN());
      return this;
    }
    
    public MessageBuilder fromLemma(Lemma lem) {
      setLineNumber(lem.getLineNumber());
      setSource(lem.getSource());
      setTLN(lem.getTln());
      return this;
    }

    public MessageBuilder setColumnNumber(int columnNumber) {
      this.columnNumber = columnNumber;
      return this;
    }

    public MessageBuilder setLine(String line) {
      this.line = line;
      return this;
    }

    public MessageBuilder setLineNumber(int lineNumber) {
      this.lineNumber = lineNumber;
      return this;
    }

    public MessageBuilder setSource(String source) {
      this.source = source;
      return this;
    }

    public MessageBuilder setTLN(String TLN) {
      this.TLN = TLN;
      return this;
    }
  }

  Message(String code, String TLN, int lineNumber, int columnNumber, String line, String source, List<String> notes) {
    this.code = code;
    this.TLN = TLN;
    this.lineNumber = lineNumber;
    this.columnNumber = columnNumber;
    this.line = line;
    this.source = source;
    this.notes = notes;
  }

  @Override
  public int compareTo(Message o) {
    if (!this.source.equals(o.source)) {
      return this.source.compareTo(o.source);
    }
    if (this.lineNumber != o.lineNumber) {
      return this.lineNumber - o.lineNumber;
    }
    return this.columnNumber - o.columnNumber;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @return the column
   */
  public int getColumnNumber() {
    return columnNumber;
  }

  /**
   * @return the line
   */
  public String getLine() {
    return line;
  }

  /**
   * @return the line
   */
  public int getLineNumber() {
    return lineNumber;
  }

  public String getMessage() {
    if (errorCodes != null && errorCodes.hasErrorCode(code)) {
      return errorCodes.getErrorCode(code).getMessage();
    } else {
      return "unknown";
    }
  }

  /**
   * @return the notes
   */
  public String[] getNotes() {
    return notes.toArray(new String[notes.size()]);
  }

  /**
   * @return the severity
   */
  public String getSeverity() {
    if (errorCodes != null && errorCodes.hasErrorCode(code)) {
      return errorCodes.getErrorCode(code).getSeverity();
    } else {
      return "unknown";
    }
  }

  /**
   * @return the source
   */
  public String getSource() {
    return source;
  }

  /**
   * @return the TLN
   */
  public String getTLN() {
    return TLN;
  }

  @Override
  public String toString() {
    Formatter formatter = new Formatter();
    formatter.format("%s:%d:%d:%s%n", source, lineNumber, columnNumber, code);
    formatter.format("  %s:%s%n", getSeverity(), getMessage());
    formatter.format("  near TLN %s%n", TLN);
    if (!line.equals("")) {
      formatter.format("  %s%n", line);
    }
    for (String note : notes) {
      formatter.format("    * %s%n", note);
    }
    return formatter.toString();
  }
}
