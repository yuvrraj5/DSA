import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) -> {
            if (nums[a] != nums[b]) {
                return Integer.compare(nums[a], nums[b]);
            }
            return Integer.compare(a, b);
        });

        int[] pos = new int[n];
        int[] val = new int[n];

        for (int i = 0; i < n; i++) {
            pos[order[i]] = i;
            val[i] = nums[order[i]];
        }

        // Connected Components
        int[] comp = new int[n];
        int cid = 0;
        comp[0] = 0;

        for (int i = 1; i < n; i++) {
            if (val[i] - val[i - 1] > maxDiff) {
                cid++;
            }
            comp[i] = cid;
        }

        // next[i] = furthest node reachable in one edge
        int[] next = new int[n];
        int r = 0;

        for (int i = 0; i < n; i++) {
            if (r < i) {
                r = i;
            }

            while (r + 1 < n && val[r + 1] - val[i] <= maxDiff) {
                r++;
            }

            next[i] = r;
        }

        int LOG = 18;
        int[][] up = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {

            int u = queries[qi][0];
            int v = queries[qi][1];

            if (u == v) {
                ans[qi] = 0;
                continue;
            }

            int l = pos[u];
            int rr = pos[v];

            if (l > rr) {
                int temp = l;
                l = rr;
                rr = temp;
            }

            if (comp[l] != comp[rr]) {
                ans[qi] = -1;
                continue;
            }

            int cur = l;
            int steps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < rr) {
                    cur = up[k][cur];
                    steps += (1 << k);
                }
            }

            ans[qi] = steps + 1;
        }

        return ans;
    }
}