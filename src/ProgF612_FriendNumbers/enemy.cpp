#include <assert.h>
#include <stdint.h>
#include <iostream>
#include <vector>

const uint64_t M = 1000267129;
const uint64_t HALF = 500133565;
static_assert(2 * HALF % M == 1, "");

uint64_t factorial(unsigned n) {
    return n == 0 ? 1 : n * factorial(n - 1) % M;
}

uint64_t modpow(uint64_t base, uint64_t pow, uint64_t mod) {
    uint64_t result = 1;
    for (unsigned i = 0; i < pow; ++i) {
        result *= base;
        result %= mod;
    }
    return result;
}

uint64_t solve(uint64_t NPOW) {
    uint64_t N = modpow(10, NPOW, M);
    uint64_t TN[10] = {0};
    std::vector<uint64_t> prev(10);
    prev[0] = TN[0] = 1;
    for (unsigned n = 1; n <= NPOW; ++n) {
        std::vector<uint64_t> next(10);
        for (unsigned k = 1; k <= 9; ++k) {
            next[k] = (k*prev[k] + prev[k-1]) % M;
            TN[k] += next[k];
            TN[k] %= M;
        }
        prev = std::move(next);
    }

    uint64_t UN[10] = {0};
    for (unsigned k = 0; k < 9; ++k) {
        UN[k] = k*TN[k+1];
    }

    uint64_t enemies = 0;
    for (uint64_t k1 = 1; k1 < 9; ++k1) {
        for (uint64_t k2 = 1; k2 <= 9 - k1; ++k2) {
            uint64_t A = factorial(9) / factorial(9 - k1 - k2) % M;
            enemies += A * TN[k1] % M * TN[k2] % M;
            enemies += A * TN[k1] % M * UN[k2] % M;
            enemies += A * UN[k1] % M * TN[k2] % M;
            enemies %= M;
        }
    }

    uint64_t allpairs = (N-1+M) * (N-2+M) % M;
    return (allpairs - enemies + M) * HALF % M;
}

int main() {
    for (unsigned pow : {2, 18, 1000, 10000, 100000, 1000000, 1000000000}) {
        std::cout << "f(10^" << pow << ") mod " << M
            << " = " << solve(pow) << '\n';
    }
}
