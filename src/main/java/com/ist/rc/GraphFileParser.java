package main.java.com.ist.rc;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;


/*****************
  * Use createGraph to add vertexes and edges to graph passed as argument
  *  
  * How the vertexes/edges are created and how they are added is 
  * the responsibility of the factories used and also passed as arguments.
  *
  * This parser does not depend on any specific representation of vertexes,edges or graphs.
  *
  ******************
  * EXAMPLE:       *
  ******************
  *   if two file's segments that describe two different edges is, for instance:

  *   ---file.txt---
  *  >[Portugal][Spain]
  *  >[Spain][France]
  *  >
  *     --- end ---

  *   Being Portugal and Spain vertexes that are connected to each other:
  * 
  *   Then format in create Graph should be 
  *   String format = "[V][V]\n"
  *   
  *   Any information to ignore or extra is referred as "ignores" and is
  *   represented in the format as "I". ex. format = "[V]I[V]\n"
  *
  *  Don't forget "I" and "V" are UPPER CASE!!. And, to add the "\n" if it's case of it!
  *
******************* */



class GraphFileParser<V,E,G>{
  final String V = "V"; //how format defines a vertex
  final String I = "I"; //how format defines something to ignore/extra
  VertexFactory<V,G> vFactory;
  EdgeFactory<V,E,G> eFactory;
  G graph;

  /*
  * Arguments:
  *  _graph: graph with vertexes and edges to add
  *  _vFactory: factory that knows how to create and add vertexes to graph
  *  _eFactory: factory that knows how to create and add edges to graph
  *
  */
  public GraphFileParser(VertexFactory<V,G> _vFactory, EdgeFactory<V,E,G> _eFactory, G _graph){
    vFactory = _vFactory;
    eFactory = _eFactory;
    graph = _graph;
  }
 
  /**
    * Arguments:
    *   format: the general structure of an edge in a file
    *   filename: the file where the actual graph information is written
    *   addIgnores: whether we want to collect the Ignores
    *   linesToDelete: lines in the beggining of the file to dele  
    *
    * Return:
    *  Doesn't return anything. The graph passed in constructor has now
    *   the vertexes and edges described in the file "fileName"
    *
    * Semantics:         
    *  The fundamental unit of a file in this context is the segments which define an edge. 
    *  This function will iterate over them and send each one to splitVertex where the necessary
    *  information to create the vertexes and edges will be returned.
    *  It will then call the factories that will know how to use this information in order to
    *  complete the graph passed in the constructor of this class.
  */
  public void createGraph(String format, String fileName, Boolean addIgnores, int linesToDelete){

    String newRegex = Pattern.quote(format);
    //newRegex will capture the segments in fileName which describe the edges
    newRegex = newRegex.replaceAll("(?<!\\\\)"+V,"\\\\E[0-9a-zA-Z]*\\\\Q");
    newRegex = newRegex.replaceAll("(?<=\\\\)"+V,V);
    newRegex = newRegex.replaceAll("(?<!\\\\)"+I,"\\\\E.*\\\\Q");
    File fp = new File(fileName);
    try{
      Scanner scan = new Scanner(fp);
      for(int i=0; i<linesToDelete; i++){
        scan.nextLine();
      }
      String group = scan.findWithinHorizon(newRegex,0);
      while(group != null){
        ParserInfo info = splitIntoVertex(format,group,addIgnores); 
        V[] ve = vFactory.addVertex(graph,info);
        eFactory.addEdge(graph,info,ve);
        group = scan.findWithinHorizon(newRegex,0);
      }
      scan.close();
    } catch(FileNotFoundException e){
      System.out.println(e);
      System.exit(-1);
    }
  }

	public void createGraph(String format, String fileName){
  	createGraph(format, fileName, false);
  }

  public void createGraph(String format, String fileName, boolean addIgnores){
    createGraph(format, fileName, false,0);
  }


  /**
    * Arguments:
    *   format: the general structure of an edge in a file
    *   phrase: the current edge being read in file
    *   addIgnores: whether we want to collect the information 
    *               assigned with "I" in format
    *
    * Return:
    *   class with vertex identification and Ignores, if addIgnores == True
    *
    * Semantics:
    *   Each phrase describe an edge. We will iterate through the phrase
    *   using the general expression "format" passed, which describes the
    *   structure of the phrase, and attach all the information in an instance
    *   of the class ParseInfo.
    *
  **/
  public ParserInfo splitIntoVertex(String format, String phrase,boolean addIgnores){
    format = "|"+ format + "|";  //the algorithms knows when to stop by looking ahead and behind 
    phrase = "|"+ phrase + "|";  //this is to always have something to look ahead and behind

    int phraseIndex = 0;  
    int formatIndex = 0; 
    int vertexNumber = 0;
    StringBuilder[] vertex = new StringBuilder[2];
    ArrayList<StringBuilder> ignoreList = new ArrayList<StringBuilder>();
    vertex[0] = new StringBuilder();
    vertex[1] = new StringBuilder();
    char toMatch;

    while( phraseIndex < phrase.length()){ 
      //Escape character behind V and I are completely ignored
      if(format.charAt(formatIndex)=='\\' && (format.charAt(formatIndex+1)!=V.charAt(0) || format.charAt(formatIndex+1)!=I.charAt(0))){
        formatIndex++;
      }

      if(format.charAt(formatIndex)==V.charAt(0) && format.charAt(formatIndex-1)!='\\'){
        formatIndex++;
        toMatch = format.charAt(formatIndex); //All the information until this character is part of the vertex name
        while(phrase.charAt(phraseIndex) != toMatch){
          vertex[vertexNumber].append(phrase.charAt(phraseIndex++)); 
        }
        vertexNumber++; //O.K. we've read one vertex, next please...
      }
      if(format.charAt(formatIndex)==I.charAt(0) && format.charAt(formatIndex)!='\\'){ 
        formatIndex++;
        toMatch = format.charAt(formatIndex);
        StringBuilder newIgnore = new StringBuilder();
        if(addIgnores){ //do we want to collect this Ignore?
          ignoreList.add(newIgnore);
        }
        while(phrase.charAt(phraseIndex) != toMatch){ 
          if(addIgnores){ 
            newIgnore.append(phrase.charAt(phraseIndex));
          }
          phraseIndex++;
        }
      }
      phraseIndex++;
      formatIndex ++;
    }
    
    if(addIgnores){ 
      return new ParserInfo(vertex,addIgnores,ignoreList);
    }
    else{
      return new ParserInfo(vertex);
    }
  }

 /*
   * Class to store the information received in file.
   * Each instance belongs to each edge segment
   * 
   * Fields:
   *  vertex: strings captured in segment where the letter "V" is. 
   *          it is supposed to be the information relevant for each vertex
   *
   *  hasIgnores: whether we captured any Ignore information
   *
   *  ignoreList: information capture in segment where the letter "I" is.
   *              it's the ignores information
 */
  class ParserInfo{
      public StringBuilder[] vertex;
      public boolean hasIgnores;
      public ArrayList<StringBuilder> ignoreList;
      public ParserInfo(StringBuilder[] _vertex){
        vertex = _vertex;
        hasIgnores = false;
        ignoreList = null;
      }
      public ParserInfo(StringBuilder[] _vertex, boolean _hasIgnores, ArrayList<StringBuilder> _ignores){
        vertex = _vertex;
        hasIgnores = _hasIgnores;
        ignoreList = _ignores;
      }
      //*change to have more than one vertex*/
      public String toString(){
        StringBuilder v = new StringBuilder();
        for(int i=0; i<vertex.length; i++){
          v.append("v["+i+"]= " + vertex[i] +"\n");
        }
        if(hasIgnores){
          v.append("IGNORELIST:\n");
          for(StringBuilder i: ignoreList){
            v.append(i + "\n");
          }
        }
        return v.toString();
      }
  }
}

