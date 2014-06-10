/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.nines.ise;

import ca.nines.ise.dom.Builder;
import ca.nines.ise.dom.DOM;
import ca.nines.ise.dom.DOMIterator;
import ca.nines.ise.node.Node;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 *
 * @author Michael Joyce <ubermichael@gmail.com>
 */
public class Abbrs {

  public static void main(String[] args) {

  Collection<File> fileList = null;  
  try {
      if (args.length == 0) {
        FileUtils fu = new FileUtils();
        File dir = new File("data/sgml");        
        SuffixFileFilter sfx = new SuffixFileFilter(".txt");
        fileList = FileUtils.listFiles(dir, sfx, TrueFileFilter.INSTANCE);
      } else {
        for(String name : args) {
          fileList = new ArrayList<>();
          fileList.add(new File(name));
        }
      }
      
      System.out.println("Found " + fileList.size() + " files to check.");
      
      Iterator fi = fileList.iterator();
      while(fi.hasNext()) {
        File in = (File) fi.next();
        DOM dom = new Builder(in).getDOM();
        DOMIterator i = dom.getIterator();
        while (i.hasNext()) {
          Node n = i.next();
          if (n.type() == "#ABBR") {
            System.out.println(n);
          }
        }
      }
    } catch (Exception ex) {
      Logger.getLogger(Abbrs.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
