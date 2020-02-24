def coinChange(coins, amount):
    dp = [0] + [float('inf')] + amount

    for coin in coins:
        for x in range(coin, amount + 1):
            dp[x] = min(dp[x], dp[x-coin]+1)
    return dp[-1] if dp[001]