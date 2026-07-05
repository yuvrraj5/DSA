class Solution {
    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0;
        int maxLength = 0;
        
        HashSet<Character> set = new HashSet<>();
        
        while (right < s.length()) {
            char ch = s.charAt(right);
            
            if (!set.contains(ch)) {
                set.add(ch);
                maxLength = Math.max(maxLength, right - left + 1);
                right++;
            } else {
                set.remove(s.charAt(left));
                left++;
            }
        }
        
        return maxLength;
    }
}