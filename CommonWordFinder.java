/**
 * Name: Mahrukh Ali
 * Uni:ma4203
 * Date: December 16,2022
 */
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.util.*;


public class CommonWordFinder {

    private String fileName;
    static int unique_words = 0;

    /**
     * This method parses the file and places all words with their appropriate count in a map.
     * Whether the map be a hashmap, avl map or bst map. If the word is not in the map,
     * it is put in the map, if it is seen multiple times,then the value of the key(word) in the map
     * is increased by one everytime. Everytime a word that has been seen in the map appears the count of unique words
     * increased by one. Then a sorting algorithm is called to sort the map
     * by maximum word count as well as lexicographically and place it in an array. Lastly a toString method is called
     * to print the array that is sorted up to the limit that the user has provided and if the limit is more than what the array
     * length is then the whole array is displayed
     * @param fileName the file that is being parsed for unique words
     * @param map the type of map either bst, avl,hash  used to store the word as a key and the count of it as a value
     * @param limit the limit of how many words will be displayed from the array after it is sorted
     * @throws IOException an exception that occurs when a problem occurs parsing the file
     */
    public static void ParseIteration(String fileName, MyMap map, int limit) throws IOException {
        try {
            /**website that showed how to parse a file*/
            /**https://www.candidjava.com/tutorial/program-to-read-a-file-character-by-character*/
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            int c = 0;
            boolean no = true;
            StringBuilder word = new StringBuilder();
            int j=0;
            int d=0;
            while ((c=br.read())!=-1)
            {
                if (no == false) {
                    word = new StringBuilder();
                }
                char character = (char) c;
                if (Character.isLetter(character) || (character=='\'')){
                    word.append(character);
                    no=true;
                    j+=1;
                }

                else if (character == '-' && word.length()>0){
                    word.append((char) character);
                    no = true;
                    j += 1;
                }
               else if ((character == ' ') || (character == '\n') || (character == '\t') || (character == '\r')){
                        String theWord = word.toString();
                        theWord = theWord.toLowerCase();
                        no = false;
                        if (map.get(theWord) == null ) {
                            if (theWord.length() > 0) {
                                map.put(theWord, 1);
                                unique_words++;
                            }
                        } else{
                            int value = (int) map.get(theWord);
                            value += 1;
                            map.put(theWord, value);
                        }
                }
               else{

                   int a=0;
                }
            }
            if(word!=null){
                String theWord = word.toString();
                theWord = theWord.toLowerCase();
                no = false;
                if (map.get(theWord) == null ) {
                    if (theWord.length() > 0) {
                        map.put(theWord, 1);
                        unique_words++;
                    }
                } else{
                    int value = (int) map.get(theWord);
                    value += 1;
                    map.put(theWord, value);
                }
            }
            Pair<String, Integer>[] words = new Pair[unique_words];
            int i = 0;
            Entry<String, Integer> max;
                while (i <unique_words) {
                    max=sort(map);
                    words[i] = new Pair<>(max.key, max.value);
                    map.remove(max.key);
                    i++;
                }

            soToString(limit, words);
        }
        catch(IOException e){
            throw new IOException(e);
        }
    }
    
    /**
     * This method finds the key that has the maximum value in the map. If there are multiple maximum values since it can be
     * equal, then they are compared by their keys and chosen based on lexicographically
     * @param map avl,hash or bst map the key is a string and the value is an integer
     * @return  <String,Integer> which has the maximum value in the map
     */
    public static Entry<String, Integer> sort(MyMap map){
        int z=0;
        Iterator<Entry<String, Integer>> iterator=map.iterator();
        Entry<String, Integer> originalWord= iterator.next();
        Entry<String, Integer> max= originalWord;
        /** Used this example to create my iterator*/
        /**https://www.javatpoint.com/how-to-iterate-map-in-java*/
        while (iterator.hasNext()) {
            originalWord=iterator.next();
            if (originalWord.value > max.value) {
                max=originalWord;
            }
            else if(originalWord.value==max.value){
                if(originalWord.key.compareTo(max.key)<0){
                    max = originalWord;
                }
            }
        }
        return max;
    }

    /**
     * This method is the toString method, which prints the array based off the limit of the words the user wants printed.
     * The number of unique words are printed first and the format of how the words and their count should be printed.
     * @param limit the amount of words the user wants to display
     * @param words the array that has been sorted based off highest to lowest as well as lexicographically
     */
    public static void soToString(int limit,Pair<String, Integer>[] words){
        int j=1;
        System.out.println("Total unique words: " + getUnique());
        int theLimit=limit;
        if (words.length<limit){
            theLimit=words.length;
        }
        String n="";
        String find= Integer.toString(theLimit);
        String theFind= Integer.toString(find.length());
        find= "%"+ theFind + "d";
        int max=0;
        int tab = 1;
        for(int s=0;s<theLimit;s++){
            if(words[s].key.length()>max){
                max=words[s].key.length();
            }
        }
        for (int i=0; i<theLimit;i++){
            System.out.printf(find,j);
            System.out.print(". " + words[i].key);
            if(words[i].key.length()<max){
                 tab=max-words[i].key.length();
            }
            String space=" ";
            space=space.repeat(tab+1);
            if(words[i].key.length()==max){
                space=" ";
            }
            System.out.print(space + words[i].value + System.lineSeparator());
            j++;
        }
    }

    /**
     * the count of unique words in the file
     * @return unique words - the count of unique words in the file
     */
    public static int getUnique() {
        return unique_words;
    }

    public static void main (String[]args) throws IOException {
        try {
            int limit = 0;
            String filename = args[0];
            if (args.length == 1) {
                System.err.println("Usage: java CommonWordFinder <filename> <bst|avl|hash> [limit]");
                System.exit(1);
            } else {
                Path path = Files.createTempFile(args[0], ".txt");
                boolean exists = Files.exists(path);
                if (exists != true) {
                    System.err.println("Error: Cannot open file " + args[0] + " for input. ");
                    System.exit(1);
                } else if (args[1].equals("bst") || args[1].equals("avl") || args[1].equals("hash")) {
                    if (args.length == 2) {
                        limit = 10;
                    }
                    else {
                        int number = Integer.parseInt(args[2]);
                        if (number < 0) {
                            System.err.println("Error: Invalid limit '" + args[2] + "' received.");
                            System.exit(1);
                        }
                        limit = number;
                    }

                }
                else{
                    System.err.println("Error: Invalid data structure " + args[1] + " received.");
                    System.exit(1);
                }

                MyMap<String, Integer> map;
                String fileName = args[0];
                if (args[1].equals("bst")) {
                    map = new BSTMap<>();

                } else if (args[1].equals("avl")) {
                    map = new AVLTreeMap<>();
                } else {
                    map = new MyHashMap<>();
                }
                ParseIteration(fileName, map, limit);
            }
        }
        catch(IOException e){
            System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
            System.exit(1);
        }
    }
}





