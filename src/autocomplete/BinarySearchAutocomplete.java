package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    /**PURPOSE: Adds all CharSequences in terms to this.terms
     * @params = Collection terms - contains all the terms that will be stored in this.terms
     * @throws = IllegalArgumentException() if terms is null
     **/
    public void addAll(Collection<? extends CharSequence> terms) {
        if (terms == null) {
            throw new IllegalArgumentException();
        }
        this.terms.addAll(terms);
        Collections.sort(this.terms, CharSequence::compare);
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    /**
     * PURPOSE: Returns a List containing all the terms stored that start with the given prefix
     * @params = CharSequence prefix - the prefix we are trying to find words that start with
     **/
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        int indexFound = binarySearch(0, terms.size(), prefix);
        // indexFound is merely one matching word, must iterate forward and backward
        // from there to find all matching words
        if (indexFound != -1) {
            int down = indexFound;
            while (down >= 0
                   && prefix.length() <= terms.get(down).length()
                   && terms.get(down).subSequence(0, prefix.length()).equals(prefix)) {
                result.add(terms.get(down));
                down --;
            }
            int high = indexFound + 1;
            while (high <= terms.size()
                   && prefix.length() <= terms.get(high).length()
                   && terms.get(high).subSequence(0, prefix.length()).equals(prefix)) {
                result.add(terms.get(high));
                high ++;
            }
        }
        return result;
    }

    /**
     * PURPOSE: Returns the index of the first index of a matching word that can be found.
     *          Returns -1 if this.terms doesn't contain any
     * @params = int low  - bottom index of subsection of this.terms we are searching in
     * @params = int high - top index of subsection of this.terms we are searching in
     * @params = CharSequence prefix - prefix we are trying to find matching word for
     **/
    private int binarySearch(int low, int high, CharSequence prefix) {
        while (low <= high) {
            int mid = (high + low) / 2;
            int compare;
            while(prefix.length() > terms.get(mid).length()) {
                mid += 1;
            }
            compare = CharSequence.compare(prefix, terms.get(mid).subSequence(0, prefix.length()));
            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                high = mid - 1;
            } else {
                low  = mid + 1;
            }
        }
        return -1;
    }
    // andrew's comment
}
