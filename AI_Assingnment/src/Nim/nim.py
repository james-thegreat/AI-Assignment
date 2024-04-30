def game_state():
    return [1, 3, 5, 7]

def evaluate(state):
    return sum(state)

def minimax(state, depth, alpha, beta, maximizing_player):
    if depth == 0 or is_terminal(state):
        return evaluate(state)
    
    if maximizing_player:
        max_eval = float('-inf')
        for child in get_children(state):
            eval = minimax(child, depth - 1, alpha, beta, False)
            max_eval = max(max_eval, eval)
            alpha = max(alpha, eval)
            if beta <= alpha:
                break
        return max_eval
    else:
        min_eval = float('inf')
        for child in get_children(state):
            eval = minimax(child, depth - 1, alpha, beta, True)
            min_eval = min(min_eval, eval)
            beta = min(beta, eval)
            if beta <= alpha:
                break
        return min_eval
    
def minimax_limited(state, depth, maximizing_player):
    if depth == 0 or is_terminal(state):
        return evaluate(state)

    if maximizing_player:
        max_eval = float('-inf')
        for child in get_children(state):
            eval = minimax_limited(child, depth - 1, False)
            max_eval = max(max_eval, eval)
        return max_eval
    else:
        min_eval = float('inf')
        for child in get_children(state):
            eval = minimax_limited(child, depth - 1, True)
            min_eval = min(min_eval, eval)
        return min_eval
    
def is_terminal(state):
    return sum(state) == 0

def get_children(state):
    children = []
    for i in range(len(state)):
        for x in range(1, state[i] + 1):
            child = state.copy()
            child[i] -= x
            children.append(child)
    return children

state = game_state()

def display_game_state(state):
    print("Current Game State:")
    for i in range(len(state)):
        print(" " * (len(state) - i - 1) + " ".join("I" * state[i]) + " " * (len(state) - i - 1))
    print("\n")

def get_user_move(state):
    while True:
        try:
            heap = int(input("Choose a heap (0-3): "))
            stones = int(input("How many stones to take (1-7): "))
            if 0 <= heap < len(state) and 1 <= stones <= state[heap]:
                return heap, stones
            else:
                print("Invalid move. Try again.")
        except ValueError:
            print("Invalid input. Try again.")

def play_game():
    state = game_state()
    display_game_state(state)

    while not is_terminal(state):
        if sum(state) % 2 == 0: # User's turn
            heap, stones = get_user_move(state)
            state[heap] -= stones
            display_game_state(state)
        else: # Computer's turn
            best_move = minimax_limited(state, 3, True) # Assuming the computer is maximizing
            # still need to implement a way to translate the best_move value back into a heap and stones here
            # For simplicity, assume that the computer always takes 1 stone from the first heap
            state[0] -= 1
            display_game_state(state)

    if sum(state) == 0:
        print("It's a draw!")
    else:
        print("Player wins!") if sum(state) % 2 == 0 else print("Computer wins!")

play_game() 
#test git
#test git