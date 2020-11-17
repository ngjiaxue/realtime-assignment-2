package assignment2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * This class is for storing methods.
 *
 * @author Ng Jia Xue
 */
public class Methods {

    /**
     * This method is to scrape from GitHub Wiki Page.
     *
     * @param http      URL link to scrape data from.
     * @param className Class name for elements to scrape.
     * @param index     Total index to be remove when received scraped data from websites.
     * @return Matric and name in ArrayList.
     */
    public static ArrayList<Data> scrapeFromSource(String http, String className, int index) {
        ArrayList<Data> arrayList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(http).get();
            Elements elements = doc.select(className);
            String matric, name;

            /* parameter index being used to delete unused rows */
            if (index > 0) {
                elements.subList(0, index).clear();
            }

            for (Element element : elements) {
                String[] tempArray = refactor(element.text().split(" "));
                matric = tempArray[0];
                name = tempArray[1];
                /* if parameter removeWord is true add matric, name and link else add matric and name only to ArrayList<Data> */
                arrayList.add(new Data(matric, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* return the ArrayList<Data> obtained */
        return arrayList;
    }

    /**
     * This method is to check the frequencies of a character that occurs in name.
     *
     * @param name The name of the student.
     * @return The frequencies of a character that occurs more than 3 times.
     */
    public static String[] checkFrequency(String name) {
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            map.merge(c, 1, Integer::sum);
        }

        map.entrySet().stream().sorted(Collections.reverseOrder(comparingByValue())).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        map.values().removeIf(value -> value < 3);

        final String[] temp = {""};
        map.forEach((Character, Integer) -> temp[0] += Character + " = " + Integer + "\n");

        return temp;
    }

    /**
     * This method is to refactor the scraped text into array[2] where array[0] = matric,  array[0] = name.
     *
     * @param splitted Splitted text where .split(" ") was called and store in array.
     * @return array[2] to split matric and name perfectly.
     * <p>
     * example:  splitted[0] = 1
     * splitted[1] = 123456
     * splitted[2] = Aurthur
     * splitted[3] = Boy
     * splitted[4] = Cat
     *
     * splittedText[0] = 123456
     * splittedText[1] = Aurthur Boy Cat
     * </p>
     */
    private static String[] refactor(String[] splitted) {
        String[] splittedText = {"", ""};
        splittedText[0] = splitted[1];

        for (int i = 2; i < splitted.length; i++) {
            splittedText[1] += splitted[i] + (i < splitted.length - 1 ? " " : "");
        }

        return splittedText;
    }
}

