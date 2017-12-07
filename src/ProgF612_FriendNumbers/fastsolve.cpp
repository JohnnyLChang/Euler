#include "euler.h"
const ll base = 1000267129LL;
const ll n = 1000000000000000000LL, m = 18;
const ll inv2 = 500133565LL;
ll R[20][1024], T[1024];
ll ans;
int main()
{
	for (int k = 1; k <= 9; k++) R[1][1 << k] = 1;
	for (int i = 1; i < m; i++)
	{
		for (int s1 = 0; s1 < 1024; s1++)
		{
			for (int k = 0; k <= 9; k++)
			{
				int s2 = s1 | (1 << k);
				R[i + 1][s2] = mod(R[i + 1][s2] + R[i][s1], base);
			}
		}
	}
	for (int i = 1; i <= m; i++)
		for (int s = 0; s < 1024; s++)
			T[s] = mod(T[s] + R[i][s], base);
	for (int s1 = 0; s1 < 1024; s1++)
	{
		for (int s2 = 0; s2 < 1024; s2++)
			if (s1&s2) ans = mod(ans + T[s1] * T[s2], base);
		ans = mod(ans + T[s1], base);
	}
	ans = mod(ans*inv2 - (n - 1), base);
	printf("%lld\n", ans);
	print_time();
	return 0;
}
