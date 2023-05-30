import java.util.ArrayList;
import java.util.*;

public class SetsClass {
    public SetsClass() {
        ArrayList<HashSet> arrayList = new ArrayList<HashSet>();
        int ran = (int)(Math.random()*13)+2;
        for(int i = 0; i < ran; i++) {
            HashSet<Integer> hashSet = new HashSet<Integer>();
            for(int j = 0; j < 10; j++) {
                int r = (int)(Math.random()*20)+1;
                hashSet.add(r);
            }
            arrayList.add(hashSet);
        }

        System.out.println(arrayList);
        
    }

    public Set<Integer> intersection(Set<Integer> s1, Set<Integer> s2) {
        Iterator<Integer> it = s1.iterator();
        String s = "";
        while(it.hasNext()){
            s += it.next();
        }
        String [] common = s.split("");
        Set<Integer> same = new HashSet<Integer>();

        for(int i = 0; i < common.length; i++) {
            if(s1.contains(Integer.parseInt(common[i])) && s2.contains(Integer.parseInt(common[i]))) {
                same.add(Integer.parseInt(common[i]));
            }
        }
        return same;
    }

    public Set<Integer> union(Set<Integer> s1, Set<Integer> s2){
        Set<Integer> un = new HashSet<Integer>();
        Set<Integer> set = intersection(s1, s2);
        Iterator<Integer> it = set.iterator();
        while(it.hasNext()) {
            if(!s1.contains(it.next()) && !s2.contains(it.next())) {
                un.add(it.next());
            }
        }
        return un;
    }

    public Set<Integer> intersectionEvens(Set<Integer> s1, Set<Integer> s2) {
        Set<Integer> set = intersection(s1, s2);
        Set<Integer> evens = new HashSet<Integer>();
        Iterator<Integer> it = set.iterator();
        while(it.hasNext()) {
            if(it.next()%2 == 0) {
                evens.add(it.next());
            }
        }
        return evens;
    }

    public Set<Integer> unionEvens(Set<Integer> s1, Set<Integer> s2) {
        Set<Integer> set = union(s1, s2);
        Set<Integer> evens = new HashSet<Integer>();
        Iterator<Integer> it = set.iterator();
        while(it.hasNext()) {
            if(it.next()%2 == 0) {
                evens.add(it.next());
            }
        }
        return evens;
    }
    public static void main(String[]args) {
        SetsClass app = new SetsClass();
    }
}