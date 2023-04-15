
package idiot.game.elements;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CardComparator implements Comparator<Card> {

    /* state attributes */

    private Map<Character, Integer> priorityValues = new HashMap<>(); // priority values for comparing suits

    /* constructors */

    public CardComparator() {

        // (suit -> sorting priority value) dictionary
        priorityValues.put('S', 0);
        priorityValues.put('H', 1);
        priorityValues.put('D', 2);
        priorityValues.put('C', 3);

    }

    /* the required compare method */
    
    public int compare(Card x, Card y) {

        int[] faces = {x.getFace(), y.getFace()};

        // when comparing the hands of two players
        for (int i = 0; i < faces.length; i++) {

            // give aces and 2s a higher priority value
            if (faces[i] == 1 || faces[i] == 2) faces[i] += 13;

        }

        // face comparison
        int facePriority = faces[0] - faces[1];

        // negative -> set x in front of y
        if (facePriority != 0)
        return facePriority;

        else { // faces are the same, compare suits instead

            // suit comparison
            int suitPriority = this.priorityValues.get(x.getSuit()) - this.priorityValues.get(y.getSuit());

            // negative -> set x in front of y, zero -> the cards are equal
            return suitPriority;

        }
    }
}
