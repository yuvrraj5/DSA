class Solution:
    def convert(self, s: str, numRows: int) -> str:
        if numRows == 1 or numRows >= len(s):
            return s

        rows = [""] * numRows
        curr_row = 0
        going_down = False

        for ch in s:
            rows[curr_row] += ch

            if curr_row == 0 or curr_row == numRows - 1:
                going_down = not going_down

            if going_down:
                curr_row += 1
            else:
                curr_row -= 1

        return "".join(rows)