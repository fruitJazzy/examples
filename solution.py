
"""
Задача: missing_number_in_progression https://github.com/Hexlet/battle_asserts/tree/master/src/battle_asserts/issues/missing_number_in_progression.clj
Given an array that represents elements of an arithmetic progression in order.
One element is missing in the progression. Return the missing number.
"""
def solution(a):
    return (a[0] + a[-1]) / 2 * (len(a) + 1) - sum(a)

l = [1,3,7,9,11,13]

print(solution(l))
