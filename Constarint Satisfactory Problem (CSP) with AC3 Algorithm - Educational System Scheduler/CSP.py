from queue import Queue

global tedad_class


def tadakhol(class1, class2):
    s1 = class1[0]
    s2 = class2[0]
    f1 = class1[1]
    f2 = class2[1]
    if (s1 < s2 < f1) or (s1 < f2 < f1) or (s1 == s2 and f1 == f2):
        return True
    return False


def backtracking_search(csp, assignment):
    return recursive_backtracking(assignment, csp)


def recursive_backtracking(assignment, csp):
    AC_3(csp)
    if complete_assignment(assignment):
        return assignment
    variable = 0
    for i in range(tedad_class):
        if assignment[i] == []:
            variable = i
            break
    for value in domain[variable]:
        if value_is_consistent_with_assignment(
                csp,assignment, value, variable):
            assignment[variable] = value
            result = recursive_backtracking(
                assignment, csp)
            if result != False:
                return result
            assignment[variable] = []
    return False


def value_is_consistent_with_assignment(csp,
            assignment, value, variable):
    for i in range(variable):
        if assignment[i] == value:
            shomare_classe_ostad = i
            if csp[shomare_classe_ostad].__contains__(variable):
                return False
    return True


def AC_3(csp):
    # araye ye arc ha
    queue = Queue()
    for i in range(len(csp)):
        for j in range(len(csp[i])):
            Xi = i
            Xj = csp[i][j]
            queue.put((Xi, Xj))
    while not queue.empty():
        arc = queue.get()
        if remove_inconsistent_values(arc):
            for Xk in csp[Xi]:
                queue.put((Xk, Xi))


def remove_inconsistent_values(arc):
    removed = False
    Xi, Xj = arc[0], arc[1]
    to_be_omitted=[]
    for x in domain[Xi]:
        if allow_other_values(Xj, x) == False:
            to_be_omitted.append(x)
            removed = True
    for i in range(len(to_be_omitted)):
        domain[Xi].remove(to_be_omitted[i])
    return removed


def allow_other_values(Xj, x):
    count = 0
    for i in range(len(domain[Xj])):
        if domain[Xj][i] != x:
            count += 1
    if count == 0:
        return False
    return True


def complete_assignment(assignment):
    for i in range(tedad_class):
        if assignment[i] == []:
            return False
    return True





tedad_class, tedad_ostad = map(int, input().split())
zamane_har_class = [[] for i in range(tedad_class)]
clashaye_har_ostad = [[] for i in range(tedad_ostad)]
# araye ye tadakholha
csp = [[] for i in range(tedad_class)]
# dar vaqe mige baraye har class che ostada ee mishe beran
domain = [[] for i in range(tedad_class)]
assignment = [[] for i in range(tedad_class)]

for i in range(tedad_class):
    l = input().split('-')
    arr = l[0].split(":")
    start = arr[0] + '.' + arr[1]
    start = float(start)
    arr = l[1].split(":")
    finish = arr[0] + '.' + arr[1]
    finish = float(finish)
    zamane_har_class[i].append(start)
    zamane_har_class[i].append(finish)

for i in range(tedad_ostad):
    l = input()
    arr = l.split()
    for j in range(len(arr)):
        clashaye_har_ostad[i].append(int(arr[j]) - 1)

for i in range(tedad_class):
    for j in range(tedad_class):
        if i != j and tadakhol(zamane_har_class[i], zamane_har_class[j]):
            csp[i].append(j)

for i in range(tedad_ostad):
    for j in range(len(clashaye_har_ostad[i])):
        domain[clashaye_har_ostad[i][j]].append(i)

arr = backtracking_search(csp, assignment)
flag = True
if (type(arr) == bool):
    print("NO")
else:
    for i in range(tedad_class):
        print(arr[i] + 1)
