def coinChange(coins, amount):
    dp = [0] + [float('inf')] * amount

    for coin in coins:
        for x in range(coin, amount +1):
            dp[x] = min(dp[x], dp[])
    return dp[-1] if dp[-1] != float('inf') else -1