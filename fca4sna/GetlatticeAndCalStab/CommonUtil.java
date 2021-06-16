package fca4sna.GetlatticeAndCalStab;
import java.util.HashSet;
public class CommonUtil {
    public static HashSet<String> judgeSpecialConcept(String newSpc2,HashSet<String> hashSet){
        boolean flag = true;
        for (String attr:hashSet
        ) {
            if (attr.contains(newSpc2)){
                flag = false;
            }
        }
        if (flag){
            hashSet.add(newSpc2);
        }
        return hashSet;
    }
}
