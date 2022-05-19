def have_child(parent, variable_evidence, adj, n):
    a = []
    arr = all_descendants(parent, n, adj, a)
    for child in arr:
        if variable_evidence.__contains__(child):
            return True


def all_descendants(node, n, adj, descendants):
    for i in range(len(adj[node])):
        descendants.append(adj[node][i])
        all_descendants(adj[node][i], n, adj, descendants)
    return descendants


def check_path(masir, variable_evidence, adj):
    for i in range(len(masir) - 2):
        a, b, c = masir[i], masir[i + 1], masir[i + 2]
        if (adj[a].__contains__(b) and adj[b].__contains__(c)) or (adj[c].__contains__(b) and adj[b].__contains__(a)):
            if variable_evidence.__contains__(b):
                return "inactive"
        # hat
        if adj[b].__contains__(a) and adj[b].__contains__(c):
            if variable_evidence.__contains__(b):
                return "inactive"
        # V
        if adj[a].__contains__(b) and adj[c].__contains__(b):
            if not (variable_evidence.__contains__(b) or have_child(b, variable_evidence, adj, n)):
                return "inactive"
    return "active"


n, m = map(int, input().split())
adj = [[] for i in range(n)]
for i in range(m):
    u, v = map(int, input().split())
    u, v = u - 1, v - 1
    adj[u].append(v)
q = int(input())
for i in range(q):
    masir = []
    variable_evidence = []
    temp = input().split(" ")
    for j in range(len(temp)):
        masir.append(int(temp[j]) - 1)
    str = input()
    if str == "none":
        variable_evidence = []
    else:
        temp = str.split(" ")
        for j in range(len(temp)):
            variable_evidence.append(int(temp[j]) - 1)
    print(check_path(masir, variable_evidence, adj))
