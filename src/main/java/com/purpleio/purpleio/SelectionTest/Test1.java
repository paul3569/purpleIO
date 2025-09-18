package com.purpleio.purpleio.SelectionTest;


import java.util.HashMap;
import java.util.Map;

/*
    테스트 1 : 로마 숫자를 정수로
    로마 숫자는 일곱 가지 기호로 표시됩니다: I, V, X, L, C, D, M

    ````
    Symbol       Value
    I             1
    V             5
    X             10
    L             50
    C             100
    D             500
    M             1000
    ```
    예를 들어, 2는 로마 숫자로 II로 표기되며, 두 개를 더하면 됩니다. 12는 XII로 표기하는데,
    간단히 X + II입니다. 숫자 27은 XXVII로 쓰는데, XX + V + II입니다.

    로마 숫자는 일반적으로 왼쪽에서 오른쪽으로 가장 큰 것부터 가장 작은 것까지 적습니다. 그러나 4는 IIII가 아닙니다.
    대신 숫자 4는 IV로 표기됩니다. 1이 5 앞에 오기 때문에 빼면 4가 되기 때문입니다. 숫자 9도 같은 원리가 적용되어 IX로 표기됩니다.
    뺄셈이 사용되는 경우는 6가지가 있습니다:

    - I를 V(5)와 X(10) 앞에 놓아 4와 9를 만들 수 있습니다.
    - X는 L(50)과 C(100) 앞에 놓아 40과 90을 만들 수 있습니다.
    - C는 D(500)와 M(1000) 앞에 배치하여 400과 900을 만들 수 있습니다.

    - `1 <= s.length <= 15`
    - `s` 는 로마 숫자 문자열로만 이루어져 있습니다.`('I', 'V', 'X', 'L', 'C', 'D', 'M')`.
    - `s` 는 범위 `[1, 3999]` 사이의 유효한 숫자입니다.
 */
public class Test1 {

    private static Map<Character, Integer> romanMap = new HashMap<>();
    static {
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);
    }

    public static int solution(String str){

        int result = 0;

        for (int i = 0; i < str.length(); i++) {
            int currentVal = romanMap.get(str.charAt(i));
            // 다음 문자가 존재하고 현재 값이 다음 값보다 작으면 뺄셈 처리
            if (i < str.length() - 1 && currentVal < romanMap.get(str.charAt(i + 1))) {
                result -= currentVal;
            } else {
                result += currentVal;
            }
        }
        return result;

    }

    public static void main(String[] args) {
        String[] tests = {"II", "XII", "XXVII", "IV", "IX", "XL", "XC", "CD", "CM", "MCMXCIV"};
        for (String test : tests) {
            System.out.println(test + " -> " + solution(test));
        }
    }


}
