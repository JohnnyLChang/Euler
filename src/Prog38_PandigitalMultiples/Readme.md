# Prog 38

檢查PanDigits的方法很有效率,不用一直想到用陣列

```=java
private boolean isPandigital(long n) {
        int digits = 0;
        int count = 0;
        int tmp;

        while (n > 0) {
            tmp = digits;
            //The minus one is there to make 1 fill the first bit and so on
            digits = digits | 1 << (int)((n % 10) - 1); 

          //Check to see if the same digit is found multiple times
            if (tmp == digits) { 
                return false;
            }

            count++;
            n /= 10;
        }

        return digits == (1 << count) - 1;
    }

```

問題本身是找到大的數值,因此只要從最大可行解9387往前即可