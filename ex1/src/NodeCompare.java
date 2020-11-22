package ex1.src;

import java.util.Comparator;

public class NodeCompare implements Comparator<node_info> {
    public int compare(node_info n1, node_info n2) 
    { 
        int ans = (int) (n2.getTag() - n1.getTag());
        
        if(ans < 0)
        	ans = 1;
        else if (ans > 0)
        	ans = -1;
        return ans;
    } 
}
