package com.purpleio.purpleio.SelectionTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
    테스트 3 : 괄호 유효성 체크
    **'(', ')', '{', '}', '[' 및 ']' 문자만 포함된 문자열 s가 주어졌을 때, 입력 문자열이 유효한지 판단하세요.**

    입력 문자열은 다음과 같은 경우 유효합니다.

    - 열린 괄호는 같은 유형의 괄호로 닫아야 합니다.
    - 열린 괄호는 올바른 순서로 닫아야 합니다.
    - 모든 닫는 괄호에는 같은 유형의 열린 괄호가 있어야 합니다.

    - `1 <= s.length <= 104`
    - 괄호의 종류는 `'()[]{}'`만 존재합니다.


 */
public class Test3 {

    private static Map<Character, Character> parenthesesMap = new HashMap<>();
    static {
        parenthesesMap.put('{', '}');
        parenthesesMap.put('[', ']');
        parenthesesMap.put('(', ')');
    }


    public static boolean solution(String str){

        Stack<Character> st = new Stack<>();

        for(int i=0; i<str.length(); i++){
            char tempC = str.charAt(i);
            // 열리는 괄호면 push
            if(parenthesesMap.containsKey(tempC)) st.push(tempC);
            else {
                // 닫힌 괄호면서 짝이 맞지 않은경우, 혹은 닫힌 괄호가 먼저 나온 경우
                if(st.isEmpty() || tempC!=parenthesesMap.get(st.pop())) return false;
            }
        }

        return st.empty();
    }

    public static void main(String[] args) {

        String str = "()";

        System.out.println(solution(str));
    }
}
